package com.timetable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.List;

@Service
public class TimetableService {
    private static final Logger logger = LoggerFactory.getLogger(TimetableService.class);
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void createTable() {
        logger.info("Requested to create a new table");
        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS timetable (id INTEGER PRIMARY KEY AUTOINCREMENT, task STRING, priority INTEGER, status STRING)");
    }

    public void clearTable() {
        logger.info("Requested to clear the table");
        jdbcTemplate.execute(
                "DELETE FROM timetable");
    }

    public void setSequence() {
        logger.info("Requested to set the sequence");
        jdbcTemplate.execute(
                "UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME='timetable'");
    }

    public Number createTask(Task task) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        logger.info("Requested to create a new task");
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(
                            String.format("INSERT INTO timetable (task, priority, status) values('%s', %s, 'to Do')", task.getTask(), task.getPriority()));
                    return ps;
                }, keyHolder
        );
        return keyHolder.getKey();
    }
    public void updateStatus(Task task, int id) {
        logger.info("Requested to the status of the task");
        jdbcTemplate.update(
                String.format("UPDATE timetable SET status = '%s' WHERE id = %s", task.getStatus(), id));
    }

    public void deleteTask(int id) {
        logger.info("Requested to delete the task ");
        jdbcTemplate.execute(
                String.format("DELETE FROM timetable WHERE id = %s", id));
    }

    public List<Task> showTasks() {
        logger.info("Requested to show tasks in descending order");
        String sql = "SELECT * FROM timetable ORDER BY priority DESC";
        List<Task> tasks = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Task.class));
        return tasks;
    }
}





