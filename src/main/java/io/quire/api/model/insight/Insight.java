package io.quire.api.model.insight;

import io.quire.api.model.field.FieldDefinition;
import io.quire.api.model.work.*;
import io.swagger.annotations.*;

import java.util.Map;

public class Insight extends Work {

    @Override
    @ApiModelProperty(
        value = "URL of this insight view on the Quire website.",
        example = "https://quire.io/w/my_project?insight=View101"
    )
    public String getUrl() { return null; }

    @Override
    @ApiModelProperty(
        value = "Owner this insight belongs to.",
        position = 99
    )
    public InsightOwner getOwner() { return null; }

    @ApiModelProperty(
        value = "Custom-field definitions for this insight view, keyed by field name. "
              + "Use the `add-field`, `update-field`, `remove-field`, `rename-field`, "
              + "and `move-field` extensions to mutate entries.",
        position = 70
    )
    public Map<String, FieldDefinition> getFields() { return null; }
}
