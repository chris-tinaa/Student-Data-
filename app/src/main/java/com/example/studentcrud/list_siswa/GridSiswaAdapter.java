package com.example.studentcrud.list_siswa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.studentcrud.R;
import com.example.studentcrud.model.Siswa;

import java.util.ArrayList;

public class GridSiswaAdapter extends RecyclerView.Adapter<GridSiswaAdapter.GridViewHolder> {

    private ArrayList<Siswa> listSiswa;
    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public GridSiswaAdapter(ArrayList<Siswa> listSiswa) {
        this.listSiswa = listSiswa;
    }

    @NonNull
    @Override
    public GridSiswaAdapter.GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.siswa_item, parent,false );
        return new GridViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull GridSiswaAdapter.GridViewHolder holder, int position) {
        holder.tvNama.setText( listSiswa.get(position).getNama() );
        holder.tvEmail.setText( listSiswa.get(position).getEmail() );
        holder.tvNohp.setText( listSiswa.get(position).getNo_hp() );
        Glide.with(holder.itemView.getContext())
                .load(listSiswa.get( position ).getImage())
                .into(holder.ivFoto);


        holder.itemView.setOnClickListener( (data) -> {
            onItemClickCallback.onItemClicked( listSiswa.get( holder.getAdapterPosition() ) );
        } );
    }

    @Override
    public int getItemCount() {
        return listSiswa.size();
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {

        TextView tvNama;
        TextView tvEmail;
        TextView tvNohp;
        ImageView ivFoto;

        public GridViewHolder(@NonNull View itemView) {
            super( itemView );

            tvNama = itemView.findViewById( R.id.tv_nama );
            tvEmail = itemView.findViewById( R.id.tv_email );
            tvNohp = itemView.findViewById( R.id.tv_nohp );
            ivFoto = itemView.findViewById( R.id.iv_profile );
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Siswa data);
    }
}
