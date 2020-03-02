package DBConnect;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.tomcat.util.codec.binary.Base64;

public class ImageUpload { // �̹��� ���� ���ε�
	private static ImageUpload instance = new ImageUpload();

	public static ImageUpload getInstance() {
		return instance;
	}
	//	DB����
	private DBConnector dbc = new DBConnector(); // DBConnector ��ü����
	private Connection conn;    //  connecttion:db�� �����ϰ� ���ִ� ��ü
	private String sql = "";
	private String sql2 = "";
	private PreparedStatement pstmt;
	private PreparedStatement pstmt2;
	private ResultSet rs;
	
	// �����
	private String returns;

	//	��ü ����
	private File file;
	private FileInputStream fis;
	private FileOutputStream fos;
	// �̹��� ���� ���� - https://aristatait.tistory.com/16?category=672398
	// https://hks003.tistory.com/11

	public String orgShow() { // ������ ������ �̹��� �ҷ�����
			try {
				conn = dbc.getConn();
				sql = "select * from organization";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if (rs.next()) { // ���ε��� �̹��� ������ ��ΰ� ������ ��
					String filePath = rs.getString("organization_image");
					file = new File(filePath);
					fis = new FileInputStream(file);

					byte[] encoding = new byte[(int) file.length()];
					fis.read(encoding);
					String encoded = Base64.encodeBase64String(encoding);

					returns = encoded;
				} else { // ���ε��� �̹��� ������ ��ΰ� �������� ���� ��
					returns = "fileNotExist";
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.err.println("ImageUpload orgShow FileNotFoundException error");
				returns = "error";
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.err.println("ImageUpload orgShow SQLException error");
				returns = "error";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("ImageUpload orgShow IOException error");
				returns = "error";
			}	finally {
				if(file != null) {
					file = null;
				}
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.err.println("IOException orgShow IOException error");
					returns = "error";
				}
			}

		return returns;
	}

