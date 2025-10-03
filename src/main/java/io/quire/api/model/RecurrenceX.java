package io.quire.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class RecurrenceX extends Recurrence {

    @ApiModelProperty(
        value = "Identifier for the recurrence series this task belongs to. "
              + "Tasks that share the same seriesId are part of the same recurring series.",
        example = "j47xvxul26"
    )
    public String getSeriesId() { return null; }
}
