package mx.Iteso;

import static mx.Iteso.MathUtils.*;

/**
 * Created by Angel on 9/9/16.
 */
public class Encryption implements  Cyphers {

    private final int ALPH_SIZE = 26;

    public String encrypt(String plainText, String key) {
        return encryptHill(encryptVigenere(encryptCesar(plainText, genK1(plainText)), plainText), genK3(plainText));
    }

    public String decrypt(String encryptedText, String key) {
        return "Not coded decryption";
    }

    public int genK1(String key) {
        int sum = 0;

        for (int i = 0; i < key.length(); i++)
            if(key.charAt(i) != ' ')
                sum += getIndex(key.charAt(i));

        return sum % ALPH_SIZE;
    }

    public int[][] genK3(String key) {
        int[][] k3 = new int[][] {
                {getIndex(key.charAt(0)), getIndex(key.charAt(1))},
                {getIndex(key.charAt(2)), getIndex(key.charAt(3))}
        };

        int detK = modulo(determinant(k3), ALPH_SIZE);
        int gcd = getGCD(ALPH_SIZE, detK);
        int i = 0;

        while(detK == 0 || gcd != 1) {
            switch (i % 4) {
                case 0: k3[0][0] += 1;
                    break;
                case 1: k3[0][1] += 1;
                    break;
                case 2: k3[1][0] += 1;
                    break;
                case 3: k3[1][1] += 1;
                    break;
            }

            detK = modulo(determinant(k3),ALPH_SIZE);
            gcd = getGCD(ALPH_SIZE, detK);
            i++;
        }

        return k3;
    }

    public String encryptCesar(String plainText, int k1) {

        return "Cesar";
    }

    public String encryptVigenere(String plainText, String k2) {

        return "Vigenere";
    }

    public String encryptHill(String plainText, int[][] k3) {
        StringBuilder encrypted = new StringBuilder();

        for(int i = 0; i < plainText.length(); i++) {
            // We get the indexes for the next two letters different to a blank space
            while(plainText.charAt(i) == ' ') {
                i++;
                encrypted.append(String.valueOf(' '));
            }

            int letterInd1 = i;
            i++;
            while(plainText.charAt(i) == ' ') i++;

            int letterInd2 = i;

            // We grab the letters we are going to multiply
            int[][] letters = new int[][] {
                    {getIndex(plainText.charAt(letterInd1))},
                    {getIndex(plainText.charAt(letterInd2))}
            };

            // We multiply this new matrix with the k3
            int[][] encrypted2Letters = multiply(k3, letters);

            // We add the encrypted first letter to the string
            encrypted.append(String.valueOf(getLetter(encrypted2Letters[0][0] % ALPH_SIZE)));

            // We add the spaces between letter1 and letter2
            while(letterInd2 - letterInd1 != 1) {
                encrypted.append(String.valueOf(' '));
                letterInd1++;
            }

            // We add the encrypted second letter to the string
            encrypted.append(String.valueOf(getLetter(encrypted2Letters[1][0] % ALPH_SIZE)));
        }

        return encrypted.toString();
    }

    public String decryptCesar(String encryptedText, int k1) {

        return null;
    }

    public String decryptVigenere(String encryptedText, String k2) {

        return null;
    }

    public String decryptHill(String encryptedText, int[][] k3) {
        int x = 1;

        // We get the determinant of k3
        double detK = determinant(k3) % ALPH_SIZE;

        // We find the value of y
        double y = (ALPH_SIZE*x+1)/detK;
        while(y % 1 != 0) {
            x++;
            y = (ALPH_SIZE*x+1)/detK;
        }
        int invDetK = (int)y % ALPH_SIZE;

        // We calculate the transverse cofactor of K
        int[][] transvCofK = get2x2TransverseCofactor(k3);

        // We multiply the tranverse cofactor of K with the inverse determinant of K to get the inverse of K (K^-1)
        int[][] inverseK = transvCofK;
        for(int i = 0; i < 2; i++)
            for(int j = 0; j < 2; j++)
                inverseK[j][i] = (inverseK[j][i] * invDetK) % ALPH_SIZE;

        // We call encryptHill with the inverse K since decrypting is the same process at this point as encrypting
        return encryptHill(encryptedText, inverseK).toLowerCase();
    }
}
