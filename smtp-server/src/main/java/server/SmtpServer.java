package server;

import java.io.IOException;
import java.nio.channels.*;
import javax.imageio.IIOException;
import storage.MailStorage;


// Boundry to Network in ECB-Pattern
public class SmtpServer {
    private static final int PORT = 25;
    private Selector selector;
    private ServerSocketChannel serverChannel;
    private final SmtpCommandHandler commandHandler;
    

    public SmtpServer() {
        this.commandHandler = new SmtpCommandHandler(new MailStorage());
    }
    private void start() throws IOException {

    }

    private void eventloop() throws IOException {
        while(true) {

        }
    }

    private void acceptConnection(SelectionKey key) throws IOException {

    }

    private void readFromClient(SelectionKey key) throws IIOException {

    }
    public static void main(String[] args) {
        try {
            new SmtpServer().start();
        }
        catch(IOException e) {

        }
    }

}
