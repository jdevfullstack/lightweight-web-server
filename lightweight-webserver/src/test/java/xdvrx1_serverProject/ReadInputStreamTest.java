package xdvrx1_serverProject;

import java.io.*;
import java.net.*;

import org.junit.*;

//streams are expressed referring to files
//but since we want to test the functionality,
//and since streams are treated almost the same,
//we use streams using files
public class ReadInputStreamTest {
    
    File rootDirectory = new File(".");
    File tempFile;
    String defaultPage;
    Socket connection;
    
    InputStream is;
    OutputStream out;
    
    FileInputStream fin;
    BufferedInputStream bis;
    
    Reader in;
    Writer out2;
    
    ReadInputStream readInputStream;
    
    @Before
    public void setUp() throws Exception {
        tempFile = File.createTempFile("tempFileX", ".txt");
        
        out = new FileOutputStream(tempFile);
        out = new BufferedOutputStream(out);
        
        out2 = new OutputStreamWriter(out, "US-ASCII");
        
        out2.append('a');
        out2.append('b');
        out2.append('c');
        out2.append('\r');
        out2.append('\n');
        out2.append(' ');
        
        out2.flush();    
        
        is = new FileInputStream(tempFile); 
        bis = new BufferedInputStream(is); 
        
        in = new InputStreamReader(bis, "US-ASCII");
        
        readInputStream = new ReadInputStream();
        
        defaultPage = "index.html";
        connection = new Socket();
        
    }
    
    @Test
    public void testMethodShouldReturnNotNull() {
        //test the method whether its returning
        //an object
        Assert.assertNotNull(readInputStream.
                                 readUserRequest(bis, in, connection));
    }
    
    @Test
    public void testMethodShouldReturnAnObject() {
        
        //a simple expectation from the encoded data
        StringBuffer expectedResult = new StringBuffer();
        expectedResult.append('a');
        expectedResult.append('b');
        expectedResult.append('c');
        expectedResult.append('\r');
        expectedResult.append('\n'); 
        expectedResult.append(' ');
        
        Assert.assertEquals(expectedResult.toString(), 
                            readInputStream.
                                readUserRequest(bis, in, connection).toString());
        
    }
    
    @Test(expected = IOException.class)
    public void testMethodShouldThrowException() throws IOException  {
        
        //when we close the input stream, it should 
        //throw an exception
        in.close();
        StringBuffer bufferResult = readInputStream.readUserRequest(bis, in, connection); 
        //from the method, it will go directly to else
        //and will return null, thereby indicating that there
        //is an exception        
        
        if (bufferResult == null)
            //in order for this test to pass            
            //it should deliberately throw the exception
            throw new IOException("IOException");
        
    }
    
    @After
    public void tearDown() throws Exception {         
        tempFile.deleteOnExit();        
    }
    
}
