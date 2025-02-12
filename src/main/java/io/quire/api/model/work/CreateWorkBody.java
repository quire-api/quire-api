package io.quire.api.model.work;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel()
public class CreateWorkBody {
	@ApiModelProperty(example = "Highlight101",
		value = "(Optional) ID of the record. "
		+ "If omitted, Quire will generate one automatically. "
		+ "It must be unquie in the whole project if specified")
	public String getId() { return null; }

	@ApiModelProperty(example = "Highlight 101",
		value = "The name of the record.", required = true)
	public String getName() { return null; }

	@ApiModelProperty(example = "**Great** record to start with.",
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

	@ApiModelProperty(value = "(Optional) OID of the external team that this record belongs to.",
		example = "rcBHBYXZSiyDRrHrWPutatfF", position = 15)
	public String getPartner() { return null; }

	@ApiModelProperty(value = "(Optional) When this record was aimed to start.",
		example = "2024-01-02", position = 50)
	public String getStart() { return null; }

	@ApiModelProperty(value = "(Optional) When this record was aimed to complete.",
		example = "2024-05-25", position = 50)
	public String getDue() { return null; }
}
