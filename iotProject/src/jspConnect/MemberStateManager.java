package jspConnect;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import DBConnect.MemberState;
import util.DataAES;

public class MemberStateManager {
	//	필요한 객체
	private MemberState mem = MemberState.getInstance();;
	private PropLoad pl;
	
	//	변수들
	private String name;
	private String phone;
	private String dept;		//	과정
	private String team;		//	소속
	private String beforeName;	
	private String beforePhone;	
	private String result;
	
	public MemberStateManager(PropLoad pl, String name, String phone, String dept, String team,
			 String beforeName, String beforePhone) {
		this.pl = pl;
		
		if(pl.getType().equals("memShow")) {
			memShowCheck();
		}
		else if(pl.getType().equals("memAdd")) {
			memAddCheck(name, phone, dept, team);
		}
		else if(pl.getType().equals("memModify")) {
			memModifyCheck(beforeName, beforePhone, name, phone, dept, team);
		}
		else if(pl.getType().equals("memDelete")) {
			memDeleteCheck(name, phone, dept, team);
		}
		else {
			memberError();
		}
		
	}

	public void memShowCheck() {
		try {
			result = mem.memShow();
			result = DataAES.aesEncryption(result,  pl.getSecurityKey());
			
			
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			System.err.println("MemberStateManager InvalidKeyException error");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.err.println("MemberStateManager UnsupportedEncodingException error");
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("MemberStateManager NoSuchPaddingException error");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println("MemberStateManager NoSuchAlgorithmException error");
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			System.err.println("MemberStateManager InvalidAlgorithmParameterException error");
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("MemberStateManager BadPaddingException error");
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			System.err.println("MemberStateManager IllegalBlockSizeException error");
		}
		
	}
	
	public void memAddCheck(String name, String phone, String dept, String team) {
		try {
			
			this.name = DataAES.aesDecryption(name, pl.getSecurityKey());
			this.phone = DataAES.aesDecryption(phone, pl.getSecurityKey());
			this.dept = DataAES.aesDecryption(dept, pl.getSecurityKey());		
			this.team = DataAES.aesDecryption(team, pl.getSecurityKey());
			
			
			result = mem.memAdd(this.name, this.phone, this.dept, this.team);
			result = DataAES.aesEncryption(result,  pl.getSecurityKey());
			
			
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			System.err.println("MemberStateManager InvalidKeyException error");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.err.println("MemberStateManager UnsupportedEncodingException error");
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("MemberStateManager NoSuchPaddingException error");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println("MemberStateManager NoSuchAlgorithmException error");
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			System.err.println("MemberStateManager InvalidAlgorithmParameterException error");
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("MemberStateManager BadPaddingException error");
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			System.err.println("MemberStateManager IllegalBlockSizeException error");
		}
		
	}
	
	public void memModifyCheck(String beforeName, String beforePhone, String name,
			String phone, String dept, String team) {
		try {
			
			this.beforeName = DataAES.aesDecryption(beforeName, pl.getSecurityKey());
			this.beforePhone = DataAES.aesDecryption(beforePhone, pl.getSecurityKey());			
			this.name = DataAES.aesDecryption(name, pl.getSecurityKey());
			this.phone = DataAES.aesDecryption(phone, pl.getSecurityKey());
			this.dept = DataAES.aesDecryption(dept, pl.getSecurityKey());		
			this.team = DataAES.aesDecryption(team, pl.getSecurityKey());
			

			result = mem.memModify(this.beforeName, this.beforePhone, this.name, this.phone, this.dept, this.team);
			result = DataAES.aesEncryption(result,  pl.getSecurityKey());
			
			
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			System.err.println("MemberStateManager InvalidKeyException error");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.err.println("MemberStateManager UnsupportedEncodingException error");
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("MemberStateManager NoSuchPaddingException error");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println("MemberStateManager NoSuchAlgorithmException error");
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			System.err.println("MemberStateManager InvalidAlgorithmParameterException error");
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("MemberStateManager BadPaddingException error");
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			System.err.println("MemberStateManager IllegalBlockSizeException error");
		}
		
	}
	
	public void memDeleteCheck(String name, String phone, String dept, String team) {
		try {
			
			this.name = DataAES.aesDecryption(name, pl.getSecurityKey());
			this.phone = DataAES.aesDecryption(phone, pl.getSecurityKey());
			this.dept = DataAES.aesDecryption(dept, pl.getSecurityKey());		
			this.team = DataAES.aesDecryption(team, pl.getSecurityKey());
			
			
			result = mem.memDelete(this.name, this.phone, this.dept, this.team);
			result = DataAES.aesEncryption(result,  pl.getSecurityKey());
			
			
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			System.err.println("MemberStateManager InvalidKeyException error");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.err.println("MemberStateManager UnsupportedEncodingException error");
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("MemberStateManager NoSuchPaddingException error");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println("MemberStateManager NoSuchAlgorithmException error");
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			System.err.println("MemberStateManager InvalidAlgorithmParameterException error");
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("MemberStateManager BadPaddingException error");
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			System.err.println("MemberStateManager IllegalBlockSizeException error");
		}
		
	}
	
	public void memberError() {
		try {
			result = DataAES.aesEncryption("error/nonTypeRequest", pl.getSecurityKey());
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			System.err.println("MemberStateManager InvalidKeyException error");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.err.println("MemberStateManager UnsupportedEncodingException error");
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("MemberStateManager NoSuchPaddingException error");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println("MemberStateManager NoSuchAlgorithmException error");
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			System.err.println("MemberStateManager InvalidAlgorithmParameterException error");
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("MemberStateManager BadPaddingException error");
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			System.err.println("MemberStateManager IllegalBlockSizeException error");
		}

	}
	
	public String getResult() {
		return result;
	}
}
