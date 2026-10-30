package io.quire.api.model.sublist;

import io.quire.api.model.work.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class CreateSublistBody extends CreateWorkBody {

    @ApiModelProperty(
        value = "(Optional) List of task OIDs to include in this sublist. "
              + "All descendants of the specified tasks will be included as well."
    )
    public List<String> getIncludes() { return null; }

    @ApiModelProperty(
        value = "(Optional) Whether every task belongs to the sublist by default. "
              + "Default: `false` — only the tasks in `includes` are in it, with "
              + "their subtasks. With `true`, every task of the owner is in it; "
              + "leave tasks out afterwards by updating the sublist with "
              + "`changes` (`\"exclude\": true`). Settable only when creating "
              + "the sublist.",
        example = "false"
    )
    public Boolean getAll() { return null; }

    @ApiModelProperty(
        value = "(Optional) Target start date: a date only, e.g. `2024-01-02`; "
              + "a value with a time of day returns `400`.",
        example = "2024-01-02",
        position = 50
    )
    public String getStart() { return null; }

    @ApiModelProperty(
        value = "(Optional) Target due date: a date only, e.g. `2024-05-25`; "
              + "a value with a time of day returns `400`.",
        example = "2024-05-25",
        position = 50
    )
    public String getDue() { return null; }
}
