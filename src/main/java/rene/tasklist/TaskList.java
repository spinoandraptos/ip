package rene.tasklist;
import rene.storage.Storage;
import rene.task.Task;
import rene.task.ToDo;
import rene.task.Deadline;
import rene.task.Event;
import rene.exception.ReneExceptions;
import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> allTasks; //array of inputs
    public void addToTaskList(String input, Task.TaskType taskType, boolean showMessage){
        switch (taskType) {
            case TODO:
                try {
                    String toDoDescription = input.split("todo")[1].strip();
                    if (toDoDescription.equals("")) {
                        throw new ReneExceptions("Incomplete Command");
                    }
                    allTasks.add(new ToDo(toDoDescription));
                    if (showMessage) {
                        System.out.println("    I have added the following task OwO:");
                        System.out.printf("      [T][] %s\n", viewTaskByIndex(getTaskListSize()));
                        System.out.println("    Now you have " + getTaskListSize() + " tasks in the list! UWU");
                    }
                    break;
                } catch (ArrayIndexOutOfBoundsException | ReneExceptions incompleteCommand) {
                    System.out.println("    Ohnus! You did not use give todo a name!");
                    System.out.println("    Pwease format your input as todo [task name]!");
                    return;
                }
            case DEADLINE:
                String deadlineTiming;
                String deadlineDescription;
                String[] deadlineDetails;
                try {
                    deadlineDetails = input.split("deadline")[1].strip().split("/");
                } catch (ArrayIndexOutOfBoundsException incompleteCommand) {
                    System.out.println("    Ohnus! You did not use give deadline a name!");
                    System.out.println("    Pwease format your input as deadline [task name] /by [time]!");
                    return;
                }

                try {
                    deadlineDescription = deadlineDetails[0].strip();
                    if (deadlineDescription.equals("")) {
                        throw new ReneExceptions("Incomplete Deadline Description");
                    }
                    deadlineTiming = deadlineDetails[1].strip().split("by")[1].strip();
                    if (deadlineTiming.equals("")) {
                        throw new ReneExceptions("Incomplete Due Time");
                    }
                    allTasks.add(new Deadline(deadlineDescription, deadlineTiming));
                    if (showMessage) {
                        System.out.println("    I have added the following task OwO:");
                        System.out.printf("      [D][] %s\n", viewTaskByIndex(getTaskListSize()));
                        System.out.println("    Now you have " + getTaskListSize() + " tasks in the list! UWU");
                    }
                    break;
                } catch (IndexOutOfBoundsException incompleteCommand) {
                    System.out.println("    Ohnus! You did not use '/by' to signal due time!");
                    System.out.println("    Pwease format your input as deadline [task name] /by [time]!");
                    return;
                } catch (ReneExceptions incompleteCommand) {
                    String exceptionMessage = incompleteCommand.getMessage();
                    switch (exceptionMessage) {
                        case "Incomplete Deadline Description":
                            System.out.println("    Ohnus! You did not use give deadline a name!");
                            System.out.println("    Pwease format your input as deadline [task name] /by [time]!");
                            return;
                        case "Incomplete Due Time":
                            System.out.println("    Ohnus! You did not use give deadline a due time!");
                            System.out.println("    Pwease format your input as deadline [task name] /by [time]!");
                            return;
                        default:
                            return;

                    }
                }
            case EVENT:
                String eventStartTiming = null;
                String eventEndTiming = null;
                String[] eventDetails;
                String eventDescription = null;

                try {
                    eventDetails = input.split("event")[1].strip().split("/");
                } catch (ArrayIndexOutOfBoundsException incompleteCommand) {
                    System.out.println("    Ohnus! You did not use give event a name!");
                    System.out.println("    Pwease format your input as event [task name] /from [start time] /to [end time]!");
                    return;
                }
                try {
                    eventDescription = eventDetails[0].strip();
                    if (eventDescription.equals("")) {
                        throw new ReneExceptions("Incomplete Event Description");
                    }
                    eventStartTiming = eventDetails[1].strip().split("from")[1].strip();
                    if (eventStartTiming.equals("")) {
                        throw new ReneExceptions("Incomplete Start Time");
                    }
                } catch (IndexOutOfBoundsException incompleteCommand) {
                    System.out.println("    Ohnus! You did not use '/from' to signal start time!");
                    System.out.println("    Pwease format your input as event [task name] /from [start time] /to [end time]!");
                    return;
                } catch (ReneExceptions incompleteCommand) {
                    String exceptionMessage = incompleteCommand.getMessage();
                    switch (exceptionMessage) {
                        case "Incomplete Event Description":
                            System.out.println("    Ohnus! You did not use give event a name!");
                            System.out.println("    Pwease format your input as event [task name] /from [start time] /to [end time]!");
                            return;
                        case "Incomplete Start Time":
                            System.out.println("    Ohnus! You did not use give event a start time!");
                            System.out.println("    Pwease format your input as event [task name] /from [start time] /to [end time]!");
                            return;
                        default:
                            return;
                    }
                }
                try {
                    eventEndTiming = eventDetails[2].strip().split("to")[1].strip();
                    if (eventEndTiming.equals("")) {
                        throw new ReneExceptions("Incomplete Start Time");
                    }
                    allTasks.add(new Event(eventDescription, eventStartTiming, eventEndTiming));
                    if (showMessage) {
                        System.out.println("    I have added the following task OwO:");
                        System.out.printf("      [E][] %s\n", viewTaskByIndex(getTaskListSize()));
                        System.out.println("    Now you have " + getTaskListSize() + " tasks in the list! UWU");
                    }
                } catch (IndexOutOfBoundsException incompleteCommand) {
                    System.out.println("    Ohnus! You did not use '/to' to signal end time!");
                    System.out.println("    Pwease format your input as event [task name] /from [start time] /to [end time]!");
                    return;
                } catch (ReneExceptions incompleteCommand) {
                    System.out.println("    Ohnus! You did not use give event a end time!");
                    System.out.println("    Pwease format your input as event [task name] /from [start time] /to [end time]!");
                    return;
                }
                break;
        }
    }

    public void printTask(Task task, boolean asList){
        int taskIndex = allTasks.indexOf(task);
        switch(task.getTaskType()) {
            case TODO:
                if (task.taskIsDone()) {
                    if(asList){
                        System.out.printf("    %d: [T][X] %s\n", taskIndex+1, task.getTaskDescription());
                    } else{
                        System.out.printf("        [T][X] %s\n", task.getTaskDescription());
                    }

                } else {
                    if(asList){
                        System.out.printf("    %d: [T][] %s\n", taskIndex+1, task.getTaskDescription());
                    } else{
                        System.out.printf("        [T][] %s\n", task.getTaskDescription());
                    }
                }
                break;
            case DEADLINE:
                if (task.taskIsDone()) {
                    if (asList) {
                        System.out.printf("    %d: [D][X] %s %s\n", taskIndex + 1, task.getTaskDescription(), task.getTaskTiming());
                    } else {
                        System.out.printf("        [D][X] %s %s\n", task.getTaskDescription(), task.getTaskTiming());
                    }
                }
                else {
                    if (asList) {
                        System.out.printf("    %d: [D][] %s %s\n", taskIndex + 1, task.getTaskDescription(), task.getTaskTiming());
                    } else {
                        System.out.printf("        [D][] %s %s\n", task.getTaskDescription(), task.getTaskTiming());
                    }
                }
                break;
            case EVENT:
                if (task.taskIsDone()) {
                    if (asList) {
                        System.out.printf("    %d: [E][X] %s %s\n", taskIndex+1, task.getTaskDescription(), task.getTaskTiming());
                    } else {
                        System.out.printf("        [E][X] %s %s\n", task.getTaskDescription(), task.getTaskTiming());
                    }
                } else {
                    if (asList) {
                        System.out.printf("    %d: [E][] %s %s\n", taskIndex+1, task.getTaskDescription(), task.getTaskTiming());
                    } else {
                        System.out.printf("        [E][] %s %s\n", task.getTaskDescription(), task.getTaskTiming());
                    }
                }
                break;
        }
    }
    public void printTaskList(){
        if(allTasks.isEmpty()){
            System.out.println("    No tasks found! Time to add some OWO");
        }
        else {
            for (Task task : allTasks) {
                printTask(task, true);
            }
        }
    }
    public void markTaskAsDone(int index, boolean showMessage){
        try{
            allTasks.get(index-1).markAsDone();
            if(showMessage) {
                Task task = allTasks.get(index - 1);
                System.out.println("    Roger that! I have marked the following task as done >w< !");
                printTask(task, false);
            }
        } catch (IndexOutOfBoundsException invalidIndex){
            System.out.println("    Ohnuuu! Please enter valid task number *sobs*");
        }
    }
    public void markTaskAsNotDone(int index){
        try{
            allTasks.get(index-1).markAsNotDone();
            Task task = allTasks.get(index-1);
            System.out.println("    Roger that! I have unmarked the following task as done >w< !");
            printTask(task, false);
        } catch (IndexOutOfBoundsException invalidIndex){
            System.out.println("    Ohnuuu! Please enter valid task number *sobs*");
        }
    }

    public void deleteTaskByIndex(int index){
        try{
            Task task = allTasks.get(index-1);
            allTasks.remove(index - 1);
            System.out.println("    Roger that! I have deleted the following task >w< !");
            printTask(task, false);
            System.out.println("    Now you have " + getTaskListSize() + " tasks in the list! UWU");
        } catch (IndexOutOfBoundsException invalidIndex){
            System.out.println("    Ohnuuu! Please enter valid task number *sobs*");
        }
    }

    public String viewTaskByIndex(int index){
        try{
            switch(allTasks.get(index-1).getTaskType()) {
                case TODO:
                    return allTasks.get(index-1).getTaskDescription();
                case DEADLINE:
                case EVENT:
                    return allTasks.get(index-1).getTaskDescription() + " " + allTasks.get(index-1).getTaskTiming();
                default:
                    return "Task Not Found";
            }
        } catch(NullPointerException | IndexOutOfBoundsException invalidIndex){
            System.out.println("    Ohnuuu! Please enter valid task number *sobs*");
            return "Task Not Found";
        }
    }
    public int getTaskListSize(){
        return allTasks.size();
    }
    public ArrayList<Task> getAllTasks(){
        return allTasks;
    }

    public TaskList(){
        allTasks = new ArrayList<>();
    }
}