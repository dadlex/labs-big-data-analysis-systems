import org.bouncycastle.cms.CMSException;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Arrays;


public class Lab2 {
    public static void main(String[] args) throws {
        Security.setProperty("crypto.policy", "unlimited");
//        int maxKeySize = javax.crypto.Cipher.getMaxAllowedKeyLength("AES");
//        System.out.println("Max Key Size for AES : " + maxKeySize);
        try {
            DataCryptoProcessor encryptor = new DataCryptoProcessor();

            String secretMessage = "My password is 123456Seven";
            System.out.println("Original Message : " + secretMessage);
            byte[] stringToEncrypt = secretMessage.getBytes();
            byte[] encryptedData = encryptor.encryptData(stringToEncrypt);
            System.out.println("Encrypted Message : " + new String(encryptedData));
            byte[] rawData = encryptor.decryptData(encryptedData);
            String decryptedMessage = new String(rawData);
            System.out.println("Decrypted Message : " + decryptedMessage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }


}
