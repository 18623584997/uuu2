package main;

import server.Server;

/**
 * Created by Administrator on 2016/10/25.
 */
public class ServerMain {
    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
