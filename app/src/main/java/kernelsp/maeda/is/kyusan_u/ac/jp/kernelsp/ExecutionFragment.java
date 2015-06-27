package kernelsp.maeda.is.kyusan_u.ac.jp.kernelsp;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ExecutionFragment extends Fragment {
    private ExecutionActivity parent;
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.execution_mode,container,false);

        Button btnMove =(Button)view.findViewById(R.id.consoleScreen);
        btnMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.move();
            }
        });
        return view;
    }

    public void onAttach(Activity activity){
        parent = (ExecutionActivity)activity;
        super.onAttach(activity);
    }


}