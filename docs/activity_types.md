# Activity Types

## Task Related Activities

| Type | Description | `what` | `tasks` | 
|--|--|--|--|
| 0 | Adds a task. | Task | |
| 1 | Removes a task. | Task | All tasks being removed. |
| 3 | Edits the content of a task. | Task | |
| 5 | Completes a task. | Task | All tasks being completed. |
| 6 | Reopens a task. | Task | All tasks being reopened. |
| 7 | Assigns a member to a task. | Task | |
| 8 | Un-assigns a member from a task. | Task | |
| 9 | Sets a due date to a task. | Task | |
| 10 | Clears the due date. | Task | |
| 11 | Changes the status. | Task | |
| 12 | Peekaboos a task. | Task | All tasks being peekabooed. |
| 13 | Reshows a task. | Task | All tasks being reshowed. |
| 16 | Adds a comment. | Task | |
| 17 | Undo the completing. | Task | All tasks being reopened. |
| 18 | Undo the removal. | Task | All tasks being un-removed. |
| 19 | Undo the peekabooing. | Task | All tasks being reshowed. |
| 20 | Adds an attachment. | Task | |
| 21 | Removes an attachment. | Task | |
| 24 | Sets an external team to a task. | Task | |
| 25 | Removes an external team from a task. | Task | |
| 27 | Removes a comment from a task. | Task | |
| 28 | Adds a tag to a task. | Task | |
| 29 | Removes a tag from a task. | Task | |
| 30 | Transfers a task to another project. | Task | |
| 31 | Duplicates a task. | Task | |
| 32 | Mentions a member in a comment or description. | Task | |
| 33 | Duplicates a recurring task automatically when completed. | Task | |
| 34 | Edits the time track. | Task | |
| 35 | Sets the priority. | Task | |
| 36 | Changes the task type. | Task | |
| 38 | Sets a start date to a task. | Task | |
| 39 | Clears the start date. | Task | |

*Notes*

- For 5, 6 and 11, there are two extra fields, `status` and `previousStatus`, to indicate the new and previous status.

## Project Related Activities

| Type | Description | What | 
|--|--|--|
| 100 | Creates a project. | Project |
| 101 | Removes a project. | Project |
| 102 | Undo the removal. | Project |
| 103 | Edits the content of a project. | Project |
| 104 | Adds a member to a project. | Project |
| 105 | Removes a member from a project. | Project |
| 106 | Adds a member to an external team. | Project |
| 107 | Removes a member from an external team. | Project |
| 108 | Transfers a project to another organization. | Project |
| 109 | Adds a comment. | Project |
| 110 | Adds an attachment. | Project |
| 111 | Removes an attachment. | Project |
| 113 | Removes a comment. | Project |
| 114 | Adds a new tag. | Project |
| 115 | Removes a tag from a project. | Project |
| 118 | Adds an external team. | Project |
| 119 | Removes an external team. | Project |
| 122 | Exports a project. | Project |
| 123 | Imports to a project. | Project |
| 124 | Duplicates a project. | Project |
| 130 | Sets a project as public. | Project |
| 131 | Sets a project as private. | Project |
| 132 | Mentions a member in a comment or description. | Project |
| 133 | Configures the Github integration. | Project |
| 134 | Changes the permission of a project member. | Project |
| 135 | Changes the permission of an external team member. | Project |
| 136 | Archives a project. | Project |
| 137 | Unarchives a project. | Project |
| 138 | Removes the Github integration. | Project |
| 139 | Adds a share link to a project. | Project |
| 140 | Removes a share link. | Project |
| 146 | Adds a new sublist. | Project |
| 147 | Removes a sublist. | Project |
| 148 | Adds a new status. | Project |
| 149 | Removes a status. | Project |
| 150 | Enables the mailbox (Email-to-Quire) for a project. | Project |
| 151 | Disables the mailbox for a project. | Project |
