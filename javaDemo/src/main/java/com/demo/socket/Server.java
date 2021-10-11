package com.demo.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;

public class Server {

    private Socket socket;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;

    public Server() {
        try (ServerSocket serverSocket = new ServerSocket(12380);) {
            System.out.println("The server is waiting your input...");

            while (true) {
                socket = serverSocket.accept();
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                printWriter = new PrintWriter(socket.getOutputStream(), true);

                String line;
                int length = 0;
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.contains("Content-Length")) {
                        length = Integer.parseInt(line.split(":")[1].trim());
                    }

                    if ("".equals(line)) {
                        char[] body = new char[length];
                        System.out.println(bufferedReader.read(body, 0, length));
                        System.out.println(String.valueOf(body));
                        break;
                    }
                }

                printWriter.close();
                bufferedReader.close();
                socket.close();

                if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server();
    }

}
