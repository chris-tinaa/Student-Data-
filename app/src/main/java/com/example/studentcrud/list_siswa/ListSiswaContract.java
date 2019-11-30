package com.example.studentcrud.list_siswa;

import com.example.studentcrud.model.Siswa;

import java.util.ArrayList;

interface ListSiswaContract {

    interface View{
        void showToast(String s);
        void showProgress(String s);
        void stopProgress();
        void setData(ArrayList<Siswa> siswa);
    }

    interface Presenter {
        void attachView(ListSiswaContract.View view);
        void loadData();
    }
}
