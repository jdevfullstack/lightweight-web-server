package xdvrx1_serverProject;

class POSTMethod {
    
    String returnPOSTData(String userRequestToString) {      
        
        //get the request body for POST method
        //add 4, because the index that
        //will be returned is relative to the 
        //very first occurence of the string
        String requestBody = 
            userRequestToString
            .substring(userRequestToString.lastIndexOf("\r\n\r\n") + 4);
        
        //just showing the input data back to the client
        //a lot of things can be done for the request body
        //it can go directly to a file or a database,
        //or be loaded into an XML file for further processing      
        return requestBody;
    }
}