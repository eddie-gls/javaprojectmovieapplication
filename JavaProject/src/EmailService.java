/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author gallo
 */

import javax.net.ssl.HttpsURLConnection;
import java.io.OutputStream;
import java.net.URL;

public class EmailService {

    public static void sendMail(String to, String subject, String message) {
        try {

            URL url = new URL("https://api.brevo.com/v3/smtp/email");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("accept", "application/json");
            conn.setRequestProperty("api-key", "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");
            conn.setRequestProperty("content-type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);

            // IMPORTANT : remplacer les sauts de ligne
            message = message.replace("\n", "\\n");

            String json = "{"
                    + "\"sender\": {\"name\": \"Cinema\", \"email\": \"galloiseddie@gmail.com\"},"
                    + "\"to\": [{\"email\": \"" + to + "\"}],"
                    + "\"subject\": \"" + subject + "\","
                    + "\"textContent\": \"" + message + "\""
                    + "}";

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes("UTF-8"));
            }

            int response = conn.getResponseCode();
            System.out.println("Brevo responded with: " + response);

            if (response != 200 && response != 201 && response != 202) {
                System.out.println("Server returned error: " + response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}