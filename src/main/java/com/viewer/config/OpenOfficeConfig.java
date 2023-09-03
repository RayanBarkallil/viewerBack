package com.viewer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.jodconverter.local.office.LocalOfficeManager;


@Configuration
public class OpenOfficeConfig {
	
	
	@Bean
    public LocalOfficeManager officeManager() {
        return LocalOfficeManager.builder()
                .portNumbers(2002, 2003, 2004, 2005)
                .build();
    

}

	   @Bean
	    public LocalOfficeManager officeManagerWithOfficeHome() {
	        return LocalOfficeManager.builder()
	                .officeHome("C:\\Program Files (x86)\\OpenOffice 4")
	                .build();
	    }
}
