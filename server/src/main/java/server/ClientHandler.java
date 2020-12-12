package server;

import server.logics.Logics;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.StringTokenizer;

public class ClientHandler implements Runnable {

    private static Socket clientDialog;

    public ClientHandler(Socket client){clientDialog = client;}

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        BufferedOutputStream dataOut = null;
        String fileRequested = null;

        try{
            // Канал чтения из сокета
            in = new BufferedReader(new InputStreamReader(clientDialog.getInputStream()));
            // канал записи в сокет (для HEADER)
            out = new PrintWriter(clientDialog.getOutputStream());
            // канал записи в сокет (для данных)
            dataOut = new BufferedOutputStream(clientDialog.getOutputStream());


            // Первая строка запроса
            String input = in.readLine();
            // Разбираем запрос по токенам
            StringTokenizer parse = new StringTokenizer(input);
            // Получаем HTTP метод от клиента
            String method = parse.nextToken().toUpperCase();
            // Текст запроса от клиента
            fileRequested = parse.nextToken();

            System.out.println("Method: " + method);
            System.out.println("Request: " + fileRequested);

            // Проверяем что этот запрос не связан с фоткой
            if(!fileRequested.equals("/favicon.ico")) {
                // Прописываем GET
                if (method.equals("GET")) {
                    String content = getContentType(fileRequested);
                    String body = Logics.getBody(fileRequested.substring(1));

                    // Шлем HTTP Headers
                    out.println("HTTP/1.1 200 OK");
                    out.println("Server: Java HTTP Server : 1.0");
                    out.println("Date: " + new Date());
                    out.println("Content-type: " + content);
                    //out.println("Transfer-Encoding: chunked");
                    out.println("Connection: keep-alive");
                    out.println("Vary: Accept-Encoding");

                    // Длина ответа - эхо запроса без первого /
                     out.println("Content-length: " + body.getBytes().length);
                    out.println();
                    out.flush();

                    dataOut.write(body.getBytes(), 0, body.getBytes().length);

                    System.out.println("Ответ отослан: " + body);
                    dataOut.flush();
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getContentType(String fileRequested) {
        return "text/html; charset=UTF-8";
    }
}
