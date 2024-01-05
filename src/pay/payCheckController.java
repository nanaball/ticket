package pay;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class payCheckController implements Initializable {

	@FXML Label userID, date, musical, seat;
	@FXML Button btnEnd;

	Connection conn = null;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// 결제 화면에서 MySQL 연결
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				System.out.println("driver 존재");
				
				Properties prop = new Properties();
				// 임의로 DB를 만들어서 주소를 정함
				// 주소만 바꾸면 연동 될 것 같아요
				prop.load(new FileReader("src/pay/DB/mysql.properties")); 
				System.out.println(prop);
				
				conn = DriverManager.getConnection(prop.getProperty("url"),prop);
				System.out.println(conn);
				
				String sql = "" +
				 "SELECT userID, price, seat, musical, date from dummy where userID=?";
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				// 아이디가 h 인 사람의 가격, 좌석, 예매한 작품, 날짜 정보 구현
				// 나중에 로그인 정보와 맞추면 될 듯
				// 어덯게 하지..?
				pstmt.setString(1, "h");	
					
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					String userID = rs.getString("userID");
					String seat = rs.getString("seat");
					String musical = rs.getString("musical");
					String date = rs.getString("date");
					System.out.println(userID+":"+seat+":"+musical+":"+date);
					
					
				}else {
					System.out.println("존재X");
				}
				
				userID.setText(rs.getString("userID"));
				seat.setText(rs.getString("seat"));
				musical.setText(rs.getString("musical"));
				date.setText(rs.getString("date"));
					
				} catch (FileNotFoundException e) {
					System.out.println("file not found");
					
				} catch (IOException e) {
					System.out.println("ioe err");
					
				} catch (SQLException e1) {
					System.out.println("sql error");
					
				} catch (ClassNotFoundException e1) {
					System.out.println("class not found");
	
				}
			
			btnEnd.setOnAction((e)->{
//				System.exit(0);
				Platform.exit();
			});
	
	}
	
}
