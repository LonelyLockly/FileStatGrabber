/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evp.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Егор
 */
public class FileCollector
{

    private static final Logger logger = LogManager.getRootLogger();

    /**
     * @param initialPath path to find files in
     * @return all files from current path recursively
     */
    public static List<File> getAllFilesToHandle(String initialPath)
    {
        List<File> filesToHandle = Collections.EMPTY_LIST;
        if (initialPath != null && !initialPath.isEmpty())
        {
            filesToHandle = new ArrayList<>();
            File inFile = new File(initialPath);
            if (inFile.isDirectory())
            {
                filesToHandle.addAll(FileUtils.listFiles(inFile, new String[]
                {
                    "txt"
                }, true));
            }
            else
            {
                filesToHandle.add(inFile);
            }
        }
        return filesToHandle;
    }
}
