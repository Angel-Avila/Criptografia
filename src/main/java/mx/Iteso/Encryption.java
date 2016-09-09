package mx.Iteso;

/**
 * Created by Angel on 9/9/16.
 */
public class Encryption implements  Cyphers {

    String key = "";
    String k1 = "";
    String k2 = "";
    int[][] k3 = new int[2][2];

    public String encrypt(String plainText, String key) {

        return "Not coded encryption";
    }

    public String decrypt(String encryptedText, String key) {

        return "Not coded decryption";
    }

    public int genK1(String key) {
        int sum = 0;

        for (int i = 0; i < key.length(); i++)
            if(key.charAt(i) != ' ')
                sum += MathUtils.getIndex(key.charAt(i));

        return sum % 26;
    }

    public int[][] genK3(String key) {

        return null;
    }

    public String encryptCesar(String plainText, int k1) {

        return null;
    }

    public String encryptVigenere(String plainText, String k2) {

        return null;
    }

    public String encryptHill(String plainText, int[][] k3) {

        return null;
    }

    public String decryptCesar(String encryptedText, int k1) {

        return null;
    }

    public String decryptVigenere(String encryptedText, String k2) {

        return null;
    }

    public String decryptHill(String encryptedText, int[][] k3) {

        return null;
    }
}
