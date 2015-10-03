package cl.tesis.tls;

import java.util.Arrays;

public enum TLSVersion {
    SSL_30("SSL 3.0", "0300", new byte[]{0x03, 0x00}),
    TLS_10("TLS 1.0", "0301", new byte[]{0x03, 0x01}),
    TLS_11("TLS 1.1", "0302", new byte[]{0x03, 0x02}),
    TLS_12("TLS 1.2", "0303", new byte[]{0x03, 0x03});

    private String name;
    private String stringVersion;
    private byte[] byteVersion;

    TLSVersion(String name, String stringVersion, byte[] byteVersion) {
        this.name = name;
        this.stringVersion = stringVersion;
        this.byteVersion = byteVersion;
    }

    public String getName() {
        return name;
    }

    public String getStringVersion() {
        return stringVersion;
    }

    public byte[] getByteVersion() {
        return byteVersion;
    }

    public static String getNameByByte(byte[] version) {
        for (TLSVersion e : TLSVersion.values()) {
            if (Arrays.equals(version, e.getByteVersion())) {
                return e.getName();
            }
        }
        return null;
    }

    public static String getNameByString(String version) {
        for (TLSVersion e : TLSVersion.values()) {
            if (version.equals(e.getStringVersion())) {
                return e.getName();
            }
        }
        return null;
    }
}
