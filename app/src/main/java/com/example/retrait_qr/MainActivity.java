package com.example.retrait_qr;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity  {
    Button scanBtn,retBtn;
    TextView messageText,mntret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scanBtn = findViewById(R.id.scanBtn);
        retBtn = findViewById(R.id.retraitBtn);
        messageText = findViewById(R.id.textContent);
        mntret =findViewById(R.id.mntretrait);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RepoServiceAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RepoServiceAPI myApi = retrofit.create(RepoServiceAPI.class);


        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                intentIntegrator.setPrompt("Scan a barcode or QR Code");
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.initiateScan();
            }
        });

        retBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<Compte> call = myApi.Retrait(Long.parseLong(messageText.getText().toString()),
                        Double.valueOf(mntret.getText().toString()));
                call.enqueue(new Callback<Compte>() {
                    @Override
                    public void onResponse(Call<Compte> call, Response<Compte> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(MainActivity.this, "User updated successfully!", Toast.LENGTH_SHORT).show();
                            mntret.setText("");
                            messageText.setText("QR CODE");
                        }
                    }

                    @Override
                    public void onFailure(Call<Compte> call, Throwable t) {
                        Log.e("ERROR: ", t.getMessage());
                    }
                });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            } else {

                messageText.setText(intentResult.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
