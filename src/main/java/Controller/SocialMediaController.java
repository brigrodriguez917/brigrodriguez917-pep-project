package Controller;
import Service.MessageService;
import Service.AccountService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import io.javalin.Javalin;
import io.javalin.http.Context;



import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    private MessageService messageService;
    private AccountService accountService;

    public SocialMediaController() {
        this.messageService = new MessageService();
        this.accountService = new AccountService();
    }

    
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("/messages", this::getAllMessages);
        app.get("/messages/{message_id}", this::getMessageById);
        app.delete("/messages/{message_id}", this::deleteMessageById);
        app.patch("/messages/{message_id}", this::updateMessageById);
        app.post("/messages", this::createMessage);
        app.post("/login", this::userLogin);
        app.post("/register", this::registerUser);
        app.get("/accounts/{account_id}/messages", this::getMessagesByUser);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */

     private void getAllMessages(Context ctx){
        List<Message>messages = messageService.getAllMessages();
        ctx.json(messages);
     }
    private void createMessage(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);


        if (message.getMessage_text() == null || message.getMessage_text().trim().isEmpty() || message.getMessage_text().length() > 255){
            ctx.status(400).result("");
            return;
        }
        boolean userExists = messageService.checkIfUserExists(message.getPosted_by());

        if (!userExists){
            ctx.status(400).result("");
            return;
        }
        Message createdMessage = messageService.createMessage(message);
        if (createdMessage != null){
            ctx.status(200).json(mapper.writeValueAsString(createdMessage));

        } else {
            ctx.status(400).result("");
        }
    
    }
    private void getMessageById(Context ctx){
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(message_id);

        if (message != null){
            ctx.json(message);
        } else {
            ctx.status(200).result("");
        }
        }
        private void deleteMessageById(Context ctx){
            int message_id = Integer.parseInt(ctx.pathParam("message_id"));
            Message deletedMessage = messageService.deleteMessageById(message_id);

            if (deletedMessage != null){
                ctx.json(deletedMessage);
            } else {
                ctx.status(200);
            }

        }
        private void updateMessageById(Context ctx) throws JsonProcessingException{
            ObjectMapper mapper = new ObjectMapper();
            int message_id = Integer.parseInt(ctx.pathParam("message_id"));
            Message updatedMessage = mapper.readValue(ctx.body(), Message.class);

            Message result = messageService.updateMessageById(message_id,updatedMessage);

            if(result != null){
                ctx.json(mapper.writeValueAsString(result));
            } else{
                ctx.status(400);
            }
           
        
        }
        private void getMessagesByUser(Context ctx){
            int account_id = Integer.parseInt(ctx.pathParam("account_id"));
            List<Message> messages = messageService.getMessagesByUser(account_id);
            ctx.json(messages);
        }
    
        private void registerUser(Context ctx) throws JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();
            Account account = mapper.readValue(ctx.body(), Account.class);
            Account registeredAccount = accountService.registerUser(account);

            if (registeredAccount != null){
                ctx.json(mapper.writeValueAsString(registeredAccount));
            } else {
                ctx.status(400).result("");
            }
        }
        private void userLogin(Context ctx) throws JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();
            Account account = mapper.readValue(ctx.body(), Account.class);
            Account loggedInAccount = accountService.userLogin(account);

            if (loggedInAccount != null){
                ctx.json(mapper.writeValueAsString(loggedInAccount));
            } else {
                ctx.status(401).result ("");
            }
        }
}
