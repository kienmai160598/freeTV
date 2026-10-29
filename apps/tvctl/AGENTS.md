# tvctl

Go 1.22, standard library only. Module `github.com/kienmai160598/freeTV/apps/tvctl`.

| Package | Responsibility |
|---|---|
| `cmd/tvctl` | Flags, usage, one file per command group; env fallbacks `TV_HOST`, `TV_PORT`, `FORCE_HOME`, `FORCE_SETUPWRAITH`, `STOCK_LAUNCHER`, `INPUT_KEY_SERVICE`, `HOME_KEY_SERVICE`, `NOW_PLAYING` |
| `internal/adb` | Wire protocol over TCP: CNXN/AUTH (RSA, Android public key format), streams, `shell:`/`exec:`, sync push/pull |
| `internal/tv` | `Device` interface (`Shell`, `Push`), `Session` (lazy connect, reconnect after an error, md5-checked push with base64-over-shell fallback), Home queries |
| `internal/tv/tvtest` | In-memory `Fake` device for tests |
| `internal/debloat` | List parsing, guards, recovery record, restore commands |
| `internal/launcher` | Install and Home handover with rollback |
| `internal/contract` | Generated from `packages/contract/contract.json` (`make generate`); never edit |
| `internal/theme` | Validate `config.json` against the contract keys, push a theme folder, restart Home keeping accessibility services |
| `internal/netcheck` | Is port 5555 closed |

## Invariants

- A list is refused as a whole if any entry is not a bare package name; BOM and CRLF are stripped.
- A list naming a core Android package (`android`, SystemUI, Settings, permission controller,
  package installer, ext services/shared also under the bare `android.ext.*` names, providers
  other than calendar/contacts/dictionary/blocked numbers, the Google module overlays) or TV
  middleware (`com.cltv.mal`, `com.mediatek.tis`, `com.mediatek.tv.service|agent`) is refused,
  with no override flag: the TV may not boot again.
- `disable`: guards first (setupwraith while it owns Wi-Fi; the Home owner while no other launcher
  would answer Home, or while setupwraith claims HOME and stays enabled, since its
  RecoveryActivity outranks an installed launcher; settings apps, `*FallbackHome` and setupwraith
  never count as launchers), then the recovery record is written, then `pm disable-user --user 0`
  per package, then `pm list packages -d`. Home comes from `cmd package resolve-activity`, else
  from `dumpsys activity`; when neither names one the guard is skipped with a warning.
  `ro.adb.secure` of `0` or empty gets a warning; a failed read gets none.
- `disable`, `restore` and `install` end with `tv.RebootDelayNotice`: Android writes package
  state about 10 s after a change, so a reboot within 15 s boots with the old state.
- Recovery record: `~/.cleanyourtv/recovery-<host>-<sha1(resolved list path)[:8]>.json`, JSON with
  `created, home, host, list, packages{name: enabled|disabled|absent}, settings, version: 1`,
  written atomically. It holds the list, Home's package and every installed
  `tv.KnownStockLaunchers`. First record wins; later runs only add new names. A record that fails
  validation is never overwritten and blocks the run.
- `restore` replays the record (sorted `pm enable` of enabled ones only, so a stock launcher comes
  back only if recorded enabled; animation scales or delete for `null`). Home: a recorded Home
  that is not ours is set again; ours stays Home unless the stock launcher was recorded enabled;
  no Home and the stock one recorded disabled/absent leaves Home alone; else resolve-and-set the
  stock one. No record: falls back to enabling the whole list and the stock launcher, unless
  other records exist for that host, then it asks for `--record`.
- `install`: push APK to `/data/local/tmp`, `pm install -r`, `am start -W` must say `Status: ok`
  with no `Error` line, then disable stock and set Home, then confirm Home; else roll back and, if
  that fails too, write `~/freeTV-handover-<host>.txt`.
- Accessibility services: `install` grants the `AUTO_START` and `APP_AUTO_START` app-ops to the
  launcher (TCL), and a service already listed is cleared and written back so it binds again
  after `pm install -r`. The list is empty for about a second while that happens.
- `theme` restarts Home with `tv.StartHome` (`am start` with the HOME intent), never by package.
- Every behaviour that changes what reaches a TV has a test against `tvtest.Fake`; protocol changes
  are tested against the fake adbd in `internal/adb/adb_test.go` (4 KB packets like Android 5).

`make test` runs gofmt, vet and tests.
