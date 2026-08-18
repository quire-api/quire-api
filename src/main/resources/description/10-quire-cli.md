# Quire CLI

Quire offers a command-line interface (`@quire-io/quire-cli`) that lets developers and power users drive their Quire workspace from a terminal or a shell script.

Once you are signed in, you can list and search projects, create or update tasks and subtasks, post comments, manage tags, sublists, statuses, documents, chats, insights, and more — and pipe the results into `jq`, `xargs`, or any other Unix tool.

> Quire CLI is built on top of the same Quire REST API documented here, so anything an integration can do via REST is reachable from your terminal once you have signed in.

## Install

The CLI ships through three channels.

### npm (recommended)

Requires Node 20 or later.

```bash
npm install -g @quire-io/quire-cli
quire --version
```

Try it without installing:

```bash
npx @quire-io/quire-cli --help
```

The package is published as [`@quire-io/quire-cli`](https://www.npmjs.com/package/@quire-io/quire-cli); the installed binary is named `quire`.

### Homebrew

For macOS or Linux users with [Homebrew](https://brew.sh):

```bash
brew install quire-io/quire/quire
quire --version
```

This installs the same single-file binary that GitHub Releases ships, via the [`quire-io/homebrew-quire`](https://github.com/quire-io/homebrew-quire) tap. After the first install, `brew upgrade quire` keeps you on the latest version.

### Single-file binary

For machines that do not have Node, download a pre-built binary from the [GitHub Releases page](https://github.com/quire-io/quire-cli/releases). One artifact per platform is attached to every release:

| Platform | Filename |
| --- | --- |
| macOS, Apple Silicon | `quire-darwin-arm64` |
| macOS, Intel | `quire-darwin-x64` |
| Linux, x86_64 | `quire-linux-x64` |
| Linux, ARM64 | `quire-linux-arm64` |
| Windows, x86_64 | `quire-win-x64.exe` |

Each release also ships a `SHA256SUMS` file so you can verify the download. On Unix:

```bash
curl -LO https://github.com/quire-io/quire-cli/releases/latest/download/quire-darwin-arm64
curl -LO https://github.com/quire-io/quire-cli/releases/latest/download/SHA256SUMS
shasum -a 256 -c SHA256SUMS --ignore-missing
chmod +x quire-darwin-arm64
mv quire-darwin-arm64 /usr/local/bin/quire
quire --version
```

The binaries embed Node 22 LTS, so no separate runtime is required.

## Updating

How you update the CLI depends on how you installed it:

| Installed via | Upgrade command |
| --- | --- |
| `npm i -g` | `npm i -g @quire-io/quire-cli@latest` |
| Homebrew | `brew upgrade quire` |
| `npx` | nothing — `npx @quire-io/quire-cli` always pulls the latest unless you pinned a version |
| GitHub Releases binary | re-download from the [Releases page](https://github.com/quire-io/quire-cli/releases) and replace the binary on `$PATH` |

Check your installed version with `quire --version` and compare against the [Releases page](https://github.com/quire-io/quire-cli/releases).

## Get started

```bash
quire login          # opens your browser, runs OAuth, saves credentials locally
quire whoami         # confirms the signed-in user and lists their organizations
quire project list   # first read against the API
```

`quire login` runs the OAuth public-client flow ([PKCE, RFC 7636](https://datatracker.ietf.org/doc/html/rfc7636)) over a loopback redirect ([RFC 8252](https://datatracker.ietf.org/doc/html/rfc8252)). The browser opens to the Quire authorization screen; after you approve, control returns to the CLI and the resulting tokens are saved to:

* `~/.config/quire/credentials.json` on Linux and macOS (mode `0600`)
* `%APPDATA%\quire\credentials.json` on Windows

The file is encrypted at rest with AES-256-GCM. The key is derived from your username and hostname, so a copy of the file taken to another machine or another user account cannot be decrypted.

Tokens are refreshed automatically; you only need to run `quire login` once per machine and per profile.

## What you can do

The CLI exposes the full Quire object model as subcommands. Run `quire --help` for the top-level list, or `quire <command> --help` for any subcommand.

| Category | Commands |
| --- | --- |
| **User & Organizations** | `quire whoami`, `quire user get`, `quire org list / get / update / limit` |
| **Projects** | `quire project list / get / update / members / export`, `quire project field`, `quire project approval-category` |
| **Tasks** | `quire task list / get / tree / search / subtasks / comments`, `quire task create / subtask / update / complete / uncomplete / move / transfer / delete`, `quire task dates / peekaboo / approve / revoke-approval`, `quire task timelog`, `quire mine` |
| **Bulk operations** | `quire task bulk-create / bulk-update / bulk-delete / bulk-move / bulk-transfer / bulk-approve / bulk-subtasks` (up to 300 tasks per call) |
| **Sublists** | `quire sublist list / get / create / update / delete / add-task / remove-task / undo-remove` |
| **Tags & Statuses** | `quire tag` and `quire status` (`list / get / create / update / delete`) |
| **Comments & Attachments** | `quire comment list / get / add / update / delete`, `quire task attach`, `quire comment attach` |
| **Chats** | `quire chat list / get / create / update / delete / comments`, `quire chat comment add` |
| **Documents** | `quire doc list / get / create / update / delete / undo-remove` |
| **Insights** | `quire insight list / get / create / update / delete`, `quire insight field` |
| **Partners** | `quire partner list / get` |
| **Notifications** | `quire notify` — send a self-targeted in-app notification |
| **URL resolver** | `quire resolve` — turn any Quire URL into the underlying object |
| **Undo** | `quire undo` — restore a recently deleted task, sublist, document, comment, chat, or insight |
| **Reference** | `quire colors` — list Quire's 48-slot color palette |

## Output modes

Every command supports three output modes, controlled by global flags:

* **Default** — colorized, human-readable tables (or aligned key-value blocks for `get` commands). Long cells are truncated; pass `--no-truncate` to keep the full text.
* **`--json`** — the raw API response, unmodified, on stdout. Designed to round-trip through `jq`, `yq`, or any other JSON tool.
* **`--quiet` (`-q`)** — only the resource ID, one per line. Designed for `xargs`.

Examples:

```bash
# human-readable
quire task search "release notes" --project my-project --mine

# scriptable
quire task search "release notes" --project my-project --json | jq '.[].name'

# pipeline-friendly
quire task search "stale" --project my-project -q | xargs -L1 quire task complete
```

Other useful global flags:

* `--color-mode auto|always|never` (or `NO_COLOR=1`) controls ANSI colors.
* `--yes` auto-confirms destructive prompts (`quire task delete`, `quire bulk-delete`, …). Required for non-interactive use.
* `--profile <name>` selects a credentials profile (see below).
* `--verbose` prints debug information to stderr.

The CLI keeps stdout clean for piping: prompts, progress messages, and warnings all go to stderr, and a broken pipe (e.g. `quire ... | head`) exits 0 silently.

## Profiles

The CLI supports multiple credentials stores via `--profile`. Each profile gets its own file (`credentials.<name>.json`) under the same config directory.

```bash
quire login   --profile work
quire login   --profile personal
quire whoami  --profile work
quire mine    --profile personal
```

The default profile is `default`; override per shell with `QUIRE_PROFILE=...`.

`quire logout [--profile <name>]` deletes the local credentials file. It does not revoke the refresh token on the Quire server — the CLI is a public OAuth client and has no client secret to authenticate a revoke call. To fully invalidate access from the server side, also visit <https://quire.io/apps> and remove the **Quire CLI** app.

## Configuration

| Environment variable | Default | Purpose |
| --- | --- | --- |
| `QUIRE_PROFILE` | `default` | Profile selected when `--profile` is not passed |
| `QUIRE_CONFIG_HOME` | `~/.config/quire/` (Unix), `%APPDATA%\quire\` (Windows) | Where credentials and other CLI state live |
| `QUIRE_API_SERVER` | `https://quire.io/api` | Override the API base URL (advanced; the CLI prints a warning to stderr when set) |
| `QUIRE_CLI_LOOPBACK_PORT` | `8866` | Local port the OAuth callback listens on. Must match the redirect URI registered on the Quire OAuth app |
| `NO_COLOR` | unset | Set to any value to disable ANSI colors |

## Authorization scopes

The first time you run `quire login`, Quire shows the standard authorization screen. You can choose:

* **Which organizations and projects** the CLI may access.
* **What it may do** — the same permission scopes that apply to regular Quire OAuth apps (see [Authentication](#authentication)).

You can revoke access at any time from your Quire account settings, or by visiting <https://quire.io/apps> and removing the **Quire CLI** app.

## Privacy and rate limits

* CLI requests count against the same per-organization API quotas as direct REST calls — see [Rate Limits](#rate-limits). On a `429`, the CLI waits for the `Retry-After` value (with a small jitter, capped at 60 seconds) and retries once before failing.
* Credentials live only on the machine where you ran `quire login`. Nothing is sent to Quire other than authenticated API calls.
* All traffic is encrypted in transit (HTTPS); access tokens never appear in command output, only in the encrypted credentials file.

## Troubleshooting

* **`Not logged in. Run \`quire login\`.`** — credentials are missing or the refresh token has been revoked. Run `quire login` again.
* **The browser does not open during `quire login`.** — the authorization URL is also printed to stderr. Copy and paste it into any browser; the loopback callback will still complete the flow.
* **`Failed to bind 127.0.0.1:8866`** — another process is using the OAuth callback port. Pick a free port, set `QUIRE_CLI_LOOPBACK_PORT=<port>`, and re-run `quire login`. The same port must be registered as a redirect URI on the Quire OAuth app, otherwise the authorization screen will reject the request.
* **`429 Too Many Requests`** — the CLI auto-retries once after the server-supplied delay. Past that, wait and re-run; the per-organization rate limit is documented at [Rate Limits](#rate-limits).
* **`quire ... | head` prints a broken-pipe stack trace.** — you should not see this; the CLI catches `EPIPE` and exits 0. If you do see it, please report it as a bug.
* **Output looks corrupted on Windows.** — make sure your terminal supports ANSI escape sequences (Windows Terminal, VS Code's terminal, and modern PowerShell do). Otherwise pass `--color-mode never` or set `NO_COLOR=1`.

If a problem persists, contact Quire support with the timestamp of the failing request, the command line you ran (with any tokens or IDs redacted), and — if helpful — the output of the same command with `--verbose`.
