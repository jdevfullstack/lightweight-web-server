# Lightweight Web Server 

*`updated 21 April 2022`*

[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2Fxdvrx1%2Flightweight-web-server&count_bg=%2379C83D&title_bg=%23555555&icon=&icon_color=%23E7E7E7&title=PAGE+VIEWS&edge_flat=false)](https://hits.seeyoufarm.com)

![picture](screenshots/screenshot1.png)

***

I'm happy to share with you my custom server written in Java.

Network programming is very important. Remember that the Internet
was created for that very reason: that is, computers
must communicate to one another around the world and 
data must arrive as soon as possible.

Unlike C, in Java you don't need to do much complexity, Java
does that exactly. But remember, data will always end as bytes.
So, everything can be processed by a computer as long as the 
programmer can represent the data as bytes.

Remember that both servers and
browsers can pass data to one another, but typically a browser will always
initiate the connection while the server is just always waiting for a connection.
The same is true for other servers like Telnet or FTP servers.

Yet, HTTP is also good to pass any data as long
as it is expressed in bytes. HTTP is so famous now as it is the protocol of
web servers and browsers, so more often, we always link HTTP for web sites.
Also, updated browsers nowadays can display more than text documents like PDF and
images and even markdown files.

Also, bytes are not even numbers, they are just representations for us humans because
a computer can only understand the presence or absence of an electrical pulse: that
is, again, represented as 0 and 1. For today, of course, typical users will hate seeing
0s and 1s so programmers do the abstraction.

This server is enabled for GET and POST methods. Please see the instructions.

For this app, this is capable of getting contents from a directory
and displaying that through a browser and capable of accepting forms using
the POST method. 

Once you get the runnable `jar` file through
`mvn install`, say, you put the `.jar` file in Desktop
directory, all the files there can simply be accessed
through a web browser, preferably Google Chrome, as
this browser can display images & videos by default.

For posting, you need to have a html form,
so that data can be entered.

## Q&A

If you have questions, please utilize the
`Discussions` feature of GitHub.
   
You can also create a pull request to start the discussion or query/ies.

Or, you can raise an issue. I promise I will answer your questions. 

## Compiling & Using The Server

This is namespaced as package `xdvrx1_serverProject`. It is up to your IDE 
how it actually manages Java projects. 

But now, I decided to pick the Maven build tool
using the Command Prompt only. I use Java on the
Command Prompt also.

If you want to follow my setup, these are the steps:

1. Install Java SDK 8 if there is none.
2. Install Maven if there is none.
3. Add both in the System Environment Variables
so that you can use it through the Command Prompt.
4. Using the Command Prompt, 
go to the project folder `lightweight-webserver`.
5. Compile or build the project.

### Command Prompt Using Maven And Java  
`mvn clean` to clean the directory

`mvn install` to install including
the runnable `.jar`

`mvn compile` to simply compile

To run the runnable `.jar`, go
to the `target` directory and type
on the Command prompt `java -jar [filename]`

and it is standalone, you can simply
copy the `.jar` and put that to any directory
you want and it will start hosting
there once you run that.

### How To Run The Server 
1. Compile the project.
2. Run the project.
3. Put `index.html` to your working directory
and other sample files.
3. Open a browser.
4. Type in the address bar `localhost`.
5. See the default web page!
6. Access every file in that root directory
by typing the filename relative to that directory
or just create a list on the default web page.
7. If you are quite confused, you can download my release.

Remember also that the `index.html`
is the default web page but of course
you can change that.

Once you have created the executable jar file,
all files within the directory of this executable jar file
can be accessed through this server. 

As my example, in my release the executable jar file must
have its own folder, then inside that folder is the default page
`index.html`, then you can create a subfolder, in my case, 
`data` and you can put files there to be served by this webserver. 

And there is the form sample to post. When you click `add record`,
the data will be sent as POST.

## Contributing

1. Fork it!
2. Then, make changes and create a pull request. 
I'm much more willing to collaborate with you!

## License

MIT- the permissive license

## TO DO
1. I still want to add a GUI, so
that this will be user-friendly
