# SMTP-Server
An implementation of an SMTP-Server in Java.

SMTP is a applicationlayerprotocol to transfer emails.
### 1. Client Connects to Port 25 on SMTP-Server and performs Handshake
Client: HELO client.domaint \n
Server: 250 server.domain, service ready
### 2. Client sends sender address
Client: MAIL FROM:<sender@example.com>
Server: 250 OK
### 3. Client sends recipients address
Client: RCPT TO:<recipient@example.com>
Server: 250 OK
### 4. Client sends emailcontent
Client: DATA
Server: 354 End data with -CLRF-.-CLRF-
Client: From: "sender" <sender@example.com>
        To: "recipient" <recipient@example.com>
        Subject: Example content
        .
Server: 250 OK
### 5. Client ends session
Client: QUIT
Server: 221 Bye
