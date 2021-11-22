package com.farul.implementasiretrofit.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.farul.implementasiretrofit.API.APIRequestData;
import com.farul.implementasiretrofit.API.RetroServer;
import com.farul.implementasiretrofit.Activity.MainActivity;
import com.farul.implementasiretrofit.Activity.UbahActivity;
import com.farul.implementasiretrofit.Model.DataModel;
import com.farul.implementasiretrofit.Model.ResponseModel;
import com.farul.implementasiretrofit.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData> {
    private Context ctx;
    private List<DataModel> listMahasiswa;
    private List<DataModel> listData;
    private String NIM;

    public AdapterData(Context ctx, List<DataModel> listMahasiswa) {
        this.ctx = ctx;
        this.listMahasiswa = listMahasiswa;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataModel dm = listMahasiswa.get(position);

        holder.tvNim.setText(dm.getNIM());
        holder.tvNama.setText(dm.getNama());
        holder.tvSemester.setText(dm.getSemester());
        holder.tvJurusan.setText(dm.getJurusan());
        holder.tvProdi.setText(dm.getProdi());
    }

    @Override
    public int getItemCount() {

        return listMahasiswa.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView tvNim, tvNama, tvSemester, tvJurusan, tvProdi;

        public HolderData(@NonNull View itemView) {
            super(itemView);

            tvNim = itemView.findViewById(R.id.tv_nim);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvSemester = itemView.findViewById(R.id.tv_semester);
            tvJurusan = itemView.findViewById(R.id.tv_jurusan);
            tvProdi = itemView.findViewById(R.id.tv_prodi);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder dialogPesan = new AlertDialog.Builder(ctx);
                    dialogPesan.setMessage("Pilih Operasi Yang Akan Dilakukan");
                    dialogPesan.setTitle("Perhatian!");
                    dialogPesan.setIcon(R.mipmap.ic_launcher_round);
                    dialogPesan.setCancelable(true);

                    NIM = tvNim.getText().toString();

                    dialogPesan.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteData();
                            dialogInterface.dismiss();
                            ((MainActivity) ctx).retrieveData();
                        }
                    });

                    dialogPesan.setNegativeButton("Ubah", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        getData();
                        dialogInterface.dismiss();
                        }
                    });

                    dialogPesan.show();

                    return false;
                }
            });
        }
        private void deleteData() {
            APIRequestData ardData = RetroServer.koneksiRetrofit().create(APIRequestData.class);
            Call<ResponseModel> hapusData = ardData.ardDeleteData(NIM);

            hapusData.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    Toast.makeText(ctx, "Kode : "+kode+" | Pesan :"+pesan, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(ctx, "Gagal Menghubungi Server : "+ t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        private void getData() {
            APIRequestData ardData = RetroServer.koneksiRetrofit().create(APIRequestData.class);
            Call<ResponseModel> ambilData = ardData.ardGetData(NIM);

            ambilData.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    listData = response.body().getData();

                    String varNIM = listData.get(0).getNIM();
                    String varNama = listData.get(0).getNama();
                    String varSemester = listData.get(0).getSemester();
                    String varJurusan = listData.get(0).getJurusan();
                    String varProdi = listData.get(0).getProdi();

//                    Toast.makeText(ctx, "Kode : "+kode+" | Pesan : "+pesan+" | Data : "+varNIM+" | "+varNama+" | "+varSemester+" | "+varJurusan+" | "+varProdi, Toast.LENGTH_SHORT).show();

                    Intent kirim = new Intent(ctx, UbahActivity.class);
                    kirim.putExtra("xNim", varNIM);
                    kirim.putExtra("xNama", varNama);
                    kirim.putExtra("xSemester", varSemester);
                    kirim.putExtra("xJurusan", varJurusan);
                    kirim.putExtra("xProdi", varProdi);
                    ctx.startActivity(kirim);
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal Menghubungi Server : "+ t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
