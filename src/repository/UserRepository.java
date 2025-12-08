package repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseConnector;
import model.User;

public class UserRepository {
	private DatabaseConnector connect;
	
	public UserRepository() {
		// TODO Auto-generated constructor stub
		connect = DatabaseConnector.getInstance();
	}
	public User loginUser(String email, String password) {
		String query = "SELECT * FROM users WHERE email = ? AND password = ?";
		try {
			PreparedStatement ps = connect.getConnection().prepareStatement(query);
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return new User(rs.getInt("id"), rs.getString("fullname"),rs.getString("email"),
						rs.getString("password"), rs.getString("phone"), rs.getString("address")
						, rs.getString("role"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
//	public boolean registerUser(User user) {
//		String query = "INSERT INTO users(email,fullname,password) VALUES(?,?,?)";
//		
//		try {
//			PreparedStatement ps = connect.getConnection().prepareStatement(query);
//			ps.setString(1, user.getEmail());
//			ps.setString(2, user.getUsername());
//			ps.setString(3, user.getPassword());
//			
//			return ps.executeUpdate() > 0;
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return false;
//	}
}
