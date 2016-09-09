package mx.Iteso;

/**
 * Created by Angel on 9/8/16.
 */
public interface Cyphers {

    String key = "";
    String k1 = "";
    String k2 = "";
    int[][] k3 = new int[2][2];

    public String encrypt(String plainText, String key);
    public String decrypt(String encryptedText, String key);

    public int genK1(String key);
    public int[][] genK3(String key);

    public String encryptCesar(String plainText, int k1);
    public String encryptVigenere(String plainText, String k2);
    public String encryptHill(String plainText, int[][] k3);

    public String decryptCesar(String encryptedText, int k1);
    public String decryptVigenere(String encryptedText, String k2);
    public String decryptHill(String encryptedText, int[][] k3);
}
