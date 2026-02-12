# Activity Types

## Task-related activities

| Type | Description | `what` | [`taskSummaries`](#tasksummaries) |
|--:|--|--|--|
| 0 | Adds a task. | Task | All tasks being added. |
| 1 | Removes a task. | Task | All tasks being removed. |
| 3 | Edits a task’s details. | Task | |
| 5 | Completes a task. | Task | All tasks being completed. |
| 6 | Reopens a task. | Task | All tasks being reopened. |
| 7 | Assigns a member to a task. | Task | |
| 8 | Unassigns a member from a task. | Task | |
| 9 | Sets a due date for a task. | Task | |
| 10 | Clears the due date. | Task | |
| 11 | Changes the status. | Task | |
| 12 | Peekaboos a task. | Task | All tasks being peekabooed. |
| 13 | Reshows a task. | Task | All tasks being reshowed. |
| 14 | Sets a value on a task’s custom field. | Task | |
| 15 | Removes the value from a task’s custom field. | Task | |
| 16 | Adds a comment. | Task | |
| 17 | Undoes completion. | Task | All tasks being reopened. |
| 18 | Undoes removal. | Task | All tasks being un-removed. |
| 19 | Undoes peekaboo. | Task | All tasks being reshowed. |
| 20 | Adds an attachment. | Task | |
| 21 | Removes an attachment. | Task | |
| 24 | Sets an external team on a task. | Task | |
| 25 | Removes an external team from a task. | Task | |
| 26 | Undoes comment removal. | Task | |
| 27 | Removes a comment from a task. | Task | |
| 28 | Adds a tag to a task. | Task | |
| 29 | Removes a tag from a task. | Task | |
| 30 | Transfers a task to another project. | Task | |
| 31 | Duplicates a task. | Task | All tasks being added. |
| 32 | Mentions a member in a comment or description. | Task | |
| 33 | Duplicates a recurring task automatically upon completion. | Task | All tasks being added. |
| 34 | Edits a time log. | Task | |
| 35 | Sets the priority. | Task | |
| 36 | Changes the task type. | Task | |
| 37 | Edits a comment. | Task | |
| 38 | Sets a start date for a task. | Task | |
| 39 | Clears the start date. | Task | |

**Notes**

- For types `5`, `6`, and `11`, two extra fields are included:  
  `status` (new value) and `previousStatus` (prior value).

### taskSummaries

An addition field for for activities that can affect multiple tasks -- for example, removing a task that has subtasks (`1`). This field is a list of `map` instances. Each `map` represents a task that was changed. If any of that task's subtasks were also changed, they are included in the map’s `tasks` field.

For example, suppose there are two tasks, `A` and `B`, where `B` is a subtask of `A`. When `A` is removed, the hook will receive the following content in `taskSummaries`.

```
[
  {
    "id": 1,
    "name": "A",
    "tasks": [
      {
        "id": 2,
        "name": "B",
        "due": "2026-05-25"
      }
    ]
  }
]
```

---

## Project-related activities

| Type | Description | `what` |
|--:|--|--|
| 100 | Creates a project. | Project |
| 101 | Removes a project. | Project |
| 102 | Undoes removal. | Project |
| 103 | Edits a project’s details. | Project |
| 104 | Adds a member to a project. | Project |
| 105 | Removes a member from a project. | Project |
| 106 | Adds a member to an external team. | Project |
| 107 | Removes a member from an external team. | Project |
| 108 | Transfers a project to another organization. | Project |
| 110 | Adds an attachment. | Project |
| 111 | Removes an attachment. | Project |
| 114 | Adds a new tag. | Project |
| 115 | Removes a tag from a project. | Project |
| 116 | Sets a project start date. | Project |
| 117 | Clears a project start date. | Project |
| 118 | Adds an external team. | Project |
| 119 | Removes an external team. | Project |
| 120 | Sets a project due date. | Project |
| 121 | Clears a project due date. | Project |
| 122 | Exports a project. | Project |
| 123 | Imports into a project. | Project |
| 124 | Duplicates a project. | Project |
| 130 | Sets a project to public. | Project |
| 131 | Sets a project to private. | Project |
| 133 | Configures the GitHub integration. | Project |
| 134 | Changes a project member’s permission. | Project |
| 135 | Changes an external team member’s permission. | Project |
| 136 | Archives a project. | Project |
| 137 | Unarchives a project. | Project |
| 138 | Removes the GitHub integration. | Project |
| 139 | Adds a share link to a project. | Project |
| 140 | Removes a share link. | Project |
| 141 | Adds a new doc. | Project |
| 143 | Removes a doc. | Project |
| 146 | Adds a new sublist. | Project |
| 147 | Removes a sublist. | Project |
| 148 | Adds a new status. | Project |
| 149 | Removes a status. | Project |
| 150 | Enables the mailbox (Email-to-Quire). | Project |
| 151 | Disables the mailbox. | Project |
| 152 | Adds a custom field. | Project |
| 153 | Removes a custom field. | Project |
| 154 | Adds an approval category. | Project |
| 158 | Undoes sublist removal. | Project |
| 159 | Adds a chat channel. | Project |
| 160 | Edits a chat channel. | Project |
| 161 | Removes a chat channel. | Project |
| 162 | Undoes chat channel removal. | Project |
| 163 | Posts a message to a chat channel. | Project |
| 164 | Removes a message from a chat channel. | Project |
| 165 | Undoes message removal from a chat channel. | Project |
| 166 | Mentions a member in a chat message. | Project |
| 167 | Sets a sublist start date. | Project |
| 168 | Clears a sublist start date. | Project |
| 169 | Sets a sublist due date. | Project |
| 170 | Clears a sublist due date. | Project |
| 175 | Archives a sublist. | Project |
| 176 | Unarchives a sublist. | Project |
| 177 | Archives a document. | Project |
| 178 | Unarchives a document. | Project |
| 179 | Archives a chat channel. | Project |
| 180 | Unarchives a chat channel. | Project |
| 181 | Edits a chat message. | Project |

---

## Organization-related activities

| Type | Description | `what` |
|--:|--|--|
| 204 | Adds a member to an organization. | Organization |
| 205 | Removes a member from an organization. | Organization |
