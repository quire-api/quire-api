package io.quire.api.model.approval;

import io.swagger.annotations.ApiModelProperty;

/**
 * Optional request body for
 * {@code POST /task/approve/id/{projectId}/{taskId}} (or the OID form
 * {@code POST /task/approve/{taskOid}}).
 *
 * <p>The body is <em>optional</em>. Without it, {@code approve} /
 * {@code reject} / {@code change} still log a comment holding only the
 * {@code <category>: <status>} header, as the apps do, and {@code request}
 * logs nothing. Send it to add your own text to that comment.</p>
 *
 * <p>Example: {@code {"description": "Please tweak the wording."}}</p>
 *
 * <p>Server behavior when the body is supplied:</p>
 * <ul>
 * <li>The comment is created on the task as a side effect of the
 *     approval call (same transaction — if either operation fails, both
 *     roll back).</li>
 * <li>The stored comment description starts with a plain
 *     {@code <category>: <status>} line + a blank line + the supplied
 *     {@code description}. The category name is left out for the default
 *     category, and the status is in the authorizing user's language.
 *     Example: posting {@code {"description": "Please tweak the wording."}}
 *     on a {@code state=change} call for the category {@code legal},
 *     named {@code Legal Review}, stores
 *     {@code Legal Review: Request changes\n\nPlease tweak the wording.}
 *     The header is applied for every {@code state} value.</li>
 * <li>The created comment does <em>not</em> appear in the approval
 *     response. To fetch it, call
 *     {@code GET /comment/list/{taskOid}} after the approval call.</li>
 * </ul>
 *
 * <p>To cancel an approval, use
 * {@code DELETE /task/revoke-approval/id/{projectId}/{taskId}}
 * (or the OID form) — not this endpoint.</p>
 */
public class ApproveTaskBody {

    @ApiModelProperty(
        value = "Content of the companion comment (Markdown supported). "
              + "An embedded `data:` image is rejected — upload the image as an attachment instead. "
              + "Required if the body is supplied. The server prepends "
              + "a `<category>: <status>` line (status in the authorizing "
              + "user's language; no category name for the default "
              + "category) and a blank line before this text, so this "
              + "field should contain only the human-written message — "
              + "do not include the header yourself.",
        example = "Please tweak the wording.",
        required = true
    )
    public String getDescription() { return null; }

    @ApiModelProperty(
        value = "(Optional) Whether to pin the companion comment on the "
              + "task. Default: `false`.",
        example = "false",
        required = false
    )
    public boolean getPinned() { return false; }

    @ApiModelProperty(
        value = "(Optional) If `true`, the comment is attributed to the "
              + "app rather than the authorizing user (matches the "
              + "`asUser` flag on `POST /comment`). Default: `false`.",
        example = "false",
        required = false
    )
    public boolean getAsUser() { return false; }
}
