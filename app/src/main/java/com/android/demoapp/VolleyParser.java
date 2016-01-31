package com.android.demoapp;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import com.android.demoapp.Data.Product;
import com.android.demoapp.views.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sarfaraz on 12/30/2015.
 */
public class VolleyParser {

    public void parseJSONObject(JSONObject jsonObject, Context ctx) {

        try {
            JSONArray j = (JSONArray) jsonObject.get("result");
            Log.i("saz","j.lenght() + "+j.length());
            for (int i = 0; i < j.length(); i++) {
                JSONObject jO = (JSONObject) j.get(i);
                Product p = new Product(jO);
                p.insert(ctx);
            }
            MainActivity.handler.sendMessage(new Message());
            Log.i("saz", "Products created: " + Product.count);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
