package br.com.diaristaslimpo.limpo;

import org.json.JSONArray;

/**
 * Created by Hugo on 10/11/2016.
 */

public interface AsyncResponse {
    void processFinish(JSONArray output);
}