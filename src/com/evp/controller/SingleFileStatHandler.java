/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evp.controller;

import com.evp.model.FileStat;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Егор
 */
public class SingleFileStatHandler implements Runnable
{

    private static final Logger logger = LogManager.getRootLogger();

    private File file;
    private FileStatSaver fileStatSaver;

    /**
     * Gets statistics about file lines and pass it further to DB saver
     *
     * @param file file to work with
     * @param fileStatSaver saver for saving computed stat
     */
    public SingleFileStatHandler(File file, FileStatSaver fileStatSaver)
    {
        this.file = file;
        this.fileStatSaver = fileStatSaver;
    }

    @Override
    public void run()
    {
        String path = file.getAbsolutePath();
        String fileName = getFileName(path);
        try
        {
            logger.info("Collecting lines from " + fileName + "...");
            List<String> fileLines = FileLinesReader.getFileLines(path);
            logger.info("Grabbing stat from " + fileName + "...");
            FileStat stat = FileLinesStatGrabber.getLinesStats(fileName, fileLines);
            if (stat != null)
            {
                logger.info("Saving stat about " + fileName + "...");
                fileStatSaver.addFileStat(stat);
            }
            else
            {
                logger.info("Can`t grab stat from " + fileName + " !");
            }
        } catch (Exception e)
        {
            logger.error("Error while handling " + fileName + "!", e);
        }
    }

    /**
     * @param path path to file for retrieving name
     * @return name of a current file
     */
    public static String getFileName(String path)
    {
        Path p = Paths.get(path);
        return p.getFileName().toString();
    }

}
