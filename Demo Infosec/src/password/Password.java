package password;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;

public class Password {
    private static final String ALL_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()-_=+[]{}|;:'\",.<>?";

    public static String generatePassword() {
        SecureRandom random = new SecureRandom();
        int passwordLength = random.nextInt(9) + 8; 
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < passwordLength; i++) {
            int randomIndex = random.nextInt(ALL_CHARACTERS.length());
            password.append(ALL_CHARACTERS.charAt(randomIndex));
        }

        return password.toString();
    }

    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());
        byte[] hashedPassword = md.digest();

        StringBuilder sb = new StringBuilder();
        for (byte b : hashedPassword) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    public static int calculatePasswordStrength(String password) {
        int length = password.length();
        int strength = 0;

        if (length >= 8) {
            strength += 2;
        }

        if (password.matches(".*[a-z].*") && password.matches(".*[A-Z].*")) {
            strength += 2;
        }

        if (password.matches(".*\\d.*")) {
            strength += 2;
        }

        if (password.matches(".*[!@#$%^&*()-_=+\\[\\]{}|;:'\",.<>?].*")) {
            strength += 2;
        }

        return strength;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Generate a random password");
            System.out.println("2. Hash a password");
            System.out.println("3. Generate and hash a random password");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                System.out.println("Exiting.");
                break;
            }

            if (choice == 1 || choice == 3) {
                String password = generatePassword();
                int strength = calculatePasswordStrength(password);
                System.out.println("Generated password: " + password);
                System.out.println("Password Strength: " + strength + " out of 8");

                if (choice == 3) {
                    String hashedPassword = hashPassword(password);
                    System.out.println("Hashed Password: " + hashedPassword);
                }
            }

            if (choice == 2) {
                System.out.print("Enter the password to hash: ");
                String passwordToHash = scanner.nextLine();
                String hashedPassword = hashPassword(passwordToHash);
                int strength = calculatePasswordStrength(passwordToHash);
                System.out.println("Hashed Password: " + hashedPassword);
                System.out.println("Password Strength: " + strength + " out of 8");
            }
        }

        scanner.close();
    }
}
