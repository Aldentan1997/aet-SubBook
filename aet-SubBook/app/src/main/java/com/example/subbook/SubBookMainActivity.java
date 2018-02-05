/**
 * SubBook Application to keep track of users subscriptions
 *
 * Copyright 2018, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under the terms and conditions of the
 * Code of Student Behaviour at University of Alberta.
 */

package com.example.subbook;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * MainActivity. Application starts here.
 *
 * @author Alden Tan
 * @see Subscription
 */
public class SubBookMainActivity extends Activity {

    private static final String FILENAME = "newfile.sav"; //for storage
    private ListView list;
    private ArrayAdapter<Subscription> adapter;
    private ArrayList<Subscription> arrayList;
    private EditText name;
    private Subscription deletesub;

    /**
     * onCreate - Creates the app when it is first launched
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_book_main);
        Button addButton = (Button) findViewById(R.id.add);
        name = (EditText) findViewById(R.id.nameEdit);
        list = (ListView) findViewById(R.id.SubscriptionList);
        list.setAdapter(adapter);

        /**
         * Implementing an onItemClick to delete subscriptions from the list.
         *
         * Sources used to help write this code come from stack overflow:
         *
         * https://stackoverflow.com/questions/11112953/android-remove-item-listview?noredirect=1&lq=1
         *
         * https://stackoverflow.com/questions/2558591/remove-listview-items-in-android
         */
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                deletesub = (Subscription) adapterView.getItemAtPosition(position);
                final int positionToRemove = position;
                arrayList.remove(positionToRemove);
                adapter.notifyDataSetChanged();

            }});

        /**
         * Implementing the "add" button on the user interface.
         * Rest of the code is from lonelytwitter implementations done in the CMPUT 301 Labs.
         */
        addButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                String text = name.getText().toString();

                /**
                 * Getting the date and formatting it apporpriately.
                 * Received help from online resources:
                 * https://stackoverflow.com/questions/5683728/convert-java-util-date-to-string
                 */
                DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                Date today = Calendar.getInstance().getTime();
                String reportDate = df.format(today);

                Subscription newsubscription = new Subscription(text,
                        reportDate);
                arrayList.add(newsubscription);
                adapter.notifyDataSetChanged();
                saveInFile();


            }
        });
    }

    /**
     * onStart taken from lonelytwitter implementation done in CMPUT 301 Lab
     */
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();
        adapter = new ArrayAdapter<Subscription>(this,
                R.layout.list_item, arrayList);
        list.setAdapter(adapter);
    }

    /**
     * loadFromFile taken from lonelytwitter implementation done in CMPUT 301 Lab
     */
    private void loadFromFile() {

        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            //Taken https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            //24/01/2018

            Type listType = new TypeToken<ArrayList<Subscription>>(){}.getType();
            arrayList = gson.fromJson(in, listType);



        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            arrayList = new ArrayList<Subscription>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * saveInFile taken from lonelytwitter implementation done in CMPUT 301 Lab
     */
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(arrayList, out);

            out.flush();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}