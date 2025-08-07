import java.util.*;

public class PlayfairCipher {

    private static char[][] keyMatrix = new char[5][5];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Playfair Cipher ===");
        System.out.print("Enter keyword: ");
        String keyword = scanner.nextLine();

        System.out.print("Enter plaintext: ");
        String plaintext = scanner.nextLine();

        generateKeyMatrix(keyword);
        displayKeyMatrix();

        String prepared = prepareText(plaintext);
        System.out.println("\nPrepared Text: " + prepared);

        String encrypted = encrypt(prepared);
        System.out.println("Encrypted Text: " + encrypted);

        String decrypted = decrypt(encrypted);
        String cleaned = cleanDecryptedText(decrypted);
        System.out.println("Decrypted Text: " + cleaned);
    }

    static void generateKeyMatrix(String keyword) {
        keyword = keyword.toUpperCase().replace("J", "I");
        Set<Character> seen = new LinkedHashSet<>();

        for (char ch : keyword.toCharArray()) {
            if (Character.isLetter(ch)) {
                seen.add(ch);
            }
        }

        for (char ch = 'A'; ch <= 'Z'; ch++) {
            if (ch != 'J') {
                seen.add(ch);
            }
        }

        Iterator<Character> iterator = seen.iterator();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                keyMatrix[i][j] = iterator.next();
            }
        }
    }

    static void displayKeyMatrix() {
        System.out.println("\nKey Matrix:");
        for (char[] row : keyMatrix) {
            for (char ch : row) {
                System.out.print(ch + " ");
            }
            System.out.println();
        }
    }

    static String prepareText(String text) {
        text = text.toUpperCase().replace("J", "I").replaceAll("[^A-Z]", "");
        StringBuilder prepared = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char a = text.charAt(i);
            char b = (i + 1 < text.length()) ? text.charAt(i + 1) : 'X';

            if (a == b) {
                prepared.append(a).append('X');
            } else {
                prepared.append(a).append(b);
                i++;
            }
        }

        if (prepared.length() % 2 != 0) {
            prepared.append('X');
        }

        return prepared.toString();
    }

    static int[] findPosition(char ch) {
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                if (keyMatrix[i][j] == ch)
                    return new int[]{i, j};
        return null;
    }

    static String encrypt(String text) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i += 2) {
            char a = text.charAt(i);
            char b = text.charAt(i + 1);
            int[] pos1 = findPosition(a);
            int[] pos2 = findPosition(b);

            if (pos1[0] == pos2[0]) { // Same row
                result.append(keyMatrix[pos1[0]][(pos1[1] + 1) % 5]);
                result.append(keyMatrix[pos2[0]][(pos2[1] + 1) % 5]);
            } else if (pos1[1] == pos2[1]) { // Same column
                result.append(keyMatrix[(pos1[0] + 1) % 5][pos1[1]]);
                result.append(keyMatrix[(pos2[0] + 1) % 5][pos2[1]]);
            } else { // Rectangle
                result.append(keyMatrix[pos1[0]][pos2[1]]);
                result.append(keyMatrix[pos2[0]][pos1[1]]);
            }
        }

        return result.toString();
    }

    static String decrypt(String text) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i += 2) {
            char a = text.charAt(i);
            char b = text.charAt(i + 1);
            int[] pos1 = findPosition(a);
            int[] pos2 = findPosition(b);

            if (pos1[0] == pos2[0]) { // Same row
                result.append(keyMatrix[pos1[0]][(pos1[1] + 4) % 5]);
                result.append(keyMatrix[pos2[0]][(pos2[1] + 4) % 5]);
            } else if (pos1[1] == pos2[1]) { // Same column
                result.append(keyMatrix[(pos1[0] + 4) % 5][pos1[1]]);
                result.append(keyMatrix[(pos2[0] + 4) % 5][pos2[1]]);
            } else { // Rectangle
                result.append(keyMatrix[pos1[0]][pos2[1]]);
                result.append(keyMatrix[pos2[0]][pos1[1]]);
            }
        }

        return result.toString();
    }

    static String cleanDecryptedText(String text) {
        StringBuilder cleaned = new StringBuilder();
        int i = 0;
        while (i < text.length()) {
            cleaned.append(text.charAt(i));
            if (i + 2 < text.length() && text.charAt(i + 1) == 'X' && text.charAt(i) == text.charAt(i + 2)) {
                i += 2;
            } else {
                i++;
            }
        }
        return cleaned.toString();
    }
}