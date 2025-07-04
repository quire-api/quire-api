# CHANGES

## July 4, 2025

* Fixes an issue with the search API where formula fields using parent and ancestors were not evaluated correctly.

## June 12, 2025

* Adds [The source ref](https://quire.io/dev/api/#operation--task-id--projectId--post) for apps to store app specific data.
* Output custom fields of the task for WebHook callback.
* Output the ID of a series of recurring tasks. It can be found in `recurrence.seriesId`.

## May 29, 2025

* Allows to upload an attachment and add to a task or a comment.
  - [Upload API for tasks](https://quire.io/dev/api/#operation--task-attach--taskOid---filename--post)
  - [Upload API for comments](https://quire.io/dev/api/#operation--comment-attach--commentOid---filename--post)

## May 13, 2025

* Adds [Chat Channel API](https://quire.io/dev/api/#tag-chat).
* [Comment API](https://quire.io/dev/api/#tag-comment) supports chat channels.
* Incompatibility:
  - Comments can't be posted to a project. Rather, use chat channels instead.

## Apr 6, 2025

* Adds new [Search API](https://quire.io/dev/api/#operation--task-search-organization-id--organizationId--get) for searching tasks under an organization or a folder.

## Mar 15, 2025

* [Search API](https://quire.io/dev/api/#operation--task-search-id--projectId--get) allows to specify days, hours and minutes in the `modified` and `commented` parameters.

## Mar 13, 2025

* [Search API](https://quire.io/dev/api/#operation--task-search-id--projectId--get) adds the `modified` and `commented` parameters for searching tasks modified and/or commented recently.

## Feb 12, 2025

* Introduces API for manipulating [documents](https://quire.io/dev/api/#tag-doc).

## Feb 5, 2025

* Allows to specify `"inherit"` as one of followers and assignees to inherit the parent task's followers and assignees.
  - For more details, please [refer here](https://quire.io/dev/api/#operation--task-id--projectId--post)
* Support `interval` for monthly and yearly recurrence

## Dec 30, 2024

* A system event, `host-revocation`, for revoking host from a token is added.

## Sept 19, 2024

* Allows to follow or unfollow an organization for WebHook
    - [Organization Activities](https://github.com/quire-api/quire-api/blob/master/docs/activity_types.md#organization-related-activities)

## Sept 6, 2024

* *BREAK CHANGE* The `recurring` field of task is renamed to `recurrence`, and the syntax has been changed too.

## April 16, 2024

* Sublist supports start and due.

## March 27, 2024

* Allow to create sublist for organizations, folders and smart folders.

## Feburary 16, 2024

* Allow to add or remove successors.

## September 26, 2023

* Allow to add a follower that receives only notifications that match the notification setting of the user: `app|team|channel|mine`

## September 1, 2023

* Activity 84, [detail](https://github.com/quire-api/quire-api/blob/master/docs/activity_types.md), replaced with 11.
  * Also, there are two extra fields, `status` and `previousStatus`, are added for Activity 5, 6 and 11 to indicate the new and previous status.

## August 23, 2023

* Support custom fields

## August 12, 2023

* Allows the redirect URL to be `localhost`.

## July 21, 2023

* An addition field called `tasks` is added to [Notification Events](https://quire.io/dev/api/#webhook) for activities that can affect multiple tasks. Refer to [Activity Types](https://github.com/quire-api/quire-api/blob/master/docs/activity_types.md).

## July 13, 2023

* `peekaboo=false` means it won't be reshowed automatically.

## April 10, 2023

* `Section` supported

## March 26, 2023

* Content's size is limited to 1MB

## Feburary 24, 2023

* `Timelogs` supported
* `etc` supported

## March 22, 2022

* API to export project into a CSV string is added
  * `GET /project/export-csv`

## February 25, 2022

* API to export project into a JSON map is added
  * `GET /project/export-json`

## January 17, 2022

* Storage API's GET: It returns 404 if not found.
    * Old Spec: it returns 200 with an empty body.

## April 27, 2021

* URL to get, update or delete a comment has been changed.
   * The caller must specify project's ID or OID. For example, `comment/id/projectId/commentOid`.

## November, 2020

* APIs to manange sublists are added.

## October 30, 2020

* We simplified the format of `due` and `start`. If you'd like to specify time, use `yyyy-mm-ddThh:mmZ`, e.g., `2020-10-30T09:30Z`. If you don't like to set time, use `yyyy-mm-dd`, e.g., `2020-10-30`. Note: they must be in UTC time.

## October 22, 2020

* The detailed information will be returned as part of `assignees`, `tags` and other fields. For example, the `project` property of the returned task record will be a map of OID, name, color, image and URL.
  * Note: many APIs are changed. Please adjust your application accordingly.
* WebHook added an extra field called `value` to carry the detailed information about a notification. For example, it carries the assignee's ID, name and URL if it is an assignment.

## October 13, 2020

* You can search tasks by matching the name and/or description with a regular expression. For case-insensitive matching, you can prefix the regular expression with `~*`. For case senstive, prefix with `~`.
  * Example: `https://quire.io/api/task/search/id/your-project?name=~Foo[s]&description=~*(green|blue)`

## October 8, 2020

* You can add a task to My Tasks by specifying `-` as the project's ID.
  * Example: `https://quire.io/api/task/id/-`
* You can add a tag to My Tasks by specifying `-`  as the project's ID too.

## May 27, 2020

* To retrieve only the projects that the current user can add tasks to, you can specify `add-task=true`.
  * Example: `https://quire.io/api/project/list?add-task=true`
* To retrieve archived projects, you can specify `archived=true`. Unlike `add-task`, non-archived projects will be returned too.
