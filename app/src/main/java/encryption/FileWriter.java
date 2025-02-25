package encryption;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Collections;

public class FileWriter {
  private File file;

  /**
   * Constructor that instantiates the class.
   *
   * @param filename
   */
  public FileWriter (String filename) {
    // Define the path to the file dynamically.
    // Define the path to the file dynamically.
    Path textFilesDir = Paths.get(System.getProperty("user.dir"), "src", "main", "textFiles");
    Path absolutePath = textFilesDir.resolve(filename).toAbsolutePath().normalize();

    this.file = absolutePath.toFile();

    // Ensure the directory exists
    if (!textFilesDir.toFile().exists()) {
        textFilesDir.toFile().mkdirs();
    }
  }

  /**
   * Overwrite content to the file connected to this writer.
   *
   * @param content
   * @throws IOException
   */
  public void write (String content) throws IOException {
    Files.write(file.toPath(), Collections.singletonList(content),
                StandardOpenOption.CREATE,          // Create the file if it doesn't exist
                StandardOpenOption.TRUNCATE_EXISTING); // Overwrite if the file exists
  }
}
