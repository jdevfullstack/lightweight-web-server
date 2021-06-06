package xdvrx1_serverProject;

import java.nio.file.Files;
import java.io.*;
import java.net.*;

import org.junit.*;

//streams are expressed referring to files
//but since we want to test the functionality,
//and since streams are treated almost the same,
//we use streams using files
public class GETMethodTest {
    
    File rootDirectory;
    String defaultPage; 
    String http_version; 
    
    GETMethod getMethod;
    
    String firstLine;
    String[] token;
    
    File tempFile;    
    OutputStream out;
    Writer out2;
    File rootDirectoryX;
    
    @Before
    public void setUp() throws Exception {
        rootDirectoryX = new File(".");

        //get the absolute path
        //you should not do this when this program
        //is run, or else a client will have an access to 
        //other directories you don't want to expose
        //but for testing purposes, we need to get the
        //canonical path
        String absolutePath = rootDirectoryX.getCanonicalPath();
        
        rootDirectory = new File(absolutePath);
        
        firstLine = "GET /README.md HTTP/1.1";
        token = firstLine.split("\\s+");
        
        defaultPage = "index.html";
        http_version = null;
        
        //the `tempFile` and `out` are just here
        //for testing, but it is not the focus of testing
        tempFile = File.createTempFile("tempFileXX", ".txt");
        out = new FileOutputStream(tempFile);
        out = new BufferedOutputStream(out);
        out2 = new OutputStreamWriter(out, "US-ASCII");
        
        getMethod = new GETMethod();        
    }
    
    @Test
    public void testObjectShouldNotBeNull() {
        
        //test both the object and the method
        Assert.assertNotNull(getMethod); 
        Assert.assertNotNull(getMethod
                                 .processGET(rootDirectory, token, 
                                             defaultPage, http_version,
                                             out2));        
    }
    
    @Test
    public void testMethodShouldReturnNull() {
        //since rootDirectoryX did not get the
        //absolute path,
        //inside the real method, the
        //`if (actualFile.getCanonicalPath().startsWith(root))` will fail
        //thereby returning null as indicated in its `else` block
        Assert.assertNull(getMethod.
                              processGET(rootDirectoryX, token, 
                                         defaultPage, http_version,
                                         out2));   
    }
    
    @After
    public void tearDown() throws Exception {
        tempFile.deleteOnExit();        
    }
    
}
