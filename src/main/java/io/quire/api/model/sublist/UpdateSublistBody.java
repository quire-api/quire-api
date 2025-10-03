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
}
