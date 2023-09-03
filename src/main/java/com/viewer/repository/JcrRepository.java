package com.viewer.repository;

import org.springframework.web.multipart.MultipartFile;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import java.io.File;
import java.io.IOException;

public interface JcrRepository {
    void save(MultipartFile file, String directory, Session session) throws RepositoryException, IOException;
    boolean saveFile(File file, String directory, Session session) throws RepositoryException, IOException;

}
