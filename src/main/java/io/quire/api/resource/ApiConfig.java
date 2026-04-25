package io.quire.api.resource;

import io.swagger.annotations.*;

@SwaggerDefinition(
  info = @Info(
    extensions = {
      @Extension(name = "x-logo", properties = {
        @ExtensionProperty(name = "url", value = "https://quire.io/s/img/quire_logo.svg")}),
    },
    title = "Quire API",
    version = "1.0.0",
    description =
        "## Error responses\n\n"
      + "All `4xx` and `5xx` responses share a common JSON envelope, "
      + "represented by the [`ErrorResponse`](#definition-ErrorResponse) "
      + "schema:\n\n"
      + "```json\n"
      + "{ \"code\": 400, \"message\": \"Invalid value for `priority`: foo (expected …).\" }\n"
      + "```\n\n"
      + "- `code` is the HTTP status code echoed in the body.\n"
      + "- `message` is a human-readable description, typically naming the "
      + "offending field and (for `400`) the expected format. Long submitted "
      + "values are truncated.\n\n"
      + "Each endpoint's `Responses` block lists the specific status codes "
      + "it can emit; the body shape is the same `ErrorResponse` envelope "
      + "everywhere.\n\n"
      + "## Rate limiting\n\n"
      + "Per-organization API quotas are enforced. When exceeded the API "
      + "returns `429 Too Many Requests` with the standard error envelope, "
      + "plus a [`Retry-After`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Retry-After) "
      + "response header (in seconds) indicating when the next call may be "
      + "retried.\n\n"
      + "To inspect current counters without consuming budget, use "
      + "[`GET /rate_limit/{oid}`](#operation--rate-limit--oid--get) "
      + "(or `/rate_limit/id/{id}`). Calls to that endpoint do not count "
      + "against the quota.\n\n"
      + "## Compact responses (`?return=`)\n\n"
      + "Single-item `POST` / `PUT` endpoints accept an optional "
      + "`?return=` query parameter to control the response shape:\n\n"
      + "- `full` (default; same as today): the full record.\n"
      + "- `compact`: identifiers only — `{\"oid\": \"…\", \"id\": …}` for "
      + "entities with both, `{\"oid\": \"…\"}` for OID-only entities (e.g. "
      + "comment, tag), `{\"name\": \"…\"}` for custom-field extensions, "
      + "`{\"id\": \"…\"}` for approval categories, `{\"value\": …}` for "
      + "task statuses.\n\n"
      + "> 💡 **Tip — pass `?return=compact` whenever you don't need "
      + "the full record back.** It's strictly better for the caller "
      + "(less bandwidth, fewer LLM tokens) AND for the server (skips "
      + "a post-write reload plus the full response render — including "
      + "per-user lookups, custom-field expansion, etc.). The default "
      + "stays `full` only for backward compatibility; new callers "
      + "(MCP integrations, scripted clients, anything that just chains "
      + "calls by OID/ID) should default to `compact`.\n\n"
      + "Any `?return=` value other than `full` / `compact` returns "
      + "`400 Bad Request`. `GET`, `DELETE`, `list`, `search`, and the "
      + "timelog endpoints do not accept `?return=` — for them the "
      + "response shape is fixed."
  ))
public interface ApiConfig {
}
