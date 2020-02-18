package util;


import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class DataAES {

    public static String secretKEY="";

    // AES ��ĪŰ ����
    public static void aesKeyGen() throws NoSuchAlgorithmException {

        KeyGenerator generator = KeyGenerator.getInstance("AES"); // Ű������ ����� ��ȣ �˰���
        SecureRandom secureRandom = new SecureRandom(); // ������ ���� ���� 'math random'���� ���� ������ ����
        generator.init(256, secureRandom); // ����� Ű ���� �� ������ �̿��Ͽ� Ű �ʱ�ȭ
        Key secureKey = generator.generateKey();

        // �������������� Base64.encodeBase64String NotMethod �̽��߻�
        // ��ĪŰ ��ü�� 'String'���� ��ȯ
        if((Build.VERSION.SDK_INT <= Build.VERSION_CODES.N))
            secretKEY = new String(Base64.encodeBase64(secureKey.getEncoded()));
        else
            secretKEY = Base64.encodeBase64String(secureKey.getEncoded());
    }


    public static String aesEncryption(String str, String key) throws UnsupportedEncodingException,
            NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException
    {
        String iv = "";
        Key keySpec;

        iv = key.substring(0,16);
        byte[] keyBytes = new byte[16];
        byte[] b = key.getBytes("UTF-8");
        int len = b.length;
        if(len > keyBytes.length)
            len = keyBytes.length;
        System.arraycopy(b, 0, keyBytes, 0, len); // b�� 0���� ���� len���� ��ŭ keybytes 0�������� ����
        keySpec = new SecretKeySpec(keyBytes, "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes())); // ��ȣȭ �غ�

        // AES ��ȣȭ
        byte[] encrypted = cipher.doFinal(str.getBytes("UTF-8"));

        // ��ȣȭ�� ������, ���ڵ� �� 'String'���� ��ȯ
        if((Build.VERSION.SDK_INT <= Build.VERSION_CODES.N))
            return new String(Base64.encodeBase64(encrypted));
        else
            return Base64.encodeBase64String(encrypted);
    }

    public static String aesDecryption(String str, String key) throws UnsupportedEncodingException,
            NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        String iv = "";
        Key keySpec;

        iv = key.substring(0,16);
        byte[] keyBytes = new byte[16];
        byte[] b = key.getBytes("UTF-8");
        int len = b.length;
        if(len > keyBytes.length)
            len = keyBytes.length;
        System.arraycopy(b, 0, keyBytes, 0, len); // b�� 0���� ���� len���� ��ŭ keybytes 0�������� ����
        keySpec = new SecretKeySpec(keyBytes, "AES");


        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes("UTF-8"))); // ��ȣȭ �غ�

        // ��ȣȭ�� ���ڵ� ������, ���ڵ� ��ȯ
        byte[] byteStr = Base64.decodeBase64(str.getBytes());
        // ���ڵ��� ��ȣȭ ������, ��ȣȭ �� 'String'���� ��ȯ
        return new String(cipher.doFinal(byteStr),"UTF-8");
    }
}