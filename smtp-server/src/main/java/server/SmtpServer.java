package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;
import storage.MailStorage;


// Boundry to Network in ECB-Pattern
public class SmtpServer {
    private static final int PORT = 2525;
    private Selector selector;
    private final SmtpCommandHandler commandHandler;
    private ServerSocketChannel servSock;



    public SmtpServer() {
        this.commandHandler = new SmtpCommandHandler(new MailStorage());
    }

    // initializes the server
    private void start() throws IOException {
        System.out.println("Starte Server \n");
        this.selector = Selector.open(); // Create Selector
        this.servSock = ServerSocketChannel.open(); // Create Socket
        servSock.configureBlocking(false); // set non blocking
        servSock.socket().bind(new InetSocketAddress(PORT)); // bind socket to port
        servSock.register(selector, SelectionKey.OP_ACCEPT); // register all incoming connections


        eventloop(); // start event loop


    }

    private void eventloop() throws IOException {
        while(true) {
            if(selector.select() == 0) /* blocking */
                continue;
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectedKeys.iterator();
            while(iter.hasNext()) { // iterate over all keys
                SelectionKey key = iter.next();
                if(key.isValid() && key.isAcceptable()) {
                    acceptConnection(key);
                }
                if(key.isValid() && key.isReadable()) {
                    readFromClient(key); // read data
                }
                if(key.isValid() && key.isWritable())
                {
                    // TODO Send Response with data from Commandhandler here
                    // sendResponse here
                    key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE); // diasbale writing

                }
                iter.remove();
            }

        // TODO implement sendResponse() in SmtpCommandHandler class

        }
    }

    private void acceptConnection(SelectionKey key) throws IOException {
        ServerSocketChannel sock = (ServerSocketChannel) key.channel(); // create socketchannel
        SocketChannel client = sock.accept(); // accept channel
        client.configureBlocking(false);


        SelectionKey clientKey = client.register(selector, SelectionKey.OP_READ); // register the channel if its ready to bee read

        // Create and attach session
        ClientSession session = new ClientSession(client);
        clientKey.attach(session);;


        // Test response not clean do over send function at isWriteable in eventloop
        // String greeting = "220 localhost SMTP server ready\r\n";
        // ByteBuffer buffer = ByteBuffer.wrap(greeting.getBytes());
        // client.write(buffer);
        commandHandler.sendGreetings(session);

    }

    private void readFromClient(SelectionKey key) throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(1024);
        SocketChannel channel = (SocketChannel) key.channel();
        ClientSession session = (ClientSession) key.attachment(); // get attached session

        int bytesRead = channel.read(buf); // read buffer
        if (bytesRead == -1) { //when client disconnects it is handled properly
            key.cancel(); 
            channel.close();
            return;
        }
        buf.flip();

        String msg = new String(buf.array(), 0, buf.limit(), StandardCharsets.US_ASCII); // convert to string
        System.out.println(msg);

        for (String line : msg.split("\r\n")) {
            if (!line.isEmpty()) {
                commandHandler.processLine(line, session); // give line and session to Cammand Handler
            }
        }
    }
    // IF response should be send set channel to writeable
    public void setWriteable(ClientSession session) throws IOException {
        SocketChannel channel = session.getChannel();
        SelectionKey key = channel.keyFor(selector);
        key.interestOps(key.interestOps() | SelectionKey.OP_WRITE); // set channel to writeable

    }

    public static void main(String[] args) {
        //TODO
        try {
            SmtpServer server = new SmtpServer();
            server.start();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

}
