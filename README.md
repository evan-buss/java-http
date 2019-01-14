# java-http

Java implementation of an HTTP server without using the java.net.http package.

Mainly created as a learning exercise to more fully understand the HTTP 
protocol.

## Details
- Uses TCP sockets 
- Processes responses and requests manually via text manipulations

##Goals
- [x] Multithreaded server implementation
- [x] Process requests and send a response
- [x] Serve JSON responses to specific queries
- [ ] Serve html pages 
- [ ] Save and load data from an SQL database