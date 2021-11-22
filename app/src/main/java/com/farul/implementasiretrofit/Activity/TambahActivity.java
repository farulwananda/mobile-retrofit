package com.farul.implementasiretrofit.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.farul.implementasiretrofit.API.APIRequestData;
import com.farul.implementasiretrofit.API.RetroServer;
import com.farul.implementasiretrofit.Model.ResponseModel;
import com.farul.implementasiretrofit.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahActivity extends AppCompatActivity {

    private EditText etNim, etNama, etSemester, etJurusan, etProdi;
    private Button btnSimpan;
    private String NIM, Nama, Semester, Jurusan, Prodi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        etNim = findViewById(R.id.et_nim);
        etNama = findViewById(R.id.et_nama);
        etSemester = findViewById(R.id.et_semester);
        etJurusan = findViewById(R.id.et_jurusan);
        etProdi = findViewById(R.id.et_prodi);
        btnSimpan = findViewById(R.id.btn_simpan);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NIM = etNim.getText().toString();
                Nama = etNama.getText().toString();
                Semester = etSemester.getText().toString();
                Jurusan = etJurusan.getText().toString();
                Prodi = etProdi.getText().toString();

                if (NIM.trim().equals("")){
                    etNim.setError("Nim Harus DiIsi!");
                }
                else if (Nama.trim().equals("")) {
                    etNama.setError("Nama Harus DiIsi!");
                }
                else if (Semester.trim().equals("")) {
                    etSemester.setError("Semester Harus DiIsi!");
                }
                else if (Jurusan.trim().equals("")) {
                    etJurusan.setError("Jurusan Harus DiIsi!");
                }
                else if (Prodi.trim().equals("")) {
                    etProdi.setError("Prodi Harus DiIsi!");
                }
                else {
                createData();
                }
            }
        });
    }
    private void createData(){
        APIRequestData ardData = RetroServer.koneksiRetrofit().create(APIRequestData.class);
        Call<ResponseModel> simpandata = ardData.ardCreateData(NIM,Nama,Semester,Jurusan,Prodi);

        simpandata.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(TambahActivity.this, "Kode : " + kode + "| Pesan : " +pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(TambahActivity.this, "Gagal Menghubungi Server" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}