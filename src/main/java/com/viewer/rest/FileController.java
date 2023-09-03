package com.viewer.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.viewer.dto.FileUploadResponse;
import com.viewer.service.JackabbitService;
import java.util.Map;
import java.util.HashMap;


import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.MediaType;

import com.viewer.service.ConverterService;

@RestController
public class FileController {
	
    private final JackabbitService jackabbitService;
    
   
    @Value("${spring.upload.directory}")
    private String uploadDirectory;
    
   

    @Autowired
    public FileController(JackabbitService jackabbitService) {
        this.jackabbitService = jackabbitService;
    }

    @PostMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FileUploadResponse> uploadFiles(@RequestParam("files") List<MultipartFile> files) {
        boolean filesSaved = jackabbitService.saveFiles(files, "Documents");

        if (filesSaved) {
            List<String> fileNames = files.stream()
                    .map(MultipartFile::getOriginalFilename)
                    .collect(Collectors.toList());
            FileUploadResponse response = new FileUploadResponse(fileNames);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    

    @PostMapping("/convertFile")
    public List<File> convertFiles(@RequestParam("files") List<MultipartFile> files) {
        List<File> convertedFiles = new ArrayList<>();

        // Map des extensions d'entrée aux extensions de sortie
        Map<String, String> extensionMapping = new HashMap<>();
        extensionMapping.put("docx", "odt");
        extensionMapping.put("doc", "odt");
        extensionMapping.put("pptx", "odp");
        extensionMapping.put("ppt", "odp");
        extensionMapping.put("xlsx", "ods");
        extensionMapping.put("xltx", "ods");
        extensionMapping.put("xls", "ods");

        for (MultipartFile file : files) {
            try {
                if (file.isEmpty()) {
                    throw new IllegalArgumentException("Fichier vide.");
                }

                String inputFileName = file.getOriginalFilename();
                String inputFilePath = uploadDirectory + inputFileName;
                file.transferTo(new File(inputFilePath));
                String inputExtension = inputFileName.substring(inputFileName.lastIndexOf(".") + 1).toLowerCase();
                String outputExtension = extensionMapping.get(inputExtension);
                if (outputExtension == null) {
                    continue;
                }

                String outputFileName = inputFileName.substring(0, inputFileName.lastIndexOf(".")) + "." + outputExtension;
                File outputFile = ConverterService.convertFiles(inputFilePath, outputFileName);
                convertedFiles.add(outputFile);
                new File(inputFilePath).delete();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        jackabbitService.saveConvertedFiles(convertedFiles, "12345");
        return convertedFiles;
    }
}
