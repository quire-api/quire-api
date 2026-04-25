package io.quire.api.model.insight;

import io.quire.api.model.work.*;
import io.swagger.annotations.ApiModel;

/**
 * Request body for {@code PUT /insight/{oid}} (and the by-ID form).
 *
 * Inherits all fields from [UpdateWorkBody](#definition-UpdateWorkBody) —
 * {@code id}, {@code name}, {@code description}, {@code iconColor},
 * {@code image}, {@code start}, {@code due}, {@code archived}. Any field
 * omitted from the request leaves its current value unchanged.
 */
@ApiModel(parent = UpdateWorkBody.class,
    description = "Request body for updating an insight view. "
                + "Extends UpdateWorkBody with no additional fields; "
                + "omitted fields preserve existing values.")
public class UpdateInsightBody extends UpdateWorkBody {
}
