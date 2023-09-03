package com.viewer.entities;

public class ConvertedFile {
    private String fileName;
    private byte[] fileContent;

    public ConvertedFile(String fileName, byte[] fileContent) {
        this.fileName = fileName;
        this.fileContent = fileContent;
    }

    public String getFileName() {
        return fileName;
    }

    public byte[] getFileContent() {
        return fileContent;
    }
}
