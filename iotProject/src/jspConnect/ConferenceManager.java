package jspConnect;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import DBConnect.Conference;
import util.DataAES;

public class ConferenceManager {
	//	필요한 객체
	private Conference conf = Conference.getInstance();;
	private PropLoad pl;
	
	//	변수들
	private String date;
	private String text;
	private String result;
	public ConferenceManager(PropLoad pl) {
		this.pl = pl;
	}

	public String cfShowCheck(String date) {
		try {
			this.date = DataAES.aesDecryption(date, pl.getSecurityKey());
			
			//Cross-site Script Check
			if(date != null) {
				XSS xss = new XSS();
				date = xss.prevention(date);			
			} else {
				date = "";
			}		
			
			result = conf.cfShow(this.date);
			result = DataAES.aesEncryption(result,  pl.getSecurityKey());
			
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			System.err.println("ConferenceManager InvalidKeyException error");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.err.println("ConferenceManager UnsupportedEncodingException error");
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("ConferenceManager NoSuchPaddingException error");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println("ConferenceManager NoSuchAlgorithmException error");
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			System.err.println("ConferenceManager InvalidAlgorithmParameterException error");
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("ConferenceManager BadPaddingException error");
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			System.err.println("ConferenceManager IllegalBlockSizeException error");
		}
		
		return result;
	}

	public String cfAddCheck(String date, String text) {
		try {
			this.date = DataAES.aesDecryption(date, pl.getSecurityKey());
			this.text = DataAES.aesDecryption(text, pl.getSecurityKey());
			
			//Cross-site Script Check
			if(date != null) {
				XSS xss = new XSS();
				date = xss.prevention(date);			
			} else {
				date = "";
			}		
			if(text != null) {
				XSS xss = new XSS();
				text = xss.prevention(text);
			} else {
				date = "";
			}		
			
			result = conf.cfAdd(this.date, this.text);
			result = DataAES.aesEncryption(result,  pl.getSecurityKey());
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			System.err.println("ConferenceManager InvalidKeyException error");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.err.println("ConferenceManager UnsupportedEncodingException error");
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("ConferenceManager NoSuchPaddingException error");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println("ConferenceManager NoSuchAlgorithmException error");
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			System.err.println("ConferenceManager InvalidAlgorithmParameterException error");
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("ConferenceManager BadPaddingException error");
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			System.err.println("ConferenceManager IllegalBlockSizeException error");
		}
		
		return result;
	}
	
	public String cfError() {
		try {
			result = DataAES.aesEncryption("error/nonTypeRequest", pl.getSecurityKey());
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			System.err.println("ConferenceManager InvalidKeyException error");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.err.println("ConferenceManager UnsupportedEncodingException error");
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("ConferenceManager NoSuchPaddingException error");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println("ConferenceManager NoSuchAlgorithmException error");
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			System.err.println("ConferenceManager InvalidAlgorithmParameterException error");
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("ConferenceManager BadPaddingException error");
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			System.err.println("ConferenceManager IllegalBlockSizeException error");
		}

		return result;
	}
}
