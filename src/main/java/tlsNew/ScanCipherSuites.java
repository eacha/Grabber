package tlsNew;


import cl.tesis.mail.StartTLS;
import cl.tesis.tls.exception.HandshakeHeaderException;
import cl.tesis.tls.exception.StartTLSException;
import cl.tesis.tls.exception.TLSHeaderException;
import cl.tesis.tls.handshake.ClientHello;
import cl.tesis.tls.handshake.ServerHello;
import tlsNew.constant.CipherSuites;
import tlsNew.constant.TLSCipher;
import tlsNew.constant.TLSVersion;

import java.io.IOException;

public class ScanCipherSuites extends Scan {

    public ScanCipherSuites(String host, int port) {
        super(host, port);
    }

    public ScanCipherSuitesData scanAllCipherSuites() {
        ScanCipherSuitesData scanCipherSuitesData = new ScanCipherSuitesData();
        for (TLSCipher cipher : TLSCipher.values()) {
            scanCipherSuitesData.setCipherSuite(cipher, scanCipherSuite(cipher, null));
        }
        return scanCipherSuitesData;
    }

    public ScanCipherSuitesData scanAllCipherSuites(StartTLS start) {
        ScanCipherSuitesData scanCipherSuitesData = new ScanCipherSuitesData();
        for (TLSCipher cipher : TLSCipher.values()) {
            scanCipherSuitesData.setCipherSuite(cipher, scanCipherSuite(cipher, start));
        }
        return scanCipherSuitesData;
    }

    private String scanCipherSuite(TLSCipher cipher, StartTLS start) {
        ServerHello serverHello;
        try {
            /* Open and setting the connection */
            this.getConnection(host, port);

                /* STARTTLS */
            if (start != null) {
                this.ignoreFirstLine();
                this.startProtocolHandshake(start);
            }

            this.out.write(new ClientHello(TLSVersion.TLS_11.getStringVersion(), cipher).toByte());
            this.in.read(buffer);
            serverHello = new ServerHello(buffer);

        } catch (StartTLSException | TLSHeaderException | HandshakeHeaderException | IOException e) {
            this.close();
            return null;
        }

        this.close();
        return CipherSuites.getNameByByte(serverHello.getCipherSuite());
    }

}
