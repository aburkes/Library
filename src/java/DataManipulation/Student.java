package DataManipulation;

import javax.faces.bean.ManagedBean;

/**
 *
 * @author Jimmy Hoang
 */
@ManagedBean
public class Student {
    //fields 
    private int studentID;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    public Student(){}

    //Constructor with student ID implemented
    public Student(int studentID, String firstName, String lastName, String email, String phone) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }
    //Constructor without insertted ID
    public Student(String firstName, String lastName, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }
//methods
    
    //Get Functions
    public int getStudentID() { return studentID; }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
    //Setter functions
    //to be used if user wants to edit their info
    public void setStudentID(int studentID)
    {
        this.studentID = studentID;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    //toString Function
    @Override
    public String toString(){
        return(String.format("Name: %s,%s %nEmail:  %s %nPhone: %s",firstName,lastName,email,phone));
    }
    
    
}
