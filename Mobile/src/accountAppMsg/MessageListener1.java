/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accountAppMsg;

import accountAppView.MainFormController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TextArea;
import javax.net.ssl.SSLServerSocket;

/**
 *
 * @author Deamon
 */
public class MessageListener1 extends Thread {

    public TextArea textChatUsr1;
    public MainFormController mainForm;
    ServerSocket server;
    int port = 8875;
    TextArea chat;
    Thread listener1;

    public MessageListener1(TextArea chat) {
        this.port = port;
        
        try {
            server = new ServerSocket(8875);
        } catch (IOException ex) {
            System.out.println("There is a problem with port/server registering");
            Logger.getLogger(MessageListener1.class.getName()).log(Level.SEVERE, null, ex);
        }
        startListener(chat);
    }


    public void startListener(TextArea chat) {
        System.out.println("chat " + chat.getText());
        listener1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println("Listener thread started");
                    Socket clientsocket;
                    try {
                        while ((clientsocket = server.accept()) != null) {
                            InputStream is = clientsocket.getInputStream();
                            BufferedReader br = new BufferedReader(new InputStreamReader(is));
                            String line = br.readLine();
                            System.out.println("chat " + line);
                            if (line != null) {

                                chat.appendText(line + System.lineSeparator());

                            }
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(MessageListener1.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        listener1.start();
        
    }
    
    public void stoptListener(){
        listener1.stop();
        System.out.println("Listener stoped");
    }

}
