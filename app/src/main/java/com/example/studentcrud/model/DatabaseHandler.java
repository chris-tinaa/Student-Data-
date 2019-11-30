package com.example.studentcrud.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // database name
    private static final String DATABASE_NAME = "DataSiswaManager";

    // table name
    private static final String TABLE_SISWA = "siswa";

    // column tables
    private static final String KEY_ID = "id_siswa";
    private static final String KEY_NAME = "nama_siswa";
    private static final String KEY_EMAIL = "email_siswa";
    private static final String KEY_NOHP = "nohp_siswa";
    private static final String KEY_IMG = "img_siswa";

    public DatabaseHandler(Context context) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    // create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SISWA_TABLE = "CREATE TABLE " + TABLE_SISWA + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_NOHP + " TEXT,"
                + KEY_IMG + " BLOB)";
        db.execSQL( CREATE_SISWA_TABLE );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SISWA);
        onCreate(db);
    }

    // add data
    public void addRecord(Siswa siswa){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues(  );
        values.put( KEY_NAME, siswa.getNama() );
        values.put( KEY_EMAIL, siswa.getEmail() );
        values.put( KEY_NOHP, siswa.getNo_hp() );
        values.put( KEY_IMG, siswa.getImage() );

        db.insert( TABLE_SISWA, null, values );
        db.close();
    }

    // get one data
    public Siswa getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_SISWA,
                new String[]{KEY_ID, KEY_NAME, KEY_EMAIL, KEY_NOHP, KEY_IMG},
                KEY_ID + "=?",
                new String[]{String.valueOf( id )},
                null,
                null,
                null
        );

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Siswa siswa = new Siswa(
                cursor.getString( 0 ),
                cursor.getString( 1 ),
                cursor.getString( 2 ),
                cursor.getString( 3 ),
                cursor.getBlob( 4 )
        );

        return siswa;
    }


    // get all data
    public ArrayList<Siswa> getAllRecord() {
        ArrayList<Siswa> listSiswa = new ArrayList<>(  );

        String selectQuery = "SELECT * FROM " + TABLE_SISWA;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery( selectQuery, null );

        if (cursor.moveToFirst()){
            do {
                Siswa siswa = new Siswa(  );
                siswa.setIdSiswa( cursor.getString( 0 ) );
                siswa.setNama( cursor.getString( 1 ) );
                siswa.setEmail( cursor.getString( 2 ) );
                siswa.setNo_hp( cursor.getString( 3 ) );
                siswa.setImage( cursor.getBlob( 4 ) );

                listSiswa.add( siswa );
            } while (cursor.moveToNext());
        }

        return listSiswa;
    }


    // get record count
    public int getSiswaCount(){
        String countQuery = "SELECT * FROM " + TABLE_SISWA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( countQuery, null );
        cursor.close();

        return cursor.getCount();
    }


    // update record table
    public int updateSiswa(Siswa siswa){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues(  );
        values.put( KEY_NAME, siswa.getNama() );
        values.put( KEY_EMAIL, siswa.getEmail() );
        values.put( KEY_NOHP, siswa.getNo_hp() );
        values.put( KEY_IMG, siswa.getImage() );
        return db.update( TABLE_SISWA, values, KEY_ID + " = ?", new String[]{siswa.getIdSiswa()} );
    }

    public void deleteModel (Siswa siswa){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete( TABLE_SISWA, KEY_ID + " = ?", new String[]{siswa.getIdSiswa()} );
        db.close();
    }
}
