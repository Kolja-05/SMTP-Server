package server;

import java.io.IOException;
import java.nio.channels.SelectionKey;

import protocol.SmtpResponse;
import protocol.SmtpState;
import storage.MailStorage;

public class SmtpCommandHandler {

    private final MailStorage mailStorage;

    public SmtpCommandHandler(MailStorage mailStorage) {
        this.mailStorage = mailStorage;
    }


    private void handleCommand(String line, ClientSession session) throws IOException {
        String command = line.trim().toUpperCase();
        if (command.startsWith("HELO")){

        }
        //...
    }

    private void handleData(String line, ClientSession session) throws IOException {

    }

    private void handleHelo(String line, ClientSession session) throws IOException {
        if (session.getState() != protocol.SmtpState.CONNECTED) {
            sendResponse(session, SmtpResponse.BAD_SEQUENCE);
            return;
        }
        sendResponse(session, SmtpResponse.OK);
        session.setState(SmtpState.GREETED);
        return;

    }

    private void handleRecptTo(String line, ClientSession session) throws IOException {
        if (session.getState() != protocol.SmtpState.MAIL_FROM) {
            sendResponse(session, SmtpResponse.BAD_SEQUENCE);
            return;
        }
        String address = extractAddress(line);
        if (address == null || address.isBlank()) {
            sendResponse(session, SmtpResponse.UNKNOWN);
        }
        session.setRecipient(address);
        session.setState(SmtpState.RCPT_TO);
        return;
    }

    private void handleMailFrom(String line, ClientSession session) throws IOException {
        if (session.getState() != SmtpState.GREETED) {
            sendResponse(session, SmtpResponse.BAD_SEQUENCE);
            return;
        }
        String address = extractAddress(line);
        if (address == null || address.isBlank()) {
            sendResponse(session, SmtpResponse.UNKNOWN);
            return;
        }
        session.setSender( address);
        session.setState(SmtpState.MAIL_FROM);
        sendResponse(session, SmtpResponse.OK);
        // TODO reset
        return;
    }

    private void handleDataCommand(ClientSession session) throws IOException {
        if (session.getState() != SmtpState.RCPT_TO) {
            sendResponse(session, SmtpResponse.UNKNOWN);
        }
        session.setState(SmtpState.DATA);
        return;
    }

    private void handleHelp(ClientSession session) throws IOException {
        sendResponse(session, SmtpResponse.HELP);
    }

    private void handleQuit(ClientSession session) throws IOException {
        sendResponse(session, SmtpResponse.BYE);
    }

    private String extractAddress(String line) {
        return null;
    }

    private void sendResponse(ClientSession session, SmtpResponse response) throws IOException {

    }
}
