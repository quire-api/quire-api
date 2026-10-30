package io.quire.api.model.project;

import io.swagger.annotations.*;

import java.util.List;
import java.util.Map;

@ApiModel(description = "A project exported as JSON. Besides the keys below, "
    + "it carries the project's own settings, such as `description`, "
    + "`fields`, `states`, `followers` and `createdAt`.")
public class ProjectJsonMap {

    @ApiModelProperty(
        value = "Object identifier (OID), a UUID-like unique string.",
        example = "Dyh2YkFcu9uLgLFIeN1kB4Ld"
    )
    public String getOid() { return null; }

    @ApiModelProperty(
        value = "Project ID.",
        example = "My_Project"
    )
    public String getId() { return null; }

    @ApiModelProperty(
        value = "Display name of the project.",
        example = "My Project"
    )
    public String getName() { return null; }

    @ApiModelProperty(
        value = "Version of the export format.",
        example = "9.0.0"
    )
    public String getVersion() { return null; }

    @ApiModelProperty(
        value = "Root tasks matching `?status=`. Each carries its subtasks "
              + "under `tasks`, and its `comments` and `reminders`."
    )
    public List<Map<String, Object>> getTasks() { return null; }

    @ApiModelProperty(
        value = "OIDs of the project's members."
    )
    public List<String> getMembers() { return null; }

    @ApiModelProperty(
        value = "The users the export refers to, such as members, "
              + "creators and assignees."
    )
    public List<Map<String, Object>> getUsers() { return null; }

    @ApiModelProperty(
        value = "The project's tags, and its organization's global tags."
    )
    public List<Map<String, Object>> getTags() { return null; }

    @ApiModelProperty(
        value = "The project's external teams. Omitted when the user is in "
              + "an external team."
    )
    public List<Map<String, Object>> getPartners() { return null; }

    @ApiModelProperty(
        value = "The project's reminders that aren't on a task; a task's "
              + "reminders are under that task. Omitted if none."
    )
    public List<Map<String, Object>> getReminders() { return null; }

    @ApiModelProperty(
        value = "The project's sublists."
    )
    public List<Map<String, Object>> getSublists() { return null; }

    @ApiModelProperty(
        value = "The project's documents."
    )
    public List<Map<String, Object>> getDocs() { return null; }

    @ApiModelProperty(
        value = "The project's chats, each with its `comments`."
    )
    public List<Map<String, Object>> getChats() { return null; }

    @ApiModelProperty(
        value = "The project's insights."
    )
    public List<Map<String, Object>> getInsights() { return null; }

    @ApiModelProperty(
        value = "The project's dashboards."
    )
    public List<Map<String, Object>> getDashboards() { return null; }
}
