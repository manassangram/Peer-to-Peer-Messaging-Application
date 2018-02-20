package com.example.manassangram.myapplication5;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by manas.sangram on 06-04-2016.
 */
public class ChatActivity extends Activity {


    Socket sock;
    String ip,name;
    ListView l;

    public static int PICK_IMAGE_REQUEST = 1;
    byte[] imagebyte;
    String[] values;
    ArrayList<String> mylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        Intent i = getIntent();
        ip = i.getStringExtra("ip");
        name = i.getStringExtra("name");
        Toast.makeText(this,ip,Toast.LENGTH_LONG).show();
        values = new String[]{};
        mylist = new ArrayList<String>();

        l = (ListView)findViewById(R.id.msgListView);
        values = new String[mylist.size()];

        for (int j=0;j<mylist.size();j++){
            values[j] = mylist.get(j);
        }
        TextView usertv = (TextView)findViewById(R.id.messageditText);
        usertv.setText(name);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.simple_list_item_1,values);
        l.setAdapter(adapter);
        //setListAdapter(adapter);



    }

    public void onClickBack(View v)
    {
        Intent chatIntent = new Intent(this,MainActivity.class);
        startActivity(chatIntent);
    }

    public void onSelectClick(View v)
    {
        if(ip == "0")
        {
            Toast.makeText(this,"User is Offline",Toast.LENGTH_LONG).show();
        }
        else {
            Intent intent = new Intent();
            // Show only images, no videos or anything else
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            // Always show the chooser (if there are multiple options available)
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }


    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 70, stream);
                imagebyte = stream.toByteArray();
                new ChatConn().execute();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onSendClick(View v)
    {
        EditText msget = (EditText)findViewById(R.id.messageEditText);


        mylist.add(msget.getText().toString());
        values = new String[mylist.size()];

        for (int i=0;i<mylist.size();i++){
            values[i] = mylist.get(i);
        }
        //values[i-1] = msget.getText().toString();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.chatbubble,values);
        l.setAdapter(adapter);

    }

    public class ChatConn extends AsyncTask<Void , Integer , Void> {

        String ip;
        String number;
        public ChatConn()
        {
            //this.number = number;
        }
        protected Void  doInBackground(Void... params) {

            try {
                Log.i("Asynctask", "doInBackground");
                Socket sock = new Socket(ip,23457);
                OutputStream out = sock.getOutputStream();
                InputStream in =sock.getInputStream();
                out.write(imagebyte.length);
                out.write(imagebyte);
                out.flush();
                sock.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

}
