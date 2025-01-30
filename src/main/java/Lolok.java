
public class Lolok {
    private final String name = "Lolok";
    private Storage storage;
    private TaskList taskList;
    private Command command;
    private Ui ui;

    public Lolok(String path) {
        readData(path);
        this.ui = new Ui();
    }

    public void run() {
        ui.greet(this.name);
        this.getUserInput();
    }

    public void readData(String path) {
        this.storage = new Storage(path);
        this.taskList = new TaskList(storage.loadData());
    }

    private void exit() {
        this.storage.saveData(taskList.getList(), false);
        System.out.println("Bye. Hope to see you again soon!");
        Ui.printLine();
    }

    private void getUserInput() {
        boolean exit = false;
        while (!exit) {
            try {
                String input = ui.readCommand();
                Ui.printLine();
                Command command = new Command(input.split(" "));
                command.executeCommand(taskList, ui, storage);
                exit = command.isExit();
            } catch (LolokException e) {
                // should already handle exception in Command
                System.out.println(e.toString());
            } finally {
                if(!exit) {
                    Ui.printLine();
                }
            }
        }
        this.exit();
    }

    public static void main(String[] args) {
        String defaultPath = "./data/lolok_data.txt";
        Lolok lolok = new Lolok(defaultPath);
        lolok.run();
    }
}
