package lolok.command;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import lolok.exception.InvalidCommandException;

public class CommandTest {
    @Test
    public void testEmptyCommand() {
        // new String[]{} is not same as split
        assertThrows(InvalidCommandException.class, () -> new Command("".split(" ")));
    }

    @Test
    public void testInvalidCommand() {
        String[] input = "wrong command".split(" ");
        assertThrows(InvalidCommandException.class, () -> new Command(input));
    }
}
