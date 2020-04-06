package com.CoronavirusInfo.myapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity  {
    public static final String CONFIRMED_CASES = BuildConfig.CONFIRMED_CASES;
    public static final String RECOVERED_CASES = BuildConfig.RECOVERED_CASES;
    public static final String DEATH_CASES = BuildConfig.DEATH_CASES;
    BarChart barChart;
    ArrayList<BarEntry> barEntryArrayList = new ArrayList<>();
    ArrayList<String> labelsNames = new ArrayList<>();
    ArrayList<Integer> NewCases = new ArrayList<>();
    ArrayList<Integer> Recovered = new ArrayList<>();
    ArrayList<Integer> Deaths = new ArrayList<>();
    ArrayList<String> Days = new ArrayList<>();

    ArrayList<CasesPerDay> casesPerDays = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        barChart = findViewById(R.id.barchart);
        Last7DaysCases(7);
        RadioGroup radioGroup = findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int switchId) {

                switch (switchId) {
                    case R.id.one_month:
                        NewCases(8);
                        break;
                    case R.id.three_month:
                        Last7DaysCases(7);
                        break;
                    case R.id.tweleve_month:
                        casesPerDays.clear();
                        Thanatoi(1);
                        break;
                }
            }
        });





    }

    public void Last7DaysCases(final int p){
        casesPerDays.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(CONFIRMED_CASES , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = response.length() - p; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String Date2 = jsonObject.getString("Date");
                        String Cases = jsonObject.getString("Cases");
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat last = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat newd = new SimpleDateFormat("dd/MM");
                        Date date1 = last.parse(Date2);
                        assert date1 != null;
                        String date2 = newd.format(date1);
                        System.out.println(date2);

                        int z = Integer.parseInt(Cases);
                        casesPerDays.add(new CasesPerDay(date2, z));




                    } catch (JSONException | ParseException e) {
                        e.printStackTrace();
                    }
                }
                barEntryArrayList.clear();
                labelsNames.clear();
                fillBars();



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public void fillBars(){
        barEntryArrayList.clear();
        labelsNames.clear();
        for (int h = 0; h < casesPerDays.size(); h++){
            String Date = casesPerDays.get(h).getDate();
            int cases  = casesPerDays.get(h).getCases();
            barEntryArrayList.add(new BarEntry(h,cases));
            labelsNames.add(Date);}

        BarDataSet barDataSet = new BarDataSet(barEntryArrayList,"Κρούσματα ανά Ημέρα στην Ελλάδα");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        Description description = new Description();
        barDataSet.setValueTextSize(15f);
        description.setText("Κρούσματα");
        barChart.setDescription(description);
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsNames));
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setTextSize(11);
        xAxis.setLabelCount(labelsNames.size());
        xAxis.setLabelRotationAngle(0);
        barChart.animateY(2000);
        barChart.invalidate();
        casesPerDays.clear();
    }

    public void NewCases(final int p){
        NewCases.clear();
        Days.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(CONFIRMED_CASES , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = response.length() - p; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            String Date2 = jsonObject.getString("Date");
                            String Cases = jsonObject.getString("Cases");
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat last = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat newd = new SimpleDateFormat("dd/MM");
                            Date date1 = last.parse(Date2);
                            assert date1 != null;
                            String date2 = newd.format(date1);
                            System.out.println(date2);

                            int z = Integer.parseInt(Cases);
                            NewCases.add(z);
                            Days.add(date2);


                        } catch (JSONException | ParseException e) {
                            e.printStackTrace();
                        }
                    }
                casesPerDays.clear();
                for (int x = 0; x < 7; x++) {
                    casesPerDays.add(new CasesPerDay(Days.get(x+1), NewCases.get(x+1) - NewCases.get(x)));
                }
               fillBars();


                }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public void TherapeumenoiThanatoi(final int p){
        Recovered.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(RECOVERED_CASES , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = response.length() - p; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String Cases = jsonObject.getString("Cases");
                        int z = Integer.parseInt(Cases);
                        Recovered.add(z);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Recovered.get(0));
                casesPerDays.add(new CasesPerDay("Θεραπευμένοι", Recovered.get(0)));
               fillBars();



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);


    }

    public void Thanatoi(final int p){
        Deaths.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest1 = new JsonArrayRequest(DEATH_CASES , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = response.length() - p; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String Cases = jsonObject.getString("Cases");
                        System.out.println(Cases);
                        int z1 = Integer.parseInt(Cases);
                        System.out.println(z1);
                        Deaths.add(z1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                casesPerDays.add(new CasesPerDay("Θάνατοι", Deaths.get(0)));
                TherapeumenoiThanatoi(1);




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest1);

    }
}

