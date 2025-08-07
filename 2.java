import java.util.Scanner;

public class RailFenceCipher {

    public static String encrypt(String text, int rails) {
        text = text.toUpperCase();
        int len = text.length();

        char[][] rail = new char[rails][len];

        for (int i = 0; i < rails; i++)
            for (int j = 0; j < len; j++)
                rail[i][j] = '\n';

        boolean down = false;
        int row = 0, col = 0;

        for (char c : text.toCharArray()) {
            rail[row][col++] = c;

            if (row == 0 || row == rails - 1)
                down = !down;

            row += down ? 1 : -1;
        }

        System.out.println();
        printRailMatrix(rail, rails, len);

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < rails; i++)
            for (int j = 0; j < len; j++)
                if (rail[i][j] != '\n')
                    result.append(rail[i][j]);

        return result.toString();
    }

    public static String decrypt(String cipher, int rails) {
        int len = cipher.length();

        char[][] rail = new char[rails][len];

        boolean down = false;
        int row = 0, col = 0;

        for (int i = 0; i < len; i++) {
            rail[row][col++] = '*';

            if (row == 0 || row == rails - 1)
                down = !down;

            row += down ? 1 : -1;
        }

        int index = 0;
        for (int i = 0; i < rails; i++) {
            for (int j = 0; j < len; j++) {
                if (rail[i][j] == '*' && index < len) {
                    rail[i][j] = cipher.charAt(index++);
                }
            }
        }

        System.out.println();
        printRailMatrix(rail, rails, len);

        StringBuilder result = new StringBuilder();
        down = false;
        row = 0;
        col = 0;

        for (int i = 0; i < len; i++) {
            result.append(rail[row][col++]);

            if (row == 0 || row == rails - 1)
                down = !down;

            row += down ? 1 : -1;
        }

        return result.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Text: ");
        String message = sc.nextLine();

        int rails;
        while (true) {
            System.out.print("Enter Rails: ");
            rails = sc.nextInt();
            if (rails >= 2) break;
            System.out.println("Minimum 2 rails needed!");
        }

        String encryptedText = encrypt(message, rails);
        System.out.println("\nEncode: " + encryptedText);

        String decryptedText = decrypt(encryptedText, rails);
        System.out.println("\nDecode: " + decryptedText);
    }

    public static void printRailMatrix(char[][] rail, int rows, int cols) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++)
                System.out.print(rail[i][j] == '\n' ? ' ' : rail[i][j]);
            System.out.println();
        }
    }
}
