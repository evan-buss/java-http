# java-http

Java implementation of an HTTP server without using the java.net.http package.

Mainly created as a learning exercise to more fully understand the HTTP 
protocol.

## Details
- Uses TCP sockets 
- Processes responses and requests manually via text manipulations

## Goals
- [x] Multithreaded server implementation
- [x] Process requests and send a response
- [x] Serve JSON responses to specific queries
- [x] Serve html pages
- [x] GZIP before sending data
    - All response bodies are gzipped sending  
- [ ] Create configuration file to change certain settings
    - Ability to specify the static files folder
- [ ] Load images
    - This should fix the /favicon.ico failed response
- [ ] Load external css
- [ ] Handle requests according to their HTTP method
- [ ] Save and load data from an SQL database