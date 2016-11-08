/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evp.controller;

import com.evp.dao.FileStatDAO;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Егор
 */
public class FileStatGrabberController
{

    private static final Logger logger = LogManager.getRootLogger();
    private static final ThreadFactory fileHandlerThreadFactory = new ThreadFactoryBuilder().setNameFormat("fileHandler-%d").build();
    private static final ExecutorService fileHandlerExecutor = Executors.newFixedThreadPool(10, fileHandlerThreadFactory);
    private static final ThreadFactory fileStatSaverThreadFactory = new ThreadFactoryBuilder().setNameFormat("fileStatSaver-%d").build();
    private static final ScheduledExecutorService fileStatSaverExecutor = Executors.newScheduledThreadPool(1, fileStatSaverThreadFactory);

    public FileStatGrabberController()
    {
    }

    /**
     * Handling all files in path, collecting stats from them and saving it to
     * DB
     *
     * @param path path to work with
     */
    public void handleAllFilesInPath(String path)
    {
        logger.info("Collecting all files from the path...");
        List<File> filesToHandle = FileCollector.getAllFilesToHandle(path);
        logger.info("Creating fileStatDAO...");
        FileStatDAO fileStatDAO = new FileStatDAO();
        logger.info("Creating fileStatSaver...");
        FileStatSaver fileStatSaver = new FileStatSaver(fileStatDAO);
        fileStatSaverExecutor.scheduleAtFixedRate(fileStatSaver, 0, 100, TimeUnit.MILLISECONDS);
        logger.info("Files handling start...");
        for (File file : filesToHandle)
        {
            fileHandlerExecutor.execute(new SingleFileStatHandler(file, fileStatSaver));
        }
        while (!fileHandlerExecutor.isTerminated() && fileStatSaver.isWorking())
        {
            try
            {
                logger.info("Awaiting for stats saving...");
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e)
            {
                logger.error("FileStatGrabberController was interrupted!", e);
            }
        }
        fileHandlerExecutor.shutdown();
        fileStatSaverExecutor.shutdown();
    }
}
