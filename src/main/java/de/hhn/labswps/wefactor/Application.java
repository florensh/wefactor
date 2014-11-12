package de.hhn.labswps.wefactor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableJpaRepositories(basePackages = "de.hhn.labswps.wefactor.domain")
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
public class Application {

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

}