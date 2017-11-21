package DataManipulation;

import java.sql.Timestamp;
import javax.faces.bean.ManagedBean;

@ManagedBean
public class Record {

    public int recordID;
    public Book book;
    public User user;
    public Student student;
    public Timestamp checkoutDate, returnDate;

    public Record(){}

    public Record(int recordID, Book book, Student student, User user, Timestamp checkoutDate) {
        this.recordID = recordID;
        this.book = book;
        this.user = user;
        this.student = student;
        this.checkoutDate = checkoutDate;
        this.returnDate = returnDate;
    }

    public Record(Book book, Student student, User user, Timestamp checkoutDate) {
        this.book = book;
        this.user = user;
        this.student = student;
        this.checkoutDate = checkoutDate;
        this.returnDate = returnDate;
    }

    public Record(int bookID, int studentID, int userID, Timestamp checkoutDate) {
        this.book = new Book(bookID, null, 0, null, 0, 0);
        this.user = new User(userID, null, null, 0, null);
        this.student = new Student(studentID, null, null, null, null);
        this.checkoutDate = checkoutDate;
    }

    public int getRecordID() {
        return recordID;
    }

    public Book getBook() {
        return book;
    }

    public User getUser() {
        return user;
    }

    public Student getStudent() {
        return student;
    }

    public Timestamp getCheckoutDate() {
        return checkoutDate;
    }

    public Timestamp getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Timestamp returnDate) {
        this.returnDate = returnDate;
    }
}
