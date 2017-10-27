package DataManipulation;

import javax.faces.bean.ManagedBean;



@ManagedBean
public class DataManipulation {
    private String testString;

    public DataManipulation() {
        this.testString = "Test!";
    }

    public String getTestString() {
        return testString;
    }

    public void setTestString(String testString) {
        this.testString = testString;
    }
    
    
}
