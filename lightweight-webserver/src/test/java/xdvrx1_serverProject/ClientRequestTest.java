package xdvrx1_serverProject;

import java.io.*;
import java.net.*;

import org.junit.*;

//streams are expressed referring to files
//but since we want to test the functionality,
//and since streams are treated almost the same,
//we use streams using files
public class ClientRequestTest {
    
    File rootDirectory;
    String defaultPage;
    Socket connection;
    File tempFile;
    
    ClientRequest clientRequest;
    
    @Before
    public void setUp() throws Exception {
        rootDirectory = new File(".");
        defaultPage = "index.html";
        //a blank socket,
        connection = new Socket();
        
        tempFile = File.createTempFile("sample", null); 
        
        clientRequest = new ClientRequest(rootDirectory,
                                          defaultPage,
                                          connection);
    }
    
    @Test
    public void testObjectShouldNotBeNull() throws IOException {
        //just a basic test whether this object
        //is successfully created
        Assert.assertNotNull(clientRequest);        
    }
        
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorShouldThrowException()  {
        //`tempFile` is not a directory, it is a file
        //so, this constructor, as expected, should
        //throw IllegalArgumentException
        ClientRequest clientRequest2 = 
            new ClientRequest(tempFile, defaultPage, connection);
    }
    
    @Test
    public void testConstructorShouldNotThrowException()  {
        //now, rootDirectory is a real directory
        //it will not throw an exception
        ClientRequest clientRequest3 = 
            new ClientRequest(rootDirectory, defaultPage, connection);       
        
    }
    
    @After
    public void tearDown() throws Exception {
        tempFile.deleteOnExit();        
    }
    
}
