package launcher.FileHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {

    File outputfile = new File("simulation.txt");
    FileWriter fileWriter;
    private Writer() throws IOException {
        try {
            if ( outputfile.createNewFile() ) {
                System.out.println("Simulation.txt file created");
                fileWriter = new FileWriter(outputfile, true);
            }
            else
                throw new IOException("File Already Exist");
        } catch (IOException error) {
            System.out.println("Cant create output file: " + error.getMessage());
            error.printStackTrace();
            System.exit(1);
        }
    }

    private static class SingletonHelper {
        private static final Writer instance;

        static {
            try {
                instance = new Writer();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Writer getInstance() {
        return SingletonHelper.instance;
    }

    public void write(String message) {
        try {
            fileWriter.write(message);
            fileWriter.write("\n");
            fileWriter.flush();
        } catch (IOException error){
            System.out.println("Cant Write the File! " + error.getMessage());
            error.printStackTrace();
            System.exit(1);
        }
    }
}
