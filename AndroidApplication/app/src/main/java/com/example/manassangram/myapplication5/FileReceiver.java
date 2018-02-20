package com.example.manassangram.myapplication5;

import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.security.Timestamp;
import java.util.Date;

/**
 * Created by manas.sangram on 12-04-2016.
 */
public class FileReceiver implements Runnable {


    Thread newThread;
    Socket sock;

    FileReceiver(Socket sock)
    {
        newThread = new Thread(this);
        newThread.start();
        this.sock = sock;
    }

    public void run()
    {

        try {


            InputStream in =sock.getInputStream();
            int length = in.read();

            byte[] imagbuf = new byte[length];

            Date d = new Date();

            File sdCard = Environment.getExternalStorageDirectory();
            File directory  = new File(sdCard.getAbsolutePath()+"/myappDownloads");
            File imagefile = new File(directory,"imagefile"+d.getTime()+".png");
            FileOutputStream fout = new FileOutputStream(imagefile);
            BufferedOutputStream bos = new BufferedOutputStream(fout);
            OutputStreamWriter osw = new OutputStreamWriter(fout);

            in.read(imagbuf);
            bos.write(imagbuf);
            bos.flush();
            bos.close();

            sock.close();



        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
