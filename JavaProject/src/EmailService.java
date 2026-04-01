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

// Utility class used to send transactional emails through Brevo API.
public class EmailService {

    // Sends a plain-text email to one recipient.
    public static void sendMail(String to, String subject, String message) {
        try {

            // Brevo SMTP API endpoint.
            URL url = new URL("https://api.brevo.com/v3/smtp/email");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

            // Configure HTTP method and required headers.
            conn.setRequestMethod("POST");
            conn.setRequestProperty("accept", "application/json");

            // API key for authenticating this request.
            conn.setRequestProperty("api-key", "...");

            conn.setRequestProperty("content-type", "application/json; charset=UTF-8");
            // Enable request body writing.
            conn.setDoOutput(true);

            // Escape line breaks so the message stays valid inside JSON text.
            message = message.replace("\n", "\\n");

            // Build JSON payload expected by Brevo.
            String json = "{"
                    + "\"sender\": {\"name\": \"Cinema\", \"email\": \"galloiseddie@gmail.com\"},"
                    + "\"to\": [{\"email\": \"" + to + "\"}],"
                    + "\"subject\": \"" + subject + "\","
                    + "\"textContent\": \"" + message + "\""
                    + "}";

            // Send the JSON payload using UTF-8 encoding.
            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes("UTF-8"));
            }

            // Read HTTP response code for request status.
            int response = conn.getResponseCode();
            System.out.println("Brevo responded with: " + response);

            // Log non-success responses for troubleshooting.
            if (response != 200 && response != 201 && response != 202) {
                System.out.println("Server returned error: " + response);
            }

        } catch (Exception e) {
            // Print stack trace to help diagnose transport or API errors.
            e.printStackTrace();
        }
    }
}