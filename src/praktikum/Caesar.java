package praktikum;

import javax.swing.*;

public class Caesar {
    public static void main(String[] args) {
        String text = "";
        int key = 0;
        String input = JOptionPane.showInputDialog("Wollen Sie eine Nachricht verschlüsseln(v/V) oder entschlüsseln(e/E)?");
        if (input.equalsIgnoreCase("v")) {
            key = Integer.parseInt(JOptionPane.showInputDialog("Geben Sie den Schlüssel ein: "));
            text = JOptionPane.showInputDialog("Geben Sie einen zu verschlüsselnden Text ein: ");
        } else if (input.equalsIgnoreCase("e")) {
            key = Integer.parseInt(JOptionPane.showInputDialog("Geben Sie den Schlüssel ein: "));
            key = -key;
            text = JOptionPane.showInputDialog("Geben Sie einen zu entschlüsselnden Text ein: ");
        } else {
            System.out.println("Ungültige eingabe. Programm wird beendet");
            System.exit(0);
        }
        StringBuffer crypt = new StringBuffer();
        for (int i = 0; i < text.length(); i++) {
            char current = text.charAt(i);
            if (current == 32) {
                crypt.append(" ");
                continue;
            }
            if (current > 96 && current < 123) {
                current += key;
                if (current > 122)
                    current = (char) ((current % 122) + 96);
                crypt.append(current);
            } else if (current > 64 && current < 91) {
                current += key;
                if (current > 90)
                    current = (char) ((current % 90) + 64);
                crypt.append(current);
            } else if (current > 47 && current < 58) {
                current += key;
                if (current > 57)
                    current = (char) ((current % 57) + 47);
                crypt.append(current);
            } else {
                System.out.println("Die Nachricht enthält ungültige Zeichen");
                System.exit(0);
            }
        }
        if (input.equalsIgnoreCase("v")) {
            JOptionPane.showMessageDialog(null, "Verschlüsselt: " + crypt);
        } else if (input.equalsIgnoreCase("e")) {
            JOptionPane.showMessageDialog(null, "Entschlüsselt: " + crypt);
        }

    }
}
