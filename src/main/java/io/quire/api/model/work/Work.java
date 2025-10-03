package io.quire.api.model.work;

import io.quire.api.model.*;
import io.swagger.annotations.*;

public class Work extends Identity {

    @ApiModelProperty(
        value = "Display name (Markdown supported).",
        example = "Highlight 101"
    )
    public String getName() { return null; }

    @ApiModelProperty(
        value = "Name with Markdown removed.",
        example = "Highlight 101"
    )
    public String getNameText() { return null; }

    @ApiModelProperty(
        value = "Name rendered as an HTML fragment converted from Markdown.",
        example = "Highlight 101"
    )
    public String getNameHtml() { return null; }

    @ApiModelProperty(
        value = "ID.",
        example = "Highlight101"
    )
    public String getId() { return null; }

    @ApiModelProperty(
        value = "URL of this record on the Quire website.",
        example = "https://quire.io/w/my_project?sublist=Highlight101"
    )
    public String getUrl() { return null; }

    @ApiModelProperty(
        value = "External team this record belongs to. Null if the record is not accessible to external-team members.",
        position = 15
    )
    public SimpleTaggingEntity getPartner() { return null; }

    @ApiModelProperty(
        value = "Timestamp (UTC, ISO 8601) when this record was archived. Null if not archived.",
        example = "2020-02-22T02:06:58.158Z",
        position = 50
    )
    public String getArchivedAt() { return null; }

    @ApiModelProperty(
        value = "Target start date for this record, or null if not specified.",
        example = "2024-01-02",
        position = 50
    )
    public String getStart() { return null; }

    @ApiModelProperty(
        value = "Target due date for this record, or null if not specified.",
        example = "2024-05-25",
        position = 50
    )
    public String getDue() { return null; }

    @ApiModelProperty(
        value = "Project this record belongs to.",
        position = 99
    )
    public WorkOwner getOwner() { return null; }

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
}
