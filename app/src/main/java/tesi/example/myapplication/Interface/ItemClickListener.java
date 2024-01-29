package tesi.example.myapplication.Interface;

import java.util.List;

import tesi.example.myapplication.fragment.ResultsItem;

public interface ItemClickListener {
    void onItemClick(ResultsItem currentItem);



    void onItemDetailsFragmentClick(ResultsItem currentItem);


    void onProfileDataAvailable(List<ResultsItem> attackLists);




}
