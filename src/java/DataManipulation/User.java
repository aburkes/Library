package DataManipulation;

import javax.faces.bean.ManagedBean;

/**
 *
 * @author Jimmy Hoang
 */

@ManagedBean
public class User {
    //fields
    private int userID;
    private String userName;
    private String password;
    // security
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
    
    //new methods; need to have documentation changed for them

    public User(){} //add to top when finished.

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSecurityQuestion(int securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }
    
    
    
    
    
}//end of Class User