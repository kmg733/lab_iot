package util;

import org.apache.commons.codec.binary.Base64;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class DataRSA {

    /*��ȣȭ*/
    public static String rsaEncryption(String plainData, String stringPublicKey) throws BadPaddingException,
            IllegalBlockSizeException, InvalidKeySpecException,
            NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {

        // ������ ���޹��� 'String����Ű'�� '����Ű ��ü'�� ����� ����
        byte[] bytePublicKey = Base64.decodeBase64(stringPublicKey.getBytes());
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(bytePublicKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

        // ������� ����Ű��ü�� ������� ��ȣȭ���� �����ϴ� ����
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey); // ��ȣȭ �غ�

        // ��ȣȭ ����
        byte[] byteEncryptedData = cipher.doFinal(plainData.getBytes());

        // ��ȣȭ ������, ���ڵ� �� 'String'���� ��ȯ
        
        return Base64.encodeBase64String(byteEncryptedData);
    }

    /*��ȣȭ*/
    public static String rsaDecryption(String encryptedData, String stringPrivateKey) throws NoSuchAlgorithmException,
            InvalidKeySpecException, NoSuchPaddingException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        // ������ ���޹��� 'String����Ű'�� '����Ű ��ü'�� ����� ����
        byte[] bytePrivateKey =  Base64.decodeBase64(stringPrivateKey.getBytes());
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(bytePrivateKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

        // ������� ����Ű��ü�� ������� ��ȣȭ���� �����ϴ� ����
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey); // ��ȣȭ �غ�

        // ��ȣȭ�� ���ڵ� �����͸� ���ڵ� ����
        byte[] byteEncryptedData = Base64.decodeBase64(encryptedData.getBytes());

        // ��ȣȭ ����
        byte[] byteDecryptedData = cipher.doFinal(byteEncryptedData);

        // ��ȣȭ �� 'String'���� ��ȯ
        return new String(byteDecryptedData);
    }
}