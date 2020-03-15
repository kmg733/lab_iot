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

import util.DBAES;

public class DBConnector { // DB접근제어
	private Connection conn;
	private DBAES aes;
	
	// DB접속
	private String dbURL = "jdbc:mariadb://localhost:3306/securitylab_andb?serverTimezone=UTC"; // db주소
	private String dbID = "root"; // db아이디
	private String dbPassword = ""; // db비밀번호
	
	private FileInputStream fis;
	private FileInputStream key_fis;

	public DBConnector() {

		try {
			// 암호화된 DB password read
			String propFile = "C:/Users/securityLab_5/eclipse-workspace/IoT/src/util/AESkey.properties";
			Properties props = new Properties();
			fis = new FileInputStream(propFile);
			props.load(new java.io.BufferedInputStream(fis));

			// 암호화에 사용할 키 read
			String read_key = "C:/Users/securityLab_5/Desktop/libraryFolder/key_management/keymanagement.properties";
			Properties key = new Properties();
			key_fis = new FileInputStream(read_key);
			key.load(new java.io.BufferedInputStream(key_fis));

			String aes_key = key.getProperty("key");
			if (aes_key != null) {
				aes = new DBAES(aes_key); // 암호화에 사용할 키 전달
			}

			if (aes != null) {
				dbPassword = aes.aesDecode(props.getProperty("password")); // 복호화
			}

		} catch (FileNotFoundException e) {// 예외처리 ,대응부재 제거
			System.err.println("CommDAO FileNotFoundException error");
		} catch (IOException e) {
			System.err.println("CommDAO IOException error");
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
		} finally { // 자원 해제
			try {
				if (fis != null)
					fis.close(); // 부적절한 자원 해제
				if (key_fis != null)
					key_fis.close();
			} catch (IOException e) {
				System.err.println("DBConnector close IOTException error");
			}

		}

	}

	public Connection getConn() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			return DriverManager.getConnection(dbURL, dbID, dbPassword);
		} catch (SQLException e) {
			System.err.println("DBconnectorIoT Connection error");
			return null;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("DBConnector  ClassNotFoundException error");
			return null;
		}
	}

}
