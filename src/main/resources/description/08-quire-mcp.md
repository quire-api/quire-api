# Quire MCP Server

Quire offers an [MCP](https://modelcontextprotocol.io/) (Model Context Protocol) server that lets AI assistants — Claude, ChatGPT, Cursor, VS Code, and other MCP-compatible clients — work with your Quire workspace through natural language.

Once connected, the assistant can list and search your projects, create or update tasks and subtasks, post comments, manage tags, sublists, statuses, documents, chats, insights, and more — all on your behalf and within the permissions you grant.

> Quire MCP is built on top of the same Quire REST API documented here, so anything an integration can do via REST is reachable from an AI assistant once it is connected.

## Server URL

The Quire MCP server is hosted at:

```
https://mcp.quire.app/mcp
```

Authentication is handled by OAuth 2.1 — your AI assistant walks you through the standard authorization screen, you approve access for the workspaces you choose, and the assistant receives a token scoped to your account. No client ID or client secret needs to be configured by the end user.

The server speaks the [Streamable HTTP transport](https://modelcontextprotocol.io/specification/basic/transports#streamable-http) and supports [Dynamic Client Registration (RFC 7591)](https://datatracker.ietf.org/doc/html/rfc7591) and [PKCE (RFC 7636)](https://datatracker.ietf.org/doc/html/rfc7636), so any compliant MCP client can connect with just the URL above.

## What you can do

The Quire MCP server exposes the full Quire object model as MCP tools. At a high level:

| Category | What the AI can do |
| --- | --- |
| **User & Organizations** | Identify the current user, look up users, list and update organizations |
| **Projects** | List, search, and inspect projects; manage members, custom fields, and approval categories |
| **Tasks** | Create, update, move, transfer, complete, approve, and delete tasks; manage dates, peekaboo, and time logs; search across a project, folder, or organization |
| **Bulk operations** | Create, update, move, transfer, approve, or delete many tasks in one call |
| **Subtasks & Sublists** | Build task hierarchies, group tasks into sublists, and reorder within sublists |
| **Comments & Attachments** | Read and post comments on tasks and chats; attach files to tasks or comments |
| **Tags & Statuses** | Manage the project's tag library and workflow statuses |
| **Documents** | Create, read, update, and delete project documents |
| **Chats & Insights** | Start chat threads, post chat comments, and manage insight reports and their fields |
| **Partners** | List and inspect partner workspaces |
| **URL resolver** | Turn any pasted Quire URL (task, project, document, …) into the underlying object |

For the authoritative, always-up-to-date list of tools and their parameters, run `tools/list` from your MCP client after connecting.

## Connect your AI assistant

You only need the server URL — the authorization flow is started by the client and completed in your browser.

### Claude Desktop

1. Open **Settings → Connectors**.
2. Click **Add custom connector**.
3. Enter a name (for example, `Quire`) and the URL `https://mcp.quire.app/mcp`.
4. Save and click **Connect**. Claude Desktop will open a browser window where you can sign in to Quire and grant access.

### Claude Code

Add the server with one command:

```bash
claude mcp add --transport http quire https://mcp.quire.app/mcp
```

Then trigger the OAuth flow from inside Claude Code:

```
/mcp
```

Pick `quire`, follow the link to authorize, and you're done.

### Gemini CLI

Edit `~/.gemini/settings.json` (user scope) or `.gemini/settings.json` (project scope):

```json
{
  "mcpServers": {
    "quire": {
      "httpUrl": "https://mcp.quire.app/mcp",
      "authProviderType": "dynamic_discovery"
    }
  }
}
```

Restart Gemini CLI, then trigger OAuth:

```
/mcp auth quire
```

`dynamic_discovery` makes the CLI auto-detect the OAuth requirement, register a client, and open a browser to authorize. (OAuth requires that your local machine can receive a redirect on `http://localhost:7777/oauth/callback`.)

### ChatGPT

ChatGPT supports remote MCP servers as **Connectors** (available on Plus, Pro, Business, Enterprise, and Edu plans).

1. Open `chatgpt.com` and go to **Settings → Connectors**.
2. Click **Add connector**.
3. Enter a name (for example, `Quire`) and the URL `https://mcp.quire.app/mcp`.
4. Sign in to Quire when prompted and approve access.

### Perplexity

Perplexity supports remote MCP servers as **Custom Connectors** on paid plans (Pro, Max, Enterprise).

1. Open `perplexity.ai` and go to **Settings → Connectors**.
2. Click **Add Connector → Advanced** (or **Add custom connector**).
3. Enter a name (for example, `Quire`), set the URL to `https://mcp.quire.app/mcp`, and choose **Streamable HTTP** as the transport with **OAuth** as the authentication method.
4. Save, then click **Connect** and sign in to Quire to approve access.

### Cursor

Add the following entry to your MCP settings (`~/.cursor/mcp.json` or **Settings → MCP**):

```json
{
  "mcpServers": {
    "quire": {
      "url": "https://mcp.quire.app/mcp"
    }
  }
}
```

Reload Cursor and complete the OAuth flow when prompted.

### VS Code (GitHub Copilot Chat)

Create or edit `.vscode/mcp.json` in your workspace (or the user-level equivalent):

```json
{
  "servers": {
    "quire": {
      "type": "http",
      "url": "https://mcp.quire.app/mcp"
    }
  }
}
```

Open the Command Palette and run **MCP: List Servers**, select `quire`, and start it. VS Code will open a browser to authorize.

### Windsurf

Edit `~/.codeium/windsurf/mcp_config.json`:

```json
{
  "mcpServers": {
    "quire": {
      "serverUrl": "https://mcp.quire.app/mcp"
    }
  }
}
```

Restart Windsurf and authorize when prompted.

### Other MCP clients

Any client that supports Streamable HTTP MCP servers with OAuth can connect — just point it at `https://mcp.quire.app/mcp`. For clients that only speak the older `stdio` transport, use a bridge such as [`mcp-remote`](https://www.npmjs.com/package/mcp-remote):

```json
{
  "mcpServers": {
    "quire": {
      "command": "npx",
      "args": ["-y", "mcp-remote", "https://mcp.quire.app/mcp"]
    }
  }
}
```

## Authorization scopes

The first time you connect, Quire shows the standard authorization screen. You can choose:

* **Which organizations and projects** the assistant may access.
* **What it may do** — the same permission scopes that apply to regular Quire OAuth apps (see [Authentication](#authentication)).

You can revoke access at any time from your Quire account settings, or by removing the connector from your AI client.

## Privacy and rate limits

* Quire MCP requests count against the same per-organization API quotas as direct REST calls — see [Rate Limits](#rate-limits).
* Conversations with your AI assistant are not stored on Quire's servers; the MCP server only sees the individual tool calls the assistant makes on your behalf.
* All traffic is encrypted in transit (HTTPS); access tokens are never exposed to model output.

## Troubleshooting

* **"Connection refused" or "Failed to authenticate"** — make sure the URL is exactly `https://mcp.quire.app/mcp` (with the `/mcp` path) and that your client supports the Streamable HTTP transport.
* **Authorization screen never appears** — some clients require you to manually trigger the OAuth flow (for example, `/mcp` in Claude Code). Check the client's MCP server panel for an "authorize" button or status message.
* **The assistant can't see a project** — confirm the project belongs to an organization you authorized, and that your Quire role on that project allows the action you're asking for.
* **Tool calls return `429 Too Many Requests`** — you have hit the per-organization rate limit. The assistant will receive a `Retry-After` value; wait that many seconds before retrying.

If a problem persists, contact Quire support with the timestamp of the failing request and (if shown) the request ID surfaced by the MCP client.
