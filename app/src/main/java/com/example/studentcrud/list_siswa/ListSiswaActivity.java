package com.example.studentcrud.list_siswa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.studentcrud.model.DatabaseHandler;
import com.example.studentcrud.detail_siswa.DetailSiswaActivity;
import com.example.studentcrud.R;
import com.example.studentcrud.model.Siswa;

import java.util.ArrayList;

public class ListSiswaActivity extends AppCompatActivity {

    private RecyclerView rvSiswa;
    private ArrayList<Siswa> listSiswa = new ArrayList<>();
    private String title = "Data Siswa";
    private GridSiswaAdapter gridSiswaAdapter;
    private DatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        db = new DatabaseHandler( this );
        rvSiswa = findViewById( R.id.rv_siswa );
        rvSiswa.setHasFixedSize( true );

        setActionBarTitle( title );
        listSiswa = db.getAllRecord();
        showRecyclerGrid();

    }

    private void showRecyclerGrid(){
        rvSiswa.setLayoutManager( new GridLayoutManager( this,2 ) );
        gridSiswaAdapter = new GridSiswaAdapter( listSiswa );
        rvSiswa.setAdapter( gridSiswaAdapter );

        gridSiswaAdapter.setOnItemClickCallback( (data) -> {
            showSelectedSiswa( data );
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.main_activity_menu, menu );
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        moveActivity(null);
        return super.onOptionsItemSelected( item );
    }

    private void moveActivity(Siswa siswa){
        Intent intent = new Intent(this, DetailSiswaActivity.class);
        intent.putExtra("siswaObj", siswa);
        startActivity( intent );
    }

    private void setActionBarTitle(String title){
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle( title );
        }
    }

    private void showSelectedSiswa( Siswa siswa){
        moveActivity( siswa );
    }

    @Override
    protected void onResume() {
        super.onResume();

        listSiswa = db.getAllRecord();
        showRecyclerGrid();
    }
}
