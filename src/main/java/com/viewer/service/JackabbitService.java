package com.viewer.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.viewer.repository.JcrRepository;
import com.viewer.Session.JcrSessionManager;

import javax.jcr.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
public class JackabbitService {

	  /*  @Autowired
    private Repository repository; */

    private final JcrSessionManager sessionManager;
    private final JcrRepository jcrRepository;

    @Autowired
    public JackabbitService(JcrSessionManager sessionManager, JcrRepository jcrRepository) {
        this.sessionManager = sessionManager;
        this.jcrRepository = jcrRepository;
    }

    @Value("${spring.jcr.repositories.default.username}")
    private String username;

    @Value("${spring.jcr.repositories.default.password}")
    private String password;



    public boolean saveFiles(List<MultipartFile> files, String folder) {
        try {
            Session session = sessionManager.getSession(username, password);
            sessionManager.setSession(session);

            List<String> uploadedFileNames = new ArrayList<>();
            for (MultipartFile file : files) {
                jcrRepository.save(file, folder,session);


              //  uploadedFileNames.add(file.getOriginalFilename());
            }
            session.logout();


            return true;
        } catch (IOException | RepositoryException e) {
            return false;
        }
    }
    
    
    
    
    public boolean saveConvertedFiles(List<File> files, String folder) {
        try {
            Session session = sessionManager.getSession(username, password);
            sessionManager.setSession(session);

            //List<String> uploadedFileNames = new ArrayList<>();
            for (File file : files) {
            	
            	
            	
        jcrRepository.saveFile(file, folder,session);
  
  
               // uploadedFileNames.add(file.getName());
            }
            session.logout();


            return true;
        } catch (IOException | RepositoryException e) {
            return false;
        }
    }
    
}
