package com.example.manassangram.myapplication5;
// Contacts Activity
/**
 * Created by manas.sangram on 05-04-2016.
 */
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TabFragment1 extends ListFragment implements AdapterView.OnItemClickListener {

    String ip;
    Socket sock;
    String number,name;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tab_fragment_1, container,
                false);
        DatabaseHandler db = new DatabaseHandler(getContext());
        List<Contact> contactlist = db.getAllContacts();
        String[] values = new String[contactlist.size()];

        for (int i=0;i<contactlist.size();i++){
            values[i] = contactlist.get(i).getName();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
        MySocket mysocket = MySocket.getMySocketInstance();
        sock = mysocket.getSocket();
        return rootView;
    }


    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        getListView().setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Contact c;
        DatabaseHandler db = new DatabaseHandler(getContext());
        c = db.getContact(position);
        number = c.getPhoneNumber();
        name = c.getName();
        Toast.makeText(getContext(),number,Toast.LENGTH_LONG).show();

        new MiniClient().execute(sock);
        //Toast.makeText(getContext(),ip,Toast.LENGTH_LONG).show();

/*
        Intent i = new Intent(getContext(),ChatActivity.class);
        i.putExtra("ip",ip);
        startActivity(i);
        */

    }

    public class MiniClient extends AsyncTask<Socket , Integer , Void> {


        public MiniClient()
        {

        }
        protected Void  doInBackground(Socket... params) {

            try {
                Log.i("Asynctask", "doInBackground");
                Socket socket = params[0];
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                DataInputStream in = new DataInputStream(socket.getInputStream());

                out.writeUTF(number);
                byte[] buffer = new byte[15];
                ip = in.readUTF();
                ip = ip.substring(1);
                Intent i= new Intent(getContext(), ChatActivity.class);
                i.putExtra("name",name);
                i.putExtra("ip",ip);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(i);


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


    }
}