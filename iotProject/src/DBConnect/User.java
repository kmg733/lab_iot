package DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class User {
	private static User instance = new User();
	
	public static User getInstance() {
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
	private ResultSet rs2;
	private StringBuilder returnb;
	private String returns; //메소드 성공 여부 반환
	private Random rn;	//	비밀번호를 재발급 받을때 랜덤값을 만들 랜덤함수;
	private int tempPW;
	
	/*
	public String user_Add( String id, String name, String pwd) {	//	사용자가 회원 가입할 때
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	데이터베이스 접근을 위한 로그인 
			sql = "select * from add_user where id=? and name=?"; 	//	user 테이블에 회원정보 넣기
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			rs = pstmt.executeQuery();	//	db에 쿼리문 입력
			if(rs.next()) {	//	add_user테이블에 id가 존재할 때
				sql2 = "select * from user where id=?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, id);
				rs = pstmt2.executeQuery();
				if(rs.next()) {	//	user테이블에 id가 존재할 때
					returns = "user_AlreadExist";
				}
				else {	//	user테이블에 id가 존재하지 않을 때
					sql3 = "insert into user (id, password, name) values (?, ?, ?)"; 	//	user 테이블에 회원정보 넣기
					pstmt3 = conn.prepareStatement(sql3);
					pstmt3.setString(1, id);
					pstmt3.setString(2, pwd);
					pstmt3.setString(3, name);
					pstmt3.executeUpdate();	//	db에 쿼리문 입력		
					returns = "user_AddSuccess";
				}
			}
			else {	//	해당 회원정보가 존재하지 않을 때 혹은 잘못 입력했을 때
				returns = "idOrNameNotExist";
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
	*/
	
	/* 관리자 - 회원 정보 관리 */	
	
	public String user_List() {	//	회원 정보 리스트 - user 테이블 리스트
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	데이터베이스 접근을 위한 로그인 
			sql = "select * from user"; 	//	user테이블로부터 모든 정보를 불러오기
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
	
	public String addUser_List() {	//	회원 정보 리스트 - add_user 테이블 리스트
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	데이터베이스 접근을 위한 로그인 
			sql = "select * from add_user"; 	//	user테이블로부터 모든 정보를 불러오기
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
			returns = "error " + e;
		}
		finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
		}
		return returns;
	}
	
	public String addUser_Add(String name, String id) {	//	관리자가 add_user 테이블에 회원 정보를 추가할 때
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			sql = "select * from add_user where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				returns = "IDAleadyExist";
			}
			else {
				sql2 = "insert into add_user (id, name) values (?, ?)";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, id);
				pstmt2.setString(2, name);	
				pstmt2.executeUpdate();
				returns = "addUser_addSuccess";
			}
		} catch(Exception e) {
			e.printStackTrace();
			returns = ""+e;
		}		
		return returns;
		
	}
	
	public String user_Delete(String name, String id) {	//	회원 정보 삭제 - user테이블만
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // 데이터베이스 접근을 위한 로그인
			sql = "select * from user where id=? and name=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			rs = pstmt.executeQuery();
			if(rs.next()) {	//	user테이블에 id와 name이 존재하는지 확인
				sql2 = "delete from user where id=? and name=?"; // user 테이블에 id와 name에 해당되는 레코드 삭제
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, id);
				pstmt2.setString(2, name);
				pstmt2.executeUpdate(); // db에 쿼리문 입력
				returns = "user_deleted";				
			}
			else {
				returns = "user_valueNotExistToDelete";
			}
			
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
	
	public String addUser_Delete(String name, String id) {	//	회원 정보 삭제 - user테이블과 add_user테이블 둘다
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // 데이터베이스 접근을 위한 로그인
			sql = "select * from user where id=? and name=?"; // user 테이블에 id와 name에 해당되는 레코드 삭제
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			rs = pstmt.executeQuery(); // db에 쿼리문 입력
			if(rs.next()) {	//	user테이블에 정보가 존재할 때
				sql2 = "delete from user where id=? and name=?"; // user 테이블에 id와 name에 해당되는 레코드 삭제
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, id);
				pstmt2.setString(2, name);
				pstmt2.executeUpdate(); // db에 쿼리문 입력
				
				sql3 = "delete from add_user where id=? and name=?";	//	add_user 테이블에 id와 name에 해당되는 레코드 삭제
				pstmt3 = conn.prepareStatement(sql2);
				pstmt3.setString(1, id);
				pstmt3.setString(2, name);
				pstmt3.executeUpdate(); // db에 쿼리문 입력
				returns ="deleteAllSuccess";
			}
			else {	//	user테이블에 정보가 존재하지 않을 때
				sql2 = "select * from add_user where id=? and name=?"; // user 테이블에 id와 name에 해당되는 레코드 찾기
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, id);
				pstmt2.setString(2, name);
				rs2 = pstmt2.executeQuery(); // db에 쿼리문 입력
				if(rs2.next()) {	//	add_user테이블에 정보가 존재할 때
					sql3 = "delete from add_user where id=? and name=?";	//	add_user 테이블에 id와 name에 해당되는 레코드 삭제
					pstmt3 = conn.prepareStatement(sql3);
					pstmt3.setString(1, id);
					pstmt3.setString(2, name);
					pstmt3.executeUpdate(); // db에 쿼리문 입력
					returns ="deleteAllSuccess2";
				}
				else {	//	add_user테이블에 정보가 존재하지 않을 때
					returns = "addUserDataNotExist";
				}
			}			
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
	
	/* 공통(수정) */
	public String user_Modify(String before_name, String before_id ,String after_name, String after_id, String after_password) {	//	add_user테이블과 user테이블 모두 수정 가능
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // 데이터베이스 접근을 위한 로그인
			sql = "select * from add_user where id=? and name=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, before_id);
			pstmt.setString(2, before_name);
			rs = pstmt.executeQuery();
			if(rs.next()) {	//	add_user테이블에 수정할 id와 name이 존재할 때				
				if(user_Delete(before_id, before_name).equals("user_Deleted")) { // user테이블 에도 수정할 id와 name이 존재할 때
					conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
					sql2 = "update add_user set id=?, name=? where id=? and name=?";
					pstmt2 = conn.prepareStatement(sql2);
					pstmt2.setString(1, after_id);
					pstmt2.setString(2, after_name);
					pstmt2.setString(3, before_id);
					pstmt2.setString(4, before_name);
					pstmt2.executeUpdate();	
					
					sql3 = "insert into user (id, password, name) values (?, ?, ?)";
					pstmt3 = conn.prepareStatement(sql3);
					pstmt3.setString(1, after_id);					
					pstmt3.setString(2, after_password);
					pstmt3.setString(3, after_name);
					pstmt3.executeUpdate();
					returns = "user_modified";
				}
				else {	//	add_user테이블에만 수정할 id와 name이 존재할 때
					conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
					sql2 = "update add_user set id=?, name=? where id=? and name=?";
					pstmt2 = conn.prepareStatement(sql2);
					pstmt2.setString(1, after_id);
					pstmt2.setString(2, after_name);
					pstmt2.setString(3, before_id);
					pstmt2.setString(4, before_name);
					pstmt2.executeUpdate();
					returns = "user_modified2";
				}
			}
			else {	//	add_user테이블에 수정할 id혹은 name이 존재하지 않을 때
				returns = "user_valueNotExist";
			}
			
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
