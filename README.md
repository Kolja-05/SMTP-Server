# SMTP Server

An implementation of an SMTP server in Java.

SMTP is an **application-layer protocol** used to transfer email messages between clients and servers.

---

## SMTP Session Flow

### 1. Client connects to the SMTP server (port 25) and performs the handshake

```
Server: 220 server.domain, Service ready
Client: HELO client.domain
Server: 2OK
```

---

### 2. Client sends the sender address

```
Client: MAIL FROM:<sender@example.com>
Server: 250 OK
```

---

### 3. Client sends the recipient address

```
Client: RCPT TO:<recipient@example.com>
Server: 250 OK
```

---

### 4. Client sends the email content

```
Client: DATA
Server: 354 End data with <CRLF>.<CRLF>
```

Client sends headers and body, terminated by `<CRLF>.<CRLF>`:

```
Client: From: "Sender" <sender@example.com>
        To: "Recipient" <recipient@example.com>
        Subject: Example Subject

        Example Body
        .
```

```
Server: 250 OK
```

---

### 5. Client ends the session

```
Client: QUIT
Server: 221 Bye
```

---
