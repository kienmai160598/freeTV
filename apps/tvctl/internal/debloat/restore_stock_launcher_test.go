package debloat

import (
	"encoding/json"
	"os"
	"slices"
	"strings"
	"testing"

	"github.com/kienmai160598/freeTV/apps/tvctl/internal/tv"
)

const (
	launcherx       = "com.google.android.apps.tv.launcherx"
	verizon         = "com.verizon.tv.launcher"
	verizonHome     = verizon + "/.HomeActivity"
	setHomeActivity = "cmd package set-home-activity --user 0 "
)

func homeCommandsIn(sent []string) []string {
	var homes []string
	for _, command := range sent {
		if strings.HasPrefix(command, "cmd package set-home-activity") {
			homes = append(homes, command)
		}
	}

	return homes
}

func singleLine(text string) string {
	return strings.Join(strings.Fields(text), " ")
}

func TestAStockLauncherTheFactoryDisabledStaysOff(t *testing.T) {
	w := newWorld(t, "com.example.enabled")
	f := newTV()
	f.installed = []string{"com.example.enabled", stock, verizon}
	f.disabled = []string{stock}
	f.home = verizonHome
	must(t, w.disable(f, DisableOptions{}))
	if state := w.loadRecord().Packages[stock]; state != stateDisabled {
		t.Fatalf("the unlisted stock launcher is recorded as %q", state)
	}

	r := w.restore(f)
	must(t, r)
	if slices.Contains(r.sent, "pm enable "+stock) {
		t.Error("turned on a stock launcher that was off before cleanup")
	}

	homes := homeCommandsIn(r.sent)
	if !slices.Equal(homes, []string{setHomeActivity + verizonHome}) {
		t.Errorf("home commands %v", homes)
	}
}

func TestUndoingASecondListLeavesThisProjectsLauncherOnHome(t *testing.T) {
	w := newWorld(t, "com.example.enabled")
	f := newTV()
	f.installed = []string{"com.example.enabled", stock, custom}
	f.disabled = []string{stock}
	f.home = tv.LauncherActivity
	f.claims = []string{custom}
	must(t, w.disable(f, DisableOptions{}))
	r := w.restore(f)
	must(t, r)
	if slices.Contains(r.sent, "pm enable "+stock) {
		t.Error("turned on the stock launcher install had disabled")
	}

	homes := homeCommandsIn(r.sent)
	if !slices.Equal(homes, []string{setHomeActivity + tv.LauncherActivity}) {
		t.Fatalf("home commands %v", homes)
	}

	enable := slices.Index(r.sent, "pm enable "+custom)
	if enable < 0 || enable > slices.Index(r.sent, homes[0]) {
		t.Errorf("this project's launcher is enabled before it is made Home: %v", r.sent)
	}

	if !strings.Contains(singleLine(r.warn), stock+" was disabled then") {
		t.Errorf("warnings: %s", r.warn)
	}
}

func TestOnlyInstalledStockLaunchersAreRecorded(t *testing.T) {
	w := newWorld(t, "com.example.enabled")
	f := newTV()
	f.installed = []string{"com.example.enabled", stock, launcherx}
	f.disabled = []string{launcherx}
	must(t, w.disable(f, DisableOptions{}))
	want := map[string]string{
		"com.example.enabled": stateEnabled,
		stock:                 stateEnabled,
		launcherx:             stateDisabled,
	}

	got := w.loadRecord().Packages
	if len(got) != len(want) {
		t.Fatalf("packages = %v", got)
	}

	for name, state := range want {
		if got[name] != state {
			t.Errorf("%s = %q, want %q", name, got[name], state)
		}
	}
}

func TestNoHomeAndAStockLauncherThatWasOffLeavesHomeAlone(t *testing.T) {
	w := newWorld(t, "com.example.enabled")
	f := newTV()
	f.installed = []string{"com.example.enabled", stock}
	f.disabled = []string{stock}
	f.home = ""
	must(t, w.disable(f, DisableOptions{}))
	r := w.restore(f)
	must(t, r)
	if slices.Contains(r.sent, "pm enable "+stock) {
		t.Error("turned on a stock launcher that was off before cleanup")
	}

	if homes := homeCommandsIn(r.sent); len(homes) != 0 {
		t.Errorf("home commands %v", homes)
	}

	if !strings.Contains(r.warn, "leaving Home alone") {
		t.Errorf("warnings: %s", r.warn)
	}
}

func (w *world) forgetStockLauncherInRecord() {
	data, err := os.ReadFile(w.record)
	if err != nil {
		w.t.Fatal(err)
	}

	var record map[string]any
	if err := json.Unmarshal(data, &record); err != nil {
		w.t.Fatal(err)
	}

	delete(record["packages"].(map[string]any), stock)
	edited, _ := json.Marshal(record)
	if err := os.WriteFile(w.record, edited, 0o644); err != nil {
		w.t.Fatal(err)
	}
}

func oldRecordTV(home string) *fakeTV {
	f := newTV()
	f.installed = []string{"com.example.enabled", stock, custom}
	f.disabled = []string{stock}
	f.home = home
	f.claims = []string{custom}
	return f
}

func TestAnOldRecordDoesNotTurnOnAStockLauncherItDoesNotHold(t *testing.T) {
	w := newWorld(t, "com.example.enabled")
	f := oldRecordTV(tv.LauncherActivity)
	must(t, w.disable(f, DisableOptions{}))
	w.forgetStockLauncherInRecord()
	r := w.restore(f)
	must(t, r)
	if slices.Contains(r.sent, "pm enable "+stock) {
		t.Error("turned on a stock launcher the record never saw")
	}

	homes := homeCommandsIn(r.sent)
	if !slices.Equal(homes, []string{setHomeActivity + tv.LauncherActivity}) {
		t.Errorf("home commands %v", homes)
	}

	said := singleLine(r.warn)
	if !strings.Contains(said, stock+" was not in the record then") ||
		!strings.Contains(said, "scripts/tvctl shell 'pm enable "+stock+"'") {
		t.Errorf("warnings: %s", r.warn)
	}
}

func TestAnOldRecordWithNoHomeStillResolvesTheStockLauncher(t *testing.T) {
	w := newWorld(t, "com.example.enabled")
	f := oldRecordTV("")
	must(t, w.disable(f, DisableOptions{}))
	w.forgetStockLauncherInRecord()
	r := w.restore(f)
	must(t, r)
	if slices.Contains(r.sent, "pm enable "+stock) {
		t.Error("turned on a stock launcher the record never saw")
	}

	if homes := homeCommandsIn(r.sent); !slices.Equal(homes, []string{tv.SetHomeTo(stock)}) {
		t.Errorf("home commands %v", homes)
	}
}
