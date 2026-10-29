package debloat

import (
	"strings"
	"testing"
)

var corePackages = []string{
	"com.google.android.permissioncontroller", "com.android.permissioncontroller",
	"com.google.android.packageinstaller", "com.android.packageinstaller",
	"com.google.android.ext.services", "com.android.ext.services",
	"com.google.android.ext.shared", "com.android.ext.shared",
	"com.google.android.modulemetadata", "com.android.modulemetadata",
	"com.google.android.overlay.modules.ext.services",
	"com.google.android.overlay.modules.permissioncontroller",
	"com.android.systemui", "com.android.shell", "com.android.phone",
	"com.android.settings", "com.android.tv.settings",
	"com.android.providers.settings", "com.android.providers.tv",
	"com.android.providers.media", "com.android.providers.downloads",
	"android.ext.services", "android.ext.shared",
	"com.cltv.mal", "com.mediatek.tis", "com.mediatek.tv.service", "com.mediatek.tv.agent",
}

var lookAlikesThatAreNotCore = []string{
	"com.android.providers.calendar", "com.android.providers.contacts",
	"com.android.providers.userdictionary", "com.android.providers.blockednumber",
	"com.android.settings.intelligence", "com.android.tv.settings.overlay",
	"com.android.phone.extra", "com.google.android.tungsten.setupwraith",
	"com.google.android.apps.tv.launcherx", "com.android.shell2",
	"com.example.android.ext.services", "android.ext.services.overlay",
	"android.ext.sharedlib", "android.extservices",
	"com.cltv.fast", "com.cltv.hybrid", "com.mediatek.tv.settings", "com.mediatek.tis.overlay",
}

func TestEveryCorePackageIsRefused(t *testing.T) {
	for _, name := range corePackages {
		err := refuseCorePackages("l", []string{"com.example.enabled", name})
		if err == nil || !strings.Contains(err.Error(), "  "+name) {
			t.Errorf("%s was not refused: %v", name, err)
		}
	}
}

func TestLookAlikesOfCorePackagesAreAllowed(t *testing.T) {
	if err := refuseCorePackages("l", lookAlikesThatAreNotCore); err != nil {
		t.Error(err)
	}
}

func TestTVMiddlewareIsRefusedBeforeAnythingReachesTheTV(t *testing.T) {
	w := newWorld(t, "com.example.enabled", "com.cltv.mal", "android.ext.services")
	r := w.disable(newTV(), DisableOptions{ForceHome: true, ForceSetupWizard: true})
	if r.err == nil || !strings.Contains(r.err.Error(), "TV middleware") {
		t.Fatalf("error = %v", r.err)
	}

	if len(r.sent) != 0 || w.loadRecord() != nil {
		t.Errorf("sent %v; a refused list writes no record", r.sent)
	}
}
