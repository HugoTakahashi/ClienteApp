package br.com.diaristaslimpo.limpo.task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ConectaWS {

    private OkHttpClient client = new OkHttpClient();

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public void doPost(String url, String request) throws IOException, JSONException {
        RequestBody body = RequestBody.create(JSON, request);
        Request req = postBuild(url, body);

        client.newCall(req).execute();
    }

    public JSONObject doPostJsonObject(String url, String request) throws IOException, JSONException {
        RequestBody body = RequestBody.create(JSON, request);
        Request req = postBuild(url, body);

        Response response = client.newCall(req).execute();
        String jsonstr = response.body().string();
        JSONObject item = new JSONObject(jsonstr);

        return item;
    }

    public JSONArray doPostJsonArray(String url, String req) throws IOException, JSONException {
        RequestBody body = RequestBody.create(JSON, req);
        Request reqs = postBuild(url, body);

        Response response = client.newCall(reqs).execute();
        String jsonstr = response.body().string();
        JSONArray item = new JSONArray(jsonstr);

        return item;
    }

    public void doGet(String url, String par) {
        url= url + "/" + par;
        JSONObject item = null;
        Request request = getBuild(url);

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject doGetJsonObject(String url, String par) {
        url= url + "/" + par;
        JSONObject item = null;
        Request request = getBuild(url);

        try {
            Response response = client.newCall(request).execute();
            String json = response.body().string();
            item = new JSONObject(json);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return item;
    }

    public JSONArray doGetJsonArray(String url, String par) {
        url= url + "/" + par;
        JSONArray item = null;
        Request request = getBuild(url);

        try {
            Response response = client.newCall(request).execute();
            String json = response.body().string();
            item = new JSONArray(json);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return item;
    }

    private Request postBuild(String url, RequestBody body) {
        return new Request.Builder()
                .url(url)
                .post(body)
                .build();
    }

    private Request getBuild(String url) {
        return new Request.Builder()
                .url(url)
                .build();
    }
}