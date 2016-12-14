package com.gmail.programlancer.primo;

/**
 * Created by Victor on 07/12/2016.
 */

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpManager {

    public static final String ServerDefaultIP = "192.168.0.101";
    public static final String ServerPath = "/PhpPrimo/findNeighbors.php";

    public static String getData() {

        String result = null;

        URL url = null;
        try {
            url = new URL("http", ServerDefaultIP, ServerPath);

            HttpURLConnection conn = null;
            try {

                Map<String, String> fields = new LinkedHashMap<>(3);
                fields.put("user", "0545958901");
                fields.put("north", "032_38_27.56");
                fields.put("east", "035_20_18.43");

                StringBuilder post = new StringBuilder();

                for (Map.Entry<String, String> field :
                        fields.entrySet()) {
                    if (post.length() != 0) post.append('&');

                    post.append(URLEncoder.encode(field.getKey(), "UTF-8"));
                    post.append('=');
                    post.append(URLEncoder.encode(field.getValue(), "UTF-8"));
                }

                byte[] postBytes = post.toString().getBytes("UTF-8");

                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("Content-Length", String.valueOf(postBytes.length));

                conn.connect();

                ByteArrayOutputStream os = new ByteArrayOutputStream();
                os.write(postBytes);
                os.writeTo(conn.getOutputStream());

                int responseCode = conn.getResponseCode();
                result = "Response code is " + Integer.toString(responseCode);
                /*switch (responseCode) {
                    case 200:
                        int b;
                        StringBuilder res = new StringBuilder();

                        BufferedInputStream in = new BufferedInputStream(conn.getInputStream());

                        while ((b = in.read()) != -1) {

                            res.append((char) b);
                        }

                        result = "Response is " + res.toString();

                        break;

                    default:
                        result = "Response code is " + Integer.toString(responseCode);
                }*/

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return result;
    }


}
