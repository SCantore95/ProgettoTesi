package tesi.example.myapplication.fragment;

import android.content.Intent;

import java.util.List;

public interface ItemClickListener {
    void onItemClick(ResultsItem currentItem);



    void onItemDetailsFragmentClick(ResultsItem currentItem);


    void onProfileDataAvailable(List<ResultsItem> attackLists);

    void onStatsFragmentClick(Intent intent);


}
