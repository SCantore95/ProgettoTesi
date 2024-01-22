package tesi.example.myapplication.fragment;

import java.util.List;

public interface ProfileDataListener {
    void onProfileDataAvailable(List<ResultsItem> data);
    void onDataReceived(String data);

}
