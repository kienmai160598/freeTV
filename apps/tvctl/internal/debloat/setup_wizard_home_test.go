package debloat

import (
	"slices"
	"strings"
	"testing"

	"github.com/kienmai160598/freeTV/apps/tvctl/internal/tv"
)

const (
	launcherxHome   = launcherx + "/.home.HomeActivity"
	recoveryScreen  = tv.SetupWizard + "/.RecoveryActivity"
	settingsHome    = "com.android.tv.settings/.system.FallbackHome"
	wifiOfSetup     = " cuid=10031 cname=" + tv.SetupWizard + ":10031 allowAutojoin=true"
	wifiOfSystem    = " cuid=1000 cname=android.uid.system:1000 allowAutojoin=true"
	disableLauncher = "pm disable-user --user 0 " + launcherx
	disableSetup    = "pm disable-user --user 0 " + tv.SetupWizard
)

func googleTV() *fakeTV {
	f := newTV()
	f.installed = []string{
		"com.example.ads", tv.SetupWizard, launcherx, custom, "com.android.tv.settings",
	}
	f.disabled = nil
	f.home = launcherxHome
	f.claims = []string{launcherxHome, recoveryScreen, tv.LauncherActivity, settingsHome}
	f.wifi = wifiOfSetup
	return f
}

func TestTheIssueCaseKeepsBothLauncherxAndSetupwraith(t *testing.T) {
	w := newWorld(t, tv.SetupWizard, launcherx, "com.example.ads")
	r := w.disable(googleTV(), DisableOptions{})
	must(t, r)
	if slices.Contains(r.sent, disableLauncher) || slices.Contains(r.sent, disableSetup) {
		t.Errorf("sent %v", r.sent)
	}

	if !slices.Contains(r.sent, "pm disable-user --user 0 com.example.ads") {
		t.Errorf("the rest of the list still goes: %v", r.sent)
	}

	said := singleLine(r.warn)
	wanted := []string{
		"Skipping " + launcherx + ": it owns Home, and " + tv.SetupWizard + " stays enabled",
		"RecoveryActivity",
		"--force-home",
	}

	for _, want := range wanted {
		if !strings.Contains(said, want) {
			t.Errorf("missing %q in: %s", want, r.warn)
		}
	}
}

func TestAnUnlistedSetupwraithKeepsTheOwnerToo(t *testing.T) {
	w := newWorld(t, launcherx, "com.example.ads")
	r := w.disable(googleTV(), DisableOptions{})
	must(t, r)
	if slices.Contains(r.sent, disableLauncher) || !strings.Contains(r.warn, "RecoveryActivity") {
		t.Errorf("sent %v\n%s", r.sent, r.warn)
	}
}

func TestForceHomeDisablesTheOwnerButNotSetupwraith(t *testing.T) {
	w := newWorld(t, tv.SetupWizard, launcherx, "com.example.ads")
	r := w.disable(googleTV(), DisableOptions{ForceHome: true})
	must(t, r)
	if !slices.Contains(r.sent, disableLauncher) {
		t.Error("--force-home did not disable the owner")
	}

	if slices.Contains(r.sent, disableSetup) {
		t.Error("--force-home is not --force-setupwraith: the Wi-Fi guard still holds")
	}
}

func TestSetupwraithDisabledInTheSameRunLetsTheOwnerGo(t *testing.T) {
	f := googleTV()
	f.wifi = wifiOfSystem
	r := newWorld(t, tv.SetupWizard, launcherx, "com.example.ads").disable(f, DisableOptions{})
	must(t, r)
	if !slices.Contains(r.sent, disableSetup) || !slices.Contains(r.sent, disableLauncher) {
		t.Errorf("sent %v", r.sent)
	}
}

func TestALauncherThatOwnsHomeLetsTheListDisableLauncherx(t *testing.T) {
	f := googleTV()
	f.home = tv.LauncherActivity
	r := newWorld(t, launcherx, "com.example.ads").disable(f, DisableOptions{})
	must(t, r)
	if !slices.Contains(r.sent, disableLauncher) || strings.Contains(r.warn, "RecoveryActivity") {
		t.Errorf("sent %v\n%s", r.sent, r.warn)
	}
}

func TestWithoutSetupwraithAnsweringHomeAnotherLauncherStillCounts(t *testing.T) {
	f := googleTV()
	f.claims = []string{launcherxHome, tv.LauncherActivity, settingsHome}
	r := newWorld(t, launcherx, "com.example.ads").disable(f, DisableOptions{})
	must(t, r)
	if !slices.Contains(r.sent, disableLauncher) {
		t.Errorf("sent %v\n%s", r.sent, r.warn)
	}
}
