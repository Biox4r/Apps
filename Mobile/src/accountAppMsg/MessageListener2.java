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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TextArea;

/**
 *
 * @author Deamon
 */
public class MessageListener2 extends Thread {
    public TextArea textChatUsr1;
    public MainFormController mainForm;
    ServerSocket server;
    int port = 8872;
    TextArea chat;
    Thread listener2;

    public MessageListener2(TextArea chat) {
        this.port = port;
        this.chat = chat;
        try {
            server = new ServerSocket(8872);
        } catch (IOException ex) {
            System.out.println("There is a problem with port/server registering");
            Logger.getLogger(MessageListener1.class.getName()).log(Level.SEVERE, null, ex);
        }
        startListener(chat);
    }


    public void startListener(TextArea chat) {
        System.out.println("chat " + chat.getText());
        listener2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println("Listener2 thread started");
                    Socket clientsocket;
                    try {
                        while ((clientsocket = server.accept()) != null) {
                            InputStream is = clientsocket.getInputStream();
                            BufferedReader br = new BufferedReader(new InputStreamReader(is));
                            String line = br.readLine();
                            
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
        listener2.start();
    }
    
    public void stoptListener(){
        listener2.stop();
        System.out.println("Listener2 stoped");
    }
}
