/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evp.model;

import java.util.List;

/**
 *
 * @author Егор
 */
public class FileStat {

    private final int fileId;
    private final String fileName;
    private final List<LineStat> lineStats;

    public FileStat(int fileId, String fileName, List<LineStat> lineStats) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.lineStats = lineStats;
    }

    public int getFileId() {
        return fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public List<LineStat> getLinesStats() {
        return lineStats;
    }

}
