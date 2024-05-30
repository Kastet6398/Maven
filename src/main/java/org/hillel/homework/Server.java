package org.hillel.homework;

import java.io.*;
import java.net.*;
import java.time.*;
import java.util.logging.*;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(Constants.PORT)) {
            System.out.println(Constants.SERVER_STARTED_MESSAGE);

            Socket clientSocket = serverSocket.accept();
            System.out.println(Constants.CLIENT_CONNECTED_MESSAGE);

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), Constants.AUTO_FLUSH);

            out.println(Constants.GREETING);

            String clientGreeting = in.readLine();
            System.out.printf(Constants.DISPLAY_CLIENT_GREETING_TEMPLATE, clientGreeting);

            if (clientGreeting.matches(Constants.CONTAINS_RUSSIAN_LETTERS_REGEX)) {
                out.println(Constants.PALIANYTSIA_MEANING_QUESTION);
                String response = in.readLine();
                if (response != null && response.equalsIgnoreCase(Constants.PALIANYTSIA_MEANING)) {
                    out.printf(Constants.DATETIME_MESSAGE_TEMPLATE, LocalDateTime.now());
                    System.out.println(Constants.SENT_DATETIME_MESSAGE);
		    out.println(Constants.FAREWELL);
                } else {
                    System.out.println(Constants.INCORRECT_PALYANYTSYA_MEANING_ANSWER);
                }
            } else {
	        out.println(Constants.FAREWELL);
	    }

            clientSocket.close();
            System.out.println(Constants.CLIENT_DISCONNECTED_MESSAGE);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}

