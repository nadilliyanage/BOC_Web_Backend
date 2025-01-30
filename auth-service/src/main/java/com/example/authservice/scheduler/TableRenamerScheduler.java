package com.example.authservice.scheduler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class TableRenamerScheduler {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Run at midnight every day
    @Scheduled(cron = "0 0 0 * * ?") // Cron expression for midnight
    public void renameTable() {
        // Get yesterday's date
        LocalDate yesterday = LocalDate.now().minusDays(1);
        String formattedDate = yesterday.format(DateTimeFormatter.ofPattern("yyyy_MM_dd"));

        // Rename the table
        String renameQuery = "ALTER TABLE send_message RENAME TO send_message_" + formattedDate;
        jdbcTemplate.execute(renameQuery);

        // Create a new send_message table
        String createTableQuery = "CREATE TABLE send_message ("
                + "id BIGINT AUTO_INCREMENT PRIMARY KEY, "
                + "campaign_name VARCHAR(255) NOT NULL, "
                + "sender VARCHAR(255) NOT NULL, "
                + "number VARCHAR(20) NOT NULL, "
                + "message TEXT NOT NULL, "
                + "schedule DATETIME, "
                + "status VARCHAR(50) NOT NULL"
                + ")";
        jdbcTemplate.execute(createTableQuery);

        System.out.println("Table renamed and new table created for " + LocalDate.now());
    }
}
