package com.upmoon.alexanderbean.barcrawlr.networking;

import android.net.Uri;
import android.util.Log;

import com.upmoon.alexanderbean.barcrawlr.model.Plan;
import com.upmoon.alexanderbean.barcrawlr.model.User;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Alex on 1/19/2017.
 */

public class BarConnector implements Connector {

    private String Address, ApiKey;

    public BarConnector(String address, String key){

        Address = address;
        ApiKey = key;
    }

    @Override
    public String sendPlan(Plan plan, User self) {

        String address = Uri.parse(Address + "/addplan")
                .buildUpon()
                .appendQueryParameter("apikey",ApiKey)
                .appendQueryParameter("nick",self.getName())
                .appendQueryParameter("lon",Double.toString(self.getLon()))
                .appendQueryParameter("lat",Double.toString(self.getLat()))
                .build()
                .toString();

        try{
            return barPost(address,plan.toJson());
        }catch (IOException e){
            return "BAD THINGS HAPPENED";
        }
    }

    @Override
    public String sendCode(String code, User self) {
        String address = Uri.parse(Address + "/getplan")
                .buildUpon()
                .appendQueryParameter("apikey",ApiKey)
                .appendQueryParameter("code",code)
                .appendQueryParameter("nick",self.getName())
                .appendQueryParameter("lon",Double.toString(self.getLon()))
                .appendQueryParameter("lat",Double.toString(self.getLat()))
                .build()
                .toString();
        try{
            return barGet(address);
        }catch (IOException e){
            return "BAD THINGS HAPPENED";
        }
    }

    @Override
    public String locationUpdate(String code, User self) {
        String address = Uri.parse(Address + "/update")
                .buildUpon()
                .appendQueryParameter("apikey",ApiKey)
                .appendQueryParameter("code",code)
                .appendQueryParameter("nick",self.getName())
                .appendQueryParameter("lon",Double.toString(self.getLon()))
                .appendQueryParameter("lat",Double.toString(self.getLat()))
                .build()
                .toString();
        try{
            return barGet(address);
        }catch (IOException e){
            return "BAD THINGS HAPPENED";
        }
    }

    @Override
    public String disconnect(String code, User self) {
        String address = Uri.parse(Address + "/disconnect")
                .buildUpon()
                .appendQueryParameter("apikey",ApiKey)
                .appendQueryParameter("code",code)
                .appendQueryParameter("nick",self.getName())
                .build()
                .toString();
        try{
            return barGet(address);
        }catch (IOException e){
            return "BAD THINGS HAPPENED";
        }
    }

    public String barGet(String address) throws IOException {
        URL url = new URL(address);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            /*if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " +
                        address);
            }*/
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return new String(out.toByteArray());
        } finally {
            connection.disconnect();
        }
    }

    public String barPost(String address, String plan) throws IOException {
        DefaultHttpClient dhc = new DefaultHttpClient();

        HttpPost post = new HttpPost(address);

        StringEntity se = new StringEntity(plan);

        post.setEntity(se);
        post.setHeader("Content-type", "application/json");
        HttpResponse httpResponse = dhc.execute(post);

        InputStream inputStream = httpResponse.getEntity().getContent();

        String result = "";

        if(inputStream != null){
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
            String line = "";

            while((line = bufferedReader.readLine()) != null)
                result += line;
        }

        return result;
    }
}
