package com.example.manassangram.myapplication5;

import java.net.Socket;

/**
 * Created by manas.sangram on 12-04-2016.
 */
public class MySocket {
    public Socket socket;
    private static MySocket mysock = new MySocket();

    private MySocket()
    {
    }
  /*  public MySocket()
    {

    }
    */

    public void putSocket(Socket socket)
    {
       this.socket = socket;
    }

    public Socket getSocket()
    {
        return socket;
    }
    public static MySocket getMySocketInstance()
    {
        return mysock;
    }
}
