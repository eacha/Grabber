package cl.tesis.ssl;

import cl.tesis.output.ListWritable;
import sun.misc.BASE64Encoder;
import sun.security.provider.X509Factory;

import javax.security.auth.x500.X500Principal;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Certificate implements ListWritable {
    private String sigantureAlgorithm;
    private Date expiredTime;
    private String organizationName;
    private String organizationURL;
    private String keyBits;
    private String PemCert;

    public Certificate(X509Certificate x509Certificate) {
        Map<String, String> subjectMap = parserX500Principal(x509Certificate.getSubjectX500Principal());

        this.sigantureAlgorithm = x509Certificate.getSigAlgName();
        this.expiredTime = x509Certificate.getNotAfter();
        this.organizationName = subjectMap.get("O");
        this.organizationURL = subjectMap.get("CN");
        this.keyBits = parserPublicKeyBits(x509Certificate);
        this.PemCert = toPemFormat(x509Certificate);
    }

    protected Map<String, String> parserX500Principal(X500Principal x500Principal) {
        String[] values = x500Principal.getName().split(",");

        Map<String, String> principalMap = new HashMap<>();

        for (String value : values) {
            String[] elements = value.split("=");

            if (elements.length >= 2) {
                principalMap.put(elements[0], elements[1]);
            }
        }

        return principalMap;
    }

    protected String parserPublicKeyBits(X509Certificate x509Certificate) {
        String[] publicKey = x509Certificate.getPublicKey().toString().split("[\r\n]+");
        Pattern pattern =  Pattern.compile("(.*), (.*) bits");
        Matcher matcher = pattern.matcher(publicKey[0]);
        boolean success = matcher.find();

        if (success) {
            return matcher.group(2);
        }

        return null;
    }

    protected String toPemFormat(X509Certificate x509Certificate) {
        String pemFormat = "";
        BASE64Encoder encoder = new BASE64Encoder();

        try {
            pemFormat += X509Factory.BEGIN_CERT + "\n";
            pemFormat += encoder.encode(x509Certificate.getEncoded());
            pemFormat += "\n" + X509Factory.END_CERT;
        }catch (CertificateEncodingException e) {
            pemFormat = "";
        }

        return pemFormat;
    }


    @Override
    public List<String> getParameterList() {
        ArrayList<String> parameters = new ArrayList<>();

        parameters.add("Signature Algorithm");
        parameters.add("Expired Time");
        parameters.add("Organization Name");
        parameters.add("Organization URL");
        parameters.add("Key Bits");
        parameters.add("PEM Certificate");

        return parameters;
    }

    @Override
    public List<String> getValueList() {
        ArrayList<String> values = new ArrayList<>();

        values.add(this.sigantureAlgorithm);
        values.add(this.expiredTime.toString());
        values.add(this.organizationName);
        values.add(this.organizationURL);
        values.add(this.keyBits + "");
        values.add(this.PemCert);

        return values;
    }

    @Override
    public String toString() {
        return "Certificate{" +
                "sigantureAlgorithm='" + sigantureAlgorithm + '\'' +
                ", expiredTime=" + expiredTime +
                ", organizationName='" + organizationName + '\'' +
                ", organizationURL='" + organizationURL + '\'' +
                ", keyBits='" + keyBits + '\'' +
                ", PemCert='" + PemCert + '\'' +
                '}';
    }

    public String getSigantureAlgorithm() {
        return sigantureAlgorithm;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public String getOrganizationURL() {
        return organizationURL;
    }

    public String getKeyBits() {
        return keyBits;
    }

    public String getPemCert() {
        return PemCert;
    }
}
