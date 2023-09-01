# CHANGES

## September 1, 2023

* Activity 84, [detail](https://github.com/quire-api/quire-api/blob/master/docs/activity_types.md#project-related-activities), replaced with 11.
  * Also, there are two extra fields, `status` and `previousStatus`, are added for Activity 5, 6 and 11 to indicate the new and previous status.

## August 23, 2023

* Support custom fields

## August 12, 2023

* The redirect URL is allowed to be `localhost`.

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
* WebHhook added an extra field called `value` to carry the detailed information about a notification. For example, it carries the assignee's ID, name and URL if it is an assignment.

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
