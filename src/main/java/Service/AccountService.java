package Service;
import Model.Account;
import DAO.AccountDAO;


public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        this.accountDAO = new AccountDAO();
    }
    public Account userLogin (Account account){
      
        Account dbAccount = accountDAO.getAccountByUsername(account.getUsername());

        if (dbAccount != null && account.getPassword().equals(dbAccount.getPassword())){
            return dbAccount;
        }
        return null;
    }
    public Account registerUser(Account account){

        if (account.getUsername().trim().isEmpty()  || account.getPassword().length() < 4){
            return null;
        }
       
        if (accountDAO.getAccountByUsername(account.getUsername()) != null){
            return null;
        }

        return accountDAO.registerUser(account);
    }
    public Account getAccountById(int account_id){
        return accountDAO.getAccountById(account_id);
    }

    }
    
