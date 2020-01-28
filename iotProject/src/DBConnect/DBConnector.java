package DBConnect;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import utill.AESDec;

public class DBConnector {
	private Connection conn;
	private AESDec aes;
	
	private String dbURL = "jdbc:mariadb://localhost:8080/iot?serverTimezone=UTC";
	private String dbID = "testid";
	private String dbPassword = "";
	
	public DBConnector() {
		try {
			//���� ��ȣȭ�� DB password read
				String propFile = "E:\\eclipse\\iotProject\\src\\util\\key.properties";		
	      		Properties props = new Properties();
	      		FileInputStream fis = new FileInputStream(propFile);	         
	       		props.load(new java.io.BufferedInputStream(fis));			

			//�ܺο� ����� ���Ű read
	       		String read_key = "E:\\eclipse\\jar���ϸ���\\key_management\\keymanagement.properties";
	        	Properties key = new Properties();	        
	       		FileInputStream key_fis = new FileInputStream(read_key);
	       		key.load(new java.io.BufferedInputStream(key_fis));
	        
	       		 String aes_key = key.getProperty("key");
	       		 if(aes_key !=null) {
	        		aes = new AESDec(aes_key);	//��ȣȭ�� �ڵ�
	    		}	  
	        
			String dbPassword = "";
			
			if(aes != null)
				dbPassword = aes.aesDecode(props.getProperty("password"));	//��ȣȭ�� �ڵ� ���ڵ�
			
			if(dbPassword != null) {
				Class.forName("org.mariadb.jdbc.Driver");
				conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
			}
			
			if(fis != null) 
				fis.close(); //�������� �ڿ� ���� 
			if(key_fis != null)
				key_fis.close();
		} catch (FileNotFoundException e) {//����ó�� ,�������� ����
			System.err.println("CommDAO FileNotFoundException error");
		} catch (IOException e) {
			System.err.println("CommDAO IOException error");
		} catch (SQLException e) {
			System.err.println("CommDAO SQLException error");
		} catch (ClassNotFoundException e) {
			System.err.println("CommDAO ClassNotFoundException error");
		} catch (InvalidKeyException e) {
			System.err.println("CommDAO InvalidKeyException error");
		} catch (NoSuchAlgorithmException e) {
			System.err.println("CommDAO NoSuchAlgorithmException error");
		} catch (NoSuchPaddingException e) {
			System.err.println("CommDAO NoSuchPaddingException error");
		} catch (InvalidAlgorithmParameterException e) {
			System.err.println("CommDAO InvalidAlgorithmParameterException error");
		} catch (IllegalBlockSizeException e) {
			System.err.println("CommDAO IllegalBlockSizeException error");
		} catch (BadPaddingException e) {
			System.err.println("CommDAO BadPaddingException error");
		}
	}


	public String getURL() {
		return dbURL;
	}
	
	public String getID() {
		return dbID;
	}
	
	public String getPW() {
		return dbPassword;
	}		

}