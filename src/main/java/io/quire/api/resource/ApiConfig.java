package io.quire.api.resource;

import io.swagger.annotations.*;

@SwaggerDefinition(
  info = @Info(
    extensions = {
      @Extension(name = "x-logo", properties = {
        @ExtensionProperty(name = "url", value = "https://quire.io/s/img/quire_logo.svg")}),
    },
    title = "Quire API",
    version = "1.0.0",
    termsOfService = "https://quire.io/terms",
    contact = @Contact(
      name = "Quire",
      email = "info@quire.io",
      url = "https://quire.io"
    ),
    license = @License(
      name = "Apache 2.0",
      url = "http://www.apache.org/licenses/LICENSE-2.0"
    )
  ))
public interface ApiConfig {
}
