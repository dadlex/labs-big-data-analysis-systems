import java.security.*;
import java.util.Arrays;

public class Lab2 {
    public static void main(String[] args) {
        Security.setProperty("crypto.policy", "unlimited");
//        int maxKeySize = javax.crypto.Cipher.getMaxAllowedKeyLength("AES");
//        System.out.println("Max Key Size for AES : " + maxKeySize);
        try {
            DataCryptoProcessor encryptor = new DataCryptoProcessor();

            String secretMessage = "This message exists to show that encryption works";
            System.out.println("Original Message : " + secretMessage + "\n");

            byte[] stringToEncrypt = secretMessage.getBytes();
            byte[] encryptedData = encryptor.encryptData(stringToEncrypt);
            System.out.println("Encrypted Message : " + new String(encryptedData) + "\n");

            byte[] rawData = encryptor.decryptData(encryptedData);
            String decryptedMessage = new String(rawData);
            System.out.println("Decrypted Message : " + decryptedMessage + "\n");


            byte[] signedData = encryptor.signData(rawData);
            if (encryptor.verifySignedData(signedData)) {
                System.out.println("successful verification");
            } else {
                System.out.println("failed to verify");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}
