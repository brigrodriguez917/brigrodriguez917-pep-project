package DAO;

import Model.Message;
import java.util.List;
import java.util.ArrayList;
import Util.ConnectionUtil;
import java.sql.*;


public class MessageDAO {
    public List<Message> getAllMessages(){
        List<Message> messages = new ArrayList <>();
        Connection conn = null;
        try { conn = ConnectionUtil.getConnection();
            String sql = "SELECT * FROM MESSAGE";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){

                Message message = new Message (
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
                messages.add(message);
            }
        } catch (SQLException e){
            e.printStackTrace();

        }
        return messages;
    }


    public Message insertMessage(Message message){
        Connection conn = null;
        
        try {
            conn = ConnectionUtil.getConnection();

            String sql = "INSERT INTO MESSAGE (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3,message.getTime_posted_epoch());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()){
                int message_id = rs.getInt(1);
                return new Message (message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e){
                    e.printStackTrace();

                }
            }
        }
        return null;
    }
    public Message getMessageById(int message_id){
        Connection conn = null;
        try {
            conn = ConnectionUtil.getConnection();
            String sql = "SELECT * FROM MESSAGE WHERE message_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, message_id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return new Message (
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
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
    public Message deleteMessageById(int message_id){
        Connection conn = null;
        try {
            conn = ConnectionUtil.getConnection();
            String sql = "DELETE FROM MESSAGE WHERE message_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, message_id);
            Message deletedMessage = getMessageById(message_id);

            if (deletedMessage != null){
                ps.executeUpdate();
                return deletedMessage;
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    public Message updateMessageById (int message_id, Message message){
        Connection conn = null;
        try {
            conn = ConnectionUtil.getConnection();
            String sql = "UPDATE MESSAGE SET message_text = ? WHERE message_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, message.getMessage_text());
            ps.setInt(2,message_id);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0){
                return getMessageById(message_id);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    public List<Message> getMessagesByUser(int account_id){
      List <Message> messages = new ArrayList<>();
      Connection conn = null;
      PreparedStatement ps = null;
      ResultSet rs = null;
      try {
        conn = ConnectionUtil.getConnection();
        String sql = "SELECT * FROM MESSAGE WHERE posted_by = ?";

        ps = conn.prepareStatement(sql);
        ps.setInt(1, account_id);

        rs = ps.executeQuery();

        while (rs.next()){
            Message message = new Message (
                rs.getInt("message_id"),
                rs.getInt("posted_by"),
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch")
            );
            messages.add(message);
        }
      } catch (SQLException e){
        e.printStackTrace();
      } finally {
        if (rs != null){
            try{
                rs.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        if (ps != null){
            try {
            ps.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
      }
    
      if (conn != null){
        try {
            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
      }
    }
    return messages;
}
public boolean checkIfUserExists (int userId){
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        conn = ConnectionUtil.getConnection();
    String sql = "SELECT COUNT (*) FROM ACCOUNT WHERE account_id = ?"; 
    ps = conn.prepareStatement(sql);
    ps.setInt(1, userId);
    rs = ps.executeQuery();

    if (rs.next()){
        int count = rs.getInt(1);
        return count > 0;
    }
    } catch (SQLException e){
        e.printStackTrace();
    } finally {
        if (rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null){
            try {
                ps.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    return false;
}
}
