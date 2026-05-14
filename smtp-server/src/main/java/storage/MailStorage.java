package storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Random;

import server.ClientSession;

// added: Java NIO for FileSystem channel
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;


// Boundry to Filesystem in ECB-Pattern
public class MailStorage {

    // email storage path: mails/<receiver>/<sender>_<message_id>
    private static final String MAIL_BASE_DIR = "mails";

    // 0-9999 random message_id as required
    private final Random random = new Random();


    public void saveMail(ClientSession session) throws IOException {
        //TODO
        // get sender, receiver and content from session
        String sender = session.getSender();
        String recipient = session.getRecipient();
        String content = session.getData();

        // check if all information exists correctly
        if (sender==null || recipient==null || content==null) {
            throw new IOException("Sender, recipient or content missing!");
        }

        // create Path, i.e. mails/<recipient>
        Path recipientDir = createRecipientDir(recipient);

        // generate messageId
        int messageId = generateMessage();
        // file name n <sender>_<message_id>
        String fileName = sender + "_" + messageId;
        // create Path
        Path mailPath = recipientDir.resolve(fileName);

        // if name already exists, generate another message_Id
        while(Files.exists(mailPath)) {
            messageId = generateMessage();
            fileName = sender + "_" + messageId;
            mailPath = recipientDir.resolve(fileName);
        }

        // write content into File
        writeFile(mailPath, content);

    }

    private Path createRecipientDir(String recipient) throws IOException {
        //return null;
        //TODO
        // use Path.of to concatenate path: mails/<recipient>
        Path recipientDir = Path.of(MAIL_BASE_DIR, recipient);
        // create a directory under the path; if already exists do nothing
        Files.createDirectories(recipientDir);

        return recipientDir;
    }

    private int generateMessage() {
        //return 0;
        //TODO
        return random.nextInt(10000);
    }

    private void writeFile(Path path, String content) throws IOException {
        //TODO
        // ATTENTION: use Java NIO Channel, i.e. filesystem channel!

        // US-ASCII, encode String into Bytebuffer
        ByteBuffer buffer = StandardCharsets.US_ASCII.encode(content);

        // use FileChannel.open to create a file channel
        // use try for automatic channel.close() 
        // use FileChannel.open: apply to OS for a file channel
        try (
            FileChannel channel = FileChannel.open(
                path,
                StandardOpenOption.CREATE_NEW, // create new file
                StandardOpenOption.WRITE       // allow write operation
            )
        ) {
            // if there is data left, continue writing FROM buffer
            while (buffer.hasRemaining()) {
                channel.write(buffer);
            }

        }
    }
}
