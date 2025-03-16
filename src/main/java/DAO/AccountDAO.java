package DAO;

import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;

public class AccountDAO {
    public Account registerUser(Account account){
        Connection conn = null;
        try {
            conn = ConnectionUtil.getConnection();
            String sql = "INSERT INTO ACCOUNT (username, password) VALUES (?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()){
                int account_id = rs.getInt(1);
                return new Account (account_id, account.getUsername(), account.getPassword());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null){
                try {
                    conn.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    public Account getAccountByUsername(String username){
        Connection conn = null;
        try {
            conn = ConnectionUtil.getConnection();
            String sql = "SELECT * FROM ACCOUNT WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null){
                try {
                    conn.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    } 
    public Account getAccountById(int account_id){
        Connection conn = null;
        try {
            conn = ConnectionUtil.getConnection();
            String sql = "SELECT * FROM ACCOUNT WHERE account_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, account_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                );
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (conn != null){
                try {
                    conn.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public Account userLogin(Account account){
        Connection conn = null;
        try {
            conn = ConnectionUtil.getConnection();
            String sql = "SELECT * FROM ACCOUNT WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, account.getUsername());

            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                Account dbAccount = new Account (
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")

                );
                if (account.getPassword().equals(dbAccount.getPassword())){
                    return dbAccount;
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (conn!= null){
                try {
                    conn.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    }


