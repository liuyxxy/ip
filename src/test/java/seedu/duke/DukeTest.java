package seedu.duke;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class DukeTest {
    public static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("d MMM yyyy kkmm", Locale.ENGLISH);

    @Test
    public void TaskListOperationsTest() {
        ArrayList<Task> ls = new ArrayList<>();
        ls.add(new ToDo("test todo", false));
        ls.add(new Deadline("test deadline", LocalDateTime.parse("02 Dec 2019 1800", FORMATTER), false));
        ls.add(new Event("test event", LocalDateTime.parse("02 Mar 2020 1800", FORMATTER), false));
        TaskList tl = new TaskList(ls);

        assertEquals(3, tl.size());
        tl.add(new ToDo("test todo 2", false));
        assertEquals(4, tl.size());

        tl.remove(3);
        assertEquals(3, tl.size());

        String actual = tl.get(0).getStatus();
        String expected = "[T][\u2718] test todo";
        assertEquals(actual, expected);
    }

}
