/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evp.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Егор
 */
public class FileLinesReader
{

    private static final Logger logger = LogManager.getRootLogger();

    /**
     * @param path path to file to work with
     * @return all lines from current file
     */
    public static List<String> getFileLines(String path)
    {
        List<String> lines = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(path)))
        {
            lines = stream.collect(Collectors.toList());
        } catch (IOException e)
        {
            logger.error("Error while reading lines from file!");
        }
        return lines;
    }
}
