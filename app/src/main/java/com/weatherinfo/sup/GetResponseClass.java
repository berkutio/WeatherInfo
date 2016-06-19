package com.weatherinfo.sup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

public class GetResponseClass {

    public static String getResult(String urlPath) {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlPath);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setReadTimeout(10000);
            c.connect();
            reader = new BufferedReader(new InputStreamReader(c.getInputStream()));
            StringBuilder buf = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                buf.append(line + "\n");
            }
            return (buf.toString());
        }catch (SocketTimeoutException e){
            return String.valueOf(HttpURLConnection.HTTP_BAD_REQUEST);
        } catch (IOException e) {
            return String.valueOf(HttpURLConnection.HTTP_BAD_REQUEST);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
        }
    }


}
