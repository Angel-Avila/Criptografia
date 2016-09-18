package mx.Iteso;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Angel && Diego on 9/8/16.
 */
public class CyphersTest {

    private Cyphers cypher;
    private Encryption encryption;
    private String plainText = "el almacen fue encontrado huye";
    private String encryptedCesar = "OV KVWKMOX PEO OXMYXDBKNY REIO";
    private String encryptedVigenere = "VV KMILQJE PEF AYQTEDBBZZ VZPO";
    private String encryptedText = "RT HTEFIXC VRE ASILCZWOTJ RJBE";
    private String key = "HAARMBEV";
    private int k1 = 10;
    private int[][] k3 = new int[][] {
            {7, 0},
            {0, 17}
    };

    @Before
    public void setUp() {
        cypher = mock(Cyphers.class);
        encryption = new Encryption();

        // Encrypt
        when(cypher.encrypt(plainText, key)).thenReturn(encryptedText);

        // Gen Keys
        when(cypher.genK1(key)).thenReturn(k1);
        when(cypher.genK3(key)).thenReturn(k3);

        // Encrypt
        when(cypher.encryptCesar(plainText, k1)).thenReturn(encryptedCesar);
        when(cypher.encryptVigenere(encryptedCesar, key)).thenReturn(encryptedVigenere);
        when(cypher.encryptHill(encryptedVigenere, k3)).thenReturn(encryptedText);

        // Decrypt
        when(cypher.decrypt(encryptedText, key)).thenReturn(plainText);

        when(cypher.decryptHill(encryptedText, k3)).thenReturn(encryptedVigenere);
        when(cypher.decryptVigenere(encryptedVigenere, key)).thenReturn(encryptedCesar);
        when(cypher.decryptCesar(encryptedCesar, k1)).thenReturn(plainText);
    }

    // Encryption tests
    @Test
    public void testEncrypt(){
        assertEquals(encryptedText, cypher.encrypt(plainText, key));
    }

    @Test
    public void testEncryptCesar() {
        assertEquals(encryptedCesar, cypher.encryptCesar(plainText, 10));
    }

    @Test
    public void testEncryptVigenere() {
        assertEquals(encryptedVigenere, cypher.encryptVigenere(encryptedCesar, key));
    }

    @Test
    public void testEncryptHill() {
        assertEquals(encryptedText, encryption.encryptHill(encryptedVigenere.toUpperCase(), k3));
    }

    // Decryption tests
    @Test
    public void testDecrypt() {
        assertEquals(plainText, cypher.decrypt(encryptedText, key));
    }

    @Test
    public void testDecryptHill() {
        assertEquals(encryptedVigenere, encryption.decryptHill(encryptedText, k3));
    }

    @Test
    public void testDecryptVigenere() {
        assertEquals(encryptedCesar, cypher.decryptVigenere(encryptedVigenere, key));
    }

    @Test
    public void testDecryptCesar() {
        assertEquals(plainText, cypher.decryptCesar(encryptedCesar, k1));
    }

    @Test
    public void testGetK1() {
        assertEquals(k1, encryption.genK1(key));
    }

    @Test
    public void testGetK3() {
        assertEquals(k3, encryption.genK3(key));
    }

    @Ignore
    public void testGetDeterminantGetGCD() {
        int det = MathUtils.determinant(k3);
        System.out.println("Det: " + det + ", GCD: " + MathUtils.getGCD(26, det) + ", " + (17*7));
    }

    @Ignore
    public void testMatrixMult() {
        int[][] a = new int[][] {
                {4,1},
                {7,3}
        };

        int[][] b = new int[][] {
                {17},
                {0}
        };

        System.out.println(a[0][0] + ", " + a[0][1] + ", " + a[1][0] + ", " + a[1][1]);
        System.out.println(MathUtils.multiply(a,b)[0][0] + ", " + MathUtils.multiply(a,b)[1][0]);
    }
}
