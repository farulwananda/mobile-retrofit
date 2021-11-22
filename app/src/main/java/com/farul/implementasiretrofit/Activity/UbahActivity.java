package com.farul.implementasiretrofit.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.farul.implementasiretrofit.API.APIRequestData;
import com.farul.implementasiretrofit.API.RetroServer;
import com.farul.implementasiretrofit.Model.ResponseModel;
import com.farul.implementasiretrofit.R;

import java.nio.charset.StandardCharsets;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahActivity extends AppCompatActivity {

    private String xNim, xNama, xSemester, xJurusan, xProdi;
    private EditText etNIM, etNama, etSemester, etJurusan, etProdi;
    private Button btnUbah;
    private String yNIM, yNama, ySemester, yJurusan, yProdi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah);

        Intent terima = getIntent();
        xNim = terima.getStringExtra("xNim");
        xNama = terima.getStringExtra("xNama");
        xSemester = terima.getStringExtra("xSemester");
        xJurusan = terima.getStringExtra("xJurusan");
        xProdi = terima.getStringExtra("xProdi");

        etNIM = findViewById(R.id.et_nim);
        etNama = findViewById(R.id.et_nama);
        etSemester = findViewById(R.id.et_semester);
        etJurusan = findViewById(R.id.et_jurusan);
        etProdi = findViewById(R.id.et_prodi);
        btnUbah = findViewById(R.id.btn_ubah);

        etNIM.setText(xNim);
        etNama.setText(xNama);
        etSemester.setText(xSemester);
        etJurusan.setText(xJurusan);
        etProdi.setText(xProdi);

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            yNIM = etNIM.getText().toString();
            yNama = etNama.getText().toString();
            ySemester = etSemester.getText().toString();
            yJurusan = etJurusan.getText().toString();
            yProdi = etProdi.getText().toString();

            updateData();
            }
        });
    }

    private void updateData() {
        APIRequestData ardData = RetroServer.koneksiRetrofit().create(APIRequestData.class);
        Call<ResponseModel> ubahData = ardData.ardUpdateData(yNIM, yNama, ySemester, yJurusan, yProdi);

        ubahData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(UbahActivity.this, "Kode : " + kode + "| Pesan : " +pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(UbahActivity.this, "Gagal Menghubungi Server" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}