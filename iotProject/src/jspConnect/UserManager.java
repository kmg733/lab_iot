package jspConnect;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import DBConnect.User;
import util.DataAES;

public class UserManager {
	//	필요한 객체
	private User user = User.getInstance();;
	private PropLoad pl;
	
	//	변수들
	private String name;
	private String id;
	private String pwd;
	private String mail;
	private String b_name;
	private String b_id;
	private String result;
	
	public UserManager(PropLoad pl) {
		this.pl = pl;
	}
	
	
	public String addUser_ListCheck() {
		try {
			
			result = user.addUser_List();
			result = DataAES.aesEncryption(result,  pl.getSecurityKey());
			
			
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			System.err.println("UserManager InvalidKeyException error");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.err.println("UserManager UnsupportedEncodingException error");
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("UserManager NoSuchPaddingException error");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println("UserManager NoSuchAlgorithmException error");
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			System.err.println("UserManager InvalidAlgorithmParameterException error");
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("UserManager BadPaddingException error");
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			System.err.println("UserManager IllegalBlockSizeException error");
		}
		
		return result;
	}
	
	public String user_ListCheck(String name, String id) {
		try {
			this.name = DataAES.aesDecryption(name, pl.getSecurityKey());
			this.id = DataAES.aesDecryption(id, pl.getSecurityKey());
			
			//Cross-site Script Check
			if(name != null) {
				XSS xss = new XSS();
				name = xss.prevention(name);			
			} else {
				name = "";
			}	
			if(id != null) {
				XSS xss = new XSS();
				id = xss.prevention(id);			
			} else {
				id = "";
			}	
			
			result = user.user_List(this.name, this.id);
			result = DataAES.aesEncryption(result,  pl.getSecurityKey());
			
			
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			System.err.println("UserManager InvalidKeyException error");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.err.println("UserManager UnsupportedEncodingException error");
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("UserManager NoSuchPaddingException error");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println("UserManager NoSuchAlgorithmException error");
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			System.err.println("UserManager InvalidAlgorithmParameterException error");
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("UserManager BadPaddingException error");
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			System.err.println("UserManager IllegalBlockSizeException error");
		}
		
		return result;
	}
	
	public String addUser_AddCheck(String name, String id) {
		try {
			this.name = DataAES.aesDecryption(name, pl.getSecurityKey());
			this.id = DataAES.aesDecryption(id, pl.getSecurityKey());
			
			//Cross-site Script Check
			if(name != null) {
				XSS xss = new XSS();
				name = xss.prevention(name);			
			} else {
				name = "";
			}	
			if(id != null) {
				XSS xss = new XSS();
				id = xss.prevention(id);			
			} else {
				id = "";
			}	
			
			result = user.addUser_Add(this.name, this.id);
			result = DataAES.aesEncryption(result,  pl.getSecurityKey());
			
			
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			System.err.println("UserManager InvalidKeyException error");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.err.println("UserManager UnsupportedEncodingException error");
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("UserManager NoSuchPaddingException error");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println("UserManager NoSuchAlgorithmException error");
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			System.err.println("UserManager InvalidAlgorithmParameterException error");
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("UserManager BadPaddingException error");
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			System.err.println("UserManager IllegalBlockSizeException error");
		}
		
		return result;
	}
	
	public String addUser_DeleteCheck(String name, String id) {
		try {
			this.name = DataAES.aesDecryption(name, pl.getSecurityKey());
			this.id = DataAES.aesDecryption(id, pl.getSecurityKey());
			
			//Cross-site Script Check
			if(name != null) {
				XSS xss = new XSS();
				name = xss.prevention(name);			
			} else {
				name = "";
			}	
			if(id != null) {
				XSS xss = new XSS();
				id = xss.prevention(id);			
			} else {
				id = "";
			}	
			
			result = user.addUser_Delete(this.name, this.id);
			result = DataAES.aesEncryption(result,  pl.getSecurityKey());
			
			
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			System.err.println("UserManager InvalidKeyException error");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.err.println("UserManager UnsupportedEncodingException error");
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("UserManager NoSuchPaddingException error");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println("UserManager NoSuchAlgorithmException error");
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			System.err.println("UserManager InvalidAlgorithmParameterException error");
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("UserManager BadPaddingException error");
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			System.err.println("UserManager IllegalBlockSizeException error");
		}
		
		return result;
	}
	
	public String userError() {
		try {
			result = DataAES.aesEncryption("error/nonTypeRequest", pl.getSecurityKey());
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			System.err.println("UserManager InvalidKeyException error");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.err.println("UserManager UnsupportedEncodingException error");
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("UserManager NoSuchPaddingException error");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println("UserManager NoSuchAlgorithmException error");
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			System.err.println("UserManager InvalidAlgorithmParameterException error");
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("UserManager BadPaddingException error");
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			System.err.println("UserManager IllegalBlockSizeException error");
		}

		return result;
	}
}
