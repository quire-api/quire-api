package io.quire.api.model.sublist;

import java.util.List;
import io.quire.api.model.work.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class UpdateSublistBody extends UpdateWorkBody {

    @ApiModelProperty(
        value = "(Optional) List of changes that add or remove tasks from this sublist. See `Change` for the operation schema."
    )
    public List<Change> getChanges() { return null; }

    @ApiModelProperty(
        value = "(Optional) Target start date for this record.",
        example = "2024-01-02"
    )
    public String getStart() { return null; }

    @ApiModelProperty(
        value = "(Optional) Target due date for this record.",
        example = "2024-05-25"
    )
    public String getDue() { return null; }
}
