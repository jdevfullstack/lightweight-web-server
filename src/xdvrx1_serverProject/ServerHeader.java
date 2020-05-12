package xdvrx1_serverProject;

import java.util.*;
import java.io.*;

class ServerHeader {
   
   private static Date current = new Date();
   
   static void serverHeader(Writer out, 
                            String responseCode,
                            String contentType,
                            int length)
      throws IOException {
      
      out.write(responseCode + "\r\n");
      out.write("Date: " + current + "\r\n");
      out.write("Server: `xdvrx1_Server` 3.0\r\n");
      out.write("Content-length: " + length + "\r\n"); 
      out.write("Content-type: " + contentType + "\r\n\r\n");
      out.flush();
   }
   
}
