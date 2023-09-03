package com.viewer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.jackrabbit.commons.JcrUtils;


import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import java.net.MalformedURLException;

@Configuration
public class JackrabbitConfig {

    @Value("${spring.jcr.repositories.default.uri}")
    private String repositoryUri;

    @Bean
    public Repository jackrabbitRepository() throws RepositoryException, MalformedURLException {
        return JcrUtils.getRepository(repositoryUri);      		
        		
    }
}
