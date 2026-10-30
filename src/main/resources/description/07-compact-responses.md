# Compact Responses

Many endpoints accept an optional `?return=` query parameter to control the shape of the
response body:

- `full` (default) — the full record.
- `compact` — identifiers only:

| Entity | Compact shape |
|---|---|
| Task, project, organization, sublist, document, chat, insight, dashboard | `{"oid": "…", "id": …}` |
| Comment, reminder, tag | `{"oid": "…"}` |
| Custom field (of a project or an insight) | `{"name": "…"}` |
| Approval category | `{"id": "…"}` |
| Status | `{"value": …}` |

> 💡 **Tip — pass `?return=compact` whenever you don't need the full record back.**
> It is strictly better for the caller (less bandwidth, fewer LLM tokens) and for the
> server, which then skips a post-write reload plus the full response render —
> including per-user lookups, custom-field expansion, and so on. The default stays
> `full` only for backward compatibility; new callers (MCP integrations, scripted
> clients, anything that just chains calls by OID/ID) should default to `compact`.

## Where it applies

`?return=` is honored by:

- **Single-item `POST` / `PUT`** create and update endpoints, and every `PUT …/undo-remove/…`
  (task, comment, sublist, document, chat, insight, dashboard).
- **`GET /task/list/…`**, in both the flat and the `?depth>1` subtree form. Items render as
  `{oid, id, cursor?}`, and subtree nodes as `{oid, id, tasks?, cropped?}`.
- **Bulk write endpoints**, where it applies to every element of the returned array;
  `null` slots for skipped items are preserved in either mode. See
  [Rate Limits](#rate-limits) for what those calls cost.

It is **ignored** by a `GET` on a single record, by `DELETE`, by the search endpoints
(`search`, `search-organization`, `search-folder`), and by the timelog, attach, emoji,
storage, and notification endpoints — their response shape is fixed.
`DELETE /task/bulk-remove/…` likewise ignores it, since its response is already in
identifier shape.

On an endpoint that honors `?return=`, an empty value means `full`, and any value other
than `full` or `compact` returns `400 Bad Request`. Elsewhere any value is ignored.
