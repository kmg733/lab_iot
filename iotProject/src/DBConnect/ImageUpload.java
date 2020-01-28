package DBConnect;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImageUpload {
	private static ImageUpload instance = new ImageUpload();
	
	public static ImageUpload getInstance() {
		return instance;
	}
	
	private DBConnector dbc = new DBConnector();	//	DBConnector 객체생성
	private Connection conn;	//	db에 접근하게 해주는 객체
	private String sql = ""; 	//	쿼리1(MariaDB에 들어갈 명령어지문)
	private String sql2 = "";
	private PreparedStatement pstmt;		//	db에 sql문을 전달해주는 객체
	private PreparedStatement pstmt2;
	private ResultSet rs;	//	db에서 쿼리의 실행결과를 가져오는 객체
	private String returns; //메소드 성공 여부 반환
	private int result;
	
	//이미지 파일 참고 - https://aristatait.tistory.com/16?category=672398, https://hks003.tistory.com/11
	public String orgUpload(int number, String fileName) {	//	연구실 조직도 이미지 업로드
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // 데이터베이스 접근을 위한 로그인
			File imgFile = new File(fileName);
			FileInputStream fin = new FileInputStream(imgFile);
			sql = "select * from organization where number=?"; // organization 테이블의 이미지 숫자와 이지미에 접근
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			rs = pstmt.executeQuery();
			if(rs.next()) {	//	이미지가 있을 때
				sql2 = "update organization set organization_image=? where number=?"; // organization 테이블의 이미지 업데이트
				pstmt2 = conn.prepareStatement(sql);
				pstmt2.setBinaryStream(1, fin, (int)imgFile.length());	//	데이터 베이스에 이미지 파일 업로드
				pstmt2.setInt(2, number);
				pstmt2.executeUpdate(); // db에 쿼리문 입력						
			}
			else {	//	이미지가 없을 때
				sql2 = "insert into organization (number, organizaion_image) values (?, ?)"; // organization 테이블의 이미지 넣기
				pstmt2 = conn.prepareStatement(sql);
				pstmt2.setInt(1, number);
				pstmt2.setBinaryStream(2, fin, (int)imgFile.length());	//	데이터 베이스에 이미지 파일 업로드
				pstmt2.executeUpdate(); // db에 쿼리문 입력				
			}			
			returns = "orgUploaded";
		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		}
		finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
		}
		return returns;
	}	

	public String orgDelete(int number) {	//	연구실 조직도 이미지 삭제
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // 데이터베이스 접근을 위한 로그인
			sql = "delete from organization where number=?"; // organization테이블의 number에 해당되는 레코드 삭제
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			pstmt.executeQuery(); // db에 쿼리문 입력
			returns = "orgDeleted";
		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		}
		finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
		}
		return returns;
	}	
	
	public String strUpload(int number, String fileName) {	//	연구실 구성도 이미지 업로드
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // 데이터베이스 접근을 위한 로그인
			File imgFile = new File(fileName);
			FileInputStream fin = new FileInputStream(imgFile);
			sql = "select * from structure where number=?"; // structure 테이블의 이미지 숫자와 이지미에 접근
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			rs = pstmt.executeQuery();
			if(rs.next()) {	//	이미지가 있을 때
				sql2 = "update structure set structure_image=? where number=?"; // structure 테이블의 이미지 업데이트
				pstmt2 = conn.prepareStatement(sql);
				pstmt2.setBinaryStream(1, fin, (int)imgFile.length());	//	데이터 베이스에 이미지 파일 업로드
				pstmt2.setInt(2, number);
				pstmt2.executeUpdate(); // db에 쿼리문 입력						
			}
			else {	//	이미지가 없을 때
				sql2 = "insert into structure (number, structure_image) values (?, ?)"; // structure 테이블의 이미지 넣기
				pstmt2 = conn.prepareStatement(sql);
				pstmt2.setInt(1, number);
				pstmt2.setBinaryStream(2, fin, (int)imgFile.length());	//	데이터 베이스에 이미지 파일 업로드
				pstmt2.executeUpdate(); // db에 쿼리문 입력				
			}	
			returns = "strUploaded";
		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		}
		finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
		}
		return returns;
	}	

	public String strDelete(int number) {	//	연구실 구성도 이미지 삭제
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // 데이터베이스 접근을 위한 로그인
			sql = "delete from structure where number=?"; // structure 테이블의 number에 해당되는 레코드 삭제
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			pstmt.executeQuery(); // db에 쿼리문 입력
			returns = "strDeleted";
		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		}
		finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
		}
		return returns;
	}	
	
	public String ipUpload(int number, String fileName) {	//	연구실 ip 및 출입키 이미지 업로드
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // 데이터베이스 접근을 위한 로그인
			File imgFile = new File(fileName);
			FileInputStream fin = new FileInputStream(imgFile);
			sql = "select * from ip where number=?"; // ip 테이블의 이미지 숫자와 이지미에 접근
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			rs = pstmt.executeQuery();
			if(rs.next()) {	//	이미지가 있을 때
				sql2 = "update ip set ip_image=? where number=?"; // ip 테이블의 이미지 업데이트
				pstmt2 = conn.prepareStatement(sql);
				pstmt2.setBinaryStream(1, fin, (int)imgFile.length());	//	데이터 베이스에 이미지 파일 업로드
				pstmt2.setInt(2, number);
				pstmt2.executeUpdate(); // db에 쿼리문 입력						
			}
			else {	//	이미지가 없을 때
				sql2 = "insert into ip (number, ip_image) values (?, ?)"; // ip 테이블의 이미지 넣기
				pstmt2 = conn.prepareStatement(sql);
				pstmt2.setInt(1, number);
				pstmt2.setBinaryStream(2, fin, (int)imgFile.length());	//	데이터 베이스에 이미지 파일 업로드
				pstmt2.executeUpdate(); // db에 쿼리문 입력				
			}	
			returns = "ipUploaded";
		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		}
		finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
		}
		return returns;
	}	

	public String ipDelete(int number) {	//	연구실 ip 및 출입키 이미지 삭제
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // 데이터베이스 접근을 위한 로그인
			sql = "delete from ip where number=?"; // ip 테이블의 number에 해당되는 레코드 삭제
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			pstmt.executeQuery(); // db에 쿼리문 입력
			returns = "ipDeleted";
		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		}
		finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
		}
		return returns;
	}	

	public String ruleUpload(int number, String fileName) {	//	연구실 규칙 이미지 업로드
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // 데이터베이스 접근을 위한 로그인
			File imgFile = new File(fileName);
			FileInputStream fin = new FileInputStream(imgFile);
			sql = "select * from rule where number=?"; // rule 테이블의 이미지 숫자와 이지미에 접근
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			rs = pstmt.executeQuery();
			if(rs.next()) {	//	이미지가 있을 때
				sql2 = "update rule set role_image=? where number=?"; // rule 테이블의 이미지 업데이트
				pstmt2 = conn.prepareStatement(sql);
				pstmt2.setBinaryStream(1, fin, (int)imgFile.length());	//	데이터 베이스에 이미지 파일 업로드
				pstmt2.setInt(2, number);
				pstmt2.executeUpdate(); // db에 쿼리문 입력						
			}
			else {	//	이미지가 없을 때
				sql2 = "insert into rule (number, role_image) values (?, ?)"; // rule 테이블의 이미지 넣기
				pstmt2 = conn.prepareStatement(sql);
				pstmt2.setInt(1, number);
				pstmt2.setBinaryStream(2, fin, (int)imgFile.length());	//	데이터 베이스에 이미지 파일 업로드
				pstmt2.executeUpdate(); // db에 쿼리문 입력				
			}	
			returns = "ruleUploaded";
		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		}
		finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
		}
		return returns;
	}	

	public String ruleDelete(int number) {	//	연구실 규칙 이미지 삭제
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // 데이터베이스 접근을 위한 로그인
			sql = "delete from rule where number=?"; // rule 테이블의 number에 해당되는 레코드 삭제
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			pstmt.executeQuery(); // db에 쿼리문 입력
			returns = "ipDeleted";
		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		}
		finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
		}
		return returns;
	}
}
