package kalra.divyanshu.grafanatest;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by divyanshukalra on 08/01/17.
 */

public class influxDB {
    private static String influx_URL = "";
    private Context influxContext = null;

    influxDB(String URL, Context context) {
        influx_URL = URL;
        influxContext = context;
    }
    public void write(String measurement, String value, String server, String region) {
        if (server == null) {
            server = "server01";
        }
        if (region == null)
        {
            region = "us-west";
        }
        final String mContent = measurement+",host="+server+",region="+region+" value="+value;
        RequestQueue queue = Volley.newRequestQueue(influxContext);
        StringRequest request = new StringRequest(Request.Method.POST, influx_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Is the responds is success
                        Log.v(TAG, response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Is the responds is fail
                        Log.v(TAG, error.toString());
                    }
                }
        ) {
            @Override
            public byte[] getBody() throws AuthFailureError {

                byte[] body = new byte[0];
                try {
                    body = mContent.getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    Log.e(TAG, "Unable to gets bytes from content", e.fillInStackTrace());
                }
                return body;
            }
        };
        queue.add(request);
    }
}
