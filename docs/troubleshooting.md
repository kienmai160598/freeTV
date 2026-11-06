# Troubleshooting

## Connecting

- macOS blocks the Google `adb` binary ("No route to host") under Local Network privacy; `tvctl` is not blocked.
- Some TVs have no Wireless debugging toggle: USB debugging alone opens port 5555 (Xiaomi).
- Some Google TV sets never open 5555 from USB debugging: pair once (`adb pair <ip>:<port> <code>`,
  `adb connect`, `adb tcpip 5555`); the pairing port shows in `dns-sd -B _adb-tls-pairing._tcp`.
  `adb tcpip` lasts until reboot; Wireless debugging turns off whenever Wi-Fi drops.
- USB debugging off can leave the Wireless debugging port open (Chromecast with Google TV): switch
  that off on its own; `tvctl closed` only checks 5555, `adb mdns services` lists the other port.
- ISP boxes with both debugging switches removed or greyed out cannot be reached at all.
- Build number asks for a password (ISP boxes): open
  `com.android.tv.settings/.system.development.DevelopmentActivity` with an activity-launcher app.
- TV on another VLAN: a temporary firewall rule for tcp/5555 only, removed afterwards.
- Unaccepted keys queue one "Allow USB debugging?" prompt each; dismiss them before key injection.
- Mi Box S and Xiaomi TV Box S 3rd gen cut USB power to peripherals while USB debugging is on.
  A USB Ethernet adapter goes too and the box falls back to Wi-Fi silently
  (`dumpsys connectivity | grep -o 'Transports: [A-Z]*'`): one sitting, then replug and check.
- Git Bash rewrites `/data/...` paths; use `scripts/tvctl.ps1` or `MSYS_NO_PATHCONV=1`.

## Working on the TV

- Chained `pm` commands may stop after the first; run one per shell call (`tvctl batch` does).
- Android saves package state about 10 s after a `pm` change (AOSP `WRITE_SETTINGS_DELAY`): a reboot
  sooner boots with the old state and the whole batch is enabled again. Wait 15 s; tvctl says so.
- App-ops are saved lazily too: follow `cmd appops set ...` with `appops write-settings`.
- A shell `reboot` gets no clean reply (adbd stops first); `probe` until the TV answers again.
- Read `pm list packages -d` after a change, not the exit code: `pm hide` can report success and
  change nothing.
- Never disable core Android (`permissioncontroller`, `packageinstaller`, `ext.services`,
  `modulemetadata`, `overlay.modules.*`, `com.android.providers.*`, `systemui`): a Mi TV Stick never
  booted again. `tvctl disable` refuses such a list; only four providers are allowed.
- The Home guard needs `cmd` (Android 7+); older sets fall back to `dumpsys activity`
  (`mHomeProcess`). "Home guard did not run" means check the list for the launcher by hand.
- `FallbackHome` placeholders and factory-test apps (`com.duokan.factorytest`) answer HOME but
  are not launchers; the Home guard ignores them.
- `resolve-activity`, `query-activities` and `cmd role` name the resolver's pick; what really took
  Home is `dumpsys activity activities | grep mFocusedApp`.
- File-based encryption (`getprop ro.crypto.type` = `file`) with no `FallbackHome`: only a
  direct-boot-aware Home can start (`dumpsys package <pkg> | grep -o '[A-Z_]*DIRECT_BOOT_AWARE'`).
  Without both, keep the stock launcher enabled and only set Home.
- `INSTALL_FAILED_OLDER_SDK`: the launcher needs Android 8.0 (`minSdk 26`); read
  `getprop ro.build.version.sdk` first. Below that the lists still apply.
- A firmware update re-enables every listed package and gives Home back: run list and install again.
- TCL's logcat main buffer is 1 MiB and fills in under a minute: `logcat -G 16M` for the session,
  or keep `logcat` running before the event. A missing line proves nothing.
- Some MediaTek Xiaomi `screencap` print a line before the PNG; save to `/data/local/tmp/s.png` and pull.
- Play Protect blocks apps targeting old SDKs (`INSTALL_FAILED_VERIFICATION_FAILURE`); target 34.
- Some China-market TCL sets refuse every sideload or any HOME app not signed by them: debloat only.
- `set-home-activity` can print `Success` and change nothing; always re-query Home.
- Check which packages really have a HOME activity before disabling by name
  (`cmd package query-activities --brief -a android.intent.action.MAIN -c android.intent.category.HOME`).

## Google TV setup wizard (`com.google.android.tungsten.setupwraith`)

