package server;

import java.nio.channels.SocketChannel;

import protocol.SmtpState;



// Entity in ECB-Pattern
public class ClientSession {
    private String sender;
    private String recipient;
    private StringBuilder body;


    private SmtpState state;
    private final SocketChannel channel;


    public ClientSession(SocketChannel channel) {
        this.channel = channel;
        this.state = protocol.SmtpState.CONNECTED;
    }

    public void reset() {
        sender = null;
        recipient = null;
        state = SmtpState.GREETED;
    }
    public void appendData(String line) {
        body.append(line).append(" \r\n");
    }

    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
 
    public SmtpState getState() {
        return state;
    }
    public void setState(SmtpState state) {
        this.state = state;
    }

    public SocketChannel getChannel() {
        return channel;
    }
}
