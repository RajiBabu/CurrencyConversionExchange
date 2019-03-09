package com.example.android.assginment3.currencyconversionexchange;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class CurrencyConverterReceiver extends BroadcastReceiver{


    String amount_data;
    String convert;

    @Override
    public void onReceive(Context context, Intent intent) {


        amount_data = intent.getStringExtra("Dollar Amount");
        convert = intent.getStringExtra("Convert To");

        String s = "Action: " + intent.getAction() + "\n" +
                "Dollar Amount: " + amount_data + "\n" +
                "Convert To: " + convert + "\n";
        Intent i = new Intent(context, MainActivity.class);
        i.putExtra("amount",amount_data);
        i.putExtra("convertTo",convert);
        context.startActivity(i);
    }
}
