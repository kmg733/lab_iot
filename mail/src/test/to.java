package test;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class to {
	public static void main(String[] args) {

		testing ta = new testing();
		String host = "smtp.naver.com";
		final String username = ta.id;
		// ���̹� ���̵� �Է����ּ���. @nave.com�� �Է����� ���ñ���.
		final String password = ta.pw;
		// ���̹� �̸��� ��й�ȣ�� �Է����ּ���.
		int port = 465; //���̹�����
//		int port = 587; //��������
		// ��Ʈ��ȣ // ���� ����
		String recipient = "zwsdkd12358@gmail.com"; // �޴� ����� �����ּҸ� �Է����ּ���.
		String subject = "�����׽�Ʈ"; // ���� ���� �Է����ּ���.
		String body = "��й�ȣ�� rlaalsrb733 �Դϴ�."; // ���� ���� �Է����ּ���.
		
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
			mimeMessage.setFrom(new InternetAddress(ta.id+"@naver.com"));
			// �߽��� ���� , ������ ����� �̸����ּҸ� �ѹ� �� �Է��մϴ�. �̶��� �̸��� Ǯ �ּҸ� �� �ۼ����ּ���.
			mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			// �����ڼ��� //.TO �ܿ� .CC(����) .BCC(��������) �� ����
			mimeMessage.setSubject(subject); // �������
			mimeMessage.setText(body); // �������
			Transport.send(mimeMessage); // javax.mail.Transport.send() �̿�
		}	catch(Exception e) {
			e.printStackTrace();
		}
	}
}
