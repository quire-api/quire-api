package io.quire.api.model.storage;

import io.swagger.annotations.ApiModel;

// A stored value is any JSON value, so it declares no properties.
@ApiModel(
    value = "StorageValue",
    description = "A stored value: any JSON value, such as an object, array, "
        + "string or number, at most 4 KiB as JSON. `GET /storage/{name}` "
        + "returns it exactly as `PUT /storage/{name}` stored it.\n\n"
        + "Example: after `PUT /storage/latest` with the body "
        + "`{\"project\": \"my_project\"}`, `GET /storage/latest` returns "
        + "`{\"project\": \"my_project\"}`."
)
public class StorageMap {
}
