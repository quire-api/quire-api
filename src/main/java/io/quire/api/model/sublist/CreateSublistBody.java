package io.quire.api.model.sublist;

import io.quire.api.model.Identity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel()
public class CreateSublistBody {
	@ApiModelProperty(example = "Sublist101",
		value = "(Optional) ID of the sublist. "
		+ "If omitted, Quire will generate one automatically. "
		+ "It must be unquie in the whole project if specified")
	public String getId() { return null; }

	@ApiModelProperty(example = "Sublist 101",
		value = "The name of the sublist.", required = true)
	public String getName() { return null; }

	@ApiModelProperty(example = "**Great** sublist to start with.",
		value = "(Optional) An optional description about this task.")
	public String getDescription() { return null; }

	@ApiModelProperty(example = "37",
		value = "(Optional) The color of the icon representing this record. "
		+ "It is an index of our predefined color palette. ")
	public String getIconColor() {
		return null;
	}

	@ApiModelProperty(example = "icon-view-kanban",
		value = "(Optional) The image representing this record. "
			+ "It shall be one of the predined images:\n"
  +" 'icon-view-list', 'icon-view-kanban', 'icon-briefcase-o', 'icon-rocket-o', 'icon-bug-o',"
  +" 'icon-leaf-o', 'icon-clapperboard', 'icon-sitemap-o', 'icon-flash-on-o', 'icon-piggy-bank-o',"
  +" 'icon-graduation-cap-o', 'icon-paper-plane-o', 'icon-globe-o', 'icon-music-o', 'icon-detail',"
  +" 'icon-beach-o', 'icon-paper', 'icon-home-o', 'icon-building', 'icon-database-o',"
  +" 'icon-microscope-o', 'icon-hamburger-o', 'icon-trophy-o', 'icon-thumbs-o-up', 'icon-thumbs-o-down',"
  +" 'icon-smile-o', 'icon-frown-o', 'icon-meh-o', 'icon-bullseye', 'icon-square-dotted-o'")
	public String getImage() {
		return null;
	}

	@ApiModelProperty(value = "(Optional) A list of tasks' OID that belong to this sublist.\n"
		+ "Note: all of descendants will be included too.")
	public List<String> getTasks() { return null; }

	@ApiModelProperty(value = "(Optional) OID of the external team that this sublist belongs to.",
		example = "rcBHBYXZSiyDRrHrWPutatfF", position = 15)
	public String getPartner() { return null; }

	@ApiModelProperty(value = "(Optional) Whether it is personal. "
		+ "If omitted, false is assumed.", position = 15)
	public boolean getPersonal() { return false; }

	@ApiModelProperty(value = "(Optional) When this sublist was aimed to complete. ",
		example = "2020-01-22T02:06:58.158Z", position = 50)
	public String getDue() { return null; }
}
