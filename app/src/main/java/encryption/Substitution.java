package encryption;

import java.io.IOException;
import java.util.*;

public class Substitution {
  private FileWriter encFile;
  private FileWriter decFile;
  private final String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

  public Substitution () {
    encFile = new FileWriter("message_enc.txt");
    decFile = new FileWriter("message_dec.txt");
  }

  /**
   * Handle the overall encryption.
   *
   * @param plainText - The text to encrypt.
   * @param key
   * @return A success message.
   * @throws IOException
   */
  public String encrypt (String plainText, String key) throws IOException {
    // Generate the substitution alphabet based on the key.
    String substitutionAlphabet = generateSubstitutionAlphabet(key);
  
    String cipherText = substituteChars(plainText, substitutionAlphabet);

    encFile.write(cipherText);

    return "The file has been encrypted and the result have been saved in the file message_enc.txt in the textFiles folder";
  }

 /**
  * Generate a substitution alphabet based on the key.
  *
  * @param key - The key for the cipher.
  * @return The generated substitution alphabet.
  */
  private String generateSubstitutionAlphabet (String key) {
      Set<Character> seen = new HashSet<>();
      StringBuilder substitutionAlphabet = new StringBuilder();
      
      // Add letters from the key to the substitution alphabet
      for (char c : key.toCharArray()) {
          if (Character.isLetter(c) && !seen.contains(c)) {
              substitutionAlphabet.append(c);
              seen.add(c);
          }
      }
      
      // Add the remaining letters of the alphabet
      for (char c : this.alphabet.toCharArray()) {
          if (!seen.contains(c)) {
              substitutionAlphabet.append(c);
              seen.add(c);
          }
      }
      
      return substitutionAlphabet.toString();
  }

  /**
   * Substitute the characters in the text with a substitution.
   *
   * @param plainText
   * @param substitutionAlphabet - The alphabet to base the encryption off.
   * @return - cipher text.
   */
  private String substituteChars (String plainText, String substitutionAlphabet) {
    StringBuilder result = new StringBuilder();

    // Iterate through all the characters of the plainText and substitute them from the substitution alphabet.
    for (char c : plainText.toCharArray()) {
      boolean isUpperCase = Character.isUpperCase(c);
      char lowerC = Character.toLowerCase(c);

      int index = alphabet.indexOf(lowerC);
      if (index != -1) {
        char newChar = substitutionAlphabet.charAt(index);
        result.append(isUpperCase ? Character.toUpperCase(newChar) : newChar);
      } else {
        // If not a letter, keep it unchanged
        result.append(c);
      }
    }

    return result.toString();
  }

  /**
   * Handle decrypting a message based on a key.
   *
   * @param cipherText
   * @param key
   * @return - A successmessage.
   * @throws IOException
   */
  public String decrypt (String cipherText, String key) throws IOException  {
    // Generate the substitution alphabet based on the key.
    String substitutionAlphabet = generateSubstitutionAlphabet(key);

    // Generate the inverse alphabet based on the substitution alphabet.
    String inverseAlphabet = generateInverseAlphabet(substitutionAlphabet);

    // Substitute the characters in the cipherText using the inverse substitution alphabet.
    String plainText = substituteChars(cipherText, inverseAlphabet);

    decFile.write(plainText);

    return "If the key was correct, the file has been decrypted and the result has been saved in the file message_dec.txt in the textFiles folder";
  }

  private String generateInverseAlphabet(String substitutionAlphabet) {
    StringBuilder inverseAlphabet = new StringBuilder();

    // For each character in the substitution alphabet, find the corresponding character in the original alphabet.
    for (char c : this.alphabet.toCharArray()) {
        // Find the index of the character in the substitution alphabet
        int index = substitutionAlphabet.indexOf(String.valueOf(c));
        
        // Append the character from the original alphabet corresponding to this position in the substitution alphabet
        if (index != -1) {
            inverseAlphabet.append(this.alphabet.charAt(index));
        }
    }

    return inverseAlphabet.toString();
  }
}
