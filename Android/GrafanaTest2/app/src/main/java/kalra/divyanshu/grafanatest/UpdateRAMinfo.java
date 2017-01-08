package kalra.divyanshu.grafanatest;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by divyanshukalra on 08/01/17.
 */

public class UpdateRAMinfo extends Service {
    Context context = this;
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
    Handler handler = new Handler();
    // Define the code block to be executed
    Runnable runnableCode = new Runnable() {
        @Override
        public void run() {
            GetandUploadRam();
            Log.d("Handlers", "Called on main thread");
            handler.postDelayed(runnableCode, 2000);
        }
    };
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        // Start the initial runnable task by posting through the handler
        handler.post(runnableCode);
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        return START_STICKY;
    }
    public void GetRequest(final long data){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.1.5:8000/polls/?data="+data;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.v(TAG, response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v(TAG, error.toString());
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    public void GetandUploadRam()
    {
        long RAMused = GetRAMData();
        GetRequest(RAMused);
    }
    public long GetRAMData()
    {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        long usedMem = (mi.totalMem - mi.availMem) / 1048576L;
        Log.v(TAG, ""+usedMem);
        return usedMem;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnableCode);
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }
}
