public class Main {

    public static void main(String args[]) {
        DataBaseEngine dbe = new DataBaseEngine();
        dbe.establishConnection();
        MainMenu.displayMenu();

    }
}
