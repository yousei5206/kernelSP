package kernelsp.maeda.is.kyusan_u.ac.jp.kernelsp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class ExecutionActivity extends Fragment {

    int pc = 0x0000;
    int acc = 0x0100;
    int ixr = 0x0000;
    int dbus;
    int state = 0;
        int ir;
    int opeState,addState;
    int instructionType;
    int type1, type2, type3, type4, type5, type6;
    int register;
    int indexRegister;
    int effectiveAddress;
    int addressConstant;
    int loadState;
    int mdr;
    int mar;
    int lbus;
    int executeMode =0;
   // private Vibrator vibrator;
    final String BACK_STACK_KEY = "BACK_STACK";

    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> future;
    private Runnable cpu;
    //private Handler mHandler = new Handler();

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
        Button btnMem = (Button) getActivity().findViewById(R.id.memDetailE);
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
        executeModesSelect();
        Execute();


        setPC();
        setAcc();
        setMar(addressConstant);

    }

    private class CPU implements Runnable{

        public void run(){

        }

    }



    public void executeModesSelect(){
        final ToggleButton breakpoint =(ToggleButton)getActivity().findViewById(R.id.breakPoint);
        final ToggleButton clock = (ToggleButton)getActivity().findViewById(R.id.clock);
        final ToggleButton instruction = (ToggleButton)getActivity().findViewById(R.id.instruction);
        final ToggleButton normal = (ToggleButton)getActivity().findViewById(R.id.normal);

        breakpoint.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                executeMode =0;
                //breakpoint.setChecked(true);
                clock.setChecked(false);
                instruction.setChecked(false);
                normal.setChecked(false);
               // Toast.makeText(getActivity(),"BreakPointモード",Toast.LENGTH_SHORT).show();
            }
        });

        clock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                executeMode = 1;
              //  clock.setChecked(true);
                breakpoint.setChecked(false);
                instruction.setChecked(false);
                normal.setChecked(false);
               // Toast.makeText(getActivity(),"Clockモード",Toast.LENGTH_SHORT).show();

            }
        });

        instruction.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                executeMode = 2;
              ///  instruction.setChecked(true);
                breakpoint.setChecked(false);
                clock.setChecked(false);
                normal.setChecked(false);
               //Toast.makeText(getActivity(),"Instructionモード",Toast.LENGTH_SHORT).show();

            }
        });

        normal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                executeMode = 3;
                //normal.setChecked(true);
                breakpoint.setChecked(false);
                instruction.setChecked(false);
                clock.setChecked(false);
                // Toast.makeText(getActivity(),"Normalモード",Toast.LENGTH_SHORT).show();


            }
        });

    }



    public void setPC() {
        pc = ConsoleActivity.getPcdata();
        TextView k16pcText = (TextView) getActivity().findViewById(R.id.k16PC);
        //k16pcText.setBackgroundResource(R.drawable.border_yellow);
        k16pcText.setText(Data.getZeroBit(pc));
    }

    public void setIr(int ir) {
        TextView k16irText = (TextView) getActivity().findViewById(R.id.k16IR);
        k16irText.setBackgroundResource(R.drawable.border_yellow);
        k16irText.setText(Data.getZeroBit(ir));
    }

    public void setAcc() {
        acc = ConsoleActivity.getAccData();
        TextView k16accText = (TextView) getActivity().findViewById(R.id.k16ACC);
        k16accText.setText(Data.getZeroBit(acc));

    }

    public void setAbus(int abus) {
        TextView k16AbusText = (TextView) getActivity().findViewById(R.id.k16Abus);
        k16AbusText.setText(Data.getZeroBit(abus));
        k16AbusText.setBackgroundResource(R.drawable.border_yellow);
    }

    public void setDbus(int dbus) {
        TextView k16DbusText = (TextView) getActivity().findViewById(R.id.k16Dbus);
        k16DbusText.setText(Data.getZeroBit(dbus));
        k16DbusText.setBackgroundResource(R.drawable.border_yellow);
    }

    public void setMar(int mar) {
        TextView k16MarText = (TextView) getActivity().findViewById(R.id.k16MAR);
        k16MarText.setText(Data.getZeroBit(mar));
        k16MarText.setBackgroundResource(R.drawable.border_yellow);

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

    //命令フェッチ：PCが示すアドレスから次に実行する命令をメモリから読みだし、IRに格納する。
    public void InstructionFetch() {
        switch (state) {
            case 0://PCの内容をAbusに流す。その番地へリード要求。
                pc = ConsoleActivity.getPcdata();
                setAbus(pc);
                setMreqLED(true);
                setRWLED(true);
                state++;
                break;

            case 1://メモリは、応答を返し、指定番地から取り出したデータをDbusにのせる。
                dbus = ConsoleActivity.memoryData.get(pc);
                setACKLED(true);
                setDbus(dbus);
                state++;

                break;

            case 2://リード要求をやめ、Dbusの内容をIRに格納する。
                setMreqLED(true);
                ir = dbus;
                setIr(ir);
                state++;
                break;

            case 3://メモリからの応答がなくなり、PCを１加える。
                setACKLED(false);
                pc = pc + 1;
                setPC();
                break;
        }
    }


    public void operandFetch() {
        instructionType =type1;
        if (instructionType == type1) {
            switch (opeState) {
                case 0: //EAの計算:インデックス修飾がない場合、アドレスはそのままにMARに乗せる。インデックス修飾ある場合、IRのアドレス部と修飾をALUで計算。
                    indexRegister = getInstructionBit(ir, 10, 10);//インデックスレジスタの値を抽出
                    addressConstant = getInstructionBit(ir, 9, 0);//アドレス部を抽出
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

                case 1://データを取り出すためのリード要求。
                    setAbus(mar);
                    setMreqLED(true);
                    setRWLED(true);
                    opeState++;
                    break;

                case 2://メモリは応答し、EAの計算したMARで指定した番地のデータをDbusに乗せる。
                    setACKLED(true);
                    dbus = ConsoleActivity.memoryData.get(mar);
                    setDbus(dbus);
                    opeState++;
                    break;

                case 3://DbusからデータをMDRに乗せる。
                    mdr = dbus;
                    setMdr(mdr);
                    setMreqLED(false);
                    opeState++;
                    break;


                case 4:
                    setACKLED(false);
                    opeState = 0;
                    break;
                //次に実行すべき命令をmdrに格納して終了。
                default:
                    break;
            }

        }
    }


    public int getInstructionBit(int data, int highIndex, int lowIndex) {
        data <<= (16 + 15 - highIndex);
        data >>= (16 + 15 - highIndex);
        data >>= lowIndex;
        return data;

    }

    //load命令：メモリからデータを読出しレジスタに格納する。
    public void load() {
        switch (loadState) {
            case 0://オペランドフェッチ
                operandFetch();
                loadState++;
                break;

            case 1:
                setRbus(0);
                lbus = mdr;
                setLbus(lbus);
                loadState++;
                break;

            case 2:
                setObus(lbus);
                loadState++;
                break;
            case 3:
                acc = lbus;
                loadState++;
                break;
            default:
                break;

        }
    }
    //レジスタへメモリの値を加算:アドレス部で指定した番地のデータとACCを加算
    public void add(){
        switch (addState){
            case 0:
                operandFetch();
                addState++;
                break;

            case 1:
                //acc = ConsoleActivity.getAccData();
               // acc =0x100;
                setRbus(acc);
                setLbus(mdr);
                addState++;
                break;

            case 2:
                acc=acc + 1;
                setObus(acc);
                addState++;
                break;

            case 3:
                setAcc();
                addState++;
                break;

            case 4:
                addState =0;
                break;

            default:
                break;

        }

    }

    public void Execute(){
        Button exebtn = (Button)getActivity().findViewById(R.id.execute);
        exebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.vib.vibrate(100);
                acc = ConsoleActivity.getAccData();
                setAcc();
                pc = ConsoleActivity.getPcdata();
                setAbus(pc);
                dbus = ConsoleActivity.memoryData.get(pc);
                setDbus(dbus);
                ir = dbus;
                setIr(ir);
                addressConstant = getInstructionBit(ir, 9, 0);//アドレス部を抽出
                effectiveAddress = addressConstant;
                mar = effectiveAddress;
                setMar(mar);
                setMdr(ConsoleActivity.memoryData.get(mar));
                add();
               // InstructionFetch();
               // operandFetch();
               // add();
            }
        });
    }


}
