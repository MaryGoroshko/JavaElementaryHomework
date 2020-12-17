package homework18.task04Streams;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileStreams implements Runnable {

    @Override
    public void run() {
        try (FileInputStream fileIn = new FileInputStream("C:\\Users\\User\\Pictures\\frog.png");
             FileOutputStream fileOut = new FileOutputStream("C:\\Users\\User\\Pictures\\NewFolder\\frog2.png")) {

            System.out.println("Starting copy files. Please wait.");
            int readByte;
            do {
                readByte = fileIn.read();
                if (readByte == -1) {
                    break;
                }
                fileOut.write(readByte);
            } while (true);

        } catch (FileNotFoundException e) {
            System.out.println("File not found " + e.getCause());
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
