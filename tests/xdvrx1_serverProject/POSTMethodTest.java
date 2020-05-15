package xdvrx1_serverProject;

import org.junit.*;

public class POSTMethodTest {
    
    POSTMethod postMethod = new POSTMethod();
    //after the third line, that is one blank line
    //in HTTP request, following that is the body of the
    //request
    String userRequestToString = 
        "first line" + "\r\n" +
        "second line" + "\r\n" +
        "third line" + "\r\n\r\n" +
        "Hello World";
    
    @Test
    public void testTheLineEndingsOfClientRequest() {
        Assert.assertEquals("Hello World", 
                            postMethod.returnPOSTData(userRequestToString));  
    }
}