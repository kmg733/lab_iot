package DBConnect;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class ImageUpload { // 이미지 관련 업로드
	private static ImageUpload instance = new ImageUpload();
	
	public static ImageUpload getInstance( ) {
		return instance;
	}

	private DBConnector dbc = new DBConnector(); // DBConnector 객체생성
	private Connection conn;
	private String sql = "";
	private String sql2 = "";
	private PreparedStatement pstmt;
	private PreparedStatement pstmt2;
	private ResultSet rs;
	private String returns;
	private int result;

	private int max = 1024 * 1024 * 10; // 파일크기 제한 - 10MB
	private String originFile = ""; // 업로드할 실제 파일명
	private HttpServletRequest req;
	 
	// 이미지 파일 참고 - https://aristatait.tistory.com/16?category=672398,
	// https://hks003.tistory.com/11

	public void setReq(HttpServletRequest req) {
		this.req = req;
	}
	
	public String orgShow(int number) {	//	연구실 조직도 이미지 불러오기
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			sql = "select * from organization where number=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			rs = pstmt.executeQuery();
			if(rs.next()) {	//	업로드한 이미지 파일이 존재할 때
				returns = rs.getString("organization_image");
			} 
			else {	//	업로드한 이미지 파일이 존재하지 않을 때
				returns = "fileNotExist";
			}

		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		}

		return returns;
	}

	public String orgUpload(int number, String savePath) { // 연구실 조직도 이미지 업로드
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());

			MultipartRequest multi = new MultipartRequest(req, savePath, max, "utf-8"); // savePath 경로에 파일 저장
																						//동일한 파일명이 있을 경우 덮어쓰기 한다
			
			Enumeration files = multi.getFileNames();
			String str = (String) files.nextElement();
			originFile = multi.getOriginalFileName(str); // 파일의 원래 이름
			String filePath = savePath+originFile;
			
			sql = "select * from organization where number=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			rs = pstmt.executeQuery();

			if (rs.next()) { // 이미지(경로)가 있을 때
				sql2 = "update organization set organization_image=? where number=?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, filePath);
				pstmt2.setInt(2, number);
				pstmt2.executeUpdate();
			} else { // 이미지(경로)가 없을 때
				sql2 = "insert into organization (number, organizaion_image) values (?, ?)";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setInt(1, number);
				pstmt2.setString(2, filePath);
				pstmt2.executeUpdate();
			}
			returns = "orgUploaded";
		} catch (Exception e) {
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
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
		}
		return returns;
	}

	public String orgDelete(int number) { // 연구실 조직도 이미지 삭제
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			sql = "select * from organization where number=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				sql2 = "delete from organization where number=?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setInt(1, number);
				pstmt2.executeQuery();
				returns = "orgDeleted";
			} else {
				returns = "orgNotExist";
			}
		} catch (Exception e) {
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
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
		}
		return returns;
	}
	
	public String strShow(int number) {	//	연구실 구성도 이미지 불러오기
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			sql = "select * from structure where number=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			rs = pstmt.executeQuery();
			if(rs.next()) {	//	업로드한 이미지 파일이 존재할 때
				returns = rs.getString("structure_image");
			} 
			else {	//	업로드한 이미지 파일이 존재하지 않을 때
				returns = "fileNotExist";
			}

		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		}

		return returns;
	}

	
	public String strUpload(int number, String savePath) { // 연구실 구성도 이미지 업로드
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());

			MultipartRequest multi = new MultipartRequest(req, savePath, max, "utf-8"); // savePath 경로에 파일 저장
																						//동일한 파일명이 있을 경우 덮어쓰기 한다
			
			Enumeration files = multi.getFileNames();
			String str = (String) files.nextElement();
			originFile = multi.getOriginalFileName(str); // 파일의 원래 이름
			String filePath = savePath+originFile;
			
			sql = "select * from structure where number=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			rs = pstmt.executeQuery();

			if (rs.next()) { // 이미지(경로)가 있을 때
				sql2 = "update structure set structure_image=? where number=?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, filePath);
				pstmt2.setInt(2, number);
				pstmt2.executeUpdate();
			} else { // 이미지(경로)가 없을 때
				sql2 = "insert into structure (number, structure_image) values (?, ?)";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setInt(1, number);
				pstmt2.setString(2, filePath);
				pstmt2.executeUpdate();
			}
			returns = "strUploaded";
		} catch (Exception e) {
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
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
		}
		return returns;
	}
	
	public String strDelete(int number) { // 연구실 구성도 이미지 삭제
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			sql = "select * from structure where number=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				sql2 = "delete from structure where number=?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setInt(1, number);
				pstmt2.executeQuery();
				returns = "strDeleted";
			} else {
				returns = "strNotExist";
			}
		} catch (Exception e) {
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
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
		}
		return returns;
	}

	public String ipShow(int number) {	//	연구실 ip 및 출입키 이미지 불러오기
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			sql = "select * from ip where number=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			rs = pstmt.executeQuery();
			if(rs.next()) {	//	업로드한 이미지 파일이 존재할 때
				returns = rs.getString("ip_image");
			} 
			else {	//	업로드한 이미지 파일이 존재하지 않을 때
				returns = "fileNotExist";
			}

		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		}

		return returns;
	}
	
	
	public String ipUpload(int number, String savePath) { // 연구실 구성도 이미지 업로드
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());

			MultipartRequest multi = new MultipartRequest(req, savePath, max, "utf-8"); // savePath 경로에 파일 저장
																						//동일한 파일명이 있을 경우 덮어쓰기 한다
			
			Enumeration files = multi.getFileNames();
			String str = (String) files.nextElement();
			originFile = multi.getOriginalFileName(str); // 파일의 원래 이름
			String filePath = savePath+originFile;
			
			sql = "select * from ip where number=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			rs = pstmt.executeQuery();

			if (rs.next()) { // 이미지(경로)가 있을 때
				sql2 = "update ip set ip_image=? where number=?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, filePath);
				pstmt2.setInt(2, number);
				pstmt2.executeUpdate();
			} else { // 이미지(경로)가 없을 때
				sql2 = "insert into ip (number, ip_image) values (?, ?)";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setInt(1, number);
				pstmt2.setString(2, filePath);
				pstmt2.executeUpdate();
			}
			returns = "ipUploaded";
		} catch (Exception e) {
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
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
		}
		return returns;
	}
	
	public String ipDelete(int number) { // 연구실 ip 및 출입키 이미지 삭제
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			sql = "select * from ip where number=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				sql2 = "delete from ip where number=?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setInt(1, number);
				pstmt2.executeQuery();
				returns = "ipDeleted";
			} else {
				returns = "ipNotExist";
			}
		} catch (Exception e) {
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
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
		}
		return returns;
	}

}
