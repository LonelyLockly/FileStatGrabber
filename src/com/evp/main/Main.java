/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evp.main;

import com.evp.controller.FileLinesStatGrabber;
import com.evp.controller.FileCollector;
import com.evp.controller.FileLinesReader;
import com.evp.controller.FileStatGrabberController;
import com.evp.controller.FileStatSaver;
import com.evp.controller.SingleFileStatHandler;
import com.evp.dao.FileStatDAO;
import com.evp.model.FileStat;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Егор
 */
public class Main
{

    private static final Logger logger = LogManager.getRootLogger();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        if (args.length == 1)
        {
            String pathToFile = args[0];

            if (!pathToFile.isEmpty())
            {
                FileStatGrabberController controller = new FileStatGrabberController();
                controller.handleAllFilesInPath(pathToFile);
            }
            else
            {
                logger.info("File path is empty!");
            }
        }
        else
        {
            logger.info("No file path specified!");
        }
    }

}
