package Service;
import Model.Message;
import DAO.MessageDAO;
import Model.Account;
import java.util.List;

public class MessageService {

    private MessageDAO messageDAO;

    public MessageService(){
        this.messageDAO = new MessageDAO();
    }
   
    public List<Message> getAllMessages(){
      
        return messageDAO.getAllMessages();
    }
    
    public Message createMessage (Message message){
        if (message.getMessage_text() == null || message.getMessage_text().trim().isEmpty() || message.getMessage_text().length() > 255){
            return null;
        }
        AccountService accountService = new AccountService();
        Account user = accountService.getAccountById(message.getPosted_by());
        if (user == null) return null;
        
        Message createdMessage = messageDAO.insertMessage(message);
        if (createdMessage == null){
            System.out.println("Failed to insert message into the database");
        }
        return createdMessage;
        
        }
    
    public Message getMessageById(int message_id){
        return messageDAO.getMessageById(message_id);
    }

    public Message deleteMessageById(int message_id){
        return messageDAO.deleteMessageById(message_id);
    }
    public Message updateMessageById(int message_id, Message updatedMessage){
        Message existingMessage = messageDAO.getMessageById(message_id);
        if (existingMessage == null) return null;
        if (updatedMessage.getMessage_text() == null || updatedMessage.getMessage_text().equals("") || updatedMessage.getMessage_text().length() > 255){
            return null;
        }
        return messageDAO.updateMessageById(message_id, updatedMessage);
    }
    public List<Message> getMessagesByUser(int account_id){
        return messageDAO.getMessagesByUser(account_id);
    }
    public boolean checkIfUserExists(int userId){
        return messageDAO.checkIfUserExists(userId);
    }
    
}
