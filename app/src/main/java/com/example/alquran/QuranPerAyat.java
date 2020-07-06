package com.example.alquran;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.alquran.adapter.SurahAdapter;
import com.example.alquran.api.ApiClient;
import com.example.alquran.api.ApiInterface;
import com.example.alquran.model.Cek;
import com.example.alquran.model.Data;
import com.example.alquran.model.Surah;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuranPerAyat extends Activity {
    private static final String arabic = "quran-uthmani";
    private static final String indo = "id.indonesian";

    private List<Surah> surahsArabic = new ArrayList<>();
    private List<Surah> surahsIndo = new ArrayList<>();

    ProgressDialog loadingData;

    Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quran_per_ayat);
        //setActionBarTitle();

        loadingData = new ProgressDialog(this);
        loadingData.setTitle("Mohon tunggu...");
        loadingData.setCancelable(false);
        loadingData.setMessage("Sedang mengambil data dari API");

        RecyclerView recyclerSurah = findViewById(R.id.surah_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerSurah.setHasFixedSize(true);
        recyclerSurah.setLayoutManager(layoutManager);

        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);

        Call<Cek> call = apiInterface.getCek(arabic);
        Call<Cek> callIndo = apiInterface.getCek(indo);

        getDataListArabic(recyclerSurah, call);
        getDataTarjim(callIndo);

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuranPerAyat.this, MainActivity.class));
                finish();
            }
        });
    }

//    private void setActionBarTitle(){
//        if (getSupportActionBar() != null){
//            getSupportActionBar().setTitle("Al-Qur'an");
//        }
//    }

    private void getDataTarjim(Call<Cek> callIndo) {
        callIndo.enqueue(new Callback<Cek>() {
            @Override
            public void onResponse(Call<Cek> call, Response<Cek> response) {
                Data data = response.body().getData();
                surahsIndo = data.getSurahs();

            }

            @Override
            public void onFailure(Call<Cek> call, Throwable t) {
                Toast.makeText(QuranPerAyat.this, "gagal", Toast.LENGTH_SHORT).show();
                Log.d("error", t.getMessage());
            }
        });
    }

    private void getDataListArabic(final RecyclerView recyclerSurah, Call<Cek> call) {
        loadingData.show();
        call.enqueue(new Callback<Cek>() {
            @Override
            public void onResponse(Call<Cek> call, Response<Cek> response) {
                Data data = response.body().getData();
                surahsArabic = data.getSurahs();

                SurahAdapter surahAdapter = new SurahAdapter(QuranPerAyat.this, surahsArabic, surahsIndo);
                recyclerSurah.setAdapter(surahAdapter);
                loadingData.dismiss();
            }

            @Override
            public void onFailure(Call<Cek> call, Throwable t) {
                loadingData.dismiss();
                Toast.makeText(QuranPerAyat.this, "gagal", Toast.LENGTH_SHORT).show();
                Log.d("error", t.getMessage());
            }
        });
    }
}