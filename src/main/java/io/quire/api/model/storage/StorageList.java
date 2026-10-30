package io.quire.api.model.storage;

import io.swagger.annotations.ApiModel;

// The keys are the caller's own, so it declares no properties.
@ApiModel(
    value = "StorageEntries",
    description = "Stored entries as one JSON object: each key maps to its "
        + "value, as it was stored. At most 20 entries, by key ascending; "
        + "`{}` if none.\n\n"
        + "Example: `{\"latest\": {\"project\": \"my_project\"}, "
        + "\"theme\": \"dark\"}`."
)
public class StorageList {
}
