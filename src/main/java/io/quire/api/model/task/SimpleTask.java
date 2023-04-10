package io.quire.api.model.task;

import io.quire.api.model.*;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class SimpleTask extends StampedEntity {

	@ApiModelProperty(example = "12", position = 1)
	public int getId() { return 0; }

	@ApiModelProperty(value = "This task's name.",
		example = "Design new **logo**", position = 2)
	public String getName() { return null; }
	@ApiModelProperty(value = "This task's name but excluding markdown characters.",
		example = "Design new logo", position = 2)
	public String getNameText() { return null; }
	@ApiModelProperty(value = "This task's name in a form of a HTML fragment converted from markdown.",
		example = "Design new <b>logo</b>", position = 2)
	public String getNameHtml() { return null; }

	@ApiModelProperty(value = "Description about this task.", example = "This is a *cool* task.", position = 3)
	public String getDescription() { return null; }
	@ApiModelProperty(value = "Description but excluding markdown characters.", example = "This is a cool task.", position = 3)
	public String getDescriptionText() { return null; }
	@ApiModelProperty(value = "Description in a form of a HTML fragment converted from markdown.", example = "This is a <i>cool</i> task.", position = 3)
	public String getDescriptionHtml() { return null; }

	@ApiModelProperty(value = "The status of this task. "
		+ "Its value must be between 0 and 100. If 100, it means completed.",
		example = "0", position = 4)
	public int getStatus() { return 0; }

	@ApiModelProperty(value = "The priority of this task. "
		+ "Its value must be between -1 (lowest) and 2 (highest). "
		+ "Default: 0.",
		example = "0", position = 4)
	public int getPriority() { return 0; }

	@ApiModelProperty(value = "Tags that are tagged to this task.", position = 5)
	public List<SimpleTaggingEntity> getTags() { return null; }

	@ApiModelProperty(value = "When to start this task."
		+ "Note: if time is specified, the millisecond will be `001`. "
		+ "Otherwise, it is `000` (so are the hour, minute and second fields).",
		example = "2018-12-20T00:00:00.000Z", position = 4)
	public String getStart() { return null; }
	@ApiModelProperty(value = "When to complete this task. "
		+ "Note: if time is specified, the millisecond will be 001. "
		+ "Otherwise, it is 000 (so are the hour, minute and second fields).",
		example = "2018-12-22T00:00:00.000Z", position = 4)
	public String getDue() { return null; }

	@ApiModelProperty(value = "Users who are assigned to this task.", position = 5)
	public List<SimpleIdentity> getAssignees() { return null; }

	@ApiModelProperty(value = "Whether this task was peekabooed. "
		+ "It is null if not peekabooed.",
		example = "true", position = 50)
	public boolean getPeekaboo() { return false; }

	@ApiModelProperty(value = "Whether this task is a section. "
		+ "It is null if it is a normal task",
		example = "true", position = 50)
	public boolean getSection() { return false; }

	@ApiModelProperty(value = "Url of this task on Quire website.",
		example = "https://quire.io/w/my_project/123", position = 50)
	public String getUrl() { return null; }
}
