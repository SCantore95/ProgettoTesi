package tesi.example.myapplication.Interface;

import java.util.List;

import tesi.example.myapplication.fragment.ResultsItem;

public interface ProfileDataListener {
    void onProfileDataAvailable(List<ResultsItem> data);
    void onDataReceived(String data);


}
