package xdvrx1_serverProject;

/**
 * This is where the request of a client
 * is being processed. When we say `client`,
 * it can be Google Chrome or any other browser.
 * This implements `Runnable` to be called in
 * `FileWebServer` class.
 */

import java.io.*;
import java.net.*;
import java.util.logging.*;

public class ClientRequest implements Runnable {
    
    private String defaultPage = "index.html";
    private File rootDirectory;   
    private Socket connection;
    
    private final static Logger requestLogger = 
        Logger.getLogger(ClientRequest.class.getCanonicalName());
    
    //the constructor
    public ClientRequest(File rootDirectory,
                         String defaultPage,
                         Socket connection) {
        
        //check if the given rootDirectory is a file
        //and will explicitly throw an exception
        if (rootDirectory.isFile()) {
            throw new 
                IllegalArgumentException("rootDirectory must be"
                                             + "a directory, not a file");
        } 
        
        //will try to get the canonical pathname
        //from the supplied `rootDirectory` argument
        try {
            rootDirectory = rootDirectory.getCanonicalFile();
        } catch (IOException ex) {
            requestLogger.log(Level.WARNING, "IOException", ex);
        }
        
        //constructors `rootDirectory` is assigned to
        //the successful `rootDirectory`
        this.rootDirectory = rootDirectory;
        
        if (defaultPage != null) {
            this.defaultPage = defaultPage;
        }
        
        this.connection = connection;
        
    }
    
    @Override
    public void run() {
        
        try {
            //remember, once the connection is established
            //the server will use the Socket to pass
            //data back and forth
            
            //raw output stream,
            //in case it is not a text document
            //this will be used purely to pass
            //the data as bytes         
            OutputStream raw = 
                new BufferedOutputStream(connection.getOutputStream());
            
            //for text files that uses the
            //underlying output stream
            Writer out = 
                new OutputStreamWriter(raw);
            
            //needed to add new line correctly
            //for different platforms
            BufferedWriter bufferedOut = 
                new BufferedWriter(out);
            
            BufferedInputStream bis =
                new BufferedInputStream(connection.getInputStream()); 
            
            //for reading the GET header from a browser
            Reader in = 
                new
                InputStreamReader(bis, "US-ASCII");
            
            //the request send by a client 
            //take note, this can be loaded into a data structure
            //instead of using StringBuffer to generate string
            //but, it's up to you, for demonstration
            //I will just use the StringBuffer to build
            //the string object
            ReadInputStream readInputStream = new ReadInputStream();
            StringBuffer userRequest = 
                readInputStream.readUserRequest(bis, in, connection);
            
            //build a string object from StringBuffer
            String userRequestToString = userRequest.toString();
            
            //get the first line through this index
            int indexOfFirst = userRequestToString.indexOf("\r\n");         
            String firstLine = userRequestToString
                .substring(0,indexOfFirst);
            
            //express it in the logger, mostly for debugging purposes
            requestLogger
                .info(connection
                          .getRemoteSocketAddress() + " " + firstLine);
            
            //`token` are the words separated from the request line,         
            //for example, `GET /data/ HTTP/1.1`
            String[] token = firstLine.split("\\s+");
            //0 index tells the method
            String method = token[0];
            //null at first
            String http_version = "";
            
            if (method.equals("GET")) {
                
                GETMethod getMethod = new GETMethod();
                byte[] _data = getMethod.processGET(rootDirectory, 
                                                    token, 
                                                    defaultPage,
                                                    http_version,
                                                    out);
                // still send the file; 
                //and use the underlying stream
                //instead of the writer
                raw.write(_data); 
                raw.flush();            
                
            } else if(method.equals("POST")) { 
                
                POSTMethod postMethod = new POSTMethod();
                
                String requestBody = 
                    postMethod.returnPOSTData(userRequestToString);
                
                //we use also the buffered out writer
                //to make sure that the new line will be correct
                //for all platforms
                bufferedOut.write("data recorded:");
                bufferedOut.newLine();
                bufferedOut.write(requestBody);
                bufferedOut.flush();
                
            } else {
                
                //not yet implemented methods
                if (http_version.startsWith("HTTP/")) { 
                    // send a MIME header
                    ServerHeader.serverHeader(out,
                                              "HTTP/1.0 501 Not Implemented",  
                                              "text/html; charset=utf-8",
                                              NotSupportedMessage.content.length());
                }
                
                out.write(NotSupportedMessage.content);
                out.flush();
            }
        } catch (IOException ex) { 
            requestLogger
                .log(Level.WARNING, "Can't talk to: " 
                         + connection.getRemoteSocketAddress(), ex);
        } finally { 
            try { 
                connection.close(); 
            } catch (IOException ex) {
                requestLogger.log(Level.WARNING, "IO exception", ex);
            }
        }
    }
    
}
