package com.example.studentcrud.list_siswa;

import com.example.studentcrud.model.Siswa;

import java.util.ArrayList;

public class ListSiswaPresenter implements ListSiswaContract.Presenter {
    private ListSiswaContract.View view;
    private ArrayList<Siswa> listData = new ArrayList<>(  );
    @Override
    public void attachView(ListSiswaContract.View view) {
        this.view = view;
    }

    @Override
    public void loadData() {

    }
}
