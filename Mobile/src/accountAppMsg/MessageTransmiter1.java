/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accountAppMsg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Deamon
 */
public class MessageTransmiter1 extends Thread {

    String message;
    String hostname = "localhost";
    private int port = 8872;
    Thread transmiter;
    public static MessageTransmiter1 instance;
    public Socket soc;

  
    public MessageTransmiter1() {
           startTransmiter();
    }

    public void startTransmiter() {
        transmiter = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Transmiter thread started");
                try {
                    soc = new Socket(hostname, 8872);

                } catch (IOException ex) {
                    Logger.getLogger(MessageTransmiter1.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        transmiter.start();

    }
    
    public void sendMsg(String message){
        try {
            soc.getOutputStream().write(message.getBytes());
            System.out.println("soc " + message);
        } catch (IOException ex) {
            Logger.getLogger(MessageTransmiter1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public MessageTransmiter1 getInstance() {
        if (instance == null) {
            instance = new MessageTransmiter1();
        }

        return instance;
    }

    public void stoptTransmiter() {
        try {
            soc.close();
        } catch (IOException ex) {
            Logger.getLogger(MessageTransmiter1.class.getName()).log(Level.SEVERE, null, ex);
        }
        transmiter.stop();
        System.out.println("Transmiter thread stoped");
    }

    public Socket getSoc() {
        return soc;
    }

    public void setSoc(Socket soc) {
        this.soc = soc;
    }

}
