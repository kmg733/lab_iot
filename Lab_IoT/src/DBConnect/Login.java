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

public class Login { // �α��� ȸ������ ����
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
	private Random rn; // ��й�ȣ�� ��߱� ������ �������� ���� �����Լ�;
	private String rePwd = "";
	private String securedPwd = "";

	public String adminLogin(String id) { // ������ �α���
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			sql = "select * from user where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id); // id�� admin id �ֱ�
			rs = pstmt.executeQuery();
			if (rs.next()) { // getString(�ش� ���̺� �÷��� �ʵ尪).equals(�޼ҵ� ����)
				returns = rs.getString("password") + " adminSuccess"; // ������ �α��� ����
			} else {
				returns = "adminFailed"; // ������ �α��� ����
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

	public String loginDB(String name, String id) { // ���Ǻο� �α���
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // �����ͺ��̽� ������ ���� �α���
			sql = "select * from user where id=? and name=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			rs = pstmt.executeQuery();
			if (rs.next()) { // �Է��� id, name�� ��ġ�ϴ� ȸ���� ������ ��
				returns = rs.getString("password") + " loginSuccess";
			} else { // �α��� ����
				returns = "loginFailed"; // user ���̺� ������ ����� ��
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

	public String createAccount(String name, String id, String pwd, String mail) { // ȸ������
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // �����ͺ��̽� ������ ���� �α���
			sql = "select * from add_user where id=? and name=?"; // ��������
			pstmt = conn.prepareStatement(sql); // db�� �����ϱ� ���� ����(sql����)�� ����
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			rs = pstmt.executeQuery();
			if (rs.next()) { // ȸ������ ������� ��
				sql2 = "select * from user where id=? and password=? and name=?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, id);
				pstmt2.setString(2, pwd);
				pstmt2.setString(3, name);
				rs2 = pstmt2.executeQuery();
				if (rs2.next()) { // �̹� ȸ�������� �Ǿ� ���� ��
					returns = "acountAleadyExist";
				} else { // ȸ�������� ���� ��
					sql3 = "insert into user (id, password, name, mail) values (?, ?, ?, ?)";
					pstmt3 = conn.prepareStatement(sql3);
					pstmt3.setString(1, id);
					pstmt3.setString(2, pwd);
					pstmt3.setString(3, name);
					pstmt3.setString(4, mail);
					pstmt3.executeUpdate();
					returns = "accountCreated";
				}
			} else { // ȸ������ ����ڰ� �ƴҶ�
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

	public String changePW(String id, String pw, String b_pw) { // �������������� ��й�ȣ ����
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // �����ͺ��̽� ������ ���� �α���
			sql = "select * from user where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) { // id�� user���̺� ������ ��
				if(BCrypt.checkpw(b_pw, rs.getString("password"))) {
					// ���� ��й�ȣ�� ��ġ�� ��
					sql2 = "update user set password=? where id=?"; // ��������
					pstmt2 = conn.prepareStatement(sql2); // db�� �����ϱ� ���� ����(sql����)�� ����
					pstmt2.setString(1, pw);
					pstmt2.setString(2, id);
					pstmt2.executeUpdate(); // db�� ������ �Է�
					returns = "pwdChangeSuccess";
				}
				else {
					returns = "pwdChangeFailed";
				}
			} else { // id�� user���̺� �������� ���� ��
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

	public String findPW(String name, String id, String mail) { // ��й�ȣ ��߱�
		try {
			rn = new Random();
			rn.setSeed(System.currentTimeMillis()); // ���� ���� �߻� ������ ���� �õ弳��

			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // �����ͺ��̽� ������ ���� �α���
			sql = "select * from user where id=? and name=? and mail=?"; // ��������
			pstmt = conn.prepareStatement(sql); // db�� �����ϱ� ���� ����(sql����)�� ����
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			pstmt.setString(3, mail);
			rs = pstmt.executeQuery(); // db�� ������ �Է�
			if (rs.next()) { // user �����ͺ��̽��� �ش� id, name, mail�� ���� �����Ͱ� ���� ��
				rePwd = "security" + rn.nextInt(1000) + "!!";
				securedPwd = BCrypt.hashpw(rePwd, BCrypt.gensalt()); // ���� ������ ��й�ȣ ��ȣȭ

				// ���� �ʱ�ȭ�� ��й�ȣ�� ��ȣȭ�ؼ� �����ͺ��̽��� ����
				sql2 = "update user set password=? where id=? and name=?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, securedPwd);
				pstmt2.setString(2, id);
				pstmt2.setString(3, name);
				pstmt2.executeUpdate();

				// �̸��Ϸ� ������ �ڵ�
				EmailInfo em = new EmailInfo();

				String host = "smtp.naver.com";
				final String username = em.getEmailId(); // ���� id
				final String password = em.getEmailPwd(); // ���� id�� pwd
				int port = 465; // ���̹�����
//				int port = 587; //��������
				// ���� ����

				String recipient = ""; // �޴� ����� �����ּҸ� �Է����ּ���.
				recipient = rs.getString("mail");
				System.out.println("mail = " + recipient);
				String subject = "����� ��й�ȣ �Դϴ�."; // ���� ���� �Է����ּ���.
				String body = "��й�ȣ�� " + rePwd + " �Դϴ�."; // ���� ���� �Է����ּ���.
				Properties props = System.getProperties(); // ������ ��� ���� ��ü ����
				// SMTP ���� ���� ����
				props.put("mail.smtp.host", host);
				props.put("mail.smtp.port", port);
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.ssl.enable", "true");
				props.put("mail.smtp.ssl.trust", host);

				// Session ����
				Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
					String un = username;
					String pw = password;

					protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
						return new javax.mail.PasswordAuthentication(un, pw);
					}
				});
				session.setDebug(true); // for debug
				Message mimeMessage = new MimeMessage(session);
				// MimeMessage ����
				try {
					mimeMessage.setFrom(new InternetAddress(em.getEmailId() + "@naver.com"));
					// �߽��� ���� , ������ ����� �̸����ּҸ� �ѹ� �� �Է��մϴ�. �̶��� �̸��� Ǯ �ּҸ� �� �ۼ����ּ���.
					mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
					// �����ڼ��� //.TO �ܿ� .CC(����) .BCC(��������) �� ����
					mimeMessage.setSubject(subject); // �������
					mimeMessage.setText(body); // �������
					Transport.send(mimeMessage); // javax.mail.Transport.send() �̿�
				} catch (Exception e) {
					e.printStackTrace();
					returns = "emailSendFailed";
				}
				returns = "emailSendSuccess";
			} else { // user �����ͺ��̽��� �ش� �����Ͱ� ���� ��
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
