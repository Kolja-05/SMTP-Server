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

    // initializes the server
    private void start() throws IOException {
        //TODO
        // configure sockets and selector
        // nonblocking
        // bind
        // start eventloop
    }

    private void eventloop() throws IOException {
        while(true) {
        //TODO
        // accept connenctions
        // create client session object
        // read line from client
        // call processLine (parses line, saves sender, recipient and data and sends correct response)
        // TODO implement sendResponse() in SmtpCommandHandler class
        }
    }

    private void acceptConnection(SelectionKey key) throws IOException {
        //TODO
    }

    private void readFromClient(SelectionKey key) throws IIOException {
        //TODO
    }
    public static void main(String[] args) {
        //TODO
        try {
            SmtpServer server = new SmtpServer();
            server.start();
        }
        catch(IOException e) {

        }
    }

}
