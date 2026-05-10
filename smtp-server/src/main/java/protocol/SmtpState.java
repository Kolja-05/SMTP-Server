package protocol;

public enum SmtpState {
    CONNECTED,
    GREETED,
    MAIL_FROM,
    RCPT_TO,
    DATA,
    QUIT,
}
