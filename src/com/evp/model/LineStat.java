/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evp.model;

/**
 *
 * @author Егор
 */
public class LineStat {

    private int lineId;
    private String longestWord;
    private String shortestWord;
    private int lineLength;
    private double avgWordLength;

    public LineStat(int lineId, String longestWord, String shortestWord, int lineLength, double avgWordLength) {
        this.lineId = lineId;
        this.longestWord = longestWord;
        this.shortestWord = shortestWord;
        this.lineLength = lineLength;
        this.avgWordLength = avgWordLength;
    }

    public int getLineId() {
        return lineId;
    }

    public String getLongestWord() {
        return longestWord;
    }

    public String getShortestWord() {
        return shortestWord;
    }

    public int getLineLength() {
        return lineLength;
    }

    public double getAvgWordLength() {
        return avgWordLength;
    }
    
    
}
