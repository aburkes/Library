package DataManipulation;

import java.sql.Timestamp;
import javax.faces.bean.ManagedBean;

/**
 * 
 * @author Jimmy
 */
@ManagedBean
/**
 * Record class holds six fields recordID, book, user, student, checkoutDate, returnDate
 */
public class Record {

    public int recordID;
    public Book book;
    public User user;
    public Student student;
    public Timestamp checkoutDate, returnDate;

    /**
     * Empty Constructor of Record
     */
    public Record(){}

    /**
     * Constructor with Parameter
     * 
     * @param recordID
     * @param book
     * @param student
     * @param user
     * @param checkoutDate 
     */
    public Record(int recordID, Book book, Student student, User user, Timestamp checkoutDate) {
        this.recordID = recordID;
        this.book = book;
        this.user = user;
        this.student = student;
        this.checkoutDate = checkoutDate;
        this.returnDate = returnDate;
    }

    /**
     * Constructor with Parameter 
     * 
     * @param book
     * @param student
     * @param user
     * @param checkoutDate 
     */
    public Record(Book book, Student student, User user, Timestamp checkoutDate) {
        this.book = book;
        this.user = user;
        this.student = student;
        this.checkoutDate = checkoutDate;
        this.returnDate = returnDate;
    }

    /**
     * Constructor with Parameter
     * 
     * @param bookID
     * @param studentID
     * @param userID
     * @param checkoutDate 
     */
    public Record(int bookID, int studentID, int userID, Timestamp checkoutDate) {
        this.book = new Book(bookID, null, 0, null, 0, 0);
        this.user = new User(userID, null, null, 0, null);
        this.student = new Student(studentID, null, null, null, null);
        this.checkoutDate = checkoutDate;
    }

    /**
     * 
     * @return recordID
     */
    public int getRecordID() {
        return recordID;
    }
    
    /**
     * 
     * @return book 
     */
    public Book getBook() {
        return book;
    }
    /**
     * 
     * @return user 
     */
    public User getUser() {
        return user;
    }

    /**
     * 
     * @return student 
     */
    public Student getStudent() {
        return student;
    }

    /**
     * 
     * @return checkoutDate 
     */
    public Timestamp getCheckoutDate() {
        return checkoutDate;
    }

    /**
     * 
     * @return returnDate 
     */
    public Timestamp getReturnDate() {
        return returnDate;
    }

    /**
     * 
     * @param returnDate 
     * sets a Time for returnDate
     */
    public void setReturnDate(Timestamp returnDate) {
        this.returnDate = returnDate;
    }
    
    /**
     * 
     * @return a String 
     */
    @Override
    public String toString() {
        String toReturn = "record: " + this.recordID + "; ";
        if(this.book != null)
            toReturn += "Title: " + this.book + "; ";
        if(this.user != null)
            toReturn += "User: " + this.user + "; ";
        if((this.student != null))
            toReturn += "Student: " + this.student + "; ";
        if(this.checkoutDate != null)
            toReturn += "Checkout: " + this.checkoutDate + "; ";
        if(this.returnDate != null)
            toReturn += "Return: " + this.returnDate;
        else
            toReturn += "Book is still checked out";
        
        return toReturn;
    }
    
}
