package xdvrx1_serverProject;

import java.nio.file.Files;
import java.io.*;
import java.net.*;

class GETMethod { 
    
    byte[] processGET(File rootDirectory, 
                      String[] token, 
                      String defaultPage,
                      String http_version,
                      Writer out) {           
        
        try {
            String fileName = token[1];
            
            //add manually the default page
            if (fileName.endsWith("/")) {
                fileName = fileName + defaultPage;
            }
            
            //get the content type for proper encoding of data
            String contentType =
                URLConnection.getFileNameMap().getContentTypeFor(fileName);
            
            if (token.length > 2) {               
                http_version = token[2];
            }
            
            File actualFile = 
                new File(rootDirectory,
                         fileName.substring(1, fileName.length()));
            
            String root = rootDirectory.getPath();
            
            // restrict clients inside the document root  
            if (actualFile.canRead()                                    
                    && actualFile.getCanonicalPath().startsWith(root)) {
                
                byte[] _data = Files.readAllBytes(actualFile.toPath());
                
                if (http_version.startsWith("HTTP/")) {
                    // send a MIME header
                    ServerHeader
                        .serverHeader(out,
                                      "HTTP/1.0 200 OK", 
                                      contentType,
                                      _data.length);
                }
                
                return _data; 
                
            } else { 
                
                // file not found               
                if (http_version.startsWith("HTTP/")) {
                    
                    // send a MIME header
                    ServerHeader
                        .serverHeader(out,
                                      "HTTP/1.0 404 File Not Found",
                                      "text/html; charset=utf-8",
                                      FileNotFoundMessage.content.length());
                }
                
                out.write(FileNotFoundMessage.content);
                out.flush();            
                return null;            
            }
            
        } catch (IOException ioe) { 
            System.out.println(ioe.getMessage());
            return null;
        }
        
    }
    
}