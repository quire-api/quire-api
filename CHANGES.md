# CHANGES

## Upcoming

* API to manange sublists are added.

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
