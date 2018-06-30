package jms.boot.app.repository;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

@Slf4j
public class PrepareStatementSql {
    public void executeSqlScript(Connection conn, File inputFile) {
        log.info("executeSqlScript :::::: started ::::::::");
        // Delimiter
        String delimiter = ";";

        // Create scanner
        Scanner scanner;
        try {
            scanner = new Scanner(inputFile).useDelimiter(delimiter);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            return;
        }

        // Loop through the SQL file statements
        Statement currentStatement = null;
        while (scanner.hasNext()) {

            // Get statement
            String rawStatement = scanner.next() + delimiter;
            try {
                log.info("Raw statement is "+rawStatement);
                // Execute statement
                currentStatement = conn.createStatement();
                currentStatement.execute(rawStatement);
             } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Release resources
                if (currentStatement != null) {
                    try {
                        currentStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                currentStatement = null;
            }
        }
        scanner.close();
    }
}
