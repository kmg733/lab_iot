package DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class UserInfo {
	private static UserInfo instance = new UserInfo();
	
	public static UserInfo getInstance() {
		return instance;
	}
	
	private DBConnector dbc = new DBConnector();	//	DBConnector 객체생성
	private Connection conn;	//	db에 접근하게 해주는 객체
	private String sql = ""; 	//	쿼리1(MariaDB에 들어갈 명령어지문)
	private String sql2 = "";
	private String sql3 = "";
	private PreparedStatement pstmt;		//	db에 sql문을 전달해주는 객체
	private PreparedStatement pstmt2;
	private PreparedStatement pstmt3;
	private ResultSet rs;	//	db에서 쿼리의 실행결과를 가져오는 객체
	private StringBuilder returnb;
	private String returns; //메소드 성공 여부 반환
	private Random rn;	//	비밀번호를 재발급 받을때 랜덤값을 만들 랜덤함수;
	private int tempPW;
	
	public String userList() {	//	회원 정보 리스트
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	데이터베이스 접근을 위한 로그인 
			sql = "selecte * from user"; 	//	user테이블로부터 모든 정보를 불러오기
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();	//	db에 쿼리문 입력
			returnb = new StringBuilder("");
			while(rs.next()) {
				returnb.append("{name:"+rs.getString("name")+",id:" +rs.getString("id")+"}");	//	회원 리스트에 표시할 이름과 id를 가져옴, json데이터 형태로 보낸다
				//https://freegae.tistory.com/5  (참고하기 - json데이터)
			}
			returns = returnb.toString();
		}
		catch(Exception e) {
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
	
	public String userAdd( String id, String name, String pwd) {	//	회원 등록
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	데이터베이스 접근을 위한 로그인 
			sql = "select * from add_user where id=?"; 	//	user 테이블에 회원정보 넣기
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();	//	db에 쿼리문 입력
			if(rs.next()) {	//	add-user테이블에 id가 존재할 때
				sql2 = "select * from user where id=?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, id);
				rs = pstmt2.executeQuery();
				if(rs.next()) {
					returns = "userAlreadExist";
				}
				else {
					sql3 = "insert into user (id, password, name) values (?, ?, ?)"; 	//	user 테이블에 회원정보 넣기
					pstmt3 = conn.prepareStatement(sql3);
					pstmt3.setString(1, id);
					pstmt3.setString(2, pwd);
					pstmt3.setString(3, name);
					pstmt3.executeUpdate();	//	db에 쿼리문 입력		
					returns = "userAddSuccess";
				}
			}
			else {	//	해당 회원정보가 존재하지 않을 때
				returns = "IDnotExist";
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			returns = ""+e;
		}
		finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
		}
		return returns;
	}
	
	public String userModify(String before_name, String before_id ,String after_name, String after_id) {	//	회원 정보 수정 - 수정을 위해 이전 id와 name을 받아와야함
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // 데이터베이스 접근을 위한 로그인
			sql = "update user set id=?, name=? where id=? and name=?"; // user 테이블에 id와 name이 일치하는 회원정보를 업데이트함
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, after_id);
			pstmt.setString(2, after_name);
			pstmt.setString(3, before_id);
			pstmt.setString(4, before_name);			
			pstmt.executeUpdate(); // db에 쿼리문 입력
			returns = "userModified";
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
	
	public String userDelete(String name, String id) {	//	회원 정보 삭제
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // 데이터베이스 접근을 위한 로그인
			sql = "delete from user where id=? and name=?"; // user 테이블에 id와 name에 해당되는 레코드 삭제
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			pstmt.executeUpdate(); // db에 쿼리문 입력
			returns = "userDeleted";
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
