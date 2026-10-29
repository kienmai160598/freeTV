package debloat

import (
	"fmt"
	"regexp"
	"strings"
)

var (
	corePackage = regexp.MustCompile(`^(android|` +
		`com\.android\.(systemui|shell|phone|settings|tv\.settings)|` +
		`com\.(google\.)?android\.(permissioncontroller|packageinstaller|` +
		`ext\.services|ext\.shared|modulemetadata)|` +
		`com\.google\.android\.overlay\.modules.*|` +
		`com\.android\.providers\..*)$`)
	providerSafeToDisable = regexp.MustCompile(
		`^com\.android\.providers\.(calendar|contacts|userdictionary|blockednumber)$`,
	)
)

const corePackagesRefused = "refusing %s: it disables core Android packages, and without " +
	"them the TV may not boot again:\n  %s\n" +
	"Take these lines out of the list (or comment them) and run it again. " +
	"Nothing was changed on the TV."

func refuseCorePackages(listPath string, names []string) error {
	var core []string
	for _, name := range names {
		if corePackage.MatchString(name) && !providerSafeToDisable.MatchString(name) {
			core = append(core, name)
		}
	}

	if len(core) == 0 {
		return nil
	}

	return fmt.Errorf(corePackagesRefused, listPath, strings.Join(core, "\n  "))
}
