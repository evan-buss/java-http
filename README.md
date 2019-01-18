# java-http

Java implementation of an HTTP server without using the java.net.http package.

Java-HTTP was created as a learning exercise to more fully understand the 
HTTP protocol. The goal is to have a fully functional HTTP implementation 
that can serve static files as well as expose an API that servers JSON data. 

### Current Features

 - API data is stored on a PostgreSQL database 

## Details
- Uses TCP sockets 
- Processes responses and requests manually via text manipulations

## Usage
1) Build the jar
    - The jar can either be a regular jar or a "fat jar".
        - The "fat jar" contains all of the dependencies along with the server.
    - `gradle build` or `gradle shadowJar`
2) Create a directory for static files named `HTML`
    - Inside the HTML file you are to place all of your different website pages
        - Note) You must have a file named `404.html` that will be served when user tries to access and invalid path.
    - The directory should be on the same level as the JAR file
3) Start the server by running the jar file. You must enter the port the 
server should listen on as a command line argument.
    - `java -jar "java-http".jar 8080`

## Goals
- [x] Multithreaded server implementation
- [x] Process requests and send a response
- [x] Serve JSON responses to specific queries
- [x] Serve html pages
- [x] GZIP before sending data files
- [x] Save and load data from an SQL database
- [x] Implement a connection pool for SQL connections.
- [ ] Create configuration file to change certain settings
    - Ability to specify the static files folder
- [ ] Load images
    - This should fix the /favicon.ico failed response
- [ ] Load external css
- [ ] Handle requests according to their HTTP method
- [ ] Add support for other file types.
    - Right now the html file extension is hard coded. This will let me load 
    javascript files from a PWA