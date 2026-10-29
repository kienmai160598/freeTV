package debloat

import (
	"fmt"
	"io"
	"regexp"
	"slices"
	"strings"

	"github.com/kienmai160598/freeTV/apps/tvctl/internal/tv"
)

var homeAnswerersThatAreNotLaunchers = []string{
	tv.SetupWizard,
	"com.android.tv.settings",
	"com.android.settings",
	"com.android.provision",
}

var (
	wifiNetworkOwner = regexp.MustCompile(`cname=\S*`)
	factoryTestApp   = regexp.MustCompile(`(?i)factorytest|factory_test|factorymode`)
	homeProcess      = regexp.MustCompile(`mHomeProcess:.*[: ]([A-Za-z0-9_.]+)/[A-Za-z0-9_.]+`)
)

const unknownWifiOwnerWarning = "Skipping %s: could not check which app owns the saved Wi-Fi " +
	"networks (dumpsys wifi failed or showed none).\n" +
	"Try again, or pass --force-setupwraith if the TV uses Ethernet.\n"

const setupWizardOwnsWifiWarning = "Skipping %s: the saved Wi-Fi network belongs to it, and " +
	"disabling it would delete that network and take the TV off Wi-Fi.\n" +
	"On the TV, forget the network and join it again in Settings > Network & Internet, " +
	"then run this again (or pass --force-setupwraith on Ethernet).\n"

const onlyHomeWarning = "Skipping %s: it owns Home, and no other app that can answer Home " +
	"is installed and staying enabled.\n" +
	"Make the new launcher Home first (make install), then run this list again to disable " +
	"the old one; or pass --force-home if you can still reach the TV.\n"

const setupWizardTakesHomeWarning = "Skipping %[1]s: it owns Home, and %[2]s stays enabled " +
	"and answers Home ahead of the other launchers here, so with %[1]s disabled Home would go " +
	"to its RecoveryActivity, the black screen system_server can crash-loop on " +
	"(docs/troubleshooting.md).\n" +
	"Make the new launcher Home first (make install), or disable setupwraith in the same run " +
	"(if it was skipped for the Wi-Fi network, forget the network and join it again in " +
	"Settings > Network & Internet), then run this list again; or pass --force-home if you " +
	"can still reach the TV.\n"

const unknownHomeWarning = "Could not tell which app owns Home on this TV, so the Home guard " +
	"did not run: neither \"cmd package resolve-activity\" (Android 7 and later) nor " +
	"\"dumpsys activity\" named one.\n" +
	"Check by hand that the list does not hold the only launcher before trusting this run.\n"

func keepSetupWizardIfItOwnsWifi(dev tv.Device, targets []string, warn io.Writer) []string {
	if !slices.Contains(targets, tv.SetupWizard) {
		return targets
	}

	dump, err := dev.Shell("dumpsys wifi")
	owners := wifiNetworkOwner.FindAllString(dump, -1)
	if err != nil || len(owners) == 0 {
		fmt.Fprintf(warn, unknownWifiOwnerWarning, tv.SetupWizard)
		return without(targets, tv.SetupWizard)
	}

	if slices.ContainsFunc(owners, ownedBySetupWizard) {
		fmt.Fprintf(warn, setupWizardOwnsWifiWarning, tv.SetupWizard)
		return without(targets, tv.SetupWizard)
	}

	return targets
}

func ownedBySetupWizard(owner string) bool {
	return strings.Contains(owner, "setupwraith")
}

func keepOnlyHome(dev tv.Device, targets []string, warn io.Writer) []string {
	if len(targets) == 0 {
		return targets
	}

	owner := homeOwner(dev)
	if owner == "" {
		fmt.Fprint(warn, unknownHomeWarning)
		return targets
	}

	if !slices.Contains(targets, owner) {
		return targets
	}

	claimants, err := tv.HomeClaimants(dev)
	if err != nil || !anotherLauncherStays(claimants, owner, targets) {
		fmt.Fprintf(warn, onlyHomeWarning, owner)
		return without(targets, owner)
	}

	if setupWizardStaysToAnswerHome(claimants, targets) {
		fmt.Fprintf(warn, setupWizardTakesHomeWarning, owner, tv.SetupWizard)
		return without(targets, owner)
	}

	return targets
}

func homeOwner(dev tv.Device) string {
	if owner := tv.PackageOf(tv.Home(dev)); tv.PackageName.MatchString(owner) {
		return owner
	}

	dump, err := dev.Shell("dumpsys activity")
	if err != nil {
		return ""
	}

	match := homeProcess.FindStringSubmatch(strings.ReplaceAll(dump, "\r", ""))
	if match == nil || !tv.PackageName.MatchString(match[1]) {
		return ""
	}

	return match[1]
}

func anotherLauncherStays(claimants []string, owner string, targets []string) bool {
	return slices.ContainsFunc(claimants, func(claimant string) bool {
		return claimant != owner &&
			!slices.Contains(targets, claimant) &&
			!slices.Contains(homeAnswerersThatAreNotLaunchers, claimant) &&
			!factoryTestApp.MatchString(claimant)
	})
}

func setupWizardStaysToAnswerHome(claimants []string, targets []string) bool {
	return slices.Contains(claimants, tv.SetupWizard) && !slices.Contains(targets, tv.SetupWizard)
}

func without(names []string, unwanted string) []string {
	isUnwanted := func(name string) bool { return name == unwanted }
	return slices.DeleteFunc(slices.Clone(names), isUnwanted)
}
