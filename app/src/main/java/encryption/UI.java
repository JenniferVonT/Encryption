package encryption;

import java.util.Scanner;

public class UI {
  private Scanner input;
  private Settings settings;

  protected UI(Scanner input, Settings settings) {
    this.input = input;
    this.settings = settings;
  }

  /**
   * Get input from user if it should decrypt or encrypt.
   */
  public void selectEncryptionOrDecryption () {
    System.out.println("Do you want to encrypt (E) or decrypt (D)? ");
    String alt = this.input.nextLine().toUpperCase();

    switch (alt) {
      case "E":
        settings.setEncrypt(true);
        settings.setDecrypt(false);
        break;
      case "D":
        settings.setEncrypt(false);
        settings.setDecrypt(true);
        break;
      default:
        clearConsole();
        System.out.println("Invalid input!");
        selectEncryptionOrDecryption();
    }
  }

  public void selectMethod () {
    try {
      System.out.println("Do you want to use substitution (S) or transposition (T)?");
      String alt = this.input.nextLine().toUpperCase();

      settings.setMethod(alt);
    } catch (IllegalArgumentException error) {
      clearConsole();
      System.out.println(error.getMessage());
      selectMethod();
    }
  }

  public void selectSecretKey () {
    try {
      System.out.println("Input the secret key: ");
      String key = this.input.nextLine();

      settings.setSecretKey(key);
    } catch (IllegalArgumentException error) {
      clearConsole();
      System.out.println(error.getMessage());
      selectSecretKey();
    }
  }

  public void selectFile () {
    try {
      System.out.println("Input the name of the file to be processed: ");
      String file = this.input.nextLine();

      settings.setFile(file);
    } catch (IllegalArgumentException error) {
      clearConsole();
      System.out.println(error.getMessage());
      selectFile();
    }
  }

  /**
   * Clear the console visually by printing clear lines.
   */
  public void clearConsole () {
    for (int i = 0; i < 30; i++) {
      System.out.println();
    }
  }  
}
