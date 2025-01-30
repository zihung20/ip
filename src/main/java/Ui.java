public class Ui {

    public static void printLine() {
        System.out.println("_".repeat(32));
    }

    public static void printMessageBlock(String message) {
        printLine();
        System.out.println(message);
        printLine();
    }
}
