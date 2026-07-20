package io.quire.api.model.dashboard;

import io.quire.api.model.work.*;
import io.swagger.annotations.ApiModel;

/**
 * Request body for {@code PUT /dashboard/{oid}} (and the by-ID form).
 *
 * Inherits all fields from [UpdateWorkBody](#definition-UpdateWorkBody) —
 * {@code id}, {@code name}, {@code description}, {@code iconColor},
 * {@code image}, {@code start}, {@code due}, {@code archived}. Any field
 * omitted from the request leaves its current value unchanged.
 */
@ApiModel(parent = UpdateWorkBody.class,
    description = "Request body for updating a dashboard. "
                + "Extends UpdateWorkBody with no additional fields; "
                + "omitted fields preserve existing values.")
public class UpdateDashboardBody extends UpdateWorkBody {
}
