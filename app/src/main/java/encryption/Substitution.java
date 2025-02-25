package encryption;

import java.io.IOException;
import java.util.*;

public class Substitution {
  private FileWriter encFile;
  private FileWriter decFile;

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
    String alphabet = "abcdefghijklmnopqrstuvwxyz";

    // Generate the substitution alphabet based on the key.
    String substitutionAlphabet = generateSubstitutionAlphabet(key, alphabet);
  
    String cipherText = substituteChars(plainText, substitutionAlphabet);
    encFile.write(cipherText);

    return "The file has been encrypted and the results have been saved in the file message_enc.txt (in the textFiles folder)";
  }

 /**
  * Generate a substitution alphabet based on the key.
  *
  * @param key - The key for the cipher.
  * @param alphabet - The standard alphabet.
  * @return The generated substitution alphabet.
  */
  private String generateSubstitutionAlphabet (String key, String alphabet) {
      Set<Character> seen = new HashSet<>();
      StringBuilder substitutionAlphabet = new StringBuilder();
      
      // Add letters from the key to the substitution alphabet
      for (char c : key.toCharArray()) {
          if (!seen.contains(c)) {
              substitutionAlphabet.append(c);
              seen.add(c);
          }
      }
      
      // Add the remaining letters of the alphabet
      for (char c : alphabet.toCharArray()) {
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
   * @param plainText - The plainText to encrypt.
   * @param substitutionAlphabet - The alphabet to base the encryption from.
   * @return - cipher text.
   */
  private String substituteChars (String plainText, String substitutionAlphabet) {
    String alphabet = "abcdefghijklmnopqrstuvwxyz";
    StringBuilder result = new StringBuilder();

    // Loop through each character in the text and perform substitution
    for (char c : plainText.toLowerCase().toCharArray()) {
        if (alphabet.indexOf(c) != -1) {
            int index = alphabet.indexOf(c);
            result.append(substitutionAlphabet.charAt(index));
        } else {
            // If it's not in the alphabet (e.g., space, punctuation), leave it unchanged
            result.append(c);
        }
    }

    return result.toString();
  }

  public String decrypt (String cipherText, String key)  throws IOException  {
    // TO-DO: Implement decryption.
    return "The file has been decrypted and the results have been saved in the file message_dec.txt in the textFiles folder";       
  }
}
