package seedu.duke;

import seedu.duke.task.Deadline;
import seedu.duke.task.Event;
import seedu.duke.task.Task;
import seedu.duke.task.ToDo;
import seedu.duke.ui.Ui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/**
 * Represents a storage space for the program output.
 */

public class Storage {

    public static final String DEFAULT_STORAGE_FILEPATH = "duke.txt";
    public static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("d MMM yyyy kkmm", Locale.ENGLISH);

    public final String path;

    public Storage() {
        this.path = DEFAULT_STORAGE_FILEPATH;
    }

    public Storage(String filepath) {
        this.path = filepath;
    }

    /**
     * Loads the tasks from the textfile so that Duke can access the tasks
     * @return List containing the tasks
     * @throws IOException When the path is incorrect
     */
    public ArrayList<Task> load() throws IOException {
        File f = new File(this.path);
        f.createNewFile();
        Scanner s = new Scanner(f); // create a Scanner using the File as the source
        ArrayList<Task> ls = new ArrayList<>();

        while (s.hasNext()) {
            String line = s.nextLine();
            String[] arr = line.split("\\*");
            String task = arr[0];
            switch (task) {
                case "E": { // case where the task is an event
                    boolean status = arr[1].equals("\u2713");
                    String todo = arr[2];
                    LocalDateTime deadline = LocalDateTime.parse(arr[3], FORMATTER);
                    ls.add(new Event(todo, deadline, status));
                    break;
                }
                case "T": {
                    boolean status = arr[1].equals("\u2713");
                    String todo = arr[2];
                    ls.add(new ToDo(todo, status));
                    break;
                }
                default: {
                    boolean status = arr[1].equals("\u2713");
                    String todo = arr[2];
                    LocalDateTime deadline = LocalDateTime.parse(arr[3], FORMATTER);
                    ls.add(new Deadline(todo, deadline, status));
                    break;
                }
            }
        }
        return ls;
    }

    /**
     * Writes the tasks from the current running Duke and saves it in a text file
     * @param ls The list of tasks of the current Duke.
     * @param ui The Ui of the current Duke.
     */
    public void save(TaskList ls, Ui ui) {
        try {
            String tasks = "";

            for (Task t : ls.getList()) {
                String check = "";
                if (t.isComplete()) {
                    check = "\u2713";
                } else {
                    check = "\u2718";
                }
                String toAdd = t.getType() + "*" + check + "*" + t.toString();
                String addition = "";
                if (t.getTime() == null) {
                    addition = "\n";
                } else {
                    addition = "*" + t.getTime().format(FORMATTER) + "\n";
                }
                tasks = tasks + toAdd + addition;
            }
            FileWriter fw = new FileWriter(this.path);

            fw.write(tasks + System.lineSeparator());

            fw.close();
        } catch (IOException e) {
            ui.showError("Whoops! Some kind of error :/ see here: " + e.getMessage());
        }

    }


}
