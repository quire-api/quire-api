package io.quire.api.model.task;

import io.quire.api.model.*;
import io.swagger.annotations.*;

import java.util.*;

public class SimpleTask extends StampedEntity {

    @ApiModelProperty(
        example = "12",
        position = 1
    )
    public int getId() { return 0; }

    @ApiModelProperty(
        value = "Task name (Markdown supported).",
        example = "Design new **logo**",
        position = 2
    )
    public String getName() { return null; }

    @ApiModelProperty(
        value = "Task name with Markdown removed.",
        example = "Design new logo",
        position = 2
    )
    public String getNameText() { return null; }

    @ApiModelProperty(
        value = "Task name rendered as an HTML fragment converted from Markdown.",
        example = "Design new <b>logo</b>",
        position = 2
    )
    public String getNameHtml() { return null; }

    @ApiModelProperty(
        value = "Task description (Markdown supported).",
        example = "This is a *cool* task.",
        position = 3
    )
    public String getDescription() { return null; }

    @ApiModelProperty(
        value = "Task description with Markdown removed.",
        example = "This is a cool task.",
        position = 3
    )
    public String getDescriptionText() { return null; }

    @ApiModelProperty(
        value = "Task description rendered as an HTML fragment converted from Markdown.",
        example = "This is a <i>cool</i> task.",
        position = 3
    )
    public String getDescriptionHtml() { return null; }

    @ApiModelProperty(
        value = "Task status, from 0 to 100. `100` indicates the task is completed.",
        example = "0",
        position = 4
    )
    public int getStatus() { return 0; }

    @ApiModelProperty(
        value = "Task priority, from -1 (lowest) to 2 (highest). Default: 0.",
        example = "0",
        position = 4
    )
    public int getPriority() { return 0; }

    @ApiModelProperty(
        value = "Tags applied to this task."
    )
    public List<SimpleTaggingEntity> getTags() { return null; }

    @ApiModelProperty(
        value = "Start date/time in UTC.\n"
              + "If a time component is present, milliseconds are set to `001`.\n"
              + "If date-only, milliseconds are `000` (and hour, minute, second are also zero).",
        example = "2018-12-20T00:00:00.000Z",
        position = 4
    )
    public String getStart() { return null; }

    @ApiModelProperty(
        value = "Due date/time in UTC.\n"
              + "If a time component is present, milliseconds are set to `001`.\n"
              + "If date-only, milliseconds are `000` (and hour, minute, second are also zero).",
        example = "2018-12-22T00:00:00.000Z",
        position = 4
    )
    public String getDue() { return null; }

    @ApiModelProperty(
        value = "Users assigned to this task.",
        position = 5
    )
    public List<SimpleIdentity> getAssignees() { return null; }

    @ApiModelProperty(
        value = "Successor task identifiers.",
        example = "['#135', '#26']"
    )
    public List<String> getSuccessors() { return null; }

    @ApiModelProperty(
        value = "Whether the task is currently peekabooed (hidden).",
        example = "true",
        position = 50
    )
    public boolean getPeekaboo() { return false; }
    // If you intend tri-state (true/false/unknown), change return type to Boolean and adjust description accordingly.

    @ApiModelProperty(
        value = "Whether this task is a section.",
        example = "true",
        position = 51
    )
    public boolean getSection() { return false; }

    @ApiModelProperty(
        value = "Whether this task is a milestone.",
        example = "true",
        position = 52
    )
    public boolean getMilestone() { return false; }

    @ApiModelProperty(
        value = "URL of this task on the Quire website.",
        example = "https://quire.io/w/my_project/123",
        position = 53
    )
    public String getUrl() { return null; }

    @ApiModelProperty(
        value = "Source reference data stored by an app.",
        example = "{'text': 'Source: [Gmail](https://gmail.com/link)'}",
        position = 60
    )
    public Map<String, Object> getSourceRef() { return null; }
}
