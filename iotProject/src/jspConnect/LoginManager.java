package jspConnect;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import DBConnect.Login;
import util.DataAES;

public class LoginManager {
	//	필요한 객체
	private Login lin = Login.getInstance();;
	private PropLoad pl;
	
	//	변수들
	private String name;
	private String id;
	private String pwd;
	private String b_pwd;
	private String mail;
	private String result;
	
	public LoginManager(PropLoad pl, String name, String id, String pwd, String b_pwd, String mail) {
		this.pl = pl;
		
		if(pl.getType().equals("adminLogin")) {
			adminLoginCheck(id);
		}
		else if(pl.getType().equals("login")) {
			loginCheck(id, name);
		}
		else if(pl.getType().equals("join")) {
			joinCheck(id, name, pwd, mail);
		}
		else if(pl.getType().equals("find")) {
			findCheck(id, name, mail);
		}
		else if(pl.getType().equals("change")) {
			changeCheck(id, pwd, b_pwd);
		}
		else {
			loginError();
		}
	}
	
	public void adminLoginCheck(String id) {
		try {
			this.id = DataAES.aesDecryption(id, pl.getSecurityKey());
					
			
			result = lin.adminLogin(this.id);
			result = DataAES.aesEncryption(result,  pl.getSecurityKey());
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager InvalidKeyException error");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager UnsupportedEncodingException error");
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager NoSuchPaddingException error");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager NoSuchAlgorithmException error");
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager InvalidAlgorithmParameterException error");
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager BadPaddingException error");
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager IllegalBlockSizeException error");
		}
		
	}
	
	public String loginCheck(String id, String name) {
		try {
			this.id = DataAES.aesDecryption(id, pl.getSecurityKey());
			this.name = DataAES.aesDecryption(name, pl.getSecurityKey());
			
			
			result = lin.loginDB(this.name, this.id);
			result = DataAES.aesEncryption(result,  pl.getSecurityKey());
			
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager InvalidKeyException error");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager UnsupportedEncodingException error");
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager NoSuchPaddingException error");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager NoSuchAlgorithmException error");
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager InvalidAlgorithmParameterException error");
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager BadPaddingException error");
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager IllegalBlockSizeException error");
		}
		
		return result;
	}
	
	public void joinCheck(String id, String name, String pwd, String mail) {
		try {
			this.id = DataAES.aesDecryption(id, pl.getSecurityKey());
			this.name = DataAES.aesDecryption(name, pl.getSecurityKey());
			this.pwd = DataAES.aesDecryption(pwd, pl.getSecurityKey());
			this.mail = DataAES.aesDecryption(mail, pl.getSecurityKey());

			
			result = lin.createAccount(this.name, this.id, this.pwd, this.mail);
			result = DataAES.aesEncryption(result,  pl.getSecurityKey());
			
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager InvalidKeyException error");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager UnsupportedEncodingException error");
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager NoSuchPaddingException error");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager NoSuchAlgorithmException error");
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager InvalidAlgorithmParameterException error");
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager BadPaddingException error");
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager IllegalBlockSizeException error");
		}
		
	}
	
	public void findCheck(String id, String name, String mail) {
		try {
			this.id = DataAES.aesDecryption(id, pl.getSecurityKey());
			this.name = DataAES.aesDecryption(name, pl.getSecurityKey());
			this.mail = DataAES.aesDecryption(mail, pl.getSecurityKey());

			
			result = lin.findPW(this.name, this.id, this.mail);
			result = DataAES.aesEncryption(result,  pl.getSecurityKey());
			
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager InvalidKeyException error");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager UnsupportedEncodingException error");
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager NoSuchPaddingException error");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager NoSuchAlgorithmException error");
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager InvalidAlgorithmParameterException error");
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager BadPaddingException error");
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager IllegalBlockSizeException error");
		}
		
	}
	
	public void changeCheck(String id, String pwd, String b_pwd) {
		try {
			this.id = DataAES.aesDecryption(id, pl.getSecurityKey());
			this.b_pwd = DataAES.aesDecryption(b_pwd, pl.getSecurityKey());
			this.pwd = DataAES.aesDecryption(pwd, pl.getSecurityKey());

			
			result = lin.changePW(this.id, this.pwd, this.b_pwd);
			result = DataAES.aesEncryption(result,  pl.getSecurityKey());
			
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager InvalidKeyException error");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager UnsupportedEncodingException error");
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager NoSuchPaddingException error");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager NoSuchAlgorithmException error");
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager InvalidAlgorithmParameterException error");
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager BadPaddingException error");
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager IllegalBlockSizeException error");
		}
		
	}
	
	public void loginError() {
		try {
			result = DataAES.aesEncryption("error/nonTypeRequest", pl.getSecurityKey());
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager InvalidKeyException error");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager UnsupportedEncodingException error");
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager NoSuchPaddingException error");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager NoSuchAlgorithmException error");
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager InvalidAlgorithmParameterException error");
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager BadPaddingException error");
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			System.err.println("LoginManager IllegalBlockSizeException error");
		}
	}
	
	public String getResult() {
		return result;
	}
}
