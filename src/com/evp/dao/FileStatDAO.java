/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evp.dao;

import com.evp.db.ConnectionPool;
import com.evp.model.FileStat;
import com.evp.model.LineStat;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Егор
 */
public class FileStatDAO
{

    private static final Logger logger = LogManager.getRootLogger();
    private ConnectionPool cp;

    public FileStatDAO()
    {
        cp = new ConnectionPool();
    }

    /**
     * Saves statistics to DB
     *
     * @param fileStats list of file stats to save
     * @return true if all files was saved, false if error has occured
     */
    public boolean saveFilesStat(List<FileStat> fileStats)
    {
        boolean result = true;
        try
        {
            for (FileStat fileStat : fileStats)
            {
                int fileId = saveFileStat(fileStat.getFileName());
                if (fileId > 0)
                {
                    saveLinesStat(fileId, fileStat.getLinesStats());
                }
                if (fileId > 0)
                {
                    logger.info("Stat saved to db!");
                }
                else
                {
                    logger.info("Stat not saved to db!");
                }
            }
        } catch (SQLException ex)
        {
            logger.error("Error while saving stat to db!", ex);
            result = false;
        }
        return result;
    }

    /**
     * Saves statistics about single file itself
     *
     * @param fileName name of file we currently saving
     * @return id of newly saved file
     * @throws SQLException
     */
    private int saveFileStat(String fileName) throws SQLException
    {
        int fileId = -1;
        String fileStatInsertScript = "INSERT INTO file_stats.file_stats(file_name) VALUES(?);";
        try (Connection conn = cp.getConnection();
                PreparedStatement ps = conn.prepareStatement(fileStatInsertScript, Statement.RETURN_GENERATED_KEYS);)
        {
            ps.setString(1, fileName);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next())
            {
                fileId = rs.getInt(1);
            }
        }
        return fileId;
    }

    /**
     * Saves statistics about single file lines
     *
     * @param fileId id of currently saving file statss
     * @param linesStat list of cirrent file lines statistics
     * @throws SQLException
     */
    private void saveLinesStat(int fileId, List<LineStat> linesStat) throws SQLException
    {
        String linesStatInsertScript = "INSERT INTO file_stats.line_stats(file_id,line_id,longest_word, shortest_word,line_length,avg_word_length) VALUES(?,?,?,?,?,?);";
        try (Connection conn = cp.getConnection();
                PreparedStatement ps = conn.prepareStatement(linesStatInsertScript);)
        {
            int i = 0;
            for (LineStat lineStat : linesStat)
            {
                ps.setInt(1, fileId);
                ps.setInt(2, lineStat.getLineId());
                ps.setString(3, lineStat.getLongestWord());
                ps.setString(4, lineStat.getShortestWord());
                ps.setInt(5, lineStat.getLineLength());
                ps.setDouble(6, lineStat.getAvgWordLength());

                ps.addBatch();
                i++;

                if (i % 1000 == 0 || i == linesStat.size())
                {
                    ps.executeBatch(); // Execute every 1000 items.
                }
            }
        }
    }
}
