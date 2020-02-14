package DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.mindrot.jbcrypt.BCrypt;

public class Login { // 로그인 회원가입 관리
	private static Login instance = new Login();

	public static Login getInstance() {
		return instance;
	}

	private DBConnector dbc = new DBConnector();
	private Connection conn;
	private String sql = "";
	private String sql2 = "";
	private String sql3 = "";
	private PreparedStatement pstmt;
	private PreparedStatement pstmt2;
	private PreparedStatement pstmt3;
	private ResultSet rs;
	private ResultSet rs2;
	private String returns;
	private Random rn; // 비밀번호를 재발급 받을때 랜덤값을 만들 랜덤함수;
	private String rePwd = "";
	private String securedPwd = "";

	public String adminLogin(String id) { // 관리자 로그인
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			sql = "select * from user where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id); // id에 admin id 넣기
			rs = pstmt.executeQuery();
			if (rs.next()) { // getString(해당 테이블 컬럼의 필드값).equals(메소드 변수)
				returns = rs.getString("password") + " adminSuccess"; // 관리자 로그인 성공
			} else {
				returns = "adminFailed"; // 관리자 로그인 실패
			}
		} catch (Exception e) {
			returns = "error";
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
		return returns;
	}

	public String loginDB(String name, String id) { // 랩실부원 로그인
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // 데이터베이스 접근을 위한 로그인
			sql = "select * from user where id=? and name=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			rs = pstmt.executeQuery();
			if (rs.next()) { // 입력한 id, name이 일치하는 회원이 존재할 때
				returns = rs.getString("password") + " loginSuccess";
			} else { // 로그인 실패
				returns = "loginFailed"; // user 테이블에 정보가 비었을 때
			}
		} catch (Exception e) {
			returns = "error";
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
		return returns;
	}

	public String createAccount(String name, String id, String pwd, String mail) { // 회원가입
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // 데이터베이스 접근을 위한 로그인
			sql = "select * from add_user where id=? and name=?"; // 쿼리구문
			pstmt = conn.prepareStatement(sql); // db에 접근하기 위한 쿼리(sql변수)를 저장
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			rs = pstmt.executeQuery();
			if (rs.next()) { // 회원가입 대상자일 때
				sql2 = "select * from user where id=? and password=? and name=?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, id);
				pstmt2.setString(2, pwd);
				pstmt2.setString(3, name);
				rs2 = pstmt2.executeQuery();
				if (rs2.next()) { // 이미 회원가입이 되어 있을 때
					returns = "acountAleadyExist";
				} else { // 회원정보가 없을 때
					sql3 = "insert into user (id, password, name, mail) values (?, ?, ?, ?)";
					pstmt3 = conn.prepareStatement(sql3);
					pstmt3.setString(1, id);
					pstmt3.setString(2, pwd);
					pstmt3.setString(3, name);
					pstmt3.setString(4, mail);
					pstmt3.executeUpdate();
					returns = "accountCreated";
				}
			} else { // 회원가입 대상자가 아닐때
				returns = "notMember";
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returns = "error";
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
			if (pstmt2 != null)
				try {
					pstmt2.close();
				} catch (SQLException ex) {
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
		}
		return returns;
	}

	public String changePW(String id, String pw, String b_pw) { // 마이페이지에서 비밀번호 수정
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // 데이터베이스 접근을 위한 로그인
			sql = "select * from user where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) { // id가 user테이블에 존재할 때
				if(BCrypt.checkpw(b_pw, rs.getString("password"))) {
					// 현재 비밀번호가 일치할 시
					sql2 = "update user set password=? where id=?"; // 쿼리구문
					pstmt2 = conn.prepareStatement(sql2); // db에 접근하기 위한 쿼리(sql변수)를 저장
					pstmt2.setString(1, pw);
					pstmt2.setString(2, id);
					pstmt2.executeUpdate(); // db에 쿼리문 입력
					returns = "pwdChangeSuccess";
				}
				else {
					returns = "pwdChangeFailed";
				}
			} else { // id가 user테이블에 존재하지 않을 때
				returns = "idNotExist";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returns = "error";
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
			if (pstmt2 != null)
				try {
					pstmt2.close();
				} catch (SQLException ex) {
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
		}
		return returns;
	}

	public String findPW(String name, String id, String mail) { // 비밀번호 재발급
		try {
			rn = new Random();
			rn.setSeed(System.currentTimeMillis()); // 같은 난수 발생 방지를 위한 시드설정

			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // 데이터베이스 접근을 위한 로그인
			sql = "select * from user where id=? and name=? and mail=?"; // 쿼리구문
			pstmt = conn.prepareStatement(sql); // db에 접근하기 위한 쿼리(sql변수)를 저장
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			pstmt.setString(3, mail);
			rs = pstmt.executeQuery(); // db에 쿼리문 입력
			if (rs.next()) { // user 데이터베이스에 해당 id, name, mail을 가진 데이터가 있을 때
				rePwd = "security" + rn.nextInt(1000) + "!!";
				securedPwd = BCrypt.hashpw(rePwd, BCrypt.gensalt()); // 새로 생성한 비밀번호 암호화

				// 새로 초기화한 비밀번호를 암호화해서 데이터베이스에 저장
				sql2 = "update user set password=? where id=? and name=?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, securedPwd);
				pstmt2.setString(2, id);
				pstmt2.setString(3, name);
				pstmt2.executeUpdate();

				// 이메일로 보내는 코드
				EmailInfo em = new EmailInfo();

				String host = "smtp.naver.com";
				final String username = em.getEmailId(); // 보낼 id
				final String password = em.getEmailPwd(); // 보낼 id의 pwd
				int port = 465; // 네이버전용
//				int port = 587; //구글전용
				// 메일 내용

				String recipient = ""; // 받는 사람의 메일주소를 입력해주세요.
				recipient = rs.getString("mail");
				System.out.println("mail = " + recipient);
				String subject = "변경된 비밀번호 입니다."; // 메일 제목 입력해주세요.
				String body = "비밀번호는 " + rePwd + " 입니다."; // 메일 내용 입력해주세요.
				Properties props = System.getProperties(); // 정보를 담기 위한 객체 생성
				// SMTP 서버 정보 설정
				props.put("mail.smtp.host", host);
				props.put("mail.smtp.port", port);
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.ssl.enable", "true");
				props.put("mail.smtp.ssl.trust", host);

				// Session 생성
				Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
					String un = username;
					String pw = password;

					protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
						return new javax.mail.PasswordAuthentication(un, pw);
					}
				});
				session.setDebug(true); // for debug
				Message mimeMessage = new MimeMessage(session);
				// MimeMessage 생성
				try {
					mimeMessage.setFrom(new InternetAddress(em.getEmailId() + "@naver.com"));
					// 발신자 셋팅 , 보내는 사람의 이메일주소를 한번 더 입력합니다. 이때는 이메일 풀 주소를 다 작성해주세요.
					mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
					// 수신자셋팅 //.TO 외에 .CC(참조) .BCC(숨은참조) 도 있음
					mimeMessage.setSubject(subject); // 제목셋팅
					mimeMessage.setText(body); // 내용셋팅
					Transport.send(mimeMessage); // javax.mail.Transport.send() 이용
				} catch (Exception e) {
					e.printStackTrace();
					returns = "emailSendFailed";
				}
				returns = "emailSendSuccess";
			} else { // user 데이터베이스에 해당 데이터가 없을 때
				returns = "userNotExist";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returns = "error";
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
			if (pstmt2 != null)
				try {
					pstmt2.close();
				} catch (SQLException ex) {
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
		}
		return returns;
	}

}
