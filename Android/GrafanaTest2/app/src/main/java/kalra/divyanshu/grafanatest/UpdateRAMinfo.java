package kalra.divyanshu.grafanatest;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by divyanshukalra on 08/01/17.
 */

public class UpdateRAMinfo extends Service {
    influxDB newConn = new influxDB("http://192.168.1.5:8086/write?db=testing", this);
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
        handler.post(runnableCode);
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        return START_STICKY;
    }

    public void GetandUploadRam()
    {
        long RAMused = GetRAMData();
        SendData(RAMused);
    }

    public long GetRAMData()
    {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        long usedMem = (mi.totalMem - mi.availMem) / 1048576L;
        Log.v("RAM USED: ", ""+usedMem);
        return usedMem;
    }

    public void SendData(long RAM) {

        newConn.write("RAM", ""+RAM, null, null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnableCode);
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }
}
