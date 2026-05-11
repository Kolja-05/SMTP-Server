# SMTP Server

An implementation of an SMTP server in Java.

SMTP is an **application-layer protocol** used to transfer email messages between clients and servers.

---

## SMTP Session Flow

### 1. Client connects to the SMTP server (port 25) and performs the handshake

```
Client → Server: HELO client.domain
Server → Client: 250 server.domain Service ready
```

---

### 2. Client sends the sender address

```
Client → Server: MAIL FROM:<sender@example.com>
Server → Client: 250 OK
```

---

### 3. Client sends the recipient address

```
Client → Server: RCPT TO:<recipient@example.com>
Server → Client: 250 OK
```

---

### 4. Client sends the email content

```
Client → Server: DATA
Server → Client: 354 End data with <CRLF>.<CRLF>
```

Client sends headers and body, terminated by `<CRLF>.<CRLF>`:

```
From: "Sender" <sender@example.com>
To: "Recipient" <recipient@example.com>
Subject: Example content

This is the message body.
.
```

```
Server → Client: 250 OK
```

---

### 5. Client ends the session

```
Client → Server: QUIT
Server → Client: 221 Bye
```

---
