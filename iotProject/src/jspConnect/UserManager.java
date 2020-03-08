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
	private String result;
	
	public UserManager(PropLoad pl, String name, String id, String pwd, String mail,
			String b_name, String b_id) {
		this.pl = pl;
		
		if(pl.getType().equals("addUser_List")) {
			addUser_ListCheck();
		}
		else if(pl.getType().equals("user_List")) {
			user_ListCheck(name, id);
		}
		else if(pl.getType().equals("addUser_Add")) {
			addUser_AddCheck(name, id);
		}
		else if(pl.getType().equals("addUser_Delete")) {
			addUser_DeleteCheck(name, id);
		}
		else {
			userError();
		}
	}
	
	
	public void addUser_ListCheck() {
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
		
	}
	
	public void user_ListCheck(String name, String id) {
		try {
			this.name = DataAES.aesDecryption(name, pl.getSecurityKey());
			this.id = DataAES.aesDecryption(id, pl.getSecurityKey());
			
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
		
	}
	
	public void addUser_AddCheck(String name, String id) {
		try {
			this.name = DataAES.aesDecryption(name, pl.getSecurityKey());
			this.id = DataAES.aesDecryption(id, pl.getSecurityKey());
			
			
			
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
		
	}
	
	public void addUser_DeleteCheck(String name, String id) {
		try {
			this.name = DataAES.aesDecryption(name, pl.getSecurityKey());
			this.id = DataAES.aesDecryption(id, pl.getSecurityKey());
			
			
			
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
		
	}
	
	public void userError() {
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

	}
	
	public String getResult() {
		return result;
	}
}
