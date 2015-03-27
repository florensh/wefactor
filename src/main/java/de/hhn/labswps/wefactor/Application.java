package de.hhn.labswps.wefactor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.system.ApplicationPidListener;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import de.hhn.labswps.wefactor.web.util.DateFormatter;
import de.hhn.labswps.wefactor.web.util.StringUtil;
import de.hhn.labswps.wefactor.web.util.TimelineUtils;

/**
 * The Class Application includes a main method to start the embedded web
 * server.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableJpaRepositories(basePackages = "de.hhn.labswps.wefactor.domain")
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
public class Application {

    /**
     * The main method to start the application.
     *
     * @param args
     *            the vm arguments
     */
    // public static void main(final String[] args) {
    // SpringApplication.run(Application.class, args);
    // }

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(
                Application.class);
        springApplication.addListeners(new ApplicationPidListener(
                "wefactor.pid"));
        springApplication.run(args);
    }

    /**
     * dateFormatter.
     *
     * @return a bean of type DateFormatter
     */
    @Bean
    public DateFormatter dateFormatter() {
        return new DateFormatter();
    }

    /**
     * stringUtil.
     *
     * @return a bean of type StringUtil
     */
    @Bean
    public StringUtil stringUtil() {
        return new StringUtil();
    }

    /**
     * timelineUtils.
     *
     * @return a bean of type TimelineUtils
     */
    @Bean
    public TimelineUtils timelineUtils() {
        return new TimelineUtils();
    }

}