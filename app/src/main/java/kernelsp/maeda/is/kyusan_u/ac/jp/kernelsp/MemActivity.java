package kernelsp.maeda.is.kyusan_u.ac.jp.kernelsp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MemActivity extends Activity {
    private String[] items = { "0000    0000", "0001    0000", "0002    0000", "0003    0000",
            "0004    0000", "0005    0000", "0006    0000", "0007    0000" };

    private int data =0;
    private static ArrayList<String> memoryTest;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mem_detail);

        ArrayAdapter arrayAdapter = new ArrayAdapter(
                this,android.R.layout.simple_expandable_list_item_1, ConsoleActivity.getMem());

        ListView memlist =(ListView)findViewById(R.id.memory);
        memlist.setAdapter(arrayAdapter);

    }




    public void backmem(View view){

        Intent i = new Intent(this,ConsoleActivity.class);
        startActivity(i);
    }

}
