package encryption;

import java.util.ArrayList;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Settings {
  private boolean encrypt = false;
  private boolean decrypt = false;
  private String method;
  private String secretKey;
  private File file;

  public Settings() {}

  public void setEncrypt (boolean state) {
    this.encrypt = state;
  }

  public boolean getEncrypt () {
    return this.encrypt;
  }

  public void setDecrypt (boolean state) {
    this.decrypt = state;
  }

  public boolean getDecrypt () {
    return this.decrypt;
  }

  public void setMethod (String method) {
    switch (method) {
      case "T":
        this.method = "T";
        break;
      case "S":
        this.method = "S";
        break;
      default:
        throw new IllegalArgumentException("Invalid method type.");
    }
  }

  public String getMethod () {
    return this.method;
  }

  public void setSecretKey (String key) {
    // If it is the substitution method check that the length is between 1-256
    // and that it only contains letters.
    if (this.method.equals("S")) {
      checkKeyForSubstitutionMethod(key);
    }

    // If it is the transposition method check that it is numeric with a length between 2-9.
    if (this.method.equals("T")) {
      checkKeyForTranspositionMethod(key);
    }

    this.secretKey = key;
  }

  /**
   * Check if the key is valid according to the substitution method.
   *
   * @param key - The key to check.
   */
  private void checkKeyForSubstitutionMethod (String key) {
    if (key == null || key.isEmpty()) {
      throw new IllegalArgumentException("Key must not be empty.");
    }

    if (key.length() < 1 || key.length() > 256) {
      throw new IllegalArgumentException("Key must be between 1-256 8-bit characters");
    }

    for (char c : key.toCharArray()) {
        if (c > 255) {  // Check if character exceeds 8-bit range
            throw new IllegalArgumentException("Substitution key must only contain characters in the 8-bit range (0-255).");
        }
    }
  }

  /**
   * Check if the key is valid according to the transposition method.
   *
   * @param key - The key to check.
   */
  private void checkKeyForTranspositionMethod (String key) {
    int length = key.length();

    if (!key.matches("[1-9]+") || length < 2 || length > 9) {
      throw new IllegalArgumentException("Transposition key must only contain unique numbers between 1-9 and be between 2-9 numbers long.");
    }

    ArrayList<Integer> usedNumbers = new ArrayList<>();

    for (int i = 0; i < length; i++) {
      int integer = Character.getNumericValue(key.charAt(i));

      if (usedNumbers.contains(integer)) {
        throw new IllegalArgumentException("Transposition key must only contain unique numbers between 1-9 and be between 2-9 numbers long.");
      }

      usedNumbers.add(integer);
    }
  }

  public String getSecretKey () {
    return this.secretKey;
  }

  /**
   * Check file type and set if it exists in the correct folder.
   */
  public void setFile (String fileName) {
    // Check that it is a .txt file.
    if (!fileName.endsWith(".txt")) {
      throw new IllegalArgumentException("Must be a .txt file");
    }

    // Define the path to the file dynamically.
    Path textFilesDir = Paths.get(System.getProperty("user.dir"), "src", "main", "textFiles");
    Path absolutePath = textFilesDir.resolve(fileName).toAbsolutePath().normalize();

    File file = absolutePath.toFile();

    // And check if the file even exists.
    if (!file.exists()) {
      throw new IllegalArgumentException("File does not exist in the textFiles folder, pleaser insert it and try again.");
    }

    this.file = file;
  }

  public File getFile () {
    return this.file;
  }
}
