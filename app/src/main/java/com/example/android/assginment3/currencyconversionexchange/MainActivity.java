package com.example.android.assginment3.currencyconversionexchange;

import android.content.ComponentName;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;



public class MainActivity extends AppCompatActivity {
    private String convertedValue = "10";
    private  String convertTo;
    private String amountStr;
    private Context mContext;
    private String mJSONURLString = "https://api.exchangeratesapi.io/latest?base=USD";
    private String currencyValue;
    private String symbol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        if (null != intent) {
            renderUI(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if (null != intent) {
            renderUI(intent);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = getIntent();
        if (null != intent) {
            renderUI(intent);
        }
    }

    public void renderUI(Intent intent) {

         convertTo = intent.getStringExtra("convertTo");
         amountStr = intent.getStringExtra("amount");

        TextView textView = findViewById(R.id.dollar_amount_value);
        textView.setText(amountStr);

        TextView textView2 = findViewById(R.id.convert_to_value);
        textView2.setText(convertTo);



    }

    public void convert() {
        String currency;
        if (null != convertTo && null != amountStr) {
            getTodayCurrencyValue();
            Double amt = Double.parseDouble(amountStr);
            Double convertedAmount = amt * Double.parseDouble(currencyValue);
            convertedValue = convertedAmount.toString();
        }
    }

    public void getTodayCurrencyValue(){
        if(convertTo.equals("British Pound")){
            symbol = "GBP";
            currencyValue = "0.76";
        }else if(convertTo.equals("Euro")){
            symbol = "EUR";
            currencyValue = "0.89";
        }else if(convertTo.equals("Indian Rupee")){
            symbol = "INR";
            currencyValue = "70.15";
        }
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                mJSONURLString,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONObject curr = response.getJSONObject("rates");
                            currencyValue = curr.getString(symbol);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                       error.printStackTrace();

                    }
                }

        );
         requestQueue.add(jsonObjectRequest);
}
    public void currencyConvert(View view) {
//      getTodayCurrencyValue();
        convert();
        Intent i = new Intent("com.assignment.amountReceiver");
        i.addFlags(Intent.FLAG_EXCLUDE_STOPPED_PACKAGES);
        i.setComponent(new ComponentName("com.example.android.assginment3.currencyconverterapp", "com.example.android.assginment3.currencyconverterapp.AmountReceiver"));
        i.putExtra("convertedAmount", convertedValue);
        i.putExtra("DollarAmount", amountStr);
        i.putExtra("ConvertTo", convertTo);

        MainActivity.this.finish();
        sendBroadcast(i);
    }

    public void closeApp(View view) {
        MainActivity.this.finish();
    }

}
