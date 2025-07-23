package com.synapse.social.studioasinc;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//This project is created by @rjfahad_bd71
public class ImageUploader {

    public interface UploadCallback {
        void onUploadComplete(String imageUrl);

        void onUploadError(String errorMessage);
    }

    public static void uploadImage(String filePath, UploadCallback callback) {
        new ImageUploadTask(callback).execute(filePath);
    }

    private static class ImageUploadTask extends AsyncTask<String, Void, String> {
        private UploadCallback callback;

        public ImageUploadTask(UploadCallback callback) {
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String filePath = params[0];
                File file = new File(filePath);
                String boundary = "*****";
                String lineEnd = "\r\n";
                String twoHyphens = "--";

                // Set your server URL for image upload
                URL url = new URL("https://api.imgbb.com/1/upload?expiration=0&key=faa85ffbac0217ff67b5f3c4baa7fb29");
                
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

                DataOutputStream dos = new DataOutputStream(connection.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"image\";filename=\"" + file.getName() + "\"" + lineEnd);
                dos.writeBytes(lineEnd);

                FileInputStream fileInputStream = new FileInputStream(file);
                int bufferSize = 1024;
                byte[] buffer = new byte[bufferSize];

                int bytesRead;
                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    dos.write(buffer, 0, bytesRead);
                }

                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                fileInputStream.close();
                dos.flush();
                dos.close();

                // Continue with your code to handle the response from the server

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = in.readLine()) != null) {
                        response.append(line);
                    }
                    in.close();
                    return response.toString();
                } else {
                    return "Error: " + responseCode;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.startsWith("Error")) {
                callback.onUploadError(result);
            } else {
                handleUploadResponse(result);
            }
        }

        private void handleUploadResponse(String response) {
            try {
                JSONObject jsonResponse = new JSONObject(response);

                // Assuming the image URL is under the key "data" and "url"
                JSONObject data = jsonResponse.getJSONObject("data");
                String imageUrl = data.getString("url");

                // Pass the image URL to the callback
                callback.onUploadComplete(imageUrl);
            } catch (JSONException e) {
                e.printStackTrace();
                callback.onUploadError("Error parsing JSON response");
            }
        }
    }
}
