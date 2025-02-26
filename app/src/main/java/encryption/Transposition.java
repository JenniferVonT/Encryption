package encryption;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

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
 
  public String encrypt (String plainText, String key) throws IOException  {
    // Create a multidimensional array with the key deciding the column placess (places of characters = number of columns)
    // and the rows are decided based on the plain text length.
    int numCols = key.length();
    int numRows = (int) Math.ceil((double) plainText.length() / numCols);

    char[][] table = buildArray(numRows, numCols, plainText);

    // Create the ciphertext based on the table and key.
    String cipherText = getCipherTextFromArray(table, key);

    // Write the cipher to a .txt file.
    encFile.write(cipherText);

    return "The file has been encrypted and the results have been saved in the file message_enc.txt in the textFiles folder";
  }

  /**
   * Build a multidimensional array filled with each character of a text.
   *
   * @param numRows - Number of rows
   * @param numCols - Number of columns
   * @param plainText - The characters that will fill the array.
   * @return
   */
  private char[][] buildArray (int numRows, int numCols, String plainText) {
    char[][] table = new char[numRows][numCols];

    // Fill the rows with all the plaintext characters.
    int textCharPosition = 0;

    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        // Fill each position with a character from the plain text.
        if (textCharPosition < plainText.length()) {
          table[i][j] = plainText.charAt(textCharPosition++);
        } else { // If the text is over but there is still positions left fill them with blanks.
          table[i][j] = ' ';
        }
      }
    }

    return table;
  }

  /**
   * Gathers cipher text from a multidimensional array based on a key.
   *
   * @param array - Array to read from.
   * @param key - Decides the order to read.
   * @return
   */
  private String getCipherTextFromArray (char[][] array, String key) {
    int numRows = array.length;
    StringBuilder cipherText = new StringBuilder();

    Integer[] order = getOrder(key);

    // Print the order (for debugging)
    System.out.println(Arrays.toString(order));

    // Read columns based on order
    for (int i = 0; i < order.length; i++) {  // Loop through the order array
      int colIndex = order[i];  // Get the column index from order

      for (int row = 0; row < numRows; row++) {
        cipherText.append(array[row][colIndex]);  // Read characters column-wise
      }
    }

    return cipherText.toString();
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

    // Sorting the indexes based on the values in keyNum
    for (int i = 0; i < places - 1; i++) {
      for (int j = i + 1; j < places; j++) {
        if (keyNum[order[i]] < keyNum[order[j]]) {
          // Swap the indexes in order array to reflect the correct sorted order
          int temp = order[i];
          order[i] = order[j];
          order[j] = temp;
        }
      }
    }

    return order;
  }

  public String decrypt (String cipherText, String key) throws IOException  {
    // TO-DO: Implement decryption.
    return "The file has been decrypted and the results have been saved in the file message_dec.txt in the textFiles folder";       
  }
}
