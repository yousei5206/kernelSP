package kernelsp.maeda.is.kyusan_u.ac.jp.kernelsp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;


public class ExecutionActivity extends Fragment {

    int pc = 0x0000;
    int acc = 0x0000;
    int ixr = 0x0000;
    int dbus;
    int state = 0;
    int instState = 0;
    int ir;
    int opeState;
    int instructionType;
    int type1, type2, type3, type4, type5, type6;
    int register;
    int indexRegister;
    int effectiveAddress;
    int addressConstant;
    int loadState;
    int mdr;
    int mar;
    final String BACK_STACK_KEY = "BACK_STACK";

    public static ExecutionActivity newInstanceE() {
        ExecutionActivity fragment = new ExecutionActivity();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        View exeview = inflater.inflate(R.layout.execution_mode, container, false);
        return exeview;

    }

    public void onStart() {
        super.onStart();
        setRetainInstance(true);
        Button btnCon = (Button) getActivity().findViewById(R.id.consoleScreen);
        btnCon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.addToBackStack(null);
                setRetainInstance(true);

//                transaction.remove(getFragmentManager().findFragmentById(R.id.fragmentExecution));
                //               transaction.replace(R.id.fragmentConsole, ConsoleActivity.newInstanceC());
                //               transaction.commit();

                manager.popBackStack();
            }
        });
        Button btnMem = (Button)getActivity().findViewById(R.id.memDetailE);
        btnMem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.hide(getFragmentManager().findFragmentById(R.id.fragmentExecution));
                transaction.replace(R.id.fragmentMem, new MemActivity());
                setRetainInstance(true);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        setPC();
        setAcc();
        InstructionFetch();

    }

    public void setPC() {
        pc = ConsoleActivity.getPcdata();
        TextView k16pcText = (TextView) getActivity().findViewById(R.id.k16PC);
        k16pcText.setBackgroundResource(R.drawable.border_yellow);
        k16pcText.setText(Data.getZeroBit(pc));


    }

    public void setAcc() {
        acc = ConsoleActivity.getAccData();
        TextView k16accText = (TextView) getActivity().findViewById(R.id.k16ACC);
        k16accText.setText(Data.getZeroBit(acc));

    }

    public void setAbus(int abus) {
        TextView k16AbusText = (TextView) getActivity().findViewById(R.id.k16Abus);
        k16AbusText.setText(Data.getZeroBit(abus));
    }

    public void setDbus(int dbus) {
        TextView k16DbusText = (TextView) getActivity().findViewById(R.id.k16Dbus);
        k16DbusText.setText(Data.getZeroBit(dbus));
    }

    public void setMar(int mar) {
        TextView k16MarText = (TextView) getActivity().findViewById(R.id.k16MAR);
        k16MarText.setText(Data.getZeroBit(mar));

    }

    public void setMdr(int mdr) {
        TextView k16MdrText = (TextView) getActivity().findViewById(R.id.k16MDR);
        k16MdrText.setText(Data.getZeroBit(mdr));
    }

    public void setRbus(int rbus) {
        TextView k16RbusText = (TextView) getActivity().findViewById(R.id.k16Rbus);
        k16RbusText.setText(Data.getZeroBit(rbus));
    }

    public void setLbus(int lbus) {
        TextView k16LbusText = (TextView) getActivity().findViewById(R.id.k16Lbus);
        k16LbusText.setText(Data.getZeroBit(lbus));
    }

    public void setObus(int obus) {
        TextView k16ObusText = (TextView) getActivity().findViewById(R.id.k16Obus);
        k16ObusText.setText(Data.getZeroBit(obus));
    }

    public void setRWLED(boolean check) {
        RadioButton rbRW = (RadioButton) getActivity().findViewById(R.id.rw);
        rbRW.setChecked(check);
    }

    public void setMreqLED(boolean check) {
        RadioButton mreq = (RadioButton) getActivity().findViewById(R.id.mreq);
        mreq.setChecked(check);
    }

    public void setACKLED(boolean check) {
        RadioButton ack = (RadioButton) getActivity().findViewById(R.id.ack);
        ack.setChecked(check);
    }

    public void setIorqLED(boolean check) {
        RadioButton iorq = (RadioButton) getActivity().findViewById(R.id.iorq);
        iorq.setChecked(check);
    }

    public void InstructionFetch() {
        switch (state) {
            case 0:
                setAbus(pc);
                dbus = ConsoleActivity.memoryData.get(pc);
                setDbus(dbus);
                state++;
                break;

            case 1:
                //dbus = ConsoleActivity.memoryData.get(pc);
                //setDbus(dbus);
                state++;

                break;

            case 2:
                ir = ConsoleActivity.memoryData.get(pc);
                state++;
                break;

            case 3:
                pc = pc + 1;
                break;
        }
    }

    //    public void instructionDecode(){
//        switch (instState){
//            case 0:
//
//
//                break;
//
//
//            case 1:
//                break;
//        }
//    }
    public void operandFetch() {
        if (instructionType == type1) {
            switch (opeState) {
                case 0: //EAの計算
                    indexRegister = sift(ir, 10, 10);//インデックスレジスタの値を抽出
                    addressConstant = sift(ir, 9, 0);//アドレス部を抽出
                    if (indexRegister == 0) {
                        effectiveAddress = addressConstant;
                        mar = effectiveAddress;
                        setRbus(0);
                    } else if (indexRegister == 1) {
                        effectiveAddress = addressConstant + ixr;
                        setRbus(indexRegister);
                    }
                    setLbus(addressConstant);
                    setMar(mar);
                    setObus(effectiveAddress);
                    opeState++;
                    break;

                case 1://メモリの読出し1
                    setAbus(mar);
                    setMreqLED(true);
                    setRWLED(true);
                    opeState++;
                    break;

                case 2:
                    setACKLED(true);
                    setDbus(dbus);

                    opeState++;
                    break;

                case 3:
                    setMdr(dbus);
                    setMreqLED(false);
                    break;


                case 4:
                    setACKLED(false);


                    break;
            }

        }
    }


    public int sift(int data, int highIndex, int lowIndex) {
        data <<= (16 + 15 - highIndex);
        data >>= (16 + 15 - highIndex);
        data >>= lowIndex;
        return data;

    }

    private void load() {
        switch (loadState) {
            case 0:
                operandFetch();
                loadState++;
                break;

            case 1:
                setRbus(0);
                setLbus(mdr);
                loadState++;
                break;

            case 2:

                break;
        }
    }
}
