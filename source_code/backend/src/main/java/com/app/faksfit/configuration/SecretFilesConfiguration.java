package com.app.faksfit.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("file:./secured.properties")
public class SecretFilesConfiguration {
}
