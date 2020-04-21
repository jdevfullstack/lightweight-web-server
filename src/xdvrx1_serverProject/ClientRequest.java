package xdvrx1_serverProject;

/*
 * This is where the request of a client
 * is being processed. When we say `client`,
 * it can be Google Chrome or any other browser.
 * This implements `Runnable` to be called in
 * `FileWebServer` class.
 */

import java.nio.file.Files;
import java.io.*;
import java.net.*;

import java.util.logging.*;

public class ClientRequest implements Runnable {
   
   FileNotFoundMessage message1 = new FileNotFoundMessage();
   NotSupportedMessage message2 = new NotSupportedMessage();
   
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
         //raw output stream,
         //in case it is not a text document
         //this will be used purely to pass
         ///the data as bytes
         OutputStream raw = 
            new BufferedOutputStream(connection.getOutputStream());
         
         //for text files that uses the
         //underlying output stream
         Writer out = 
            new OutputStreamWriter(raw);
         
         //for reading the GET header from a browser
         Reader in = 
            new
            InputStreamReader(new
                                 BufferedInputStream(connection
                                                        .getInputStream()),
                              "US-ASCII");
         
         //the request line of a client 
         StringBuilder requestLine = new StringBuilder();
         while (true) {
            int c = in.read();
            if (c == '\r' || c == '\n') break;
            requestLine.append((char) c);
         }
         
         //just converted to string for further processing
         String requestLineToString = requestLine.toString();         
         requestLogger
            .info(connection
                     .getRemoteSocketAddress() + " " + requestLineToString);
         
         //`_keywords` are the words separated from the
         //request line,         
         //`GET /data/ HTTP/1.1`
         String[] _keywords = requestLineToString.split("\\s+");
         String method = _keywords[0];
         String http_version = "";
         
         if (method.equals("GET")) {
            String fileName = _keywords[1];
            
            //add manually the default page
            if (fileName.endsWith("/")) {
               fileName = fileName + defaultPage;
            }
            
            String contentType =
               URLConnection.getFileNameMap().getContentTypeFor(fileName);
            
            if (_keywords.length > 2) {               
               http_version = _keywords[2];
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
               
               // still send the file; 
               //and use the underlying stream
               //instead of the writer
               raw.write(_data); 
               raw.flush(); 
               
            } else { 
               
               // file not found               
               if (http_version.startsWith("HTTP/")) {
                  // send a MIME header
                  ServerHeader
                     .serverHeader(out,
                                   "HTTP/1.0 404 File Not Found",
                                   "text/html; charset=utf-8",
                                   message1.body.length());
               }
               
               out.write(message1.body);
               out.flush();
            }
         } else { 
            // method is not "GET"
            if (http_version.startsWith("HTTP/")) { 
               // send a MIME header
               ServerHeader.serverHeader(out,
                                         "HTTP/1.0 501 Not Implemented",  
                                         "text/html; charset=utf-8",
                                         message2.body.length());
            }
            
            out.write(message2.body);
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