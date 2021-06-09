package xdvrx1_serverProject;

/**
 * This is the actual server class.
 * This will call the overridden 
 * method `run()` of `Runnable`. 
 */ 

import java.util.concurrent.*; 
import java.io.*; 
import java.net.*; 

import java.util.logging.*;

public class FileWebServer {
    
    private final File rootDirectory;
    private final int port;
    private static final int pool_count = 1000;
    private static final String defaultPage = "index.html";
    
    private static final Logger 
        serverLogger = Logger.getLogger(FileWebServer
                                            .class.getCanonicalName());
    
    //the constructor
    public FileWebServer(File rootDirectory, int port) 
        throws IOException {
        
        if (!rootDirectory.isDirectory()) {
            throw new IOException(rootDirectory
                                      + " is not a directory");
        }
        
        this.rootDirectory = rootDirectory; 
        this.port = port;
        
    }
    
    //void start
    public void start() 
        throws IOException {
        
        ExecutorService pool = 
            Executors.newFixedThreadPool(pool_count);
        
        try (ServerSocket server = new ServerSocket(port)) {
            
            serverLogger.info("Listening on port " + server.getLocalPort());
            serverLogger.info("@DocumentRoot");
            
            while (true) {
                try {
                    Socket request = server.accept();
                    Runnable r = 
                        new ClientRequest(rootDirectory, defaultPage, request);
                    pool.submit(r);
                } catch (IOException ex) {
                    serverLogger.log(Level.WARNING, "Error accepting connection", ex);
                }
            }
        }
    }
}