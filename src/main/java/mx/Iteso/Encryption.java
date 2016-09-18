package mx.Iteso;

import static mx.Iteso.MathUtils.*;

/**
 * Created by Angel on 9/9/16.
 */
public class Encryption implements  Cyphers {

    private final int ALPH_SIZE = 26;
    private final int UP_SET = 65;

    public String encrypt(String plainText, String key) {
        int k1 = genK1(key);
        int[][] k3 = genK3(key);
        String encryptedCesar = encryptCesar(plainText, k1);
        String encryptedVigenere = encryptVigenere(encryptedCesar, key);
        String encryptedHill = encryptHill(encryptedVigenere, k3);
        return encryptedHill;
        //return encryptHill(encryptVigenere(encryptCesar(plainText, genK1(key)), key), genK3(key));
    }

    public String decrypt(String encryptedText, String key) {
        int k1 = genK1(key);
        int[][] k3 = genK3(key);
        String encryptedVigenere = decryptHill(encryptedText, k3);
        String encryptedCesar = decryptVigenere(encryptedVigenere, key);
        String plainText = decryptCesar(encryptedCesar, k1);
        return plainText;
        //return decryptCesar(decryptVigenere(decryptHill(encryptedText, genK3(key)), key), genK1(key));
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

        System.out.println("[0][0]: " + k3[0][0] + "\n" +
                           "[1][0]: " + k3[1][0] + "\n" +
                           "[0][1]: " + k3[0][1] + "\n" +
                           "[1][1]: " + k3[1][1]);
        return k3;
    }

    /** This function receives  a String and a integer
     *  This function encrypts the plaintext to Cesar encryption
     *  This function returns a String with Cesar encryption   **/

    public String encryptCesar(String plainText, int k1) {
        String encryptTextOut = "";
        char moves;
        for(int i=0;i<plainText.length();i++){
            if(plainText.charAt(i)!=' '){
                moves = (char)(plainText.charAt(i)+k1);
                if(moves > 'z'){
                    // We use the formula m + k with a shift of the ALPH_SIZE
                    moves = (char)((plainText.charAt(i)+k1)-ALPH_SIZE);
                    encryptTextOut += moves;
                }
                    // We use the formula m + k
                else{
                    moves = (char)((plainText.charAt(i)+k1));
                    encryptTextOut += moves;
                }
            }
            else{
                moves = (char)(plainText.charAt(i));
                encryptTextOut +=moves;
            }
        }
        return encryptTextOut.toUpperCase();
    }

    /** This function receives  a String and a key string
     *  This function encrypts the plaintext to Vigenere encryption
     *  This function returns a String with Vigenere encryption   **/

    public String encryptVigenere(String plainText, String k2) {
        plainText = plainText.toUpperCase();
        String encryptTextOut = "";
        int counter = 0;
        int converter;
        char moves;
        for(int i=0;i<plainText.length();i++) {
            if (plainText.charAt(i) != ' ') {
                if (k2.length() > counter) {
                    // We use the formula (M[n] - K[n]) and used a shift of the ascii position
                    moves = (char) (plainText.charAt(i)-UP_SET + k2.charAt(counter)-UP_SET);
                    // We use the formula (M[n] - K[n]) % 26
                    converter = (int) moves %ALPH_SIZE;
                    // We use the defined alphabet
                    encryptTextOut += abecedario[converter];
                    counter++;
                    if(counter == k2.length()){
                        counter = 0;
                    }
                }
            }
            else {
                moves = plainText.charAt(i);
                encryptTextOut += moves;
            }
        }
        return encryptTextOut;
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

    /** This function receives  a String and a integer
     *  This function decrypts Cesar encryption to plain text
     *  This function returns a String with plain text   **/

    public String decryptCesar(String encryptedText, int k1) {
        encryptedText = encryptedText.toUpperCase();
        String decryptTextOut = "";
        char moves;
        for(int i=0;i<encryptedText.length();i++){
            if(encryptedText.charAt(i)!=' '){
                moves = (char)(encryptedText.charAt(i)-k1);
                if(moves < 'A'){
                    // We use the formula m - k with a shift of the ALPH_SIZE
                    moves = (char)((encryptedText.charAt(i)-k1)+ALPH_SIZE);
                    decryptTextOut += moves;
                }
                else{
                    // We use the formula m - k
                    moves = (char)((encryptedText.charAt(i)-k1));
                    decryptTextOut += moves;
                }
            }
            else{
                moves = (char)(encryptedText.charAt(i));
                decryptTextOut +=moves;
            }
        }
        return decryptTextOut.toLowerCase();
    }

    /** This function receives  a String and a key String
     *  This function decrypts Vigenere encryption to plain text
     *  This function returns a String with plain text   **/

    public String decryptVigenere(String encryptedText, String k2) {
        encryptedText = encryptedText.toUpperCase();
        String decryptTextOut = "";
        int counter = 0;
        int converter;
        char moves;
        for(int i=0;i<encryptedText.length();i++) {
            if (encryptedText.charAt(i) != ' ') {
                if (k2.length() > counter) {
                    // We use the formula (M[n] - K[n]) and used a shift of the ascii position + ALPH_Size (Safe value)
                    moves = (char) (((encryptedText.charAt(i)-UP_SET) - (k2.charAt(counter)-UP_SET)) + ALPH_SIZE);
                    // We use the formula (M[n] - K[n]) % 26
                    converter = (int) moves  % ALPH_SIZE;
                    // We use the defined alphabet
                    decryptTextOut += abecedario[converter];
                    counter++;
                    if(counter == k2.length()){
                        counter = 0;
                    }
                }
            }
            else {
                moves = encryptedText.charAt(i);
                decryptTextOut += moves;
            }
        }
        return decryptTextOut.toLowerCase();
    }

    public String decryptHill(String encryptedText, int[][] k3) {
        int x = 1;

        // We get the determinant of k3
        double detK = modulo(determinant(k3), ALPH_SIZE);

        // We find the value of y
        double y = (ALPH_SIZE*x+1)/detK;
        while(y % 1 != 0) {
            x++;
            y = (ALPH_SIZE*x+1)/detK;
        }
        int invDetK = modulo((int)y, ALPH_SIZE);

        // We calculate the transverse cofactor of K
        int[][] transvCofK = get2x2TransverseCofactor(k3);

        // We multiply the tranverse cofactor of K with the inverse determinant of K to get the inverse of K (K^-1)
        int[][] inverseK = transvCofK;
        for(int i = 0; i < 2; i++)
            for(int j = 0; j < 2; j++)
                inverseK[j][i] = modulo((inverseK[j][i] * invDetK), ALPH_SIZE);

        // We call encryptHill with the inverse K since decrypting is the same process at this point as encrypting
        return encryptHill(encryptedText, inverseK);
    }
}
