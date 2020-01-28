package DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class Login {
	private static Login instance = new Login();
		
	public static Login getInstance() {
		return instance;
	}
	
	private DBConnector dbc = new DBConnector();//	DBConnector 객체생성
	private Connection conn;	//	db에 접근하게 해주는 객체
	private String sql = ""; 	//	쿼리1(MariaDB에 들어갈 명령어지문)
	private String sql2 = "";
	private PreparedStatement pstmt;		//	db에 sql문을 전달해주는 객체
	private PreparedStatement pstmt2;
	private ResultSet rs;	//	db에서 쿼리의 실행결과를 가져오는 객체
	private String returns; //메소드 성공 여부 반환
	private Random rn;	//	비밀번호를 재발급 받을때 랜덤값을 만들 랜덤함수;
	private int tempPW;
	
	public String adminLogin(String name, String id, String pwd) { //	관리자 로그인
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	데이터베이스 접근을 위한 로그인
			sql = "select * from user where id=? and password=? and name=?"; //	Admin 계정이 user테이블에 id가 들어있는지 확인하는 쿼리구문
			pstmt = conn.prepareStatement(sql);	//	db에 접근하기 위한 쿼리(sql변수)를 저장
			pstmt.setString(1, id);	//	첫번째 ?에  매개변수 id입력
			pstmt.setString(2, pwd);	//	두번째 ?에 매개변수 pwd입력
			pstmt.setString(3, name);	//	세번째 ?에 매개변수 name입력
			rs = pstmt.executeQuery();	//	db에 쿼리문 입력
			if (rs.next()) {	//	getString(해당 테이블 컬럼의 필드값).equals(메소드 변수)
				if (rs.getString("id").equals(id) && rs.getString("pasword").equals(pwd) && rs.getString("name").equals(name)) {	//	user 테이블에 관리가 name, id, pwd가 있는가?
					returns = "adminSuccess";	// 관리자 로그인 가능
				}
				else {
					returns = "adminFail"; 	// 관리자 로그인 실패
				}
			}
			else {
				returns = "tableEmpty";	// user 테이블에 정보가 비었을 때
			}
		}
		catch (Exception e) {
			returns = "error";
		}
		finally {
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
			if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
		}
		return returns;
	}
	
	public String loginDB(String name, String id, String pwd) {	//	랩실부원 로그인
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	데이터베이스 접근을 위한 로그인
			sql = "select * from user where id=? and password=? and name=?"; 
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			pstmt.setString(3, name);
			rs = pstmt.executeQuery();	//	db에 쿼리문 입력
			if (rs.next()) {	//	getString(해당 테이블 컬럼의 필드값).equals(메소드 변수)
				if (rs.getString("id").equals(id) && rs.getString("pasword").equals(pwd) && rs.getString("name").equals(name)) {
					returns = "loginSuccess";	// 일반 로그인 가능
				}
				else {
					returns = "loginFail";	// 일반 로그인 실패
				}
			}
			else {
				returns = "emptyTable"; // user 테이블에 정보가 비었을 때
			}
		}
		catch (Exception e) {
			returns = "error";
		}
		finally {
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
			if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
		}
		return returns;
	}
	
	public String createAccount(String name, String id, String pwd) {	//	회원가입
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	데이터베이스 접근을 위한 로그인
			sql = "select id from user where id=?";	//	쿼리구문
			pstmt = conn.prepareStatement(sql);	//	db에 접근하기 위한 쿼리(sql변수)를 저장
			pstmt.setString(1, id);	// ?에 id변수를 넣음
			rs = pstmt.executeQuery();	//	db에 쿼리문 입력		
			if(rs.next()){	//	회원가입 대상자일 때
				if(rs.getString("name").equals(name)) {	//	회원정보가 있을 때
					returns = "userAleadyExist";
				}
				else {	//	회원정보가 없을 때
					sql2 = "insert into user (id, password, name) values (?, ?, ?)";
					pstmt2 = conn.prepareStatement(sql2);
					pstmt2.setString(1, id);
					pstmt2.setString(2, pwd);
					pstmt2.setString(3, name);
					pstmt2.executeUpdate();	//	db에 쿼리문 실행
					returns = "userCreatComplete";
				}
			}			
			else { 	//	회원가입 대상자가 아닐때
				returns = "notMember";
			}

		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returns = "userDataNotSaved";
		}
		finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
			if (pstmt2 != null)try {pstmt2.close();} catch (SQLException ex) {}
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
		}
		return returns;
	}
	
	//주민번호를 저장하는 테이블이 없음 <- 전화번호로 대체함, 재발급이 아니라 pw를 보여주는 형식으로 구현함
	public String findPW(String id, String name, String phoneNum) {	//	비밀번호 재발급
		try {
			rn = new Random();
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	데이터베이스 접근을 위한 로그인
			sql = "select * from member where id=? and name=? and phone=?";	//	쿼리구문
			pstmt = conn.prepareStatement(sql);	//	db에 접근하기 위한 쿼리(sql변수)를 저장
			pstmt.setString(1, id);		
			pstmt.setString(2, name);		
			pstmt.setString(3, phoneNum);		
			rs = pstmt.executeQuery();	//	db에 쿼리문 입력	
			if(rs.next()) {	//	member 데이터베이스에 해당 id, name, phoneNum을 가진 데이터가 있을 때
				tempPW = rn.nextInt(100);
				sql2 = "update user set password=? where id=?";	//	쿼리구문
				pstmt2 = conn.prepareStatement(sql2);	//	db에 접그하기 위한 쿼리(sql2변수)를 저장
				pstmt2.setString(1, "security" + tempPW);	//	임시비밀번호를 securityXX로 설정
				pstmt2.setString(2, id);
				pstmt2.executeUpdate();		//	쿼리문을 업데이트함
				returns = rs.getString("password");
			}
			else {	//	member 데이터베이스에 해당 데이터가 없을 때
				returns = "memberNotExist";
			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
			if (pstmt2 != null)try {pstmt2.close();} catch (SQLException ex) {}
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
		}
		return returns;
	}
	
	public String modifyPW(String id ,String pw) {	//	마이페이지에서 비밀번호 수정
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	데이터베이스 접근을 위한 로그인
			sql = "update user set password=? where id=?";	//	쿼리구문
			pstmt = conn.prepareStatement(sql);	//	db에 접근하기 위한 쿼리(sql변수)를 저장
			pstmt.setString(1, pw);	
			pstmt.setString(2, id);	
			pstmt.executeUpdate();	//	db에 쿼리문 입력	
			returns = "modifySuccess";
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returns = "userDataNotFound";
		}
		finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
			if (pstmt2 != null)try {pstmt2.close();} catch (SQLException ex) {}
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
		}
		return returns;
	}
	
	
}
