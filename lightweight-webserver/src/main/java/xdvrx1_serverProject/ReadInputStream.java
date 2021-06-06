package xdvrx1_serverProject;

import java.io.*;
import java.net.*;

class ReadInputStream {
    
    StringBuffer readUserRequest(BufferedInputStream bis,
                                 Reader in,
                                 Socket connection) {
        
        StringBuffer userRequest = new StringBuffer();
        
        try { 
            //this will be the basis to get
            //all the bytes from the stream
            int bufferSize = bis.available();
            
            while (true) { 
                if (userRequest.length() > bufferSize-1) {
                    //for performance, always shutdown
                    //after breaking from this loop
                    if (connection.isConnected()) {
                        connection.shutdownInput();
                    }
                    break;
                }
                
                //read() of Reader is actually a blocking
                //method, and without proper algorithm
                //this will hang the entire program
                int c = in.read();             
                userRequest.append((char) c);
                
                //ignore the line endings,
                //the Reader will interpret this as end of buffer
                //we need to read the entire content of the buffer
                if (c == '\n' || c == '\r' || c == -1) continue;
            }
            return userRequest;
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            return null;
        }
    }
}
