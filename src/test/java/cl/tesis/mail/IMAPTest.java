package cl.tesis.mail;

import cl.tesis.tls.*;
import junit.framework.TestCase;

import java.security.cert.X509Certificate;

public class IMAPTest extends TestCase {
    public static final String HOST = "192.80.24.4";
    public static final int PORT = 143;
    public static String START_PROTOCOL = "* OK [CAPABILITY IMAP4REV1 I18NLEVEL=1 LITERAL+ SASL-IR LOGIN-REFERRALS STARTTLS LOGINDISABLED] dichato.dcc.uchile.cl";

    public IMAP imap;

    public void setUp() throws Exception {
        super.setUp();
        imap = new IMAP(HOST, PORT);
    }

   public void testIMAPProtocol() throws Exception {
        String start =  imap.readBanner();
        assertEquals(true, start.contains(START_PROTOCOL));
    }

    public void testHandshake() throws Exception {
        imap.readBanner();

        TLSHandshake tlsHandshake =  new TLSHandshake(imap, StartTLS.IMAP);
        tlsHandshake.connect();
        X509Certificate[] certs =  tlsHandshake.getChainCertificate();

        Certificate[] chain = Certificate.parseCertificateChain(certs);
        assertEquals("RapidSSL SHA256 CA - G4", chain[0].getCertificateAuthority());
    }

    public void testAllTLSVersion() throws Exception {
        ScanTLSProtocols protocols =  new ScanTLSProtocols(HOST, PORT);
        ScanTLSProtocolsData version =  protocols.scanAllProtocols(StartTLS.IMAP);

        assertEquals(false, version.isSSL_30());
        assertEquals(true, version.isTLS_10());
        assertEquals(false, version.isTLS_11());
        assertEquals(false, version.isTLS_12());
    }

    public void testCipherSuite() throws Exception {
        ScanCipherSuites cipherSuites = new ScanCipherSuites(HOST, PORT);
        ScanCipherSuitesData suites = cipherSuites.scanAllCipherSuites(StartTLS.IMAP);

        assertEquals("TLS_RSA_WITH_SEED_CBC_SHA", suites.getMedium_ciphers());
        assertEquals("TLS_RSA_WITH_3DES_EDE_CBC_SHA", suites.getDes3_ciphers());
        assertEquals("TLS_RSA_WITH_AES_256_CBC_SHA", suites.getHigh_ciphers());
    }

}
