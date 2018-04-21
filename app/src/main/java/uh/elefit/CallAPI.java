package uh.elefit;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

/**
 * Created by pisoj on 21-Apr-18.
 */
public class CallAPI{
    RequestQueue MyRequestQueue;

    public JSONArray jsonArray;

    public CallAPI(RequestQueue requestQueue) {
        this.MyRequestQueue=requestQueue;
    }


    public JSONArray pozovi(String url, final ServerCallback callback){


        JsonArrayRequest MyStringRequest = new JsonArrayRequest
                (Request.Method.GET, url, (String) null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println("Response: " + response.toString());
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("zajeb");

                    }
                });


        System.out.println(MyStringRequest.toString());
        MyRequestQueue.add(MyStringRequest);
        return jsonArray;
    }
}
