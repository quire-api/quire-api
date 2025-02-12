package io.quire.api.model.sublist;

import java.util.List;
import io.quire.api.model.work.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class UpdateSublistBody extends UpdateWorkBody {
	@ApiModelProperty(value = "(Optional) A list of changes to control what tasks to be "
		+ "added to or removed from this sublist.")
	public List<Change> getChanges() { return null; }
}
