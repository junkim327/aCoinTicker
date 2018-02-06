package com.example.junyoung.acointicker;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    private static final String URL_BASE = "https://api.coinmarketcap.com/v1/";
    private static final String LIMIT = "9";

    private Timer autoUpdate;
    private int spinnerPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ButterKnife.bind(this);

        populateSpinner();
        //updateData(0);
    }

    private Call<List<Cryptocurrency>> requestHttpApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CoinMarketCapService coinMarketCapService = retrofit.create(CoinMarketCapService.class);

        return coinMarketCapService.listCryptocurrency(LIMIT);
    }

    private void updateData(int position) {
        Call<List<Cryptocurrency>> cryptocurrencies= requestHttpApi();
        spinnerPosition = position;

        cryptocurrencies.enqueue(new Callback<List<Cryptocurrency>>() {
            @Override
            public void onResponse(Call<List<Cryptocurrency>> call, Response<List<Cryptocurrency>> response) {
                Log.d(TAG, String.valueOf(response.code()));
                Log.v(TAG, String.valueOf(spinnerPosition));
                List<Cryptocurrency> currencies = response.body();
                Cryptocurrency coin = currencies.get(spinnerPosition);

                TextView priceText = findViewById(R.id.coin_price_text);
                priceText.setText(coin.getPriceUsd());

                TextView percentChangeText = findViewById(R.id.percent_change_24h_text);
                String percentChange = coin.getPercentChange() + "%";
                percentChangeText.setText(percentChange);
                if (percentChange.startsWith("-")) {
                    percentChangeText.setTextColor(getResources().getColor(R.color.percent_change_minus_color));
                } else {
                    percentChangeText.setTextColor(getResources().getColor(R.color.percent_change_plus_color));
                }

                TextView lastUpdatedText = findViewById(R.id.last_updated_text);
                Date lastUpdated = new Date(Long.parseLong(coin.getLastUpdated()) * 1000);
                lastUpdatedText.setText(lastUpdated.toString());
            }

            @Override
            public void onFailure(Call<List<Cryptocurrency>> call, Throwable t) {

            }
        });
    }

    private void populateSpinner() {
        Spinner spinner = findViewById(R.id.coin_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.coins_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateData(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        autoUpdate = new Timer();
        autoUpdate.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateData(spinnerPosition);
                    }
                });
            }
        },0, 60000);
    }

    @Override
    protected void onPause() {
        autoUpdate.cancel();
        super.onPause();
    }
}
