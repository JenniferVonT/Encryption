package encryption;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Transposition {
  private FileWriter encFile;
  private FileWriter decFile;

  public Transposition () {
    encFile = new FileWriter("message_enc.txt");
    decFile = new FileWriter("message_dec.txt");
  }
 
  public String encrypt (String plainText, String key) throws IOException  {
    // TO-DO: Implement encryption.
    return "The file has been encrypted and the results have been saved in the file message_enc.txt in the textFiles folder";
  }

  public String decrypt (String cipherText, String key) throws IOException  {
    // TO-DO: Implement decryption.
    return "The file has been decrypted and the results have been saved in the file message_dec.txt in the textFiles folder";       
  }
}
