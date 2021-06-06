package xdvrx1_serverProject;

import java.io.*;

import org.junit.*;

public class FileWebServerTest {
    
    File rootDirectory;    
    FileWebServer fileWebServer;    
    File tempFile;
    
    @Before 
    public void setUp() throws Exception {
        
        rootDirectory = new File(".");        
        fileWebServer = new FileWebServer(rootDirectory, 80);        
        tempFile = File.createTempFile("sample", null);        
        
    }
    
    @Test
    public void testObjectShouldNotBeNull() {
        //a simple test whether the object
        //is successfully created
        Assert.assertNotNull(fileWebServer);
    }
    
    @Test(expected = IOException.class)
    public void testConstructorShouldThrowException() throws IOException  {
        //`tempFile` is not a directory, it is a file
        //so this should throw the exception
        FileWebServer fileWebServer2 = new FileWebServer(tempFile, 80);    
        
    }
    
    @Test
    public void testConstructorShouldNotThrowException() throws IOException  {
        //rootDirectory is a real directory, so
        //this will not throw the exception
        FileWebServer fileWebServer3 = new FileWebServer(rootDirectory, 80);    
        
    }
    
    @After
    public void tearDown() throws Exception { 
        tempFile.deleteOnExit();        
    }
}
