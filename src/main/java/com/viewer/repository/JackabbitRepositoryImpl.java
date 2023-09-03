package com.viewer.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@Repository
public class JackabbitRepositoryImpl implements JcrRepository {

    @Override
    public void save(MultipartFile file, String directory, Session session) throws RepositoryException, IOException {
    	
        InputStream fileStream = file.getInputStream();
        String fileName = file.getOriginalFilename();
        String mimeType = file.getContentType();
        Node documents = createFolder(directory,session);
        Node fileNode = createFile(fileName, "nt:file", documents);
        Node contentNode = createFile("jcr:content", "nt:resource", fileNode);
        Binary binary = session.getValueFactory().createBinary(fileStream);

        contentNode.setProperty("jcr:data", binary);
        contentNode.setProperty("jcr:mimeType", mimeType);

        session.save();
    }
    
	    
	private Node createFolder(String nodeName,Session session) throws RepositoryException {
		
	    Node root = session.getRootNode();
	    Node folder;
	
	    if (root.hasNode(nodeName)) {
	        folder = root.getNode(nodeName);
	    } else {
	        folder = root.addNode(nodeName);
	        // You can also add additional properties or perform other actions on the newly created node here, if needed.
	    }
	
	    session.save(); // Save changes to the repository
	
	    return folder;
	}
	
	
    
	private Node createFile(String fileName, String fileType, Node folder) throws RepositoryException {
	
	    return folder.addNode(fileName, fileType);
	    
	}


	@Override
	 public boolean saveFile(File file, String directory, Session session) {
        try {
            FileInputStream fileStream = new FileInputStream(file);
            String fileName = file.getName();
            
            System.out.println(fileName);
            Node documents = createFolder(directory,session);
            String mimeType = Files.probeContentType(file.toPath());

            Node fileNode = createFile(fileName, "nt:file", documents);
            Node contentNode = createFile("jcr:content", "nt:resource", fileNode);

            Binary binary = session.getValueFactory().createBinary(fileStream);

            contentNode.setProperty("jcr:data", binary);
            contentNode.setProperty("jcr:mimeType", mimeType);

            session.save();

            fileStream.close();
            
            return true;
        } catch (IOException | RepositoryException e) {
            e.printStackTrace(); // Print the exception for debugging purposes
            return false;
        }


	
	}
  
}
