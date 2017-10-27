package DataManipulation;

/**
 *
 * @author Jimmy Hoang
 */
public class Book {
    //fields
    private int bookID;
    private int edition;
    private String title;
    private String publisher;
    private int pages;
    //put as double because price has a $xx.xx format
    private double price;

    public Book(int bookID,String title, int edition, String publisher, double price, int pages ) {
        this.bookID = bookID;
        this.edition = edition;
        this.title = title;
        this.publisher = publisher;
        this.pages = pages;
        this.price = price;
    }

    public Book(String title, int edition, String publisher, double price, int pages ) {
        this.edition = edition;
        this.title = title;
        this.publisher = publisher;
        this.pages = pages;
        this.price = price;
    }
//methods
    //Get Methods
    public int getBookID() {
        return bookID;
    }

    public int getEdition() {
        return edition;
    }

    public String getTitle() {
        return title;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getPages() {
        return pages;
    }

    public double getPrice() {
        return price;
    }
    
    //set methods

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    @Override
    public String toString()
    {
        return(String.format("Book ID:	%d%nTitle:		%s%nEdition:	%d%nPublisher:	%s%nPages:		%d%nPrice:		$%.2f",bookID,
                title,edition,publisher,pages,price));
    }
}
