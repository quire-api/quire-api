package io.quire.api.model.project;

import java.util.List;

import io.quire.api.model.Recurring;
import io.swagger.annotations.ApiModelProperty;

public class UpdateProjectBody {
	@ApiModelProperty(example = "[\"6QMKkEPBVWETLWrXqws94ALU\",\"app\"]",
		value = "(Optional) OID of the users to replace the followers of this project.\n"
		+ "Please refer to `addFollowers()` for more details.")
	public List<String> getFollowers() { return null; }
	@ApiModelProperty(example = "[\"6QMKkEPBVWETLWrXqws94ALU\"]",
		value = "(Optional) OID of the followers to be added to this project."
		+ "If \"me\" is specified, it means the current user will follow this task.\n"
		+ "If the application would like to follow (i.e., receive notifications), "
		+ "it can pass \"app\" as one of OIDs.\n"
		+ "In additions, it can pass additional information in one of the following syntaxes.\n\n"
		+ "Syntax 1:\n"
		+ "\"app|team\" or \"app|team|channel\"\n"
		+ "where team and channel can be any value.\n\n"
		+ "Syntax 2:\n"
		+ "\"app|/path\"\n"
		+ "where \"/path\" can be any URL path. It will be appended to the hook's URL "
		+ "when calling the registered hook. For example, if the hook URL is "
		+ "\"https://super.app/hooks/standard\" and the follower is "
		+ "\"app|/soc1/33456/a7\", then the notification will be sent to "
		+ "\"https://super.app/hooks/standard/soc1/33456/a7\".")
	public List<String> getAddFollowers() { return null; }
	@ApiModelProperty(example = "[\"6QMKkEPBVWETLWrXqws94ALU\"]",
		value = "(Optional) OID of the followers to be removed from this project.\n"
		+ "Please refer to `addFollowers()` for more details.")
	public List<String> getRemoveFollowers() { return null; }
}
