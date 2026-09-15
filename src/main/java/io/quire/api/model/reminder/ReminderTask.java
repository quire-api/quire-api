package io.quire.api.model.reminder;

import io.quire.api.model.NamedEntity;
import io.swagger.annotations.*;

public class ReminderTask extends NamedEntity {

    @ApiModelProperty(
        value = "Task id, unique within its project.",
        example = "1234"
    )
    public String getId() { return null; }

    @ApiModelProperty(
        value = "URL of this task on the Quire website.",
        example = "https://quire.io/w/my_project/1234"
    )
    public String getUrl() { return null; }
}
