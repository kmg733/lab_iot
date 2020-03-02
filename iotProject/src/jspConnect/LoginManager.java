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
	
	public LoginManager(PropLoad pl) {
		this.pl = pl;
	}
	
	public String adminLoginCheck(String id) {
		try {
			this.id = DataAES.aesDecryption(id, pl.getSecurityKey());
					
			//Cross-site Script Check
			if(id != null) {
				XSS xss = new XSS();
				id = xss.prevention(id);
			} else {
				id = "";
			}	
			
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
		
		return result;
	}
	
	public String loginCheck(String id, String name) {
		try {
			this.id = DataAES.aesDecryption(id, pl.getSecurityKey());
			this.name = DataAES.aesDecryption(name, pl.getSecurityKey());
			
			//Cross-site Script Check
			if(id != null) {
				XSS xss = new XSS();
				id = xss.prevention(id);
			} else {
				id = "";
			}	
			if(name != null) {
				XSS xss = new XSS();
				name = xss.prevention(name);			
			} else {
				name = "";
			}	
			
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
	
	public String joinCheck(String id, String name, String pwd, String mail) {
		try {
			this.id = DataAES.aesDecryption(id, pl.getSecurityKey());
			this.name = DataAES.aesDecryption(name, pl.getSecurityKey());
			this.pwd = DataAES.aesDecryption(pwd, pl.getSecurityKey());
			this.mail = DataAES.aesDecryption(mail, pl.getSecurityKey());

			
			//Cross-site Script Check
			if(id != null) {
				XSS xss = new XSS();
				id = xss.prevention(id);
			} else {
				id = "";
			}	
			if(name != null) {
				XSS xss = new XSS();
				name = xss.prevention(name);			
			} else {
				name = "";
			}	
			if(pwd != null) {
				XSS xss = new XSS();
				pwd = xss.prevention(pwd);
			} else {
				pwd = "";
			}	
			if(mail != null) {
				XSS xss = new XSS();
				mail = xss.prevention(mail);
			} else {
				mail = "";
			}	
			
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
		
		return result;
	}
	
	public String findCheck(String id, String name, String mail) {
		try {
			this.id = DataAES.aesDecryption(id, pl.getSecurityKey());
			this.name = DataAES.aesDecryption(name, pl.getSecurityKey());
			this.mail = DataAES.aesDecryption(mail, pl.getSecurityKey());

			
			//Cross-site Script Check
			if(id != null) {
				XSS xss = new XSS();
				id = xss.prevention(id);
			} else {
				id = "";
			}	
			if(name != null) {
				XSS xss = new XSS();
				name = xss.prevention(name);			
			} else {
				name = "";
			}	
			if(mail != null) {
				XSS xss = new XSS();
				mail = xss.prevention(mail);
			} else {
				mail = "";
			}	
			
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
		
		return result;
	}
	
	public String changeCheck(String id, String pwd, String b_pwd) {
		try {
			this.id = DataAES.aesDecryption(id, pl.getSecurityKey());
			this.b_pwd = DataAES.aesDecryption(b_pwd, pl.getSecurityKey());
			this.pwd = DataAES.aesDecryption(pwd, pl.getSecurityKey());

			
			//Cross-site Script Check
			if(id != null) {
				XSS xss = new XSS();
				id = xss.prevention(id);
			} else {
				id = "";
			}	
			if(b_pwd != null) {
				XSS xss = new XSS();
				b_pwd = xss.prevention(b_pwd);			
			} else {
				b_pwd = "";
			}	
			if(pwd != null) {
				XSS xss = new XSS();
				pwd = xss.prevention(pwd);
			} else {
				pwd = "";
			}
			
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
		
		return result;
	}
	
	public String loginError() {
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

		return result;
	}
}
