package com.viewer.dto;

import java.util.List;

public class FileUploadResponse {
    private List<String> fileNames;

    public FileUploadResponse(List<String> fileNames) {
        this.fileNames = fileNames;
    }

    public List<String> getFileNames() {
        return fileNames;
    }

    public void setFileNames(List<String> fileNames) {
        this.fileNames = fileNames;
    }
}
