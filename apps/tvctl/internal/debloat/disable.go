package debloat

import (
	"fmt"
	"io"
	"strings"

	"github.com/kienmai160598/freeTV/apps/tvctl/internal/tv"
)

type DisableOptions struct {
	ForceSetupWizard bool
	ForceHome        bool
}

const turnADBOffAdvice = "When you are done, turn USB debugging, Network/ADB debugging and " +
	"Wireless debugging off on the TV, reboot it, then check with: scripts/tvctl closed.\n"

const insecureADBWarning = "Warning: this TV reports ro.adb.secure=0, so network ADB on port " +
	"5555 accepts any device on your network without the \"Allow\" prompt.\n" + turnADBOffAdvice

const unsetADBSecureWarning = "Warning: this TV does not set ro.adb.secure. On a debug " +
	"(userdebug) build or Android 4.4 that is the same as ro.adb.secure=0: network ADB on port " +
	"5555 accepts any device on your network without the \"Allow\" prompt.\n" + turnADBOffAdvice

func Disable(
	dev tv.Device,
	recordPath, listPath, host string,
	opts DisableOptions,
	out, warn io.Writer,
) error {
	names, err := ReadList(listPath)
	if err != nil {
		return err
	}

	if err := refuseCorePackages(listPath, names); err != nil {
		return err
	}

	targets := applyGuards(dev, names, opts, warn)
	adbWarning := adbSecureWarning(dev)

	if err := SaveRecord(dev, recordPath, listPath, host, names, out); err != nil {
		return err
	}

	if err := tv.Batch(dev, disableCommands(targets), out); err != nil {
		return err
	}

	fmt.Fprint(warn, tv.RebootDelayNotice)
	fmt.Fprint(warn, adbWarning)
	return nil
}

func applyGuards(dev tv.Device, names []string, opts DisableOptions, warn io.Writer) []string {
	targets := names
	if !opts.ForceSetupWizard {
		targets = keepSetupWizardIfItOwnsWifi(dev, targets, warn)
	}

	if !opts.ForceHome {
		targets = keepOnlyHome(dev, targets, warn)
	}

	return targets
}

func adbSecureWarning(dev tv.Device) string {
	answer, err := dev.Shell("getprop ro.adb.secure")
	if err != nil {
		return ""
	}

	switch strings.TrimSpace(answer) {
	case "0":
		return insecureADBWarning
	case "":
		return unsetADBSecureWarning
	default:
		return ""
	}
}

func disableCommands(names []string) []string {
	commands := make([]string, 0, len(names)+1)
	for _, name := range names {
		commands = append(commands, "pm disable-user --user 0 "+name)
	}

	return append(commands, listDisabledPackages)
}
