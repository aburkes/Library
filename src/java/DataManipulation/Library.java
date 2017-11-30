package DataManipulation;


import javax.faces.bean.ManagedBean;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.SessionScoped;


/**
 * Represents a library and its functions.
 * 
 * @author Alan Burkes
 */
@ManagedBean
@SessionScoped
public class Library {

    final private static String CONNECTION_DATABASE = "jdbc:mysql://localhost:3306/library?zeroDateTimeBehavior=convertToNull";
    final private static String CONNECTION_AUTH_USER ="librarian";
    final private static String CONNECTION_AUTH_PASSWORD = "booksrfun451";
    
   // public static Statement statement;
    private static Connection connection;
   // public static ResultSet resultSet;
    //public static PreparedStatement preparedStatement;

    //should be in the specs, but isn't!
    private String flashMessage = "";
    private User authentcatedUser; //user logged in
    private String testedUser; // username to find password for
    private String testedUserAnswer; //answer to their security question

    private String testedUserPassword; //their username
    

    
    /**
     * Default constructor
     */
    public Library(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(CONNECTION_DATABASE, CONNECTION_AUTH_USER, CONNECTION_AUTH_PASSWORD);
        }
        catch(SQLException e) {
            System.out.println("SQL error encountered.");
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
        }
    }

    public String getFlashMessage() {
        String temp = flashMessage;
        flashMessage = "";
        return temp;
    }

    public void setFlashMessage(String flashMessage) {
        this.flashMessage = flashMessage;
    }

    /**
     * Checks to see if there is a flash message waiting to be conveyed to the User
     * @return True if there is a flash message, otherwise false.
     */
    public boolean flashExists(){
        return !flashMessage.isEmpty();
    }
    
    /*
    BEGIN AUTHENTICATION BLOCK
    */
    
    /**
     * Checks to see if current session is authenticated.
     * 
     * @return true if authenticated, false if not.
     */
    public boolean isAuthenticated(){
        try {
            return this.authentcatedUser != null;
        }
        catch(NullPointerException err) {
            return false;
        }
    }

    /**
     * Authenticates a user.
     * 
     * @param username
     * @param password
     * @see Library.authenticate
     */
    public void login(String username, String password){
        authenticate(username, password);
        
    }
    
    /**
     * Deauthenticates a user. also closes connection to database.
     */
    public void logoff() {
        this.authentcatedUser = null;
        this.flashMessage = "You have been logged off successfully";
    }
    
    /**
     * Authenticates a user. Will find a user in the database by the provided username and verify the password. If successful, it will authenticate a session.
     * @param username
     * @param password
     * @return True if authenticated, otherwise false.
     */
    private boolean authenticate(String username, String password) {
        final String AUTH_FAILURE_MESSAGE = "Authentication failed. Please check your username and password and try again";
        User thisUser = null;
        
        try{
            System.out.println("Getting User Data");
            thisUser = getUserbyName(username);
        }
        catch(SQLException err) {
            if(err.getErrorCode() == 0) {
                flashMessage = AUTH_FAILURE_MESSAGE;
            }
            else {
                System.out.println("SQL Error!");
                System.out.println(err.getMessage());
            }
        }
        
        try{
            if(thisUser.verifyPassword(password)) {
               this.authentcatedUser = thisUser;
               System.out.println("The Password was correct.");
               return true;
            }
            else 
                flashMessage = AUTH_FAILURE_MESSAGE;
        }
        catch(NullPointerException err) {
            //no need to actually return false since it will do that next anyways.
            System.out.println(err.getMessage());
        }
        
        //if all else fails...
        return false;
    }
    
    /**
     * Gets a security question for a user.
     * 
     * @param username
     * @return the user's security question
     */
    public String getSecurityQuestion(String username) {
        try {
            User user = getUserbyName(username);
            return user.lostPassword();
        } catch (SQLException ex) {
            Logger.getLogger(Library.class.getName()).log(Level.SEVERE, null, ex);
            this.setFlashMessage("Unable to find username.");
        }
        return "";
    }
    
    /**
     * Finds a password given the proper answer to a user's security question
     * 
     * @param username
     * @param securityAnswer
     * @return the user's password.
     */
    public String fetchUserPassword() {
        try {
            User user = this.getUserbyName(this.testedUser);
            if(this.testedUserAnswer.equals(user.getSecurityAnswer()))
                return user.getPassword();
        } catch (SQLException ex) {
            Logger.getLogger(Library.class.getName()).log(Level.SEVERE, null, ex);
            this.setFlashMessage("An error has occurred with your request.");
        }
        return "";
    }
    
    public String forgotPassword() {
        return "securityverification.xhtml";
    }
    

    public String getTestedUser() {
        return testedUser;
    }

    public void setTestedUser(String testedUser) {
        this.testedUser = testedUser;
    }

    public String getTestedUserPassword() {
        String password = this.testedUserPassword;
        this.testedUserPassword = null;
        return password;
    }

    public void setTestedUserPassword(String testedUserPassword) {
        this.testedUserPassword = testedUserPassword;
    }

    public String getTestedUserAnswer() {
        return testedUserAnswer;
    }

    public void setTestedUserAnswer(String testedUserAnswer) {
        this.testedUserAnswer = testedUserAnswer;
    }
    
    

    
    
    /*
    END AUTHENTICATION BLOCK
    */
    
    
    /**
     * Calls method to create a new user.
     * @param user user to inert into the database.

     * @throws SQLException 
     * @see createUser
     */
    public String newUser(User user) throws SQLException {
        try {
            createUser(user);
        }
        catch(SQLException err) {
            this.setFlashMessage("Sorry, we were unable to create this user. " + err.getMessage());
        }
        if (!this.flashExists())
            this.setFlashMessage("User created successfully");
        return "/index.xhtml";
    }

    /**
     * Calls method to create a student
     * @param student to be created
     * @throws SQLException 
     * @see createStudent
     */
    public void newStudent(Student student) throws SQLException {
        createStudent(student);
    }

    /**
     * Calls method to create a book.
     * @param book to be created
     * @throws SQLException 
     * @see createBook
     */
    public void newBook(Book book) throws SQLException {
        createBook(book);
    }

    /**
     * Calls a method to create a Record
     * @param record to be created. bookID, studentID, and userID are all required to be valid.
     * @throws SQLException 
     * @see createRecord
     */
    public void newRecord(Record record) throws SQLException {
        createRecord(record);
    }

    /**
     * Inserts a User object into the database.
     * 
     * @param user User object to be inserted.
     * @throws SQLException 
     */
    private void createUser(User user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO users VALUES (DEFAULT, ?, ?, ?, ?)");
        statement.setString(1, user.getUserName());
        statement.setString(2, user.getPassword());
        statement.setInt(3, user.getSecurityQuestion());
        statement.setString(4, user.getSecurityAnswer());
        statement.execute();
    }

    /**
     * Inserts a Student object into the database.
     * 
     * @param student Student object to be inserted.
     * @throws SQLException 
     */
    private void createStudent(Student student) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO students VALUES (DEFAULT, ?, ?, ?, ?)");
        statement.setString(1, student.getFirstName());
        statement.setString(2, student.getLastName());
        statement.setString(3, student.getEmail());
        statement.setString(4, student.getPhone());
        statement.execute();
    }

    /**
     * Inserts a Book object into the database.
     * 
     * @param book object to be inserted.
     * @throws SQLException 
     */
    private void createBook(Book book) throws SQLException {
       
        PreparedStatement statement = connection.prepareStatement("INSERT INTO books VALUES (DEFAULT, ?, ?, ?, ?, ?)");
        statement.setString(1, book.getTitle());
        statement.setInt(2, book.getEdition());
        statement.setString(3, book.getPublisher());
        statement.setDouble(4, book.getPrice());
        statement.setInt(5, book.getPages());
        statement.execute();
    }

    /**
     * Inserts a Record object into the database.
     * 
     * @param record object to be inserted.
     * @throws SQLException 
     */
    private void createRecord(Record record) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO records VALUES (DEFAULT, ?, ?, ?, ?, ?)");
        statement.setInt(1, record.getBook().getBookID());
        statement.setInt(2, record.getStudent().getStudentID());
        statement.setInt(3, record.getUser().getUserID());
        // Strangely, there is no way to directly input a LocalDateTime to a PreparedStatement
        // Even more strangely, you can't convert a LocalDateTime directly to a Date!
        // However, calling it's toString() method provides just the right format MySQL (and presumably other SQL RBDMS) requires.
        statement.setString(4, record.getCheckoutDate().toString());
    }

    /**
     * Finds a user by their User ID.
     * 
     * @param userID their User ID
     * @return The entire User object.
     * @throws SQLException 
     * @see fetchUser
     */
    public User getUserByID(int userID) throws SQLException {
        return fetchUser(Integer.toString(userID), "userID");
    }

    /**
     * Finds a Student object by their Student ID
     * @param studentID Student ID
     * @return a single matching Student object
     * @throws SQLException 
     * @see fetchStudent
     */
    public Student getStudentByID(int studentID) throws SQLException {
        return fetchStudent(studentID);
    }

    /**
     * Finds a book by it's ID
     * @param bookID the book's ID
     * @return a single matching Book object
     * @throws SQLException 
     * @see fetchBook
     */
    public Book getBookByID(int bookID) throws SQLException {
        return fetchBook(Integer.toString(bookID), "bookID");
    }

    /**
     * Finds a book by it's title.
     * @param title the book;s title
     * @return a single matching Book object.
     * @throws SQLException 
     * @see fetchBook
     */
    public Book getBookByTitle(String title) throws SQLException {
        return fetchBook(title, "title");
    }

    /**
     * gets a Record by it's ID
     * @param recordID the record ID
     * @return a single matching Record object.
     * @throws SQLException 
     * @see fetchRecord
     */
    public Record getRecordByID(int recordID) throws SQLException {
        return fetchRecord("" + recordID, "recordID");
    }
    
    /**
     * Gets all Record objects from the database
     * @return an ArrayList containing all Records.
     * @throws SQLException 
     * @see fetchAllRecords
     */
    public ArrayList<Record> getAllRecords() throws SQLException {
        return fetchAllRecords();
    }

    /**
     * Gets a single User by their name
     * @param name the user's username
     * @return a single matching User object
     * @throws SQLException 
     * @see fetchUser
     */
    public User getUserbyName(String name) throws SQLException {
        return fetchUser(name, "name");
    }
    
    /**
     * Will fetch a single user based on an arbitrary comparison search
     * @param compare The search term
     * @param field the field in the database to be searched through
     * @return a single matching User object or Null if it can't find one.
     * @throws SQLException 
     */
    private User fetchUser(String compare, String field) throws SQLException {
        User thisUser;
                
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users where " + field + " = ?");
        statement.setString(1, compare);
        System.out.println("Prepaired Statement: " + statement.toString());
        ResultSet result = statement.executeQuery();
        
        System.out.println("Statement has been executed.");
            
        if(result.next())
            System.out.println("found user " + result.getString("userID"));
        
        thisUser = new User(
                result.getInt("userID"),
                result.getString("name"),
                result.getString("password"),
                result.getInt("security_question"),
                result.getString("security_answer")
        );
        
        return thisUser;
    }
    
    /**
     * Fetches a single Student object from the database that matches an arbitrary Student ID number
     * @param studentID The Student ID to find
     * @return a single matching Student object, or null if none were found.
     * @throws SQLException 
     */
    private Student fetchStudent(int studentID) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM  students WHERE studentID = ?");
        statement.setInt(1, studentID);
        ResultSet result = statement.executeQuery();
        return new Student(
                result.getInt("studentID"),
                result.getString("first_name"),
                result.getString("last_name"),
                result.getString("email"),
                result.getString("phone")
        );
    }

    /**
     * Fetches a single Book object from the database based on an arbitrary comparison search
     * @param compare The search term to be used
     * @param field The field where the search will look for the comparison token
     * @return a single matching Book object, or null if none were found.
     * @throws SQLException 
     */
    private Book fetchBook(String compare, String field) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * from books WHERE " + field + " = ?");
        statement.setString(1, compare);
        ResultSet result = statement.executeQuery();
        return new Book(
                result.getInt("bookID"),
                result.getString("title"),
                result.getInt("edition"),
                result.getString("publisher"),
                result.getDouble("price"),
                result.getInt("pages")
        );
    }

    /**
     * Fetches a single Record object from the database based on an arbitrary comparison search.
     * @param compare The token you are searching for
     * @param field The field upon which the token will be searched for
     * @return a single matching Record object, or Null if none were found.
     * @throws SQLException 
     */
    private Record fetchRecord(String compare, String field) throws SQLException {
        Record record;

        try {

            //we are getting everything in one go for the sake of efficiency.
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM records\n" +
                            "JOIN books ON books.bookID = records.bookID\n" +
                            "JOIN students ON students.studentID = records.studentID\n" +
                            "JOIN users ON users.userID = records.userID\n" +
                            "WHERE " + field + " = ?"
            );
            //statement.setString(1, "records." +field);
            //statement.setString(2, compare);
            statement.setInt(1, Integer.parseInt(compare));
            System.out.println("Statement created, " + field + " must equal " + compare);
            System.out.println(statement.toString());
            ResultSet result = statement.executeQuery();
            ResultSetMetaData meta = result.getMetaData();
            System.out.println("Statement Executed. ");
            
            if(result.next())
                System.out.println("There is at least one result!");
            


            record = new Record(
                    result.getInt("recordID"),
                    new Book(
                            result.getInt("bookID"),
                            result.getString("title"),
                            result.getInt("edition"),
                            result.getString("publisher"),
                            result.getInt("price"),
                            result.getInt("pages")
                    ),
                    new Student(
                            result.getInt("studentID"),
                            result.getString("first_name"),
                            result.getString("last_name"),
                            result.getString("email"),
                            result.getString("phone")
                    ),
                    new User(
                            result.getInt("userID"),
                            result.getString("name"),
                            result.getString("password"),
                            result.getInt("security_question"),
                            result.getString("security_answer")
                    ),
                    //LocalDateTime.parse(result.getString("checkout_date"))
                    result.getTimestamp("checkout_date")
            );

            if(result.getTimestamp("return_date") != null) {
                //record.setReturnDate(LocalDateTime.parse(result.getString("return_date")));
                record.setReturnDate(result.getTimestamp("return_date"));
            }
        }
        catch(SQLException err) {
            System.out.println("SQL Error!");
            System.out.println(err.getMessage());
            err.printStackTrace();
            return new Record();
        }

        return record;
    }

    /**
     * Fetches an ArrayList of all Records in the database.
     * @return an ArrayList of all Records in the database
     * @throws SQLException 
     */
    private ArrayList<Record> fetchAllRecords() throws SQLException {
        PreparedStatement recordStatement;
        ResultSet results;
        ArrayList<Record> list = new ArrayList<>();

        try {
            recordStatement = connection.prepareStatement("SELECT recordID FROM records");
            results = recordStatement.executeQuery();
            while( results.next() )
            {
                //list.add(fetchRecord("recordID", "" + results.getInt(1)));
                list.add(getRecordByID(results.getInt(1)));
                
                /*
                list.add(new Record(
                        results.getInt("recordID"),
                        results.getInt("bookID"),
                        results.getInt("userID"),
                        results.getTimestamp("checkout_date")
                ));
                System.out.println("Found record #" + results.getInt("recordID"));
                */
            }
        }
        catch(SQLException err)
        {
            System.out.println("SQL ERROR!");
            System.out.println(err.getMessage());
        }

        if( list.isEmpty())
            list.add(new Record());

        return list;
    }

    /**
     * Fetches an ArrayList of all Student objects in the database
     * @return an ArrayList of all Student objects
     */
    public ArrayList<Student> getAllStudents(){
        ArrayList<Student> studentList = new ArrayList<>();
        try {
            PreparedStatement students = connection.prepareStatement("SELECT * FROM students");
            ResultSet studentResults = students.executeQuery();
            
            while(studentResults.next())
            {
                studentList.add(new Student(
                        studentResults.getInt("studentID"),
                        studentResults.getString("first_name"),
                        studentResults.getString("last_name"),
                        studentResults.getString("email"),
                        studentResults.getString("phone")
                ));
            }
            
            
        } catch (SQLException ex) {
            
            Logger.getLogger(Library.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return studentList;
    }
    
    static public ArrayList<Book> getAllBooks(){
        ArrayList<Book> bookList = new ArrayList<Book>();
        try{
            PreparedStatement books = connection.prepareStatement("SELECT * FROM books");
            ResultSet bookResults = books.executeQuery();
            
            while(bookResults.next())
            {
                bookList.add(new Book(
                        bookResults.getInt("bookID"),
                        bookResults.getString("title"),
                        bookResults.getInt("edition"),
                        bookResults.getString("publisher"),
                        bookResults.getInt("price"),
                        bookResults.getInt("pages")
                       
                ));
            }
        } catch(SQLException ex) {
          
            Logger.getLogger(Library.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bookList;
    }
    
    //Needs to go away.
    public Timestamp nowTimestamp()
    {
        return Timestamp.from(Instant.now());
    }
    
    /**
     * Write new method for time stamp
     */


}
