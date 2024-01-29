package tesi.example.myapplication.Interface;

import java.util.List;

import tesi.example.myapplication.fragment.ResultsItem;

public interface StatsDataListener {
    int BUTTON_BAR_CHART = 1;
    int BUTTON_PIE_CHART = 2;


    void onStatsDataAvailable(List<ResultsItem> currentData);

    void onStatsButtonClick(int buttonId);


}
