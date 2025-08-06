import java.util.Scanner;

public class ceaserCypher {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the Text: ");
        String text = sc.nextLine();

        System.out.println("Enter the Key: ");
        int key = sc.nextInt();

        System.out.println("Encrypted Text: " + encrypt(text, key));
        System.out.println("Decrypted Text: " + decrypt(encrypt(text, key), key));
        sc.close();
    }

    public static String encrypt(String text, int key) {
        String result = "";

        for(char ch : text.toCharArray()) {
            if (Character.isLetter(ch)) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                ch = (char) ((ch-base + key)% 26 + base);

            }
            result += ch;
        }
        return result;
    }
    
    public static String  decrypt(String text, int key) {
        return encrypt(text, 26 -(key % 26));

    }
}