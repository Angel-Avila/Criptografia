package mx.Iteso;

/**
 * Created by Angel on 9/8/16.
 */
public interface Cyphers {

    String key = "";
    String k1 = "";
    String k2 = "";
    int[][] k3 = new int[2][2];

    String encrypt(String plainText, String key);
    String decrypt(String encryptedText, String key);

    int genK1(String key);
    int[][] genK3(String key);

    String encryptCesar(String plainText, int k1);
    String encryptVigenere(String plainText, String k2);
    String encryptHill(String plainText, int[][] k3);

    String decryptCesar(String encryptedText, int k1);
    String decryptVigenere(String encryptedText, String k2);
    String decryptHill(String encryptedText, int[][] k3);
}
