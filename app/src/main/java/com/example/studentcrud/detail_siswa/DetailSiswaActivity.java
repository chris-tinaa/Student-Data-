package com.example.studentcrud.detail_siswa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentcrud.model.DatabaseHandler;
import com.example.studentcrud.R;
import com.example.studentcrud.model.Siswa;

import java.io.ByteArrayOutputStream;
import java.nio.file.Files;

import in.mayanknagwanshi.imagepicker.ImageSelectActivity;

public class DetailSiswaActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etNama, etEmail, etNohp;
    private TextView tvPic;
    private Button btnSimpan, btnPilih;
    private Siswa siswa;
    private Siswa siswaBaru = new Siswa(  );
    private String title;
    private DatabaseHandler db;
    private Context activityContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detail_siswa );

        activityContext = this;
        siswa = (Siswa) getIntent().getSerializableExtra("siswaObj");
        etNama = findViewById( R.id.et_nama );
        etEmail = findViewById( R.id.et_email );
        etNohp = findViewById( R.id.et_nohp );
        btnSimpan = findViewById( R.id.btn_simpan );
        btnPilih = findViewById( R.id.btn_pilih );
        tvPic = findViewById( R.id.tv_pic );
        db = new DatabaseHandler( this );

        if (siswa != null){
            etNama.setText( siswa.getNama() );
            etEmail.setText( siswa.getEmail() );
            etNohp.setText( siswa.getNo_hp() );
            siswaBaru.setImage( siswa.getImage() );
            title = getString( R.string.edit_data );
        } else {
            title = getString( R.string.create_data );
        }

        setActionBarTitle( title );

        btnPilih.setOnClickListener(this);

        btnSimpan.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1213 && resultCode == Activity.RESULT_OK) {

            String filePath = data.getStringExtra(ImageSelectActivity.RESULT_FILE_PATH);
            Bitmap selectedImage = BitmapFactory.decodeFile(filePath);

            long size = byteSizeOf( selectedImage );
            if (size < 15000000) {

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                selectedImage.compress( Bitmap.CompressFormat.JPEG, 100, stream );
                byte[] imageBlob = stream.toByteArray();

                siswaBaru.setImage( imageBlob );
                Toast.makeText( activityContext, "Berhasil memasukkan foto", Toast.LENGTH_SHORT ).show();
            }else {
                Toast.makeText( activityContext, "Ukuran terlalu besar", Toast.LENGTH_SHORT ).show();
            }
        }
    }

    public static int byteSizeOf(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return bitmap.getAllocationByteCount();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount();
        } else {
            return bitmap.getRowBytes() * bitmap.getHeight();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getSupportActionBar().getTitle().toString().equals( getString( R.string.edit_data) )) {
            getMenuInflater().inflate( R.menu.detail_siswa_menu, menu );
        }
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        new AlertDialog.Builder(this)
                .setTitle( "Delete data" )
                .setMessage( "Apakah anda yakin ingin menghapus?" )
                .setPositiveButton( "Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteModel( siswa );
                        finish();
                    }
                } )
                // null to dismiss
                .setNegativeButton( "No", null )
                .setIcon( R.drawable.ic_warning_black_24dp )
                .show();

        return super.onOptionsItemSelected( item );
    }

    private void setActionBarTitle(String title){
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle( title );
        }
    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setTitle( "Kembali" )
                .setMessage( "Apakah anda ingin kembali tanpa menyimpan perubahan?" )
                .setPositiveButton( "Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                } )
                // null to dismiss
                .setNegativeButton( "No", null )
                .setIcon( R.drawable.ic_warning_black_24dp )
                .show();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_pilih){
            Intent intent = new Intent(activityContext, ImageSelectActivity.class);
            intent.putExtra(ImageSelectActivity.FLAG_COMPRESS, false);//default is true
            intent.putExtra(ImageSelectActivity.FLAG_CAMERA, true);//default is true
            intent.putExtra(ImageSelectActivity.FLAG_GALLERY, true);//default is true
            startActivityForResult(intent, 1213);
        }else if (v.getId() == R.id.btn_simpan){
            siswaBaru.setNama( etNama.getText().toString() );
            siswaBaru.setEmail( etEmail.getText().toString() );
            siswaBaru.setNo_hp( etNohp.getText().toString() );

            if (getSupportActionBar().getTitle().toString().equals( getString( R.string.edit_data) )) {
                siswaBaru.setIdSiswa( siswa.getIdSiswa() );
                db.updateSiswa( siswaBaru );
            } else if (getSupportActionBar().getTitle().toString().equals( getString( R.string.create_data) )){
                db.addRecord( siswaBaru );
            }

            finish();
        }
    }
}
