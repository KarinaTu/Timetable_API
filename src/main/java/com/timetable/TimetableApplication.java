package com.timetable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class TimetableApplication {
    @Autowired
    TimetableService timetableService;

    public static void main(String[] args) {

        SpringApplication.run(TimetableApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            timetableService.create();
            Scanner scanner = new Scanner(System.in);
            boolean isCorrectAnswer = false;
            while (!isCorrectAnswer) {
                System.out.println("Do you want to start a new day?");
                String answer = scanner.nextLine();
                if (answer.equalsIgnoreCase("YES")) {
                    timetableService.clear();

                    isCorrectAnswer = true;
                } else if (answer.equalsIgnoreCase("NO")) {
                    isCorrectAnswer = true;
                } else {
                    System.out.println("Answer must be 'YES' or 'NO'. Please try again.");
                }
            }
            boolean isTimetableActive = true;
            while (isTimetableActive) {
                System.out.println("Please choose the task: 1 = Add a new task, 2 = Amend status of existing task, 3 = Show task list in descending order, 4 = Close the timetable");
                try {
                    int inputtedChoice = scanner.nextInt();
                    if (inputtedChoice < 1 || inputtedChoice > 4) {
                        System.out.println("Incorrect selection. Please try again");
                    } else if (inputtedChoice == 4) {
                        isTimetableActive = false;
                        System.out.println("Timetable is closed");
                    } else {
                        switch (inputtedChoice) {
                            case 1 -> {
                                System.out.println("Please clarify the name of the task");
                                scanner.nextLine();
                                String task = scanner.nextLine();
                                Task task1 = new Task();
                                task1.setTask(task);
                                System.out.println("Please clarify the priority");
                                int priority = scanner.nextInt();
                                task1.setPriority(priority);
                                timetableService.newTask(task1);
                            }
                            case 2 -> {
                                System.out.println("Please clarify the task ID");
                                int id = scanner.nextInt();
                                scanner.nextLine();
                                Task task2 = new Task();
                                System.out.println("Please select the new status of the task(inProgress/done/canceled)");
                                String status = scanner.nextLine();
                                task2.setStatus(status);
                                timetableService.updateStatus(task2, id);

                            }
                            case 3 -> {
                                List<Task> tasks = timetableService.showTasks();
                                for (Task task : tasks) {
                                    System.out.println("id = " + task.getId() + "; task = " + task.getTask() + "; priority = " + task.getPriority() + "; status = " + task.getStatus());
                                }
                            }
                        }
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Inserted wrong number. Please try again");
                    scanner.next();
                }
            }
        };
    }
}

