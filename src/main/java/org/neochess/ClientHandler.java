package org.neochess;

import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.StatusCode;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

public class ClientHandler extends WebSocketAdapter
{
    private final CountDownLatch closureLatch = new CountDownLatch(1);

    @Override
    public void onWebSocketConnect(Session session) {
        super.onWebSocketConnect(session);
        System.out.println("Socket Connected: " + session);
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        super.onWebSocketClose(statusCode, reason);
        System.out.println("Socket Closed: [" + statusCode + "] " + reason);
        closureLatch.countDown();
    }

    @Override
    public void onWebSocketText(String message) {
        super.onWebSocketText(message);
        // getSession().getRemote().sendString();

        try {
            getSession().getRemote().sendString("Recievedx: " + message);
        } catch (Exception ex) {}

        System.out.println("Received TEXT message: " + message);
        if (message.toLowerCase(Locale.US).contains("bye")) {
            getSession().close(StatusCode.NORMAL, "Thanks");
        }
    }

    @Override
    public void onWebSocketError(Throwable cause) {
        super.onWebSocketError(cause);
        cause.printStackTrace(System.err);
    }

    public void awaitClosure() throws InterruptedException {
        System.out.println("Awaiting closure from remote");
        closureLatch.await();
    }
}
