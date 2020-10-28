package io.quire.api.model.sublist;

import io.quire.api.model.Identity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class UpdateSublistBody {
	@ApiModelProperty(example = "Sublist101",
		value = "(Optional) ID of the sublist.")
	public String getId() { return null; }

	@ApiModelProperty(example = "Sublist 101",
		value = "(Optional) The name of the sublist.")
	public String getName() { return null; }

	@ApiModelProperty(example = "**Great** sublist to start with.",
		value = "(Optional) An optional description about this task.")
	public String getDescription() { return null; }

	@ApiModelProperty(example = "37",
		value = "(Optional) The color of the icon representing this record. "
		+ "It is an index of our predefined color palette. ")
	public String getIconColor() {return null;}

	@ApiModelProperty(example = "icon-view-kanban",
		value = "(Optional) The image representing this record. "
			+ "It shall be one of the predined images:\n"
  +" 'icon-view-list', 'icon-view-kanban', 'icon-briefcase-o', 'icon-rocket-o', 'icon-bug-o',"
  +" 'icon-leaf-o', 'icon-clapperboard', 'icon-sitemap-o', 'icon-flash-on-o', 'icon-piggy-bank-o',"
  +" 'icon-graduation-cap-o', 'icon-paper-plane-o', 'icon-globe-o', 'icon-music-o', 'icon-detail',"
  +" 'icon-beach-o', 'icon-paper', 'icon-home-o', 'icon-building', 'icon-database-o',"
  +" 'icon-microscope-o', 'icon-hamburger-o', 'icon-trophy-o', 'icon-thumbs-o-up', 'icon-thumbs-o-down',"
  +" 'icon-smile-o', 'icon-frown-o', 'icon-meh-o', 'icon-bullseye', 'icon-square-dotted-o'")
	public String getImage() {return null;}

	@ApiModelProperty(value = "(Optional) When this sublist was aimed to complete. ",
		example = "2020-01-22T02:06:58.158Z")
	public String getDue() { return null; }

	@ApiModelProperty(value = "(Optional) A list of changes to control what tasks to be "
		+ "added to or removed from this sublist.")
	public List<Change> getChanges() { return null; }

	@ApiModelProperty(example = "true",
		value = "(Optional) Specify true to archive this sublist. "
		+ "Or, specify false to undo the previous archiving if any.")
	public boolean getArchived() { return false; }
}
