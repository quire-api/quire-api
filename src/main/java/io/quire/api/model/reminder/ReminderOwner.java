package io.quire.api.model.reminder;

import io.quire.api.model.work.*;
import io.swagger.annotations.*;

public class ReminderOwner extends WorkOwner {

    @Override
    @ApiModelProperty(
        value = "Kind of the owner: `Project`, `Organization`, `Folder` or `Lot` "
              + "(a smart folder). A personal reminder reports `Project` — it "
              + "belongs to the owner's own inbox, which is a project.",
        example = "Project"
    )
    public String getType() { return null; }
}
