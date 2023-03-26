package org.itstep;
import java.io.*;
import java.net.*;
import java.util.Scanner;

import static org.itstep.Logger.log;

public class ClientTask02 {

    public static final String HOST = "127.0.0.1";
    public static final Scanner scanner = new Scanner(System.in);
    public static final Scanner scanner1 = new Scanner(System.in);


    public static void main(String[] args) throws IOException {

        int n;
        while (true) {
            System.out.println("1-send/get message, 2-get random sonet, 0-exit");
            System.out.print(">>> ");
            n = scanner.nextInt();
            switch (n) {
                case 1 -> сlientSendMessage();
                case 2 -> clientGetSonet();
                case 0 -> System.exit(0);
                default -> System.out.println("Вы неправильно ввели исходные данные");
            }

        }

    }

    private static void сlientSendMessage() throws IOException {
        Socket client = new Socket();
        SocketAddress serverSocket =
                new InetSocketAddress(InetAddress.getByName(HOST), ServerTask02.PORT);
        System.out.println("Connection to server");
        client.connect(serverSocket);
        System.out.println("Connection is successful");
        try (InputStream in = client.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(in));
             OutputStream out = client.getOutputStream();
             PrintStream print = new PrintStream(out, true)) {
            String line;
            while (true) {
                System.out.print(">>> ");
                line = scanner.nextLine();
                if ("exit".equalsIgnoreCase(line.trim())) {
                    break;
                }
                print.println(line);
                String response = reader.readLine();
                log("Server answered:", response);
            }
        }
        client.close();
    }

    private static void clientGetSonet() throws IOException {
        Socket client = new Socket();
        SocketAddress serverSocket =
                new InetSocketAddress(InetAddress.getByName(HOST), ServerTask02.PORT1);
        System.out.println("Connection to server");
        client.connect(serverSocket);
        System.out.println("Connection is successful");
        try (InputStream in = client.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(in));){
//             OutputStream out = client.getOutputStream();
//             PrintStream print = new PrintStream(out, true)){
//             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));){
            while (reader.readLine() != null) {
                reader.lines().forEach(line -> {
                    System.out.println(line);
                });
            }
        }
        client.close();
    }

}
