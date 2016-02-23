package com.robertlimantoproject.madebygue;

import android.text.Html;
import android.util.Log;

import com.robertlimantoproject.madebygue.entity.Category;
import com.robertlimantoproject.madebygue.entity.Product;
import com.robertlimantoproject.madebygue.entity.Response;
import com.robertlimantoproject.madebygue.entity.Subcategory;
import com.robertlimantoproject.madebygue.entity.User;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by user on 27/7/2015.
 */
public class ServerConnection {

    private final static String LOG = ServerConnection.class.getSimpleName();
    URL link;

    HttpURLConnection connection;

    List<NameValuePair> params;

    public ServerConnection(URL link,List<NameValuePair> params){
        this.link  = link;
        this.params = params;
    }

    public Response postData(){

        try {
            Log.v(LOG, "Posting data");
            HttpURLConnection connection;
            System.setProperty("http.keepAlive", "false");
            connection = (HttpURLConnection) link.openConnection();
            connection.setDoOutput(true);
            connection.setChunkedStreamingMode(0);
            connection.setRequestMethod("POST");

            OutputStream outputStream = connection.getOutputStream();
            DataOutputStream os = new DataOutputStream(outputStream);
            //change the params so it becomes understandable by outputstream
            String message = getQuery(params);
            outputStream.write(message.getBytes(Charset.forName("UTF-8")));
            os.flush();
            os.close();

            int responseCode = connection.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response  = new StringBuffer();

            while((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }

            in.close();
            String responseInString = response.toString();
            String responseStripped  = stripHtml(responseInString);
            Log.d(LOG, "response: " + responseStripped);
            return processResponse(responseStripped);
        }

        catch(IOException e){
            e.printStackTrace();
            Log.d(LOG, "Failure IO Exception: " + e.getMessage());
            return null;

        }
    }

    public Response getData() {




            try {
                Log.v(LOG, "Posting data");
                HttpURLConnection connection;
                System.setProperty("http.keepAlive", "false");
                connection = (HttpURLConnection) link.openConnection();
                connection.setDoOutput(true);
                connection.setChunkedStreamingMode(0);
                connection.setRequestMethod("GET");

                OutputStream outputStream = connection.getOutputStream();
                DataOutputStream os = new DataOutputStream(outputStream);
                //change the params so it becomes understandable by outputstream
                String message = getQuery(params);
                outputStream.write(message.getBytes(Charset.forName("UTF-8")));
                os.flush();
                os.close();

                int responseCode = connection.getResponseCode();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();
                String responseInString = response.toString();
                String responseStripped = stripHtml(responseInString);
                Log.d(LOG, "response: " + responseStripped);
                return processResponse(responseStripped);
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(LOG, "Failure IO Exception: " + e.getMessage());
                return null;

            }

    }

    private Response processResponse(String response){
        try {
            JSONObject json = new JSONObject(response);
            String message = json.getString("message");
            int status  = json.getInt("status");

            //For madebygue app, user entity
            if(json.has(Constants.ResponseObject.USER)){
                JSONObject jsonObject = json.getJSONObject(Constants.ResponseObject.USER);

                String name = jsonObject.getString("name");
                String password = jsonObject.getString("password");
                String email = jsonObject.getString("email");
                String address = jsonObject.getString("address");
                String handphone = jsonObject.getString("handphone");
                boolean validated = (jsonObject.getString("validated").equals("1")) ;


                User user = new User(name, handphone,address, email , password, validated);
                return new Response(status, message, user);
            }
            else if(json.has(Constants.ResponseObject.USER_RELATION)){
                JSONObject jsonObject = json.getJSONObject(Constants.ResponseObject.USER_RELATION);

                int follower = jsonObject.getInt("follower");
                int following = jsonObject.getInt("following");
                int rating = jsonObject.getInt("rating");

                User user = new User(follower, following, rating);
                return new Response(status, message, user);

            }
            else if(json.has(Constants.ResponseObject.CATEGORY)){
                JSONArray jsonArray = json.getJSONArray(Constants.ResponseObject.CATEGORY);
                Category[] catList = new Category[jsonArray.length()];
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    String name = object.getString("category_name");
                    int id = object.getInt("category_id");
                    //image is not added yet
                    //// TODO: 10/8/2015
                    catList[i] = new Category(id, name);
                }

                return new Response(status, message, catList);
            }

            else if(json.has(Constants.ResponseObject.SUBCATEGORY)){
                JSONArray jsonArray = json.getJSONArray(Constants.ResponseObject.SUBCATEGORY);
                Subcategory[] subCatList = new Subcategory[jsonArray.length()];
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    String name = object.getString("subcategory_name");
                    int id = object.getInt("subcategory_id");
                    int cat_id =object.getInt("category_id");
                    //image is not added yet
                    //// TODO: 10/8/2015
                    subCatList[i] = new Subcategory(cat_id,id, name);
                }
                return new Response(status, message, subCatList);
            }
            else if(json.has(Constants.ResponseObject.PRODUCT)){
                JSONArray jsonArray = json.getJSONArray(Constants.ResponseObject.PRODUCT);
                Product[] productList = new Product[jsonArray.length()];
                for(int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String product_desc = object.getString("product_desc");
                    int product_id = object.getInt("product_id");
                    int subcategory_id = object.getInt("subcategory_id");
                    int price = object.getInt("price");
                    String creating_duration = object.getString("creating_duration");
                    //image is not added yet
                    //// TODO: 10/8/2015

                    productList[i] = new Product(product_id, product_desc,subcategory_id,price,creating_duration);
                }
                return new Response(status, message, productList);
            }

            return new Response(status, message);
        }
        catch(JSONException e){
            e.printStackTrace();
            Log.d(LOG, "Failure JSON Object: " + e.getMessage());
            return null;
        }

    }
    public String stripHtml(String html) {
        return Html.fromHtml(html).toString();
    }

    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }

}
