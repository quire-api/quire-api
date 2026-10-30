package io.quire.api.model.reminder;

import io.quire.api.model.NamedEntity;
import io.swagger.annotations.*;

public class ReminderTask extends NamedEntity {

    @ApiModelProperty(
        value = "Task ID, unique within its project.",
        example = "42"
    )
    public int getId() { return 0; }

    @ApiModelProperty(
        value = "URL of this task on the Quire website.",
        example = "https://quire.io/w/my_project/42"
    )
    public String getUrl() { return null; }
}
