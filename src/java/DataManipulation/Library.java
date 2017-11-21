package DataManipulation;


import javax.faces.bean.ManagedBean;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.converter.LocalDateTimeStringConverter;


@ManagedBean
public class Library {

    final private String CONNECTION_DATABASE = "jdbc:mysql://localhost:3306/library";
    final private String CONNECTION_AUTH_USER = "librarian";
    final private String CONNECTION_AUTH_PASSWORD = "booksrfun451";

    //should be in the specs, but isn't!
    private Connection connection;

    //also should be in the specs!
    public Library(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(CONNECTION_DATABASE, CONNECTION_AUTH_USER, CONNECTION_AUTH_PASSWORD);
        }
        catch(SQLException e) {
            System.out.println("SQL error encountered.");
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void newUser(User user, String password, String answer) throws SQLException {
        createUser(user, password, answer);
    }

    public void newStudent(Student student) throws SQLException {
        createStudent(student);
    }

    public void newBook(Book book) throws SQLException {
        createBook(book);
    }

    public void newRecord(Record record) throws SQLException {
        createRecord(record);
    }

    private void createUser(User user, String password, String answer) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO users VALUES (DEFAULT, ?, ?, ?, ?)");
        statement.setString(1, user.getUserName());
        statement.setString(2, password);
        statement.setInt(3, user.getSecurityQuestion());
        statement.setString(4, answer);
        statement.execute();
    }

    private void createStudent(Student student) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO students VALUES (DEFAULT, ?, ?, ?, ?)");
        statement.setString(1, student.getFirstName());
        statement.setString(2, student.getLastName());
        statement.setString(3, student.getEmail());
        statement.setString(4, student.getPhone());
        statement.execute();
    }

    private void createBook(Book book) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO books VALUES (DEFAULT, ?, ?, ?, ?, ?)");
        statement.setString(1, book.getTitle());
        statement.setInt(2, book.getEdition());
        statement.setString(3, book.getPublisher());
        statement.setDouble(4, book.getPrice());
        statement.setInt(5, book.getPages());
        statement.execute();
    }

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

    public User getUserByID(int userID) throws SQLException {
        return fetchUser(Integer.toString(userID), "userID");
    }

    public Student getStudentByID(int studentID) throws SQLException {
        return fetchStudent(studentID);
    }

    public Book getBookByID(int bookID) throws SQLException {
        return fetchBook(Integer.toString(bookID), "bookID");
    }

    public Book getBookByTitle(String title) throws SQLException {
        return fetchBook(title, "title");
    }

    public Record getRecordByID(int recordID) throws SQLException {
        return fetchRecord(Integer.toString(recordID), "recordID");
    }
    public ArrayList<Record> getAllRecords() throws SQLException {
        System.out.println("Starting...");
        return fetchAllRecords();
    }

    public User getUserbyName(String name) throws SQLException {
        return fetchUser(name, "name");
    }



    private User fetchUser(String compare, String field) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users where ? = ?");
        statement.setString(1, field);
        statement.setString(2, compare);
        ResultSet result = statement.executeQuery();
        return new User(
                result.getInt("userID"),
                result.getString("name"),
                result.getString("password"),
                result.getInt("security_question"),
                result.getString("security_answer")
        );
    }

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

    private Book fetchBook(String compare, String field) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * from books WHERE ? = ?");
        statement.setString(1, field);
        statement.setString(2, compare);
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

    private Record fetchRecord(String compare, String field) throws SQLException {
        Record record;

        //we are getting everything in one go for the sake of efficiency.
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM records\n" +
                        "JOIN books ON books.bookID = records.bookID\n" +
                        "JOIN students ON students.studentID = records.studentID\n" +
                        "JOIN users ON users.userID = records.userID\n" +
                        "WHERE ? = ?"
        );
        statement.setString(1, field);
        statement.setString(2, compare);
        ResultSet result = statement.executeQuery();


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

        if(!result.getString("checkout_date").isEmpty()) {
            //record.setReturnDate(LocalDateTime.parse(result.getString("return_date")));
            record.setReturnDate(result.getTimestamp("return_date"));
        }

        return record;
    }

    private ArrayList<Record> fetchAllRecords() throws SQLException {
        PreparedStatement statement;
        ResultSet results;
        ArrayList<Record> list = new ArrayList<>();

        
        System.out.println("Got to the private method.");
        
        try {
            statement = connection.prepareStatement("SELECT * FROM records\n");

            results = statement.executeQuery();

            while( results.next() )
            {
                list.add(new Record(
                        results.getInt("recordID"),
                        results.getInt("bookID"),
                        results.getInt("userID"),
                        results.getTimestamp("checkout_date")
                ));
                System.out.println("Found record #" + results.getInt("recordID"));
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
    public Timestamp nowTimestamp()
    {
        return Timestamp.from(Instant.now());
    }


}
