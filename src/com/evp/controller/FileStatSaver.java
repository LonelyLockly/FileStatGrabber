/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evp.controller;

import com.evp.dao.FileStatDAO;
import com.evp.model.FileStat;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Егор
 */
public class FileStatSaver implements Runnable
{

    private static final Logger logger = LogManager.getRootLogger();

    private final Queue<FileStat> filesToSaveQueue = new ConcurrentLinkedQueue<>();
    private final FileStatDAO fileStatDAO;
    private boolean isWorking = true;

    public FileStatSaver(FileStatDAO fileStatDAO)
    {
        this.fileStatDAO = fileStatDAO;
    }

    /**
     * Adding file statistics into queue for saving it to DB later
     *
     * @param fileStat statistics about single file
     */
    public void addFileStat(FileStat fileStat)
    {
        this.isWorking = true;
        filesToSaveQueue.add(fileStat);
    }

    @Override
    public void run()
    {
        try
        {
            if (!filesToSaveQueue.isEmpty())
            {
                List<FileStat> filesToSave = new ArrayList<>();
                FileStat fileStat;
                while ((fileStat = filesToSaveQueue.poll()) != null)
                {
                    filesToSave.add(fileStat);
                }
                fileStatDAO.saveFilesStat(filesToSave);
                isWorking = false;
            }
        } catch (Exception e)
        {
            logger.error("Error while saving stat!", e);
        }
    }

    /**
     *
     * @return true if service is still working, false otherwise
     */
    public boolean isWorking()
    {
        return isWorking || !filesToSaveQueue.isEmpty();
    }
}
