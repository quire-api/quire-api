package io.quire.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel()
public class RecurrenceX extends Recurrence {
	@ApiModelProperty(example = "j47xvxul26",
		value = "The ID of a series of recurring tasks.\n\n"
		+ "If two tasks have the same series ID, they shall belong to "
		+ "the same series of recurring tasks.")
    public String getSeriesId() { return null; }
}
