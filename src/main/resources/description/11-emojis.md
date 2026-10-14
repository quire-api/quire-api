# Emojis

When adding or removing a comment's emoji reactions, an emoji can be given as the emoji itself or by one of its names below, with or without colons and in any case. The names are the shortcodes GitHub and Slack use. For example, `+1`, `:thumbsup:` and `👍` are the same.

| Emoji | Names
|-------|--------------------------------
| 😃    | `smiley`
| 😞    | `disappointed`
| 😐    | `neutral_face`
| 😂    | `joy`
| 😍    | `heart_eyes`
| 😜    | `stuck_out_tongue_winking_eye`
| 😏    | `smirk`
| 😱    | `scream`
| 😭    | `sob`
| 😡    | `rage`, `pout`
| 👍    | `+1`, `thumbsup`
| 👎    | `-1`, `thumbsdown`
| 🙌    | `raised_hands`
| 👊    | `punch`, `facepunch`, `fist_oncoming`
| 💪    | `muscle`
| ❤️    | `heart`
| 🎉    | `tada`
| 🚀    | `rocket`
| 💯    | `100`
| ⚠️    | `warning`

❤ and ⚠ without the variation selector (U+FE0F) are accepted as ❤️ and ⚠️. Responses always return the emoji itself.

> 💡 **Tip — prefer names when calling from a script or an AI assistant.** A name needs no percent-encoding in the URL, and it can't fall victim to a missing variation selector or a look-alike character. `POST /comment/add-emoji/{commentOid}/+1,tada` is the same request as `POST /comment/add-emoji/{commentOid}/%F0%9F%91%8D,%F0%9F%8E%89`.
