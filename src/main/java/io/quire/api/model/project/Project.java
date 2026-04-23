package io.quire.api.model.project;

import io.quire.api.model.*;
import io.quire.api.model.approval.AppvCat;
import io.quire.api.model.field.FieldDefinition;
import io.swagger.annotations.*;

import java.util.List;
import java.util.Map;

public class Project extends Identity {

    @ApiModelProperty(
        value = "Display name of the project (Markdown supported).",
        example = "My Project"
    )
    public String getName() { return null; }

    @ApiModelProperty(
        value = "Project name with Markdown removed.",
        example = "My Project"
    )
    public String getNameText() { return null; }

    @ApiModelProperty(
        value = "Project name rendered as an HTML fragment converted from Markdown.",
        example = "My Project"
    )
    public String getNameHtml() { return null; }

    @ApiModelProperty(
        value = "Project ID.",
        example = "my_project"
    )
    public String getId() { return null; }

    @ApiModelProperty(
        value = "URL of this project on the Quire website.",
        example = "https://quire.io/w/my_project"
    )
    public String getUrl() { return null; }

    @ApiModelProperty(
        value = "Organization this project belongs to."
    )
    public SimpleIdentity getOrganization() { return null; }

    @ApiModelProperty(
        value = "Total number of tasks in this project.",
        example = "30"
    )
    public int getTaskCount() { return 0; }

    @ApiModelProperty(
        value = "Number of active (not completed) tasks in this project.",
        example = "20"
    )
    public int getActiveCount() { return 0; }

    @ApiModelProperty(
        value = "Number of root (top-level) tasks in this project.",
        example = "5"
    )
    public int getRootCount() { return 0; }

    @ApiModelProperty(
        value = "Timestamp (UTC, ISO 8601) when this record was last edited.",
        example = "2018-12-22T02:06:58.158Z",
        position = 50
    )
    public String getEditedAt() { return null; }

    @ApiModelProperty(
        value = "Timestamp (UTC, ISO 8601) when this project was archived (peekaboo). Null if not archived.",
        example = "2018-12-22T02:06:58.158Z",
        position = 50
    )
    public String getArchivedAt() { return null; }

    @ApiModelProperty(
        value = "Timestamp (UTC, ISO 8601) when this project was made public. Null if not public.",
        example = "2018-12-22T02:06:58.158Z",
        position = 50
    )
    public String getPublicAt() { return null; }

    @ApiModelProperty(
        value = "Target start date for this project. Null if not set.",
        example = "2024-01-02",
        position = 50
    )
    public String getStart() { return null; }

    @ApiModelProperty(
        value = "Target due date for this project. Null if not set.",
        example = "2024-05-25",
        position = 50
    )
    public String getDue() { return null; }

    @ApiModelProperty(
        value = "Creation timestamp in UTC (ISO 8601).",
        example = "2018-12-22T02:06:58.158Z",
        position = 99
    )
    public String getCreatedAt() { return null; }

    @ApiModelProperty(
        value = "User who created this record.",
        position = 99
    )
    public SimpleIdentity getCreatedBy() { return null; }

    @ApiModelProperty(
        value = "Users who follow this project.",
        position = 60
    )
    public List<SimpleIdentity> getFollowers() { return null; }

    @ApiModelProperty(
        value = "Attachments associated with this project.",
        position = 60
    )
    public List<Attachment> getAttachments() { return null; }

    @ApiModelProperty(
        value = "Custom-field definitions for this project, keyed by field name. "
              + "Use the `add-field`, `update-field`, `remove-field`, `rename-field`, "
              + "and `move-field` extensions to mutate entries.",
        position = 70
    )
    public Map<String, FieldDefinition> getFields() { return null; }

    @ApiModelProperty(
        value = "Approval categories defined on this project. "
              + "Use the `add-appv-cat`, `update-appv-cat`, and "
              + "`remove-appv-cat` extensions to mutate entries. Null "
              + "when only the implicit default category is in effect.",
        position = 70
    )
    public List<AppvCat> getApprovalCategories() { return null; }
}
