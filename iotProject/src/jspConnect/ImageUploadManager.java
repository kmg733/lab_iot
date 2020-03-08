package jspConnect;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import DBConnect.ImageUpload;
import util.DataAES;

public class ImageUploadManager {
//	필요한 객체
	private ImageUpload imgup = ImageUpload.getInstance();;
	private PropLoad pl;
	
	//	변수들
	private String imgFile;
	private String result;
	
	//	이미지 저장을 위한 파일경로 지정
	private	File file = new File(".");
	private String savePath = file.getAbsoluteFile().toString() + "IoT/WEB-INF/upload/";
	
	
	public ImageUploadManager(PropLoad pl, String imgFile) {
		this.pl = pl;
		
		if(pl.getType().equals("orgShow")) {
			orgShowCheck();
		}
		else if(pl.getType().equals("orgUpload")) {
			orgUploadCheck(savePath, imgFile);
		}
		else if(pl.getType().equals("strShow")) {
			strShowCheck();
		}
		else if(pl.getType().equals("strUpload")) {
			strUploadCheck(savePath, imgFile);
		}
		else if(pl.getType().equals("ipShow")) {
			ipShowCheck();
		}
		else if(pl.getType().equals("ipUpload")) {
			ipUploadCheck(savePath, imgFile);
		}
		else {
			imgError();
		}
		
	}
	
	public void orgShowCheck() {
		try {
			
			result = imgup.orgShow();
			result = DataAES.aesEncryption(result,  pl.getSecurityKey());
			
			
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager InvalidKeyException error");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager UnsupportedEncodingException error");
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager NoSuchPaddingException error");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager NoSuchAlgorithmException error");
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager InvalidAlgorithmParameterException error");
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager BadPaddingException error");
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager IllegalBlockSizeException error");
		}
		
	}
	
	public void orgUploadCheck(String savePath, String imgFile) {
		try {
			this.imgFile = DataAES.aesDecryption(imgFile, pl.getSecurityKey());
			
			
			result = imgup.orgUpload(this.savePath, this.imgFile);
			result = DataAES.aesEncryption(result,  pl.getSecurityKey());
			
			
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager InvalidKeyException error");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager UnsupportedEncodingException error");
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager NoSuchPaddingException error");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager NoSuchAlgorithmException error");
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager InvalidAlgorithmParameterException error");
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager BadPaddingException error");
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager IllegalBlockSizeException error");
		}
		
	}
	
	public void strShowCheck() {
		try {
			
			result = imgup.strShow();
			result = DataAES.aesEncryption(result,  pl.getSecurityKey());
			
			
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager InvalidKeyException error");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager UnsupportedEncodingException error");
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager NoSuchPaddingException error");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager NoSuchAlgorithmException error");
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager InvalidAlgorithmParameterException error");
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager BadPaddingException error");
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager IllegalBlockSizeException error");
		}
		
	}
	
	public void strUploadCheck(String savePath, String imgFile) {
		try {
			this.imgFile = DataAES.aesDecryption(imgFile, pl.getSecurityKey());
			
			
			result = imgup.strUpload(this.savePath, this.imgFile);
			result = DataAES.aesEncryption(result,  pl.getSecurityKey());
			
			
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager InvalidKeyException error");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager UnsupportedEncodingException error");
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager NoSuchPaddingException error");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager NoSuchAlgorithmException error");
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager InvalidAlgorithmParameterException error");
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager BadPaddingException error");
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager IllegalBlockSizeException error");
		}
		
	}
	
	public void ipShowCheck() {
		try {
			
			result = imgup.ipShow();
			result = DataAES.aesEncryption(result,  pl.getSecurityKey());
			
			
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager InvalidKeyException error");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager UnsupportedEncodingException error");
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager NoSuchPaddingException error");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager NoSuchAlgorithmException error");
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager InvalidAlgorithmParameterException error");
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager BadPaddingException error");
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager IllegalBlockSizeException error");
		}
		
	}
	
	public void ipUploadCheck(String savePath, String imgFile) {
		try {
			this.imgFile = DataAES.aesDecryption(imgFile, pl.getSecurityKey());
			
			
			result = imgup.ipUpload(this.savePath, this.imgFile);
			result = DataAES.aesEncryption(result,  pl.getSecurityKey());
			
			
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager InvalidKeyException error");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager UnsupportedEncodingException error");
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager NoSuchPaddingException error");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager NoSuchAlgorithmException error");
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager InvalidAlgorithmParameterException error");
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager BadPaddingException error");
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager IllegalBlockSizeException error");
		}
		
	}
	
	public void imgError() {
		try {
			result = DataAES.aesEncryption("error/nonTypeRequest", pl.getSecurityKey());
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager InvalidKeyException error");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager UnsupportedEncodingException error");
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager NoSuchPaddingException error");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager NoSuchAlgorithmException error");
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager InvalidAlgorithmParameterException error");
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager BadPaddingException error");
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			System.err.println("ImageUploadManager IllegalBlockSizeException error");
		}

	}
	
	public String getResult() {
		return result;
	}
}
