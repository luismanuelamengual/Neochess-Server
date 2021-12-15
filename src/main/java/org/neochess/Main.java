package org.neochess;

public class Main {

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        server.setPort(8080);
        server.start();
        server.join();
    }
}
