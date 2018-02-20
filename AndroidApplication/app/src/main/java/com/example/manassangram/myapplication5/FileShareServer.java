package com.example.manassangram.myapplication5;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * Created by manas.sangram on 12-04-2016.
 */
public class FileShareServer implements Runnable {

    ServerSocket serversock;
    static Thread newThread;
    Socket sock;

    FileShareServer() throws IOException {
        serversock = new ServerSocket(23457);//SEREVR SOCKET INITIATION

        newThread = new Thread(this);
        newThread.start();
    }

    public void run()
    {
        while (true)
        {
            try {
                sock = serversock.accept();//LISTEN FOR CLIENTS
                new FileReceiver(sock);


            } catch (Exception ex) {
                // Logger.getLogger(MiniServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
