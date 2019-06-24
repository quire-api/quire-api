package io.quire.api.model.project;

import java.util.List;

import io.quire.api.model.Recurring;
import io.swagger.annotations.ApiModelProperty;

public class UpdateProjectBody {
	@ApiModelProperty(example = "[\"6QMKkEPBVWETLWrXqws94ALU\",\"app\"]",
		value = "(Optional) OID of the users to replace the followers of this project. "
		+ "If the application would like to follow, it can pass \"app\" as one of OIDs.")
	public List<String> getFollowers() { return null; }
	@ApiModelProperty(example = "[\"6QMKkEPBVWETLWrXqws94ALU\"]",
		value = "(Optional) OID of the followers to be added to this project."
		+ "If the application would like to follow, it can pass \"app\" as one of OIDs.")
	public List<String> getAddFollowers() { return null; }
	@ApiModelProperty(example = "[\"6QMKkEPBVWETLWrXqws94ALU\"]",
		value = "(Optional) OID of the followers to be removed from this project."
		+ "If the application won't like to follow any more, it can pass \"app\" as one of OIDs.")
	public List<String> getRemoveFollowers() { return null; }
}
