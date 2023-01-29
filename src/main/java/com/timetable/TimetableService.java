package com.timetable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TimetableService {
    private static final Logger logger = LoggerFactory.getLogger(TimetableService.class);
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void create() {
        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS timetable (id INTEGER PRIMARY KEY AUTOINCREMENT, task STRING, priority INTEGER, status STRING)");
    }
    public void clear() {
        jdbcTemplate.execute(
                "DELETE FROM timetable; UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME='timetable';");
    }
    public void newTask(Task task) {
        jdbcTemplate.update(
                String.format("INSERT INTO timetable (task, priority, status) values('%s', %s, 'to Do')", task.getTask(), task.getPriority()));
    }
    public void updateStatus(Task task, int id) {
        jdbcTemplate.update(
                String.format("UPDATE timetable SET status = '%s' WHERE id = %s", task.getStatus(), id));
    }
    public void deleteTask(int id) {
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





