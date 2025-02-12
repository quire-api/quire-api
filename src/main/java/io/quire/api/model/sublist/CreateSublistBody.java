package io.quire.api.model.sublist;

import io.quire.api.model.work.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel()
public class CreateSublistBody extends CreateWorkBody {
	@ApiModelProperty(value = "(Optional) A list of tasks' OID that belong to this sublist.\n"
		+ "Note: all of descendants will be included too.")
	public List<String> getIncludes() { return null; }
}
