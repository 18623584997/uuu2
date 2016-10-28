package main;

import client.Client;

/**
 * Created by Administrator on 2016/10/25.
 */
public class ClientMain {
    public static void main(String[] args) {
        Client c = new Client();
        c.connect();
    }
}
