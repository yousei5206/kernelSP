package kernelsp.maeda.is.kyusan_u.ac.jp.kernelsp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MemActivity extends Fragment {
    private String[] items = { "0000    0000", "0001    0000", "0002    0000", "0003    0000",
            "0004    0000", "0005    0000", "0006    0000", "0007    0000" };

    private int data =0;
    private static ArrayList<String> memoryTest;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        View memview = inflater.inflate(R.layout.mem_detail, container, false);
        return memview;
    }

    public void onStart(){
        super.onStart();

        ArrayAdapter arrayAdapter = new ArrayAdapter(
                getActivity(),android.R.layout.simple_expandable_list_item_1, ConsoleActivity.getMem());

        ListView memlist =(ListView)getActivity().findViewById(R.id.memory);
        memlist.setAdapter(arrayAdapter);


    }

}
