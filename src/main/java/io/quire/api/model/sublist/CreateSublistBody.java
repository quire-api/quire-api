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
}
