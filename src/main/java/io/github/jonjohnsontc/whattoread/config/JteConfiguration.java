package io.github.jonjohnsontc.whattoread.config;

import java.nio.file.Path;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.DirectoryCodeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class JteConfiguration {

    @Bean
    @Profile("!prod")
    public TemplateEngine templateEngine() {
        // in Dev: compile templates on the fly
        return TemplateEngine.create(
            new DirectoryCodeResolver(Path.of("src", "main", "jte")),
                Path.of("src", "main", "jte", "compiled"),
                ContentType.Html,
                Thread.currentThread().getContextClassLoader()
        );
    }

    @Bean
    @Profile("prod")
    public TemplateEngine templateEngineProd() {
        return TemplateEngine.createPrecompiled(ContentType.Html);
    }
}
