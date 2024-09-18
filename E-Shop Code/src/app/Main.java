package src.app;

import src.controller.EShop;

public class Main {
    public static void main(String[] args) {
        boolean useGUI = false; // // Standardmäßig wird die CUI verwendet

        // Überprüft, ob ein Argument übergeben wurde und ob es "gui" ist
        if (args.length > 0 && args[0].equalsIgnoreCase("gui")) { //Überprüft ob das array args 1 Element enthält
            useGUI = true; // Wenn das Argument "gui" ist, wird die GU bI verwendet
        }

        EShop shop = new EShop(useGUI); // Erstellt eine Instanz des EShop mit der Option, die GUI zu verwenden oder nicht
        shop.start(); // Startet den EShop
    }
}
