package cotato.backend.global.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	@Bean
	public GroupedOpenApi boardGroupedOpenApi() {
		return GroupedOpenApi
			.builder()
			.group("location")
			.pathsToMatch("/api/**")
			.addOpenApiCustomizer(openApi ->
				openApi.setInfo(new Info()
					.title("Team1 API 명세서")
					.version("1.0")
					.description("Cotato Networking 1조 API 명세서입니다.")
				)
			).build();
	}
}