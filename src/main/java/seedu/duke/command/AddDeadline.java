package seedu.duke.command;

import seedu.duke.TaskList;
import seedu.duke.ui.Ui;
import seedu.duke.task.Deadline;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Class that represents adding a task with a deadline.
 */
public class AddDeadline extends AddCommand {
    public static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("d MMM yyyy kkmm", Locale.ENGLISH);

    public AddDeadline(String[] words) {
        super(words);
    }

    /**
     * Adds the task to the list of current tasks.
     * @param ls The current list of tasks.
     * @param ui The ui that takes of printing output.
     * @throws DateTimeException If the user inputs a wrong format of the date.
     */
    @Override
    public void execute(TaskList ls, Ui ui) throws DateTimeException {
        String[] input = words[1].split(" /by ");
        assert input.length > 1 : "Should have two parts to the command.";
        LocalDateTime day = LocalDateTime.parse(input[1], FORMATTER);
        Deadline newDL = new Deadline(input[0], day, false);
        ls.add(newDL);
        String thing = "Alright then, add more things to your ever-growing list of tasks:\n"
                + newDL.getStatus().replaceAll("(?m)^", "\t")
                + "\nNow you have " + ls.size() + " tasks in the list.";
        ui.printResult(thing);
    }
}
