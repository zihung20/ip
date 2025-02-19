package loklok;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import loklok.command.Command;
import loklok.exception.InvalidCommandException;
import loklok.storage.Storage;
import loklok.task.TaskList;
import loklok.ui.Ui;

/**
 * main class of the chatbot loklok
 */
public class LokLok {
    private Storage storage;
    private TaskList taskList;
    private boolean isVillager;
    private final Random random = new Random();
    /**
     * Creates an instance of the LokLok chatbot that loads and stores data using the given file path.
     *
     * @param path the file path where data is stored
     */
    public LokLok(String path) {
        readData(path);
    }

    /**
     * Greets the user.
     *
     * @param name The name of the chatbot, e.g., "loklok" for this case.
     * @return The string representation of the greeting message.
     */
    public String greet(String name) {
        String greetMessage = "Hello! I'm " + name + ". ";
        greetMessage += "What can I do for you?";
        return greetMessage;
    }
    /**
     * Reads data from the specified file.
     *
     * @param path the file path to read data from
     */
    private void readData(String path) {
        storage = new Storage(path);
        taskList = new TaskList(storage.loadData());
    }

    private void exit() {
        storage.saveData(taskList.getList(), false);
        Ui.printMessage("Bye. Hope to see you again soon!");
    }

    private boolean checkVillagerConfiguration(String input) {
        if (input.equals("hmmm")) {
            isVillager = true;
            return true;
        } else if (input.equals("unHmmm")) {
            isVillager = false;
            return true;
        }
        return false;
    }

    public String getResponse(String input) {
        if (checkVillagerConfiguration(input)) {
            return "(⌐■_■)";
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream ps = redirectSystemOutput(byteArrayOutputStream);
        PrintStream originalOut = System.out;
        String response;

        try {
            Command command = new Command(input.split(" "));
            command.executeCommand(taskList);
            if (command.isExit()) {
                this.exit();
            }
        } catch (InvalidCommandException e) {
            Ui.printErrorMessage(e.toString());
        } finally {
            response = resetSystemOutput(ps, originalOut, byteArrayOutputStream);
        }
        if (isVillager) {
            double random = Math.random();
            return random > 0.5 ? transformToVillagerStyle(response) : response;
        }
        return response;
    }
    /**
     * Transforms a normal chatbot response into a villager-style response.
     * Each word is replaced with "hmm" followed by a random number of "m"s,
     * while maintaining the original formatting (such as line breaks).
     *
     * @param response The original response string.
     * @return The transformed villager-style response.
     */
    public String transformToVillagerStyle(String response) {
        // generated By ChatGPT
        String[] lines = response.split("(?<=\\n)");
        StringBuilder villagerResponse = new StringBuilder();

        for (String line : lines) {
            String[] words = line.split("\\s+");
            for (int i = 0; i < words.length; i++) {
                villagerResponse.append(generateRandomHmm());

                if (i < words.length - 1) {
                    villagerResponse.append(" ");
                }
            }
            villagerResponse.append("\n");
        }

        return villagerResponse.toString();
    }

    /**
     * Generates a random "hmm" with a random length of "m"s.
     *
     * @return A string containing "hmm" followed by a random number of "m"s.
     */
    private String generateRandomHmm() {
        int length = random.nextInt(7) + 1;
        return "h" + "m".repeat(length);
    }
    /**
     * Redirects the system output to a PrintStream that captures the output to a ByteArrayOutputStream.
     * @param byteArrayOutputStream the ByteArrayOutputStream to capture the output.
     * @return the PrintStream that captures the system output.
     */
    private PrintStream redirectSystemOutput(ByteArrayOutputStream byteArrayOutputStream) {
        PrintStream ps = new PrintStream(byteArrayOutputStream);
        System.setOut(ps);
        return ps;
    }

    /**
     * Resets the system output to the original PrintStream and retrieves the captured output.
     * @param printStream the PrintStream that was used for redirecting output.
     * @param originalOut the original system output (usually System.out).
     * @param baos the ByteArrayOutputStream that captured the output.
     * @return the captured output as a string.
     */
    private String resetSystemOutput(PrintStream printStream, PrintStream originalOut, ByteArrayOutputStream baos) {
        printStream.flush();
        String response = baos.toString();
        baos.reset();
        System.setOut(originalOut);
        return response;
    }
}
