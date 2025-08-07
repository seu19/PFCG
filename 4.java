import java.util.Scanner;
import java.util.Base64;
import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;

public class rsa2{
    public static void main(String[] args) throws Exception{
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Enter the text to encrypt: ");
        String text = sc.nextLine();

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);

        KeyPair pair =kpg.genKeyPair();
        PublicKey pub = pair.getPublic();

        Cipher cip = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cip.init(Cipher.ENCRYPT_MODE, pub);

        byte[] input = text.getBytes();
        cip.update(input);

        byte[] cptext = cip.doFinal();

        String encrypted = Base64.getEncoder().encodeToString(cptext);
        System.out.println("Encrypted text: "+encrypted);

        cip.init(Cipher.DECRYPT_MODE, pair.getPrivate());

        byte[] decrypted = Base64.getDecoder().decode(encrypted);
        byte[] decipher = cip.doFinal(decrypted);

        System.out.println("the decrypted text: "+new String(decipher));

        sc.close();
    }
}