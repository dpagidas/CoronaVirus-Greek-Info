package com.CoronavirusInfo.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Main3Activity extends AppCompatActivity {
    EditText editText,editText2;
    String logos;
    Spinner dropdownmenu;
    SharedPreferences sharedPreferences;
    static final String mypreference="mypref";
    static final String Onoma = "nameKey";
    static final String Dieuthinsi= "dieuthinsi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        editText = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        dropdownmenu = findViewById(R.id.aSpinner);
        sharedPreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(Onoma) && sharedPreferences.contains(Dieuthinsi)) {
            editText.setText(sharedPreferences.getString(Onoma, ""));
            editText2.setText(sharedPreferences.getString(Dieuthinsi, ""));

        }
        List<String> list = new ArrayList<>();
        list.add("Μετάβαση σε φαρμακείο ή επίσκεψη στον γιατρό, εφόσον αυτό συνιστάται μετά από σχετική επικοινωνία.");
        list.add("Μετάβαση σε εν λειτουργία κατάστημα προμηθειών αγαθών πρώτης ανάγκης, όπου δεν είναι δυνατή η αποστολή τους.");
        list.add("Μετάβαση στην τράπεζα, στο μέτρο που δεν είναι δυνατή η ηλεκτρονική συναλλαγή.");
        list.add("Κίνηση για παροχή βοήθειας σε ανθρώπους που βρίσκονται σε ανάγκη.");
        list.add("Μετάβαση σε τελετή (π.χ. κηδεία, γάμος, βάφτιση ή ανάλογες τελετές) υπό τους όρους που προβλέπει ο νόμος.");
        list.add("Σωματική άσκηση σε εξωτερικό χώρο ή κίνηση με κατοικίδιο ζώο, ατομικά ή ανά δύο άτομα, τηρώντας στην τελευταία αυτή περίπτωση την αναγκαία απόσταση 1,5 μέτρου.");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdownmenu.setAdapter(adapter);

        dropdownmenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Main3Activity.this, parent.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                if (parent.getSelectedItem().toString().equals("Μετάβαση σε φαρμακείο ή επίσκεψη στον γιατρό, εφόσον αυτό συνιστάται μετά από σχετική επικοινωνία.")) {
                    logos = "1";
                } else if (parent.getSelectedItem().toString().equals("Μετάβαση σε εν λειτουργία κατάστημα προμηθειών αγαθών πρώτης ανάγκης, όπου δεν είναι δυνατή η αποστολή τους.")) {
                    logos = "2";
                } else if (parent.getSelectedItem().toString().equals("Μετάβαση στην τράπεζα, στο μέτρο που δεν είναι δυνατή η ηλεκτρονική συναλλαγή.")) {
                    logos = "3";
                } else if (parent.getSelectedItem().toString().equals("Κίνηση για παροχή βοήθειας σε ανθρώπους που βρίσκονται σε ανάγκη.")) {
                    logos = "4";
                } else if (parent.getSelectedItem().toString().equals("Μετάβαση σε τελετή (π.χ. κηδεία, γάμος, βάφτιση ή ανάλογες τελετές) υπό τους όρους που προβλέπει ο νόμος.")) {
                    logos = "5";
                } else {
                    logos = "6";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void button(View view){
        String n = editText.getText().toString();
        String d = editText2.getText().toString();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Onoma,n);
        editor.putString(Dieuthinsi,d);
        editor.apply();
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        if (permissionCheck== PackageManager.PERMISSION_GRANTED){
            MyMessage();
        }

        else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},0);
        }

    }

    private void MyMessage(){
        String phoneNumber = "13033";
        String Message = logos+" "+ editText.getText().toString()+" "+editText2.getText().toString();

        if(!editText.getText().toString().trim().equals("")){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null,Message,null,null);
            Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"Please Enter Message", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 0 :
                if(grantResults.length >= 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    MyMessage();
                }
                else{
                    Toast.makeText(this, "You don't have required Permission", Toast.LENGTH_SHORT).show();
                }

        }
    }
}