- Its `.RecoveryActivity` answers HOME (priority 1); once the stock launcher is gone it takes Home and
  system_server crash-loops (`logcat -b crash`: `FATAL EXCEPTION IN SYSTEM PROCESS: android.display`).
  Disabling the component alone is refused; disable the package. Escape a loop with
  `pm enable --user 0 com.google.android.apps.tv.launcherx`.
- Disable it before launcherx. A reboot on `.RecoveryActivity` factory-reset a MiTV-MOSR4: if
  `mFocusedApp` shows it, re-enable launcherx first and do not reboot.
- Some firmware re-enables it at every cold boot, so Home goes black after a power cut; same escape.
- Disabling it deletes the Wi-Fi network saved during first setup and the TV goes offline for good.
  Check `dumpsys wifi | grep -o 'cname=[^ ]*'` first; if it names setupwraith, forget and re-join the
  network in Settings (owner becomes `android.uid.system`) or use Ethernet. Reconnecting alone is
  not enough. TCL G07 build AR17 had it owned by setupwraith; AR20 by `android.uid.system`.
- Casting says "Untrusted device" with it disabled (Chromecast with Google TV, Google TV Streamer):
  the Cast credentials come from its `CastInfoProvider`. Mi Box S 2nd gen reportedly unaffected.
- Re-enable it before a factory reset; keeping it in the list makes `tvctl restore` do that.

## After the handover

- No on-screen keyboard (Xiaomi): `ime set com.google.android.inputmethod.latin/com.android.inputmethod.latin.LatinIME`.
- "Select a Home app" after restore + re-apply: another HOME claimant was disabled; `tvctl install`
  picks the single known stock launcher among the candidates, else pass `--stock`.
- Wrong time zone on old firmware: `settings put global auto_time_zone 0; cmd alarm set-timezone <Zone>`,
  or a fixed `GMT+hh:mm` when the firmware's tz data predates a rule change.
- Updated system apps (voice assistant on Xiaomi Android 9) can re-enable themselves at boot.
- With launcherx disabled: Accounts & profiles is empty (enable it, switch, disable, set Home), and
  voice search for media fails while app commands still work.
- The remote's mic needs `com.google.android.katniss` and `com.google.android.tv.remote.service`
  (Xiaomi MiTV-AFMU0); with only katniss the Assistant opens and hears silence.
- Stremio plays through `com.google.android.apps.mediashell`; with it disabled nothing plays.
- Cast receiver running but invisible to phones (TCL QM7):
  `am force-stop com.google.android.apps.mediashell`.
- HDMI row opens an unrelated app: another app claims `VIEW` on `vnd.android.cursor.item/channel`
  (`cmd package query-activities --brief -a android.intent.action.VIEW -t <that type>`).
- `tvctl theme` writes `Android/data` from the shell: refused on TCL G07 (Android 11), and on
  Android 14 a new file belongs to the shell and the launcher logs `EACCES`.
- A force stop of the launcher takes both key helpers off the accessibility list; install again with
  the same flags, or set `restore_key_services`.
- After a reboot the list can still name a helper while `accessibility_enabled` is `0` (TCL): write
  it back; if nothing binds, clear the list and write it back (`tvctl install` does).
- TCL refuses to bind the helpers without the `AUTO_START` (or `APP_AUTO_START`) app-op;
  `tvctl install` grants both and saves them.
- A bound key-filtering helper stops held buttons repeating (TCL G09, Xiaomi MiTV-MOSR4);
  `"key_filter": false` in a theme trades the helper's buttons for the repeat.
- TCL logs remote keys as `TclPhoneWindowManager: handleKeyBeforeQueueing, key = <code>`.
- One app fails while the rest play: an ad-blocking Private DNS blocks its analytics hosts;
  `settings put global private_dns_mode opportunistic`, then retry.

## Xiaomi China firmware keeps Home

- `set-home-activity` is stored but ignored: Home resolves to `com.mitv.tvhome`, or to Settings'
  `FallbackHome` once that is disabled. HomeKeyService stands in either way; with the stock launcher
  disabled (`pm disable-user --user 0 com.mitv.tvhome`, undo with `pm enable`) only a black
  FallbackHome flashes. It stayed disabled across a reboot on MiTV-ASTP0 (Android 9).
- `FallbackHome` shares its task with TV Settings, so Settings opening also reports FallbackHome.
- An injected `input keyevent KEYCODE_HOME` stops app switches for 5 s: the launcher opened by the
  service shows only after that. The remote's Home goes through the service and has no delay.
- After `adb install -r` the accessibility services may stay unbound until
  `enabled_accessibility_services` is written again (`tvctl install` does).
