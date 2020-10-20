# CHANGES

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
