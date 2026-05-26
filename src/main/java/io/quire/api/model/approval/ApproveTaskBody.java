package io.quire.api.model.approval;

import io.swagger.annotations.ApiModelProperty;

/**
 * Optional request body for
 * {@code POST /task/approve/id/{projectId}/{taskId}} (or the OID form
 * {@code POST /task/approve/{taskOid}}).
 *
 * <p>The body is <em>optional</em>: omit it (or send an empty body) to
 * record only the approval transition. Send it to post a companion
 * comment on the task in addition to the transition.</p>
 *
 * <p>Example: {@code {"description": "Please tweak the wording."}}</p>
 *
 * <p>Server behavior when the body is supplied:</p>
 * <ul>
 * <li>The comment is created on the task as a side effect of the
 *     approval call (same transaction — if either operation fails, both
 *     roll back).</li>
 * <li>The stored comment description is prefixed with a bold
 *     {@code **<stream>: <status>**} line + a blank line + the supplied
 *     {@code description}. Example: posting
 *     {@code {"description": "Please tweak the wording."}} on a
 *     {@code state=change} call with {@code ?category=Legal} stores
 *     {@code **Legal: Request changes**\n\nPlease tweak the wording.}
 *     The prefix is applied for every {@code state} value.</li>
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
              + "Required if the body is supplied. The server prepends "
              + "a bold `**<stream>: <status>**` line and a blank line "
              + "before this text, so this field should contain only the "
              + "human-written message — do not include the prefix yourself.",
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
