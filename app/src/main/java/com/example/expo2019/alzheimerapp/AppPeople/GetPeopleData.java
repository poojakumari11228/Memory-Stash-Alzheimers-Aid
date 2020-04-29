package com.example.expo2019.alzheimerapp.AppPeople;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.expo2019.alzheimerapp.DataBase;
import com.example.expo2019.alzheimerapp.R;

import java.util.List;

public class GetPeopleData extends AsyncTask<String,Void,List<People>> {

    RecyclerView recyclerView;
    ListPeopleDataAdapter listPeopleDataAdapter;

    Activity context;

    public GetPeopleData(Activity context) {
        this.context = context;
    }

    @Override
    protected List<People> doInBackground(String... strings) {

        List<People> dataList;
        if(strings[0].equalsIgnoreCase("all")) {
            dataList = DataBase.getAppDatabase(this.context).peopleDao().getAll();
        }else {
             dataList = DataBase.getAppDatabase(this.context).peopleDao().getByName(strings[0]);
        }
        return dataList;
    }

    @Override
    protected void onPostExecute(List<People> people) {
        super.onPostExecute(people);

        recyclerView = (RecyclerView) ((Activity)context).findViewById(R.id.viewPeople_rv);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(mGridLayoutManager);
//        Log.d("pppppppp", "onPostExecute: "+listPeopleDataList.get(0).getName());

        listPeopleDataAdapter = new ListPeopleDataAdapter(this.context,people);
        recyclerView.setAdapter(listPeopleDataAdapter);

    }
}
