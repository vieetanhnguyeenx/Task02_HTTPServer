package config;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            String html = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>Document</title>\n" +
                    "</head>\n" +
                    "\n" +
                    "<body>\n" +
                    "    <h1>Cuộc đời.net</h1>\n" +
                    "    <form action=\"/\" method=\"POST\">\n" +
                    "        <input name=\"username\" type=\"text\" placeholder=\"name\">\n" +
                    "        <input type=\"password\" name=\"password\" placeholder=\"password\">\n" +
                    "        <button type=\"submit\">Enter</button>\n" +
                    "    </form>\n" +
                    "\n" +
                    "</body>\n" +
                    "\n" +
                    "</html>";

            String html2 = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>Document</title>\n" +
                    "</head>\n" +
                    "\n" +
                    "<body>\n" +
                    "    <h1>Hello World!</h1>\n" +
                    "</body>\n" +
                    "\n" +
                    "</html>";


            ServerSocket serverSocket = new ServerSocket(8888);


            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Connected");

                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

                final String  CRLF = "\n\r";
                String respone =
                        "HTTP/1.1 200 OK +" + CRLF +
                                "Content-Length: " + html.getBytes().length + CRLF +
                                CRLF +
                                html +
                                CRLF + CRLF;

                String headerLine = null;
                while ((headerLine = bufferedReader.readLine()).length() != 0) {
                    System.out.println(headerLine);
                }

                StringBuilder payload = new StringBuilder();
                while (bufferedReader.ready()) {
                    payload.append((char)bufferedReader.read());
                }


                if (payload.isEmpty()) {
                    outputStream.write(respone.getBytes());
                } else {
                    System.out.println("Payload is:" + payload.toString());
                    System.out.println(payload.toString());
                    String userName = getUserName(payload.toString());
                    String password = getPassword(payload.toString());
                    System.out.println(userName);
                    System.out.println(password);
                    if (Authentication(userName, password)) {
                        respone =
                                "HTTP/1.1 200 OK +" + CRLF +
                                        "Content-Length: " + html.getBytes().length + CRLF +
                                        CRLF +
                                        html2 +
                                        CRLF + CRLF;
                        outputStream.write(respone.getBytes());
                    } else {
                        outputStream.write(respone.getBytes());
                    }
                }

            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static String getUserName(String payload) {
        int start = 9;
        int end = payload.indexOf("&", start);
        return payload.substring(start, end);
    }

    public static String getPassword(String payload) {
        int start = payload.indexOf("password=") + 9;
        int end = payload.length();
        return payload.substring(start, end);
    }

    public static boolean isPost(String payload) {
        if (payload.isEmpty()) {
            return false;
        }
        return true;
    }

    public static boolean Authentication (String userName, String password) {
        if (!userName.equals("anhnv")) {
            return false;
        }
        return password.equals("123");
    }
}
