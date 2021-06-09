package xdvrx1_serverProject;

import java.io.*;

class ServerApp {   
    public void build() {
        
        try {         
            //the relative root directory
            //it's up to you if you want to change this
            File currentDir = new File(".");
            
            //create an instance of `FileWebServer`
            //at the current directory and using port 80
            //again, it is up to you when you want to change
            //the port
            FileWebServer filewebserver = new FileWebServer(currentDir, 80);
            
            //call `start` method that
            //contains the call for the Runnable `run`
            //of `ClientRequest` class
            filewebserver.start();
            
        } catch (IOException ex) {
            //throws an exception if `currentDir`
            //is not recognized as such
            System.out.println(ex.getMessage());
        }
    }
    
}