package cl.tesis.https;

import cl.tesis.output.JsonWritable;
import cl.tesis.tls.Certificate;
import cl.tesis.tls.HeartbleedData;
import cl.tesis.tls.ScanCipherSuitesData;
import cl.tesis.tls.ScanTLSProtocolsData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class HttpsCertificateData implements JsonWritable{
    private String ip;
    private String error;
    private String tlsProtocol;
    private String cipherSuite;
    private Certificate[] chain;
    private HeartbleedData heartbleedData;
    private String beastCipher;
    private ScanTLSProtocolsData protocols;
    private ScanCipherSuitesData ciphersSuites;

    public HttpsCertificateData(String ip) {
        this.ip = ip;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setTLSProtocol(String tlsProtocol) {
        this.tlsProtocol = tlsProtocol;
    }

    public void setCipherSuite(String cipherSuite) {
        this.cipherSuite = cipherSuite;
    }

    public void setChain(Certificate[] certificates) {
        this.chain = certificates;
    }

    public void setBeastCipher(String beastCipher) {
        this.beastCipher = beastCipher;
    }

    public void setHeartbleedData(HeartbleedData heartbleedData) {
        this.heartbleedData = heartbleedData;
    }

    public void setProtocols(ScanTLSProtocolsData protocols) {
        this.protocols = protocols;
    }

    public void setCiphersSuites(ScanCipherSuitesData ciphersSuites) {
        this.ciphersSuites = ciphersSuites;
    }

    @Override
    public String toJson() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(this);
    }
}
