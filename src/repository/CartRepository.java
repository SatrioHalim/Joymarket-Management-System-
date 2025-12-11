package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConnector;
import model.CartItem;
import model.Product;

public class CartRepository {
	private DatabaseConnector connect;
    
    public CartRepository() {
    	this.connect = DatabaseConnector.getInstance();
    }
    
    // Add item to cart
    public boolean addToCart(int customerId, int productId, int count) {
        String checkQuery = "SELECT * FROM cartitem WHERE customerid = ? AND productid = ?";
        String updateQuery = "UPDATE cartitem SET count = count + ? WHERE customerid = ? AND productid = ?";
        String insertQuery = "INSERT INTO cartitem (customerid, productid, count) VALUES (?, ?, ?)";
        
        try (PreparedStatement checkStmt = connect.getConnection().prepareStatement(checkQuery)) {
            checkStmt.setInt(1, customerId);
            checkStmt.setInt(2, productId);
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next()) {
                // Item already exists, update quantity
                try (PreparedStatement updateStmt = connect.getConnection().prepareStatement(updateQuery)) {
                    updateStmt.setInt(1, count);
                    updateStmt.setInt(2, customerId);
                    updateStmt.setInt(3, productId);
                    return updateStmt.executeUpdate() > 0;
                }
            } else {
                // New item, insert
                try (PreparedStatement insertStmt = connect.getConnection().prepareStatement(insertQuery)) {
                    insertStmt.setInt(1, customerId);
                    insertStmt.setInt(2, productId);
                    insertStmt.setInt(3, count);
                    return insertStmt.executeUpdate() > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Get cart items for a customer
    public List<CartItem> getCartItems(int customerId) {
        List<CartItem> cartItems = new ArrayList<>();
        String query = "SELECT ci.*, p.name as product_name, p.price as product_price, p.stock as product_stock " +
                      "FROM cartitem ci " +
                      "JOIN product p ON ci.productid = p.id " +
                      "WHERE ci.customerid = ?";
        
        try (PreparedStatement stmt = connect.getConnection().prepareStatement(query)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                CartItem item = new CartItem();
                item.setId(rs.getInt("id"));
                item.setCustomerId(rs.getInt("customerid"));
                item.setProductId(rs.getInt("productid"));
                item.setCount(rs.getInt("count"));
                
                // Product details
                item.setProductName(rs.getString("product_name"));
                item.setProductPrice(rs.getDouble("product_price"));
                item.setProductStock(rs.getInt("product_stock"));
                
                cartItems.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItems;
    }
    
    // Remove item from cart
    public boolean removeFromCart(int cartItemId) {
        String query = "DELETE FROM cartitem WHERE id = ?";
        
        try (PreparedStatement stmt = connect.getConnection().prepareStatement(query)) {
            stmt.setInt(1, cartItemId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Update cart item quantity
    public boolean updateCartQuantity(int cartItemId, int newQuantity) {
        String query = "UPDATE cartitem SET count = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connect.getConnection().prepareStatement(query)) {
            stmt.setInt(1, newQuantity);
            stmt.setInt(2, cartItemId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Clear cart for a customer
    public boolean clearCart(int customerId) {
        String query = "DELETE FROM cartitem WHERE customerid = ?";
        
        try (PreparedStatement stmt = connect.getConnection().prepareStatement(query)) {
            stmt.setInt(1, customerId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Get total cart amount for a customer
    public double getCartTotal(int customerId) {
        String query = "SELECT SUM(ci.count * p.price) as total " +
                      "FROM cartitem ci " +
                      "JOIN product p ON ci.productid = p.id " + 
                      "WHERE ci.customerid = ?";
        
        try (PreparedStatement stmt = connect.getConnection().prepareStatement(query)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
    
    // Check if product already in cart
    public boolean isProductInCart(int customerId, int productId) {
        String query = "SELECT COUNT(*) as count FROM cartitem WHERE customerid = ? AND productid = ?";
        
        try (PreparedStatement stmt = connect.getConnection().prepareStatement(query)) {
            stmt.setInt(1, customerId);
            stmt.setInt(2, productId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}