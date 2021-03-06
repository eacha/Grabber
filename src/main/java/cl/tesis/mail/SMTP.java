package cl.tesis.mail;

import cl.tesis.mail.exception.ConnectionException;

import java.io.IOException;

public class SMTP extends Mail {

    private static final int DEFAULT_PORT = 25;
    public static final String HELP = "HELP\r\n";
    public static final String EHLO = "EHLO example.cl\r\n";

    public SMTP(String host, int port) throws ConnectionException {
        super(host, port);
    }

    public SMTP(String host) throws ConnectionException {
        this(host, DEFAULT_PORT);
    }

    public String sendHELP(){
        String ret;
        try {
        this.out.write(HELP.getBytes());

        int readBytes = in.read(buffer);
        if (readBytes <= 0)
            return null;

        ret = new String(buffer, 0, readBytes);
        } catch (IOException e) {
            return null;
        }

        return ret;
    }

    public String sendEHLO() {
        String ret;
        try {
            this.out.write(EHLO.getBytes());
            int readBytes = in.read(buffer);
            if (readBytes <= 0)
                return null;

            ret = new String(buffer, 0, readBytes);
        } catch (IOException e) {
            return null;
        }

        return ret;
    }
}
