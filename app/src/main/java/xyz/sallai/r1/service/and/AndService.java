package xyz.sallai.r1.service.and;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.ContextThemeWrapper;

import com.yanzhenjie.andserver.AndServer;
import com.yanzhenjie.andserver.Server;
import com.yanzhenjie.andserver.framework.website.AssetsWebsite;
import com.yanzhenjie.andserver.framework.website.StorageWebsite;
import com.yanzhenjie.andserver.framework.website.Website;

import java.util.concurrent.TimeUnit;

public class AndService {

    private Server mServer;

    /**
     * Create server.
     */
    public AndService(Context context) {

//        mServer = AndServer.proxyServer()
//                .addProxy("www.aa.com", "http://hangzhou.gov.cn")
//                .port(9090)
//                .timeout(10, TimeUnit.SECONDS)
//                .build();

        mServer = AndServer.webServer(context)
                .port(3333)
                .timeout(10, TimeUnit.SECONDS)
                .listener(new Server.ServerListener() {
                    @Override
                    public void onStarted() {
                        // TODO The server started successfully.
                        Log.w("AndServer", "The server is Started");
                    }

                    @Override
                    public void onStopped() {
                        // TODO The server has stopped.
                    }

                    @Override
                    public void onException(Exception e) {
                        // TODO An exception occurred while the server was starting.
                        Log.w("AndServer", "The server is Exception"+e);
                    }
                })
                .build();
    }

    /**
     * Start server.
     */
    public void startServer() {
        Log.w("AndServer", "进入方法");
        if (mServer.isRunning()) {
            // TODO The server is already up.
            Log.w("AndServer", "The server is already up.");
        } else {
            Log.w("AndServer", "The server is starting");
            mServer.startup();

        }
    }

    /**
     * Stop server.
     */
    public void stopServer() {
        if (mServer.isRunning()) {
            mServer.shutdown();
        } else {
            Log.w("AndServer", "The server has not started yet.");
        }
    }
}