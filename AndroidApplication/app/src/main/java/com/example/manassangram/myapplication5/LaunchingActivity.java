package com.example.manassangram.myapplication5;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by manas.sangram on 05-04-2016.
 */
public class LaunchingActivity extends Activity
{
    String username;
    String phonenumber;
    EditText unameeditText,phnoeditText;
    Button btn;
    Socket serversocket;
    Context context;
    public InetAddress servaddress;
    SharedPreferences mypref;
    String number;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launching_layout);

        mypref= getSharedPreferences("com.example.manassangram.myapplication5._preferences", MODE_PRIVATE);
        Toast.makeText(this,mypref.getString("usernamePreference",null) ,Toast.LENGTH_LONG).show();

        if(mypref.getString("usernamePreference", null) == null) {

            startActivity(new Intent(this, WelcomeActivity.class));
        }

        File sdCard = Environment.getExternalStorageDirectory();
        File directory  = new File(sdCard.getAbsolutePath()+"/myappDownloads");
        directory.mkdir();

        Toast.makeText(this,"created  "+directory.toString(),Toast.LENGTH_LONG).show();
        context = this;

       // Uri contactsUri = ContactsContract.Contacts.CONTENT_URI;
        //String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
        ContentResolver resolver=getContentResolver();
        //CursorLoader cursorLoader = new CursorLoader(context,contactsUri,null,null,null,null);
        //Cursor c = cursorLoader.loadInBackground();
        Cursor c = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        Log.w("created cursor","created cursor");
        //List<Contact> contactlist = new ArrayList<Contact>();
        DatabaseHandler db = new DatabaseHandler(context);

        //ContentResolver cr = getContentResolver();
        if(c.moveToFirst()) {
            String name;
            String number;
            do {

                name = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
        /*    String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
            if (Integer.parseInt(c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                Cursor pCur = cr.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        new String[]{id}, null);*/
                number = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                // }
                //Log.w("Content Providers", "name = " + contact.getName() + "  no = " + contact.getPhoneNumber());
                if (db.contactExits(name)) {
                    Contact contact = new Contact(name,number);
                    db.addContact(contact);
                }
            }while(c.moveToNext());
        }
        number = mypref.getString("numberPreference", null);
        new Client(this).execute();
        try {
            new FileShareServer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Toast.makeText(this,"Connection established" ,Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this,MainActivity.class);
        //Bundle b = new Bundle();
        //i.putExtra
        startActivity(i);


    }

    protected void onResume(Bundle savedInstanceState){
        super.onResume();
    }

    protected void onPause(Bundle savedInstanceState){
        super.onPause();
    }

    protected void onStart(Bundle savedInstanceState){
        super.onStart();
    }

    protected void onStop(Bundle savedInstanceState){
        super.onStop();
    }

    protected void onRestart(Bundle savedInstanceState){
        super.onRestart();
    }


    class Client extends AsyncTask<Void , Integer , Void> {

        Context context;
        Cursor c;
        public Client(Context context)
        {
            this.context = context;
            //this.c = c;
        }
        protected Void doInBackground(Void... params) {

            try {
                Log.i("Asynctask", "doInBackground");

                Socket serversocket = new Socket("10.0.1.31",23456);
                DataOutputStream dout = new DataOutputStream(serversocket.getOutputStream());
                DataInputStream din = new DataInputStream(serversocket.getInputStream());

                dout.writeUTF(number);
                MySocket mysocket = MySocket.getMySocketInstance();
                mysocket.putSocket(serversocket);


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }



}