	public String orgUpload(String savePath, String imgFile) { // ������ ������ �̹��� ���ε�
		
			try {
				conn = dbc.getConn();
				
				String name = "org.jpg";
				String filePath = savePath + name;

				file = new File(filePath);
				fos = new FileOutputStream(file);

				byte decoded[] = Base64.decodeBase64(imgFile);	//	��ȣȭ�� �̹���
				fos.write(decoded);

				sql = "select * from organization where organization_image=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, filePath);
				rs = pstmt.executeQuery();

				if (rs.next()) { // ��ο� �̹����� ���� ��
					sql2 = "update organization set organization_image=?";
					pstmt2 = conn.prepareStatement(sql2);
					pstmt2.setString(1, filePath);
					pstmt2.executeUpdate();
				} else { // ��ο� �̹����� ���� ��
					sql2 = "insert into organization (organization_image) values (?)";
					pstmt2 = conn.prepareStatement(sql2);
					pstmt2.setString(1, filePath);
					pstmt2.executeUpdate();
				}
				returns = "orgUploaded";
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.err.println("ImageUpload orgUpload FileNotFoundException error");
				returns = "error";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("ImageUpload orgUpload IOException error");
				returns = "error";
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.err.println("ImageUpload orgUpload SQLException error");
				returns = "error";
			} finally {
				if(file != null) {
					file = null;
				}
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.err.println("ImageUpload orgUpload IOException error");
					returns = "error";
				}				
				if (pstmt != null)
					try {
						pstmt.close();
					} catch (SQLException ex) {
						System.err.println("ImageUpload orgUpload SQLException error");
						returns = "error";
					}
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException ex) {
						System.err.println("ImageUpload orgUpload SQLException error");
						returns = "error";
					}
				if (rs != null)
					try {
						rs.close();
					} catch (SQLException ex) {
						System.err.println("ImageUpload orgUpload SQLException error");
						returns = "error";
					}
			}
		
		return returns;
	}

	public String strShow() { // ������ ������ �̹��� �ҷ�����
			try {
				Connection conn = dbc.getConn();
				sql = "select * from structure";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if (rs.next()) { // ���ε��� �̹��� ������ ��ΰ� ������ ��
					String filePath = rs.getString("structure_image");
					file = new File(filePath);
					fis = new FileInputStream(file);

					byte[] encoding = new byte[(int) file.length()];
					fis.read(encoding);
					String encoded = Base64.encodeBase64String(encoding);

					returns = encoded;
				} else { // ���ε��� �̹��� ������ �������� ���� ��
					returns = "fileNotExist";
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.err.println("ImageUpload strShow FileNotFoundException error");
				returns = "error";
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.err.println("ImageUpload strShow SQLException error");
				returns = "error";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("ImageUpload strShow IOException error");
				returns = "error";
			} finally {
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.err.println("ImageUpload strShow IOException error");
					returns = "error";
				}
				if (pstmt != null)
					try {
						pstmt.close();
					} catch (SQLException ex) {
						System.err.println("ImageUpload strShow SQLException error");
						returns = "error";
					}
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException ex) {
						System.err.println("ImageUpload strShow SQLException error");
						returns = "error";
					}
				if (rs != null)
					try {
						rs.close();
					} catch (SQLException ex) {
						System.err.println("ImageUpload strShow SQLException error");
						returns = "error";
					}
			}
			
		return returns;
	}

	public String strUpload(String savePath, String imgFile) { // ������ ������ �̹��� ���ε�
			try {
				Connection conn = dbc.getConn();
				String name = "str.jpg";
				String filePath = savePath + name;

				file = new File(filePath);
				fos = new FileOutputStream(file);

				byte decoded[] = Base64.decodeBase64(imgFile);
				fos.write(decoded);
				//fos.close();

				sql = "select * from structure where structure_image=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, filePath);
				rs = pstmt.executeQuery();

				if (rs.next()) { // ��ο� �̹����� ���� ��
					sql2 = "update structure set structure_image=?";
					pstmt2 = conn.prepareStatement(sql2);
					pstmt2.setString(1, filePath);
					pstmt2.executeUpdate();
				} else { // ��ο� �̹����� ���� ��
					sql2 = "insert into structure (structure_image) values (?)";
					pstmt2 = conn.prepareStatement(sql2);
					pstmt2.setString(1, filePath);
					pstmt2.executeUpdate();
				}
				returns = "strUploaded";
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.err.println("ImageUpload strUpload FileNotFoundException error");
				returns = "error";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("ImageUpload strUpload IOException error");
				returns = "error";
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.err.println("ImageUpload strUpload SQLException error");
				returns = "error";
			} finally {
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.err.println("ImageUpload strUpload IOException error");
					returns = "error";
				}
				if (pstmt != null)
					try {
						pstmt.close();
					} catch (SQLException ex) {
						System.err.println("ImageUpload strUpload SQLException error");
						returns = "error";
					}
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException ex) {
						System.err.println("ImageUpload strUpload SQLException error");
						returns = "error";
					}
				if (rs != null)
					try {
						rs.close();
					} catch (SQLException ex) {
						System.err.println("ImageUpload strUpload SQLException error");
						returns = "error";
					}
			}
			
		return returns;
	}

	public String ipShow() { // ������ ip �� ����Ű �̹��� �ҷ�����
			try {
				Connection conn = dbc.getConn();
				sql = "select * from ip";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if (rs.next()) { // ���ε��� �̹��� ������ ��ΰ� ������ ��
					String filePath = rs.getString("ip_image");
					file = new File(filePath);
					fis = new FileInputStream(file);

					byte[] encoding = new byte[(int) file.length()];
					fis.read(encoding);
					String encoded = Base64.encodeBase64String(encoding);

					returns = encoded;
				} else { // ���ε��� �̹��� ������ �������� ���� ��
					returns = "fileNotExist";
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.err.println("ImageUpload ipShow FileNotFoundException error");
				returns = "error";
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.err.println("ImageUpload ipShow SQLException error");
				returns = "error";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("ImageUpload ipShow IOException error");
				returns = "error";
			} finally {
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.err.println("ImageUpload ipShow IOException error");
					returns = "error";
				}
				if (pstmt != null)
					try {
						pstmt.close();
					} catch (SQLException ex) {
						System.err.println("ImageUpload ipShow SQLException error");
						returns = "error";
					}
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException ex) {
						System.err.println("ImageUpload ipShow SQLException error");
						returns = "error";
					}
				if (rs != null)
					try {
						rs.close();
					} catch (SQLException ex) {
						System.err.println("ImageUpload ipShow SQLException error");
						returns = "error";
					}
				
			}

		return returns;
	}

	public String ipUpload(String savePath, String imgFile) { // ������ ������ �̹��� ���ε�
			try {
				Connection conn = dbc.getConn();
				String name = "ip.jpg";
				String filePath = savePath + name;

				file = new File(filePath);
				fos = new FileOutputStream(file);

				byte decoded[] = Base64.decodeBase64(imgFile);
				fos.write(decoded);

				sql = "select * from ip where ip_image=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, filePath);
				rs = pstmt.executeQuery();

				if (rs.next()) { // ��ο� �̹����� ���� ��
					sql2 = "update ip set ip_image=?";
					pstmt2 = conn.prepareStatement(sql2);
					pstmt2.setString(1, filePath);
					pstmt2.executeUpdate();
				} else { // ��ο� �̹����� ���� ��
					sql2 = "insert into ip (ip_image) values (?)";
					pstmt2 = conn.prepareStatement(sql2);
					pstmt2.setString(1, filePath);
					pstmt2.executeUpdate();
				}
				returns = "ipUploaded";
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.err.println("ImageUpload ipUpload FileNotFoundException error");
				returns = "error";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("ImageUpload ipUpload IOException error");
				returns = "error";
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.err.println("ImageUpload ipUpload SQLException error");
				returns = "error";
			} finally {
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.err.println("ImageUpload ipUpload IOException error");
					returns = "error";
				}
				if (pstmt != null)
					try {
						pstmt.close();
					} catch (SQLException ex) {
						System.err.println("ImageUpload ipUpload SQLException error");
						returns = "error";
					}
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException ex) {
						System.err.println("ImageUpload ipUpload SQLException error");
						returns = "error";
					}
				if (rs != null)
					try {
						rs.close();
					} catch (SQLException ex) {
						System.err.println("ImageUpload ipUpload SQLException error");
						returns = "error";
					}
			}
		
		return returns;
	}
}
