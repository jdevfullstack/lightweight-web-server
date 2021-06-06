package xdvrx1_serverProject;

class FileNotFoundMessage  {
    
    static final String content = 
        new StringBuilder("<html>\r\n")
        .append("<head><title>File Not Found</title>\r\n")
        .append("</head>\r\n")
        .append("<body>")
        .append("<h1>HTTP Error 404: File Not Found [Try again later]</h1>\r\n")
        .append("</body></html>\r\n")
        .toString();
    
}
