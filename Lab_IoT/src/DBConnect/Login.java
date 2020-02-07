package DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import org.mindrot.jbcrypt.BCrypt;

public class Login {	//	로그인 회원가입 관리
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
	private Random rn;	//	비밀번호를 재발급 받을때 랜덤값을 만들 랜덤함수;
	private int tempPW;	
		
	public String adminLogin(String name, String id, String pwd) { //	관리자 로그인
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			sql = "select * from user where id=? and password=? and name=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);	//	id에 admin id 넣기
			pstmt.setString(2, pwd);	// pwd에 admin pwd 넣기
			pstmt.setString(3, name);	//	name에 admin name 넣기
			rs = pstmt.executeQuery();
			if (rs.next()) {	//	getString(해당 테이블 컬럼의 필드값).equals(메소드 변수)

				if (rs.getString("id").equals(id) && rs.getString("password").equals(pwd) && rs.getString("name").equals(name)) {	//	user 테이블에 관리가 name, id, pwd가 있는가?
					returns = "adminSuccess";	// 관리자 로그인 성공
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
	
	public String loginDB(String name, String id) {	//	랩실부원 로그인
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	데이터베이스 접근을 위한 로그인
			sql = "select * from user where id=? and name=?"; 
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			rs = pstmt.executeQuery();
			if (rs.next()) {	//	입력한 id, name이 일치하는 회원이 존재할 때
				returns = rs.getString("password") +" "+ "loginSuccess";
			}
			else {	//	로그인 실패
				returns = "0 loginFailed"; // user 테이블에 정보가 비었을 때
			}
		}
		catch (Exception e) {
			returns = "0 error";
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
			sql = "select * from add_user where id=? and name=?";	//	쿼리구문
			pstmt = conn.prepareStatement(sql);	//	db에 접근하기 위한 쿼리(sql변수)를 저장
			pstmt.setString(1, id);	
			pstmt.setString(2, name);	
			rs = pstmt.executeQuery();
			if(rs.next()){	//	회원가입 대상자일 때				
				sql2 = "select * from user where id=? and password=? and name=?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, id);
				pstmt2.setString(2, pwd);
				pstmt2.setString(3, name);
				rs2 = pstmt2.executeQuery();	
				if(rs2.next()) {	//	이미 회원가입이 되어 있을 때
					returns = "acountAleadyExist";										
				}
				else {	//	회원정보가 없을 때
					sql3 = "insert into user (id, password, name) values (?, ?, ?)";
					pstmt3 = conn.prepareStatement(sql3);
					pstmt3.setString(1, id);
					pstmt3.setString(2, pwd);
					pstmt3.setString(3, name);
					pstmt3.executeUpdate();	
					returns = "accountCreated";
				}
			}			
			else { 	//	회원가입 대상자가 아닐때
				returns = "notMember";
			}

		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returns = "error";
		}
		finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
			if (pstmt2 != null)try {pstmt2.close();} catch (SQLException ex) {}
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
		}
		return returns;
	}
	
	public String changePwd(String id ,String pwd, String b_pwd) {	//	마이페이지에서 비밀번호 수정
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	데이터베이스 접근을 위한 로그인
			/* 이부분에 복호화 코드 넣기  */			
			sql = "select password from user where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {	//	id가 user테이블에 존재할 때				
				if(BCrypt.checkpw(b_pwd, rs.getString("password"))) {	//	입력한 비밀번호가 현재 비밀번호와 일치 할 때
					sql2 = "update user set password=?";	//	쿼리구문
					pstmt2 = conn.prepareStatement(sql2);	//	db에 접근하기 위한 쿼리(sql변수)를 저장
					pstmt2.setString(1, pwd);	
					pstmt2.executeUpdate();	//	db에 쿼리문 입력	
					returns = "pwdChangeSuccess";
				}	
				else {	//	현재 패스워드가 일치하지 않을 때
					returns = "pwdChangeFailed";
				}
			}
			else {	//	id가 user테이블에 존재하지 않을 때
				returns = "idNotExist";
			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returns = "error";
		}
		finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
			if (pstmt2 != null)try {pstmt2.close();} catch (SQLException ex) {}
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
		}
		return returns;
	}
	
	//주민번호를 저장하는 테이블이 없음 <- 전화번호로 대체함, 재발급이 아니라 pw를 보여주는 형식으로 구현함 <<
	public String findPwd(String name, String id, String phoneNum) {	//	비밀번호 재발급
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
	
	
	
	
}
