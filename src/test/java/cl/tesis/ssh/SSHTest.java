package cl.tesis.ssh;


import junit.framework.TestCase;

public class SSHTest extends TestCase{
    private SSHConnection connection;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        connection = new SSHConnection("192.80.24.4", 22);
    }

    public void testSSHVersion() throws Exception {
        assertEquals(true, connection.getSSHVersion().contains("SSH-2.0-OpenSSH"));
    }
}
