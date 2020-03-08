package jspConnect;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import DBConnect.Schedule;
import util.DataAES;

public class ScheduleManager {
	//	필요한 객체
	private Schedule sche = Schedule.getInstance();;
	private PropLoad pl;
	
	//	변수들
	private String title;
	private String b_title;		
	private String text;
	private String date;	
	private String result;
	
	public ScheduleManager(PropLoad pl, String title, String b_title, String text, String date) {
		this.pl = pl;
		
		if(pl.getType().equals("scheduleList")) {
			scheduleListCheck(date);
		}
		else if(pl.getType().equals("scheduleAdd")) {
			scheduleAddCheck(title, text, date);
		}
		else if(pl.getType().equals("scheduleShow")) {
			scheduleShowCheck(title, date);
		}
		else if(pl.getType().equals("scheduleDelete")) {
			scheduleDeleteCheck(title, date);
		}
		else if(pl.getType().equals("scheduleModify")) {
			scheduleModifyCheck(b_title, title, text, date);
		}
		else {
			scheduleError();
		}
	}
	
	public void scheduleListCheck(String date) {
		try {
			this.date = DataAES.aesDecryption(date, pl.getSecurityKey());
			
			result = sche.scheduleList(this.date);
			result = DataAES.aesEncryption(result,  pl.getSecurityKey());
			
			
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager InvalidKeyException error");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager UnsupportedEncodingException error");
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager NoSuchPaddingException error");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager NoSuchAlgorithmException error");
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager InvalidAlgorithmParameterException error");
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager BadPaddingException error");
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager IllegalBlockSizeException error");
		}
		
	}
	
	public void scheduleAddCheck(String title, String text, String date) {
		try {
			this.title = DataAES.aesDecryption(title, pl.getSecurityKey());
			this.text = DataAES.aesDecryption(text, pl.getSecurityKey());
			this.date = DataAES.aesDecryption(date, pl.getSecurityKey());

			
			result = sche.scheduleAdd(this.title, this.text, this.date);
			result = DataAES.aesEncryption(result,  pl.getSecurityKey());
			
			
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager InvalidKeyException error");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager UnsupportedEncodingException error");
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager NoSuchPaddingException error");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager NoSuchAlgorithmException error");
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager InvalidAlgorithmParameterException error");
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager BadPaddingException error");
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager IllegalBlockSizeException error");
		}
		
	}
	
	public void scheduleShowCheck(String title, String date) {
		try {
			this.title = DataAES.aesDecryption(title, pl.getSecurityKey());
			this.date = DataAES.aesDecryption(date, pl.getSecurityKey());

			
			result = sche.scheduleAdd(this.title, this.text, this.date);
			result = DataAES.aesEncryption(result,  pl.getSecurityKey());
			
			
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager InvalidKeyException error");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager UnsupportedEncodingException error");
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager NoSuchPaddingException error");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager NoSuchAlgorithmException error");
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager InvalidAlgorithmParameterException error");
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager BadPaddingException error");
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager IllegalBlockSizeException error");
		}
		
	}
	
	public void scheduleDeleteCheck(String title, String date) {
		try {
			this.title = DataAES.aesDecryption(title, pl.getSecurityKey());
			this.date = DataAES.aesDecryption(date, pl.getSecurityKey());


			result = sche.scheduleDelete(this.title, this.date);
			result = DataAES.aesEncryption(result,  pl.getSecurityKey());
			
			
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager InvalidKeyException error");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager UnsupportedEncodingException error");
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager NoSuchPaddingException error");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager NoSuchAlgorithmException error");
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager InvalidAlgorithmParameterException error");
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager BadPaddingException error");
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager IllegalBlockSizeException error");
		}
		
	}
	
	public void scheduleModifyCheck(String b_title, String title, String text, String date) {
		try {
			this.b_title = DataAES.aesDecryption(b_title, pl.getSecurityKey());
			this.text = DataAES.aesDecryption(text, pl.getSecurityKey());
			this.title = DataAES.aesDecryption(title, pl.getSecurityKey());			
			this.date = DataAES.aesDecryption(date, pl.getSecurityKey());

			
			result = sche.scheduleModify(this.b_title, this.title, this.text, this.date);
			result = DataAES.aesEncryption(result,  pl.getSecurityKey());
			
			
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager InvalidKeyException error");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager UnsupportedEncodingException error");
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager NoSuchPaddingException error");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager NoSuchAlgorithmException error");
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager InvalidAlgorithmParameterException error");
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager BadPaddingException error");
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager IllegalBlockSizeException error");
		}
		
	}
	
	public void scheduleError() {
		try {
			result = DataAES.aesEncryption("error/nonTypeRequest", pl.getSecurityKey());
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager InvalidKeyException error");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager UnsupportedEncodingException error");
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager NoSuchPaddingException error");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager NoSuchAlgorithmException error");
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager InvalidAlgorithmParameterException error");
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager BadPaddingException error");
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			System.err.println("ScheduleManager IllegalBlockSizeException error");
		}

	}
	
	public String getResult() {
		return result;
	}
}
