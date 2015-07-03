package kernelsp.maeda.is.kyusan_u.ac.jp.kernelsp;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.Toast;


public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        

        if(null == savedInstanceState) {
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();

            transaction.addToBackStack(null);
            // transaction.remove(R.id.fragment1);
            transaction.replace(R.id.fragmentConsole, ConsoleActivity.newInstanceC());
            transaction.commit();

        }

    }



}
