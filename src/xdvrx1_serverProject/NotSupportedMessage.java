package xdvrx1_serverProject;

class NotSupportedMessage {
    
    static final String content = new StringBuilder("<html>\r\n")
        .append("<head><title>Not Implemented</title>\r\n")
        .append("</head>\r\n")
        .append("<body>")
        .append("<h1>HTTP Error 501: Not Yet Supported Method</h1>\r\n")
        .append("</body></html>\r\n")
        .toString();
}