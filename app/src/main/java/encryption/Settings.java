package encryption;

import java.util.ArrayList;

public class Settings {
  private boolean encrypt = false;
  private boolean decrypt = false;
  private String method;
  private String secretKey;

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

  public void checkKeyForSubstitutionMethod (String key) {
    if (!key.matches("[a-zA-Z]+")) {
      throw new IllegalArgumentException("Substitution key must only contain letters. ");
    }
    if (key.length() < 1 || key.length() > 256) {
      throw new IllegalArgumentException("Key must be between 1-256 character long. ");
    }
  }

  public void checkKeyForTranspositionMethod (String key) {
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
}
