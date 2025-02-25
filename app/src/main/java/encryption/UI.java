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
   * Get input from user if it should decrypt or encrypt and set it in settings.
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

  /**
   * Get user input to select a method to use and set it in settings.
   */
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

  /**
   * Get user input to select a secret key and set it in settings.
   */
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

  /**
   * Get user input to select what file to process and set it in settings.
   */
  public void selectFile () {
    try {
      System.out.println("Input the name of the file you want to process (should be located in the textFiles folder): ");
      String file = this.input.nextLine();

      settings.setFile(file);
    } catch (IllegalArgumentException error) {
      clearConsole();
      System.out.println(error.getMessage());
      selectFile();
    }
  }

  /**
   * Ask user if they want to exit the application or repeat the process.
   *
   * @return - Yes (true) || No (false).
   */
  public boolean endSession () {
    System.out.println("\n\n");
    System.out.println("Would you want to exit (X) or start again (R)");
    String alt = this.input.nextLine().toUpperCase();

    switch (alt) {
      case "X":
        clearConsole();
        return true;
      case "R":
       clearConsole();
        return false;
      default:
        clearConsole();
        System.out.println("Invalid input.");
        return endSession();
    }
  }

  /**
   * Show a generic message.
   *
   * @param message
   */
  public void showMessage (String message) {
    System.out.println("\n\n" + message);
  }

  /**
   * Show a generic error message.
   *
   * @param message
   */
  public void showError (String message) {
    clearConsole();
    System.out.println("\u001B[31m" + "\nERROR: " + message + "\u001B[0m");
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
