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
        value = "(Optional) Target start date.",
        example = "2024-01-02",
        position = 50
    )
    public String getStart() { return null; }

    @ApiModelProperty(
        value = "(Optional) Target due date.",
        example = "2024-05-25",
        position = 50
    )
    public String getDue() { return null; }
}
