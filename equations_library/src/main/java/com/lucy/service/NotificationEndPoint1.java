package com.lucy.service;

import com.google.gson.JsonObject;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint("/notifications1")
public class NotificationEndPoint1 {
    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        for (Session s : sessions) {
            s.getBasicRemote().sendText(message);
        }
    }

    public static void sendNotification(String message) {
        synchronized (sessions) {
            for (Session session : sessions) {
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void sendNotification1(String message1, Date message2) {
        synchronized (sessions) {
            JsonObject json = new JsonObject();
            json.addProperty("message", message1);
            json.addProperty("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(message2));

            String jsonMessage = json.toString();
            for (Session session : sessions) {
                try {
                    session.getBasicRemote().sendText(jsonMessage );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void sendNotification2(String identifier, Date date,String description, String due) {
        synchronized (sessions) {
            JsonObject json = new JsonObject();
            json.addProperty("identifier", identifier);
            json.addProperty("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
            json.addProperty("description", description);
            json.addProperty("due", due);

            String jsonMessage = json.toString();
            for (Session session : sessions) {
                try {
                    session.getBasicRemote().sendText(jsonMessage );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
