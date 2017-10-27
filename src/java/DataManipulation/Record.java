package DataManipulation;

import java.time.LocalDateTime;

public class Record {

    private int recordID;
    private Book book;
    private User user;
    private Student student;
    private LocalDateTime checkoutDate, returnDate;

    public Record(int recordID, Book book, Student student, User user, LocalDateTime checkoutDate) {
        this.recordID = recordID;
        this.book = book;
        this.user = user;
        this.student = student;
        this.checkoutDate = checkoutDate;
        this.returnDate = returnDate;
    }

    public Record(Book book, Student student, User user, LocalDateTime checkoutDate) {
        this.book = book;
        this.user = user;
        this.student = student;
        this.checkoutDate = checkoutDate;
        this.returnDate = returnDate;
    }

    public Record(int bookID, int studentID, int userID, LocalDateTime checkoutDate) {
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

    public LocalDateTime getCheckoutDate() {
        return checkoutDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }
}
