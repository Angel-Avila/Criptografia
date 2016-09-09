package mx.Iteso;

import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.io.*;

/**
 * Created by Angel on 9/9/16.
 */
public class MainClass {

    public static void main(String[] args) {
        Encryption encryption = new Encryption();

        int option = 0;
        String plainText = "";
        String encryptedText = "";
        String key = "hi";

        while(option != 3) {
            option = Integer.parseInt(
                    JOptionPane.showInputDialog("¿Qué quieres hacer?\n1. Encriptar\n2.Desencriptar\n3.Salir"));

            // MARK: - Encriptar un texto ------------------------------------------------------------------------------

            if(option == 1) {
                String confirmKey = "";
                while(plainText.length() < 4)
                    plainText = JOptionPane.showInputDialog("Ingrese el texto a encriptar");

                boolean validKey = false;
                while(!validKey) {
                    key = JOptionPane.showInputDialog("Ingrese la llave que desee utilizar\n(4 caracteres min)");
                    confirmKey = JOptionPane.showInputDialog("Vuelva a introducir la llave");

                    if(validateKey(key, confirmKey))
                        validKey = true;
                }

                // Escribir en el .txt
                try {
                    PrintWriter writer = new PrintWriter(
                            new FileWriter(System.getProperty("user.dir") + File.separator + "encrypted.txt", true));
                    writer.println(encryption.encrypt(plainText, key) + "\nKey: " + key +
                    "\n------------------------------------------------------------------------------");
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                JOptionPane.showMessageDialog(null, "Su texto encriptado fue guardado en un .txt");
            }

            // MARK: - Desencriptar un texto ---------------------------------------------------------------------------

            else if(option == 2) {
                String confirmKey = "";
                while(encryptedText.length() < 4)
                    encryptedText = JOptionPane.showInputDialog("Ingrese el texto a desencriptar");

                boolean validKey = false;
                while(!validKey) {
                    key = JOptionPane.showInputDialog("Ingrese la llave");
                    confirmKey = JOptionPane.showInputDialog("Vuelva a introducir la llave");

                    if(validateKey(key, confirmKey))
                        validKey = true;
                }

                // Escribir en el .txt
                try {
                    PrintWriter writer = new PrintWriter(
                            new FileWriter(System.getProperty("user.dir") + File.separator + "decrypted.txt", true));
                    writer.println(encryption.decrypt(encryptedText, key) +
                            "\n------------------------------------------------------------------------------");
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                JOptionPane.showMessageDialog(null, "El texto encriptado es:\n" + encryption.decrypt(encryptedText, key));
            }

            plainText = "";
            encryptedText = "";
            key = "hi";
        }
    }

    private static boolean validateKey(String key, String confirmKey) {
        return key.length() >= 4 && key.equals(confirmKey);
    }

}
