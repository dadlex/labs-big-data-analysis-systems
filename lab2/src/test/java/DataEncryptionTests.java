import org.bouncycastle.cms.CMSException;
import org.junit.Before;

import org.junit.Test;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;


public class DataEncryptionTests {
    DataCryptoProcessor encryptor;

    @Before
    public void createDataEncryptor() throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException,
            IOException, KeyStoreException, NoSuchProviderException {
        encryptor = new DataCryptoProcessor();
    }

    @Test
    public void testDataEncryption() throws CertificateEncodingException, IOException, CMSException {
        String secretMessage = "My password is 123456Seven";
        System.out.println("Original Message : " + secretMessage);
        byte[] stringToEncrypt = secretMessage.getBytes();
        byte[] encryptedData = encryptor.encryptData(stringToEncrypt);
        System.out.println("Encrypted Message : " + new String(encryptedData));
        byte[] rawData = encryptor.decryptData(encryptedData);
        String decryptedMessage = new String(rawData);
        System.out.println("Decrypted Message : " + decryptedMessage);
    }

}
