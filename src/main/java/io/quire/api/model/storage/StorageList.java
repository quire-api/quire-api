package io.quire.api.model.storage;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class StorageList {

    @ApiModelProperty(
        value = "Value stored under the key `currentProject` (e.g., a project OID).",
        example = "Dyh2YkFcu9uLgLFIeN1kB4Ld"
    )
    public String getCurrentProject() { return null; }

    @ApiModelProperty(
        value = "Object stored under the key `latest` (key–value map of properties)."
    )
    public StorageMap getLatest() { return null; }

    @ApiModelProperty(
        value = "List stored under the key `myList`.",
        example = "[\"alpha\", \"beta\", \"gamma\"]"
    )
    public List<String> getMyList() { return null; }
}
