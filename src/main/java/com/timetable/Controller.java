package com.timetable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/timetable")
public class Controller {
    @Autowired
    TimetableService timetableService;
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    @PostMapping("/newTask")
    public String createNewTask(@RequestBody Task task) {
        logger.info("requested to create a new task");
        timetableService.newTask(task);
        return "new task is created";
    }

    @PutMapping("/updateStatus/{id}")
    public String updateStatus(@RequestBody Task task, @PathVariable int id) {
        logger.info("requested to amend the status of the task");
        timetableService.updateStatus(task, id);
        return "status changed";
    }

    @DeleteMapping("/deleteTask/{id}")
    public String deleteTask(@PathVariable int id) {
        logger.info("requested to delete the task");
        timetableService.deleteTask(id);
        return "task is deleted";
    }

    @GetMapping("/showTasks")
    public List<Task> showTasks() {
        logger.info("requested to show all tasks");
        try {
            return timetableService.showTasks();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return (List<Task>) new Task();
        }
    }
}
