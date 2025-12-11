package repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConnector;
import model.Product;

public class ProductRepository {
	private DatabaseConnector connect;

	public ProductRepository() {
		// TODO Auto-generated constructor stub
		this.connect = DatabaseConnector.getInstance();
	}
	
	public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM product";
        
        try (Statement stmt = connect.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Product product = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("stock"),
                    rs.getString("category")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
	
	public List<Product> searchProducts(String keyword) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM product WHERE name LIKE ? OR category LIKE ?";
        
        try (PreparedStatement ps = connect.getConnection().prepareStatement(query)) {
            String searchKeyword = "%" + keyword + "%";
            ps.setString(1, searchKeyword);
            ps.setString(2, searchKeyword);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Product product = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("stock"),
                    rs.getString("category")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
	
	public Product getProductById(int id) {
        String query = "SELECT * FROM product WHERE id = ?";
        
        try (PreparedStatement ps = connect.getConnection().prepareStatement(query)) {
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("stock"),
                    rs.getString("category")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
	public boolean updateProductStock(int productId, int newStock) {
        String query = "UPDATE product SET stock = ? WHERE id = ?";
        
        try (PreparedStatement ps = connect.getConnection().prepareStatement(query)) {
            ps.setInt(1, newStock);
            ps.setInt(2, productId);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
	public boolean checkStockAvailability(int productId, int requestedQuantity) {
        String query = "SELECT stock FROM product WHERE id = ?";
        
        try (PreparedStatement ps = connect.getConnection().prepareStatement(query)) {
            ps.setInt(1, productId);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                int currentStock = rs.getInt("stock");
                return currentStock >= requestedQuantity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
