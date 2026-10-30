package io.quire.api.model.sublist;

import java.util.List;
import io.quire.api.model.work.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class UpdateSublistBody extends UpdateWorkBody {

    @ApiModelProperty(
        value = "(Optional) Tasks to add to or remove from this sublist, applied "
              + "in order. Each item is `{\"task\": \"<task OID>\", "
              + "\"exclude\": false, \"single\": false}`:\n\n"
              + "- `exclude: true` removes the task instead of adding it.\n"
              + "- `single: true` changes only the task itself; by default its "
              + "subtasks follow it.\n\n"
              + "A task that doesn't exist is skipped. Example — add one task "
              + "with its subtasks and leave another out:\n\n"
              + "```json\n"
              + "[\n"
              + "  {\"task\": \"2MmYOpJH_ZLeehIjjytH1Rwr\"},\n"
              + "  {\"task\": \"q8Rn1zX5bKcT0wGvJmLd3PaE\", \"exclude\": true}\n"
              + "]\n"
              + "```",
        example = "[{\"task\": \"2MmYOpJH_ZLeehIjjytH1Rwr\"}, "
                + "{\"task\": \"q8Rn1zX5bKcT0wGvJmLd3PaE\", \"exclude\": true}]"
    )
    public List<Change> getChanges() { return null; }

    @ApiModelProperty(
        value = "(Optional) Target start date for this record: a date only, "
              + "e.g. `2024-01-02`; a value with a time of day returns `400`.",
        example = "2024-01-02"
    )
    public String getStart() { return null; }

    @ApiModelProperty(
        value = "(Optional) Target due date for this record: a date only, "
              + "e.g. `2024-05-25`; a value with a time of day returns `400`.",
        example = "2024-05-25"
    )
    public String getDue() { return null; }
}
