package xyz.sallai.r1.service.socket;

/**
 * Description: socket服务类
 * <p>
 * Author:
 * Date: 2023/8/15
 */
import android.util.Log;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;

public class SocketService extends WebSocketServer {
    private Set<WebSocket> clientSet = new HashSet<>();

    private static SocketService serverSocket;

    private static final String TAG = "SocketService";

    public SocketService(int port) {
        super(new InetSocketAddress(port));
    }

    /**
     * 获取socket实例，不存在实例化启动socket
     */
    public static SocketService getInstance() {
        if(null == serverSocket) {
            synchronized (SocketService.class) {
                if(null == serverSocket) {
                    serverSocket = new SocketService(9999);
                    serverSocket.start();
                }
            }
        }
        return serverSocket;
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        //开始连接
        clientSet.add(conn);
        Log.i(TAG, "onOpen: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        //服务器关闭
        clientSet.remove(conn);
        Log.i(TAG, "onClose: " + conn.getRemoteSocketAddress() + "reason:" + reason);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        //接收消息
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        //异常
        Log.i(TAG, "onStart: WebSocket server onError" + ex);
    }

    @Override
    public void onStart() {
        Log.i(TAG, "onStart: WebSocket server started");
    }

    // 自定义方法来发送广播消息给所有连接的客户端
    public void broadcastMessage(String key, Object data) {
        SocketBean socketBean = SocketBean.builder().key(key).data(data).build();
        JSON.toJSONString(socketBean);
        for (WebSocket client : clientSet) {
            client.send(socketBean);
        }
    }

}
