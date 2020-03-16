package jspConnect;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import util.DataAES;
import util.DataRSA;

public class PropLoad {
	private String propFile;
	private Properties props;
	private FileInputStream fis;
	private String type;
	private String securityKey;
	
	public PropLoad(String securityKey, String type) {
			try {
				propFile = "C:/Users/securityLab_5/eclipse-workspace/IoT/src/util/PRIVATEkey.properties";
				props = new Properties();
				fis = new FileInputStream(propFile);
				props.load(new java.io.BufferedInputStream(fis));
				
				String rsaPrivatekey = props.getProperty("key");
				if (rsaPrivatekey != null) {
					this.securityKey = DataRSA.rsaDecryption(securityKey, rsaPrivatekey);
					this.type = DataAES.aesDecryption(type, this.securityKey);
				}
				
				
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				System.err.println("PropLoad InvalidKeyException error");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.err.println("PropLoad FileNotFoundException error");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				System.err.println("PropLoad NoSuchAlgorithmException error");
			} catch (InvalidKeySpecException e) {
				// TODO Auto-generated catch block
				System.err.println("PropLoad InvalidKeySpecException error");
			} catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				System.err.println("PropLoad NoSuchPaddingException error");
			} catch (BadPaddingException e) {
				// TODO Auto-generated catch block
				System.err.println("PropLoad BadPaddingException error");
			} catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				System.err.println("PropLoad IllegalBlockSizeException error");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				System.err.println("PropLoad UnsupportedEncodingException error");
			} catch (InvalidAlgorithmParameterException e) {
				// TODO Auto-generated catch block
				System.err.println("PropLoad InvalidAlgorithmParameterException error");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("PropLoad IOException error");
			} finally {
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.err.println("PropLoad IOException error");
				}
			}
	}
	
	public String getSecurityKey() {
		return securityKey;
	}
	
	public String getType() {
		return type;
	}
	
	
}
