package br.com.diaristaslimpo.limpo.webservice;

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
    private String tudo;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public JSONObject conexao(String url, String request) throws IOException, JSONException {
        RequestBody body = RequestBody.create(JSON, request);
        Request req = new Request.Builder()
            .url(url)
            .post(body)
            .build();

        Response response = client.newCall(req).execute();
        String jsonstr = response.body().string();
        JSONObject item = new JSONObject(jsonstr);

        return item;
    }

    public JSONArray getJsonArray(String url, String par) {
        url= url + "/" + par;
        JSONArray item = null;
        Request request = new Request.Builder()
            .url(url)
            .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            String jsonstr = response.body().string();
            item = new JSONArray(jsonstr);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return item;
    }

    public JSONObject getJsonObject(String url, String par) {
        url= url + "/" + par;
        JSONObject item = null;
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            String jsonstr = response.body().string();
            item = new JSONObject(jsonstr);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return item;
    }

    public JSONArray conexaoArray(String url, String req) throws IOException, JSONException {
        RequestBody body = RequestBody.create(JSON, req);
        Request reqs = new Request.Builder()
            .url(url)
            .post(body)
            .build();

        Response response = client.newCall(reqs).execute();
        String jsonstr = response.body().string();
        JSONArray item = new JSONArray(jsonstr);

        return item;
    }
}