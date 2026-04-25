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
      + "against the quota."
  ))
public interface ApiConfig {
}
