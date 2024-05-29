package com.ierdely.elective_courses.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
    @PropertySource("classpath:/application.properties")
})
public class PropertiesConfiguration {

}
