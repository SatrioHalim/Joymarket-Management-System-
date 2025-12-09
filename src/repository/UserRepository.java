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
			connect.getConnection().setAutoCommit(false);
			PreparedStatement ps = connect.getConnection().prepareStatement(query);
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return new User(rs.getInt("id"), rs.getString("fullname"),rs.getString("email"),
						rs.getString("password"), rs.getString("phone"), rs.getString("address")
						, rs.getString("role"), rs.getString("gender"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public boolean registerCustomer(String fullname, String email, String password, String phone
			, String address, String gender) {
		String userQuery = "INSERT INTO users(fullname,email,password,phone,address,role,gender) VALUES(?,?,?,?,?,?,?)";
		String customerQuery = "INSERT INTO customer(userid,balance) VALUES(?,?)";

		try {
			connect.getConnection().setAutoCommit(false);
			// Query buat masukin ke tabel users			
			PreparedStatement psUser = connect.getConnection().prepareStatement(userQuery,PreparedStatement.RETURN_GENERATED_KEYS);
			psUser.setString(1, fullname);
			psUser.setString(2, email);
			psUser.setString(3, password);
			psUser.setString(4, phone);
			psUser.setString(5, address);
			psUser.setString(6, "customer");
			psUser.setString(7, gender);
			psUser.executeUpdate();
			
			// buat ambil id di users yg baru terus jadi reference ke table customer
			int userId = 0;
			ResultSet generatedKeys = psUser.getGeneratedKeys();
			if (generatedKeys.next()) {
	            userId = generatedKeys.getInt(1);
	        }
			
			// Query Buat masukin ke table customer
			PreparedStatement psCustomer = connect.getConnection().prepareStatement(customerQuery);
			psCustomer.setInt(1, userId);
			psCustomer.setDouble(2, 0); // default saldonya bikin 0 aja
			psCustomer.executeUpdate();
			
			connect.getConnection().commit();
		
			return true;
			
		} catch (SQLException e) {
	        try {
	            // Rollback jika ada error
	            if (connect != null) {
	                connect.getConnection().rollback();
	            }
	        } catch (SQLException rollbackEx) {
	            rollbackEx.printStackTrace();
	        }
	        e.printStackTrace();
	        return false; // Return false jika gagal
	        
	    } finally {
	        try {
	            if (connect != null) {
	                connect.getConnection().setAutoCommit(true);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
}