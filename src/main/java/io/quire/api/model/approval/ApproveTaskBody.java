package io.quire.api.model.approval;

import io.swagger.annotations.ApiModelProperty;

/**
 * Request body for {@code POST /task/approve/{taskOid}}.
 *
 * The same endpoint handles every state transition — the {@code state}
 * token determines the action. To cancel an approval, use
 * {@code DELETE /task/revoke-approval/{taskOid}} instead.
 */
public class ApproveTaskBody {

    @ApiModelProperty(
        value = "(Optional) Approval category id. Omitted / `null` / "
              + "`\"\"` resolves to the project's default category "
              + "(id `\"\"`), which is always available. An unknown id "
              + "yields `404 Not Found`.",
        example = "legal"
    )
    public String getCategory() { return null; }

    @ApiModelProperty(
        value = "State transition to apply. Case-insensitive.\n"
              + "- `request` — request approval (or roll forward from "
              + "`approved` / `rejected` / `changes` back to `awaiting`).\n"
              + "- `approve` — approve the request.\n"
              + "- `reject` — reject the request.\n"
              + "- `change` — request changes.\n\n"
              + "The caller must be a claimer (for `request`) or an "
              + "approver (for `approve` / `reject` / `change`) of the "
              + "category; otherwise `403 Forbidden` is returned.",
        example = "request",
        allowableValues = "request, approve, reject, change",
        required = true
    )
    public String getState() { return null; }
}
