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

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by manas.sangram on 05-04-2016.
 */
public class WelcomeActivity extends Activity
{
    String username;
    String phonenumber;
    EditText unameeditText,phnoeditText;
    Button btn;
    Socket serversocket;
    Context context;
    public InetAddress servaddress;
    SharedPreferences mypref;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.welcome_layout);
        unameeditText = (EditText)findViewById(R.id.editTextUsername);
        phnoeditText = (EditText)findViewById(R.id.editTextPhone);






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

    public void signUp(View v)
    {

        username = unameeditText.getText().toString();
        phonenumber = phnoeditText.getText().toString();
        String pn = "("+phonenumber.substring(0,3)+") "+phonenumber.substring(3,6)+"-"+phonenumber.substring(6,10);
        Toast.makeText(this, username+"   "+pn, Toast.LENGTH_SHORT).show();
        mypref= getSharedPreferences("com.example.manassangram.myapplication5._preferences", MODE_PRIVATE);

        SharedPreferences.Editor prefeditor = mypref.edit();
        prefeditor.putString("usernamePreference",username);
        prefeditor.putString("numberPreference",pn);
        prefeditor.commit();


        Intent i = new Intent(this,LaunchingActivity.class);
        startActivity(i);
        //Toast.makeText(this,c.client.getInetAddress().getAddress().toString(),Toast.LENGTH_LONG).show();

    }




}
