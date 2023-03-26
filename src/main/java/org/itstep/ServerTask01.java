package org.itstep;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTask01 {
    public static final int PORT = 11_000;

    public static void main(String[] args) {
        int value = 2048;
        try (InputStream in = new FileInputStream("alice.txt");
             ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Wait for connection...");
            Socket client = serverSocket.accept();

            try (OutputStream out = client.getOutputStream()) {
                System.out.println("Client ready for input data");
                while (in.available() > 0) {
                    byte[] buffer = in.readNBytes(value);
                    out.write(buffer);
                }
            }
            System.out.println("File send successful");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

