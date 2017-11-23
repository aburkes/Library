package DataManipulation;

import javax.faces.bean.ManagedBean;

/**
 *
 * @author Jimmy Hoang
 */

@ManagedBean
/**
 * Books has six fields bookID, edition, title publisher, pages and prices
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

    /**
     * Used to set values of Parameter
     * @param bookID
     * @param title
     * @param edition
     * @param publisher
     * @param price
     * @param pages 
     */
    public Book(int bookID,String title, int edition, String publisher, double price, int pages ) {
        this.bookID = bookID;
        this.edition = edition;
        this.title = title;
        this.publisher = publisher;
        this.pages = pages;
        this.price = price;
    }

    /**
     * 
     * @param title
     * @param edition
     * @param publisher
     * @param price
     * @param pages 
     */
    public Book(String title, int edition, String publisher, double price, int pages ) {
        this.edition = edition;
        this.title = title;
        this.publisher = publisher;
        this.pages = pages;
        this.price = price;
    }
    
    /**
     * 
     * @return bookID
     */
    public int getBookID() {
        return bookID;
    }

    /**
     * 
     * @return edition
     */
    public int getEdition() {
        return edition;
    }

    /**
     * 
     * @return title 
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @return publisher 
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * 
     * @return pages
     */
    public int getPages() {
        return pages;
    }

    /**
     * 
     * @return price
     */
    public double getPrice() {
        return price;
    }
    
    /**
     * 
     * @param bookID 
     * sets bookID to new values from param
     */
    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    /**
     * 
     * @param edition
     * sets new value of edition
     */
    public void setEdition(int edition) {
        this.edition = edition;
    }

    /**
     * 
     * @param title 
     * sets new value to Title
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * 
     * @param publisher
     * sets new values  to publisher
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * 
     * @param pages
     * sets new value to pages
     */
    public void setPages(int pages) {
        this.pages = pages;
    }

    /**
     * 
     * @param price
     * sets new value to price
     */
    public void setPrice(double price) {
        this.price = price;
    }
    
    /**
     * 
     * @return a String back  
     */
    @Override
    public String toString()
    {
        return(String.format("Book ID:	%d%nTitle:		%s%nEdition:	%d%nPublisher:	%s%nPages:		%d%nPrice:		$%.2f",bookID,
                title,edition,publisher,pages,price));
    }
}
