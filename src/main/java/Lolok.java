public class Lolok {
    private final String name = "Lolok";
    private final String logo = "";
    private void greet() {
        System.out.println("Hello! I'm " + this.name);
        System.out.println("What can I do for you?");
    }

    private static void printLine() {
        System.out.println("__________________________________");
    }

    private void exit() {
        System.out.println("Bye. Hope to see you again soon!");
    }
    public static void main(String[] args) {
       Lolok lolok = new Lolok();
       printLine();
       lolok.greet();
       printLine();
       lolok.exit();
       printLine();
    }
}
