package DataManipulation;

/**
 *
 * @author Jimmy Hoang
 */
public class User {
    //fields
    private int userID;
    private String userName;
    private String password;
    //if security
    private int securityQuestion;
    private String securityAnswer;
    //constant fields
    private final String SECURITY_QUESTION_1="What is your favorite Operating System?";
    private final String SECURITY_QUESTION_2="Which Comic Universe is better, Marvel or DC?";
    
    //Constructor
    public User(int userID, String userName, String password, int securityQuestion, String securityAnswer) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    }
    public User( String userName, String password, int securityQuestion, String securityAnswer) {
 
        this.userName = userName;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    
    }

    public int getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public int getSecurityQuestion() {
        return securityQuestion;
    }
    
    public boolean verifyPassword(String password){
        return(this.password.equals(password));
    }
    public String lostPassword(){
        //GETS security question identifier and returns question 1 if identifier is 1
        //returns security question 2 otherwise
        //if more security questions are needed change arguments to switch statement rather than conditional operator
        return(securityQuestion == 1?SECURITY_QUESTION_1:SECURITY_QUESTION_2);
    }
    
    
}//end of Class User