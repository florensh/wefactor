package de.hhn.labswps.wefactor;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ApplicationConfigurerAdapter extends WebMvcConfigurerAdapter {

    @Value("${maintenanceStartTime}")
    private int maintenanceStartTime;

    @Value("${maintenanceEndTime}")
    private int maintenanceEndTime;

    @Value("${maintenanceMapping}")
    private String maintenanceMapping;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {

        MaintenanceInterceptor maintenanceInterceptor = new MaintenanceInterceptor();
        maintenanceInterceptor.setMaintenanceStartTime(maintenanceStartTime);
        maintenanceInterceptor.setMaintenanceEndTime(maintenanceEndTime);
        maintenanceInterceptor.setMaintenanceMapping(maintenanceMapping);

        registry.addInterceptor(maintenanceInterceptor).addPathPatterns("/**");
    }

    @Override
    public void addArgumentResolvers(
            List<HandlerMethodArgumentResolver> argumentResolvers) {
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        resolver.setFallbackPageable(new PageRequest(0, 10));
        argumentResolvers.add(resolver);
    }

}
