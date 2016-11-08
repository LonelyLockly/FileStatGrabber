/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evp.controller;

import com.evp.model.FileStat;
import com.evp.model.LineStat;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Егор
 */
public class FileLinesStatGrabber
{

    private static final Logger logger = LogManager.getRootLogger();

    public FileLinesStatGrabber()
    {
    }

    /**
     *
     * @param fileName path to current file
     * @param lines lines of file for retrieving statistics
     * @return all lines stats from current file
     */
    public static FileStat getLinesStats(String fileName, List<String> lines)
    {
        FileStat result = null;
        if (!lines.isEmpty())
        {
            List<LineStat> linesStats = new ArrayList<>();
            for (int i = 0; i < lines.size(); i++)
            {
                String line = lines.get(i);
                int lineId = i+1;
                //Filtering BOM symbol in first line
                if (lineId == 1)
                {
                    line = line.replace("\uFEFF", "");
                }
                LineStat lineStat = getSingleLineStat(lineId, line);
                linesStats.add(lineStat);
            }
            result = new FileStat(-1, fileName, linesStats);
        }
        return result;
    }


    
    /**
     * 
     * @param lineId id of current handling line
     * @param line line to collect stats from
     * @return statistics about single line of text
     */

    private static LineStat getSingleLineStat(int lineId, String line)
    {
        LineStat result = null;
        String longestWord = "", shortestWord = "";
        int lineLength = line.length(), allWordsLength = 0, wordsCount = 0;
        double avgWordLength = 0;
        //Get words from line
        String[] words = line.split(" ");
        if (words.length > 0)
        {
            wordsCount = words.length;
            shortestWord = words[0];
            for (String word : words)
            {
                int wLen = word.length();
                //let`s remember last longest word
                if (wLen >= longestWord.length())
                {
                    longestWord = word;
                }
                //let`s remember last shortest word
                if (wLen <= shortestWord.length())
                {
                    shortestWord = word;
                }
                allWordsLength += wLen;
            }
            avgWordLength = BigDecimal.valueOf(allWordsLength / (double) wordsCount).setScale(1, RoundingMode.HALF_UP).doubleValue();
            //create statistic for single line
            result = new LineStat(lineId, longestWord, shortestWord, lineLength, avgWordLength);
        }
        return result;
    }

}
