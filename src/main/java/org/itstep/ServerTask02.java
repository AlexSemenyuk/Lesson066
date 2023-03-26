package org.itstep;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import static org.itstep.Logger.log;

public class ServerTask02 {

    public static final int PORT = 11_000;
    public static final int PORT1 = 11_500;
    public static final Scanner scanner = new Scanner(System.in);
    public static final Scanner scanner1 = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        int n;
        while (true) {
            System.out.println("1-send/get message, 2-get random sonet, 0-exit");
            System.out.print(">>> ");
            n = scanner.nextInt();
            switch (n) {
                case 1 -> serverSendMessage();
                case 2 -> serverGetSonet();
                case 0 -> System.exit(0);
                default -> System.out.println("Вы неправильно ввели исходные данные");
            }
        }
    }

    private static void serverSendMessage() throws IOException {

        // 1. Создание сервера
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Wait for connection...");
        Socket client = serverSocket.accept();
        String[] answerLine = new String[1];

        try(InputStream in = client.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            OutputStream out = client.getOutputStream();
            PrintStream printStream = new PrintStream(out, true)) {
            bufferedReader.lines()
                    .forEach(line -> {
                        log("Message from client:", line);
                        System.out.print(">>> ");
                        answerLine[0] = scanner.nextLine();
                        printStream.println("you ask: " + answerLine[0]);
                    });
        }
    }


    public static void serverGetSonet() throws IOException {

        Random random = new Random();
        int boundRandom = 154;
        int[] sonetOn = new int[1];
        sonetOn[0] = 0;
        int[] sonetNumberOn = new int[1];
        sonetNumberOn[0] = 0;

        // 1. Создание сервера
        ServerSocket serverSocket = new ServerSocket(PORT1);
        System.out.println("Wait for connection...");
        Socket client = serverSocket.accept();

        try (InputStream inFile = new FileInputStream("Sonet.txt");
                BufferedReader readerFromFile = new BufferedReader(new InputStreamReader(inFile));
                OutputStream out = client.getOutputStream();
                BufferedWriter writerClientMassage = new BufferedWriter(new OutputStreamWriter(out));) {

            System.out.println("Connection is successful");
            int[] rand = new int[1];
            rand[0] = random.nextInt(boundRandom) + 1;
            System.out.println("rand[0] = " + rand[0]);

            readerFromFile.lines().forEach(line -> {
                if (line.trim().equals("* СОНЕТЫ *")) {
                    sonetOn[0] = 1;
                }
                if (line.trim().equals("* Уильям Шекспир. Сонеты *")) {
                    sonetOn[0] = 0;
                }
                if (sonetOn[0] == 1 && line.trim().equals(rand[0] + "")) {
                    sonetNumberOn[0] = 1;
                    try {
                        writerClientMassage.write("Сонет №" + line + "\n");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (sonetOn[0] == 1 && line.trim().equals(1 + rand[0] + "")) {
                    sonetNumberOn[0] = 0;
                }
                if (sonetOn[0] == 1 && sonetNumberOn[0] == 1) {
                    try {
                        writerClientMassage.write(line + "\n");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }

}
