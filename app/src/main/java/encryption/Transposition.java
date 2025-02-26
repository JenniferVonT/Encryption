package encryption;

import java.io.IOException;
import java.util.Arrays;

public class Transposition {
  private FileWriter encFile;
  private FileWriter decFile;

  /**
   * Constructor that instantiates the class and establishes the default message files.
   */
  public Transposition () {
    encFile = new FileWriter("message_enc.txt");
    decFile = new FileWriter("message_dec.txt");
  }

  /**
   * The main encryption method.
   *
   * @param plainText - The text to encrypt.
   * @param key - Key that affects the encryption.
   * @return - A confirmation message.
   * @throws IOException
   */
  public String encrypt (String plainText, String key) throws IOException  {
    // Create a multidimensional array with the key deciding the column amounts
    // and the rows are decided based on the plain text length.
    char[][] table = buildArray(key, plainText, true);

    // Create the ciphertext based on the table and key.
    String cipherText = getTextFromArray(table, key, true);

    // Update the encrypted txt file.
    encFile.write(cipherText);

    return "The file has been encrypted and the results have been saved in the file message_enc.txt in the textFiles folder";
  }

  /**
   * The main decryption method.
   *
   * @param cipherText - The text to decrypt.
   * @param key - Key that affects the encryption.
   * @return - A confirmation message.
   * @throws IOException
   */
  public String decrypt (String cipherText, String key) throws IOException  {
    // Create a multidimensional array again, but this time for decryption instead.
    char[][] table = buildArray(key, cipherText, false);
 
    // Read the table to get the plain text from the ciphertext.
    String plainText = getTextFromArray(table, key, false);

    // Update the decrypted txt file.
    decFile.write(plainText);

    return "If the key was correct, the file has been decrypted and the result has been saved in the file message_dec.txt in the textFiles folder";   
  }

  /**
   * Build a multidimensional array filled with each character of a text.
   * Method of filling is based on encryption or decryption.
   *
   * @param key - The key to use for the columns.
   * @param text - The characters that will fill the array.
   * @return Multidimensional array.
   */
  private char[][] buildArray (String key, String text, Boolean isEncrypting) {
    int numCols = key.length();
    int numRows = (int) Math.ceil((double) text.length() / numCols);

    char[][] table = new char[numRows][numCols];

    // Begin at the start of the text.
    int textCharPosition = 0;

    // Fill the table row by row.
    if (isEncrypting) {
      for (int i = 0; i < numRows; i++) {
        for (int j = 0; j < numCols; j++) {
          // Fill each column in the row with a character from the plain text.
          if (textCharPosition < text.length()) {
            table[i][j] = text.charAt(textCharPosition++);
          } else { // If the text is over but there is still positions left fill them with blanks.
            table[i][j] = ' ';
          }
        }
      }
    } else {
      Integer[] order = getOrder(key);

      // Do the same as the previous loops but fill the row based on the column order instead.
      for (int i = 0; i < numCols; i++) {
        int index = getIndex(order, i);

        for (int j = 0; j < numRows; j++) {
          // Fill each position with a character from the cipher text.
          if (textCharPosition < text.length()) {
            table[j][index] = text.charAt(textCharPosition++);
          } else { // If the text is over but there is still positions left fill them with blanks.
            table[j][index] = ' ';
          }
        }
      }
    }
    return table;
  }

  /**
   * Get the index of a specific value in an array.
   *
   * @param array - The array to check.
   * @param value - The value to look for.
   * @return An index position, -1 if value is not found.
   */
  private int getIndex (Integer[] array, int value) {
    // Loop through the array and when a match is found return the index.
    for (int i = 0; i < array.length; i++) {
      if (array[i].equals(value)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Gets the cipher or plain text depending on if it is encrypting or decrypting
   * from a multidimensional array.
   *
   * @param array - Array to read from.
   * @param key - Decides the order to read.
   * @param isEncrypted - true = encryption || false = decryption.
   * @return The plain/cipher text.
   */
  private String getTextFromArray (char[][] array, String key, Boolean isEncrypting) {
    int numRows = array.length;
    int numCols = key.length();
    StringBuilder text = new StringBuilder();

    Integer[] order = getOrder(key);

    // Loop through the array and save the characters column wise based on the order.
    if (isEncrypting) {
      // Start with the first column and work through them.
      for (int i = 0; i < numCols; i++) {
        int colIndex = order[i];

        // Then work through each row in the column "reading" it up to down.
        for (int row = 0; row < numRows; row++) {
          text.append(array[row][colIndex]);
        }
      }
    } else {
      // If it is decrypting read the table row by row instead,
      // Start with the first row.
      for (int row = 0; row < numRows; row++) {
        // And then "read" each row from left to right.
        for (int col = 0; col < numCols; col++) {
          text.append(array[row][col]);
        }
      }
    }
    // Remove any trailing blank spaces and return it as a string.
    return text.toString().trim();
  }

  /**
   * Get the order to read based on the key, highest value = smallest number.
   *
   * @param key
   * @return - An array of indexes deciding the order.
   */
  private Integer[] getOrder (String key) {
    int places = key.length();

    // Parse the key into numbers and store in an array.
    Integer[] keyNum = new Integer[places];
    for (int i = 0; i < places; i++) {
      keyNum[i] = Character.getNumericValue(key.charAt(i));
    }

    // Create an array of indexes.
    Integer[] order = new Integer[places];

    // Initialize the order array with indexes (0, 1, 2, ...)
    for (int i = 0; i < places; i++) {
      order[i] = i;
    }

    // Sorting the indexes based on the values in keyNum in descending order
    // OBS!! switching to ascending does not work for some reason!
    for (int i = 0; i < places - 1; i++) {
      for (int j = i + 1; j < places; j++) {
        if (keyNum[order[i]] < keyNum[order[j]]) {
          // Swap the indexes in the order array to reflect the correct sorted order
          int temp = order[i];
          order[i] = order[j];
          order[j] = temp;
        }
      }
    }

    return order;
  }
}
