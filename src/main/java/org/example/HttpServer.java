package org.example;

import java.net.*;
import java.io.*;

public class HttpServer {
    private static String comando;

    public static void main(String[] args) throws IOException, ClassNotFoundException {


        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(36000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 36000.");
            System.exit(1);
        }
        boolean running = true;
        while(running) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            PrintWriter out = new PrintWriter(
                    clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Recibí: " + inputLine);
                if(inputLine.contains("hello?name=class(")){
                    String[] res = inputLine.split("name=class");
                    comando = (res[1].split("HTTP")[0]).replace(" ", "");

                    String respuesta = comando.substring(1, comando.length()-1);
                    System.out.println("respuesta: " + respuesta);
                    optenerInfo(respuesta);

                }
                //Class c = Class.forName(comando);
                //c.getDeclaredFields();
                //System.out.println(c);
                //{url del servicio}/consulta?comando=[comando]
                if (!in.ready()) {
                    break;
                }
            }

            outputLine = "HTTP/1.1 200 OK\r\n" + "Content-Type: text/html\r\n" + "\r\n" + content();

            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }


        serverSocket.close();
    }
    public static String content() {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <title>Form Example</title>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <h1>Reflective ChatGPT GET</h1>\n" +
                "        <form action=\"/hello\">\n" +
                "            <label for=\"name\">Name:</label><br>\n" +
                "            <input type=\"text\" id=\"name\" name=\"name\" value=\"class(java.lang.Math)\"><br><br>\n" +
                "            <input type=\"button\" value=\"Submit\" onclick=\"loadGetMsg()\">\n" +
                "        </form> \n" +
                "        <div id=\"getrespmsg\"></div>\n" +
                "\n" +
                "        <script>\n" +
                "            function loadGetMsg() {\n" +
                "                let nameVar = document.getElementById(\"name\").value;\n" +
                "                const xhttp = new XMLHttpRequest();\n" +
                "                xhttp.onload = function() {\n" +
                "                    document.getElementById(\"getrespmsg\").innerHTML =\n" +
                "                    this.responseText;\n" +
                "                }\n" +
                "                xhttp.open(\"GET\", \"/hello?name=\"+nameVar);\n" +
                "                xhttp.send();\n" +
                "            }\n" +
                "        </script>\n" +
                "\n" +
                "        <h1>Reflective ChatGPT Post</h1>\n" +
                "        <form action=\"/hellopost\">\n" +
                "            <label for=\"postname\">Name:</label><br>\n" +
                "            <input type=\"text\" id=\"postname\" name=\"name\" value=\"class(java.lang.Math)\"><br><br>\n" +
                "            <input type=\"button\" value=\"Submit\" onclick=\"loadPostMsg(postname)\">\n" +
                "        </form>\n" +
                "        \n" +
                "        <div id=\"postrespmsg\"></div>\n" +
                "        \n" +
                "        <script>\n" +
                "            function loadPostMsg(name){\n" +
                "                let url = \"/hellopost?name=\" + name.value;\n" +
                "\n" +
                "                fetch (url, {method: 'POST'})\n" +
                "                    .then(x => x.text())\n" +
                "                    .then(y => document.getElementById(\"postrespmsg\").innerHTML = y);\n" +
                "            }\n" +
                "        </script>\n" +
                "    </body>\n" +
                "</html>";
    }
    public static String optenerInfo(String comando) throws ClassNotFoundException {
        Class c = Class.forName(comando);

        
        return String.valueOf(c);
    }
}

