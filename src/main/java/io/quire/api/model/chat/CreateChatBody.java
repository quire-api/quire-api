package io.quire.api.model.chat;

import io.quire.api.model.work.*;
import io.swagger.annotations.*;

import java.util.List;

public class CreateChatBody extends CreateWorkBody {

    @ApiModelProperty(
        value = "(Optional) Users (OID, ID, or email) who follow this chat channel. "
              + "Only those listed follow it, so include `\"me\"` for the current user to follow.\n\n"
              + "- `\"me\"` — the current user.\n"
              + "- `\"app\"` — the application follows (receives notifications).\n\n"
              + "For app followers, additional syntaxes are supported:\n"
              + "- `app|team` or `app|team|channel`\n"
              + "- `app|/path` → appended to the hook URL.",
        example = "[\"me\"]"
    )
    public List<String> getFollowers() { return null; }
}
