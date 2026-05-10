package protocol;

import java.nio.charset.Charset;

public enum SmtpResponse {
    READY(220, "Service ready"),
    BYE(221, "Bye"),
    OK(250, "OK"),
    START(354, "Start mail input"),
    HELP(211, "Allowed commands: HELO, MAIL, FROM, RCPT TO, DATA, HELP, QUIT"),
    UNKNOWN(500, "Unknown command"),
    BAD_SEQUENCE(503, "Bad sequence"),
    FAILED(550, "Action Failed");

    private final int code;
    private final String message;

    SmtpResponse(int code, String message) {
		this.code = code;
        this.message = message;
    }


    @Override
    public String toString() {
        return code + " " + message;
    }
    public int getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}
