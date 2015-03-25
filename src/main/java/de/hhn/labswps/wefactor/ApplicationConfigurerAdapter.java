package de.hhn.labswps.wefactor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
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

}
