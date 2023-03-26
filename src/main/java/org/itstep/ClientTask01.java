package org.itstep;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;


public class ClientTask01 {
    public static final int PORT = 11_000;
    public static final String HOST = "127.0.0.1";
// Вариант 1
    public static void main(String[] args) throws IOException {
        int value = 2048;
        String fileName = "alice-copy.txt";
        if (Files.exists(Path.of(fileName))) {
            System.out.println("File exists");
            Files.delete(Path.of(fileName));
        }
        if (Files.notExists(Path.of(fileName))) {
            System.out.println("File does not exist");
        }
        Socket client = new Socket();
        SocketAddress serverSocket =
                new InetSocketAddress(InetAddress.getByName(HOST), PORT);
        System.out.println("Connection to server");
        client.connect(serverSocket);
        try (InputStream in = client.getInputStream();
             FileOutputStream out = new FileOutputStream(fileName)){
            while (in.available() > 0){
                out.write(in.readNBytes(value));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        client.close();
    }

    // Вариант 2
//    public static void main(String[] args) {
//        String fileName = "alice-copy.txt";
//        try (Socket client = new Socket(HOST, PORT);
//             InputStream in = client.getInputStream();
//             FileOutputStream out = new FileOutputStream(fileName)){
//
//            while (in.available() > 0){
//                out.write(in.readNBytes(2048));
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
