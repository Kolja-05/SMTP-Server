package storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Random;

import server.ClientSession;


// Boundry to Filesystem in ECB-Pattern
public class MailStorage {
    private static final String MAIL_BASE_DIR = "mails";

    private final Random random = new Random();


    public void saveMail(ClientSession session) throws IOException {
        //TODO
    }

    private Path createRecipientDir(String recipient) throws IOException {
        return null;
        //TODO
    }

    private int generateMessage() {
        return 0;
        //TODO
    }

    private void writeFile(Path path, String content) throws IOException {
        //TODO
    }
}
