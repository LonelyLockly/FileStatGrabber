/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evp.test;

import com.evp.controller.FileLinesStatGrabber;
import com.evp.model.FileStat;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Егор
 */
public class FileStatGrabberTest
{

    private List<String> linesForTest;

    @Before
    public void init()
    {
        linesForTest = new ArrayList<>();
        linesForTest.add("один четыре двенадцать");
        linesForTest.add("двадцать пять шестьдесят четыре");
        linesForTest.add("миллион сто тысяч семьсот восемнадцать");
    }

    @Test
    public void getLinesStatsTest()
    {

        FileStat stat = FileLinesStatGrabber.getLinesStats("file.txt", linesForTest);

        Assert.assertEquals("file.txt", stat.getFileName());

        Assert.assertEquals(1, stat.getLinesStats().get(0).getLineId());
        Assert.assertEquals("двенадцать", stat.getLinesStats().get(0).getLongestWord());
        Assert.assertEquals("один", stat.getLinesStats().get(0).getShortestWord());
        Assert.assertEquals(22, stat.getLinesStats().get(0).getLineLength());
        Assert.assertEquals(6.7, stat.getLinesStats().get(0).getAvgWordLength(), 0.1);

        Assert.assertEquals(2, stat.getLinesStats().get(1).getLineId());
        Assert.assertEquals("шестьдесят", stat.getLinesStats().get(1).getLongestWord());
        Assert.assertEquals("пять", stat.getLinesStats().get(1).getShortestWord());
        Assert.assertEquals(31, stat.getLinesStats().get(1).getLineLength());
        Assert.assertEquals(7.0, stat.getLinesStats().get(1).getAvgWordLength(), 0.1);

        Assert.assertEquals(3, stat.getLinesStats().get(2).getLineId());
        Assert.assertEquals("восемнадцать", stat.getLinesStats().get(2).getLongestWord());
        Assert.assertEquals("сто", stat.getLinesStats().get(2).getShortestWord());
        Assert.assertEquals(38, stat.getLinesStats().get(2).getLineLength());
        Assert.assertEquals(6.8, stat.getLinesStats().get(2).getAvgWordLength(), 0.1);

    }

}
