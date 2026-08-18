package io.quire.api.resource;

import io.swagger.annotations.*;

/**
 * The Swagger `info` block.
 *
 * <p>Note: {@code description} is intentionally NOT set here. The published
 * description is built from {@code src/main/resources/description/*.md},
 * concatenated by the {@code merge-description} step in {@code pom.xml} and
 * handed to the swagger plugin as {@code <descriptionFile>}, which overrides
 * any {@code description} on this annotation. Add prose to those .md files —
 * anything written here is silently dropped from the generated doc.
 */
@SwaggerDefinition(
  info = @Info(
    extensions = {
      @Extension(name = "x-logo", properties = {
        @ExtensionProperty(name = "url", value = "https://quire.io/s/img/quire_logo.svg")}),
    },
    title = "Quire API",
    version = "1.0.0"
  ))
public interface ApiConfig {
}
