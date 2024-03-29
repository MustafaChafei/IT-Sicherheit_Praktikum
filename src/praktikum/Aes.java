package praktikum;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Aes {
    SecretKey key;
    int KEY_SIZE = 128;
    Cipher enCipher;
    int TAG_LENGTH = 128;

    public void init() throws NoSuchAlgorithmException {
        KeyGenerator gen = KeyGenerator.getInstance("AES");
        gen.init(KEY_SIZE);
        key = gen.generateKey();
    }

    public String encrypt(String text) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] textInBytes = text.getBytes();
        enCipher = Cipher.getInstance("AES/GCM/NoPadding");
        enCipher.init(Cipher.ENCRYPT_MODE, key);
        return encode(enCipher.doFinal(textInBytes));
    }

    public String decrypt(String encryptedText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] textInBytes = decode(encryptedText);
        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH, enCipher.getIV());
        decryptionCipher.init(Cipher.DECRYPT_MODE, key, spec);
        return new String(decryptionCipher.doFinal(textInBytes));
    }

    public String encode(byte[] data){
        return Base64.getEncoder().encodeToString(data);
    }

    public byte[] decode(String data){
        return Base64.getDecoder().decode(data);
    }
    public static void main(String[] args){
        Aes aes = new Aes();
        try {
            aes.init();
            String encrypted = aes.encrypt("Dieser Text soll verschlüsselt werden");
            String decrypted = aes.decrypt(encrypted);

            System.out.println("Encrpted: " +encrypted);
            System.out.println("Decrypted: " +decrypted);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }
}
