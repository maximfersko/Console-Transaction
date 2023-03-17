public class Program {
    public static void main(String[] args) {
        Menu menu = new Menu();
        if (args.length > 0 && args[0].equals("--profile=dev")) {
            menu.mainMenu(Menu.Mode.Dev);
        } else {
            menu.mainMenu(Menu.Mode.Prod);
        }
    }

}