package kernelsp.maeda.is.kyusan_u.ac.jp.kernelsp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.util.ArrayList;


public class ConsoleActivity extends FragmentActivity {
    int dataBit[] = new int[16];
    int inputBit =0;
    int address;
    String memdata ="";
    boolean memRegflag = true;
    boolean memFlag =true;
    boolean regFlag = false;
    static int pcdata =0;
    String pcData="";
    static int accData =0;
    static int ixrData =0;
    int data0;
    String data3;
    String memData ="0000";
    String addressNum;


    private static final int MEMORY_SIZE = 65536;
    private static ArrayList<String> memory;
    static ArrayList<Integer> memoryData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_console);
        memory = new ArrayList<>(MEMORY_SIZE);
        memoryData = new ArrayList<>(MEMORY_SIZE);

        for (int i=0x000;i<MEMORY_SIZE;i++){
            if(0x000f >= i){
                Data.zeroBit ="000";
                addressNum = Data.zeroBit +Integer.toHexString(i);
            }else if(0x000f < i && 0x00f0 >= i){
                Data.zeroBit ="00";
                addressNum = Data.zeroBit +Integer.toHexString(i);
            }else if(0x00f0 < i && 0x0f00 >= i){
                Data.zeroBit ="0";
                addressNum = Data.zeroBit +Integer.toHexString(i);
            }else {
                addressNum = Data.zeroBit + Integer.toHexString(i);
            }
            memory.add(addressNum+"     "+memData);
            memoryData.add(i);
        }

        CheckInputdata0();
        CheckInputdata1();
        CheckInputdata2();
        CheckInputdata3();
        CheckInputdata4();
        CheckInputdata5();
        CheckInputdata6();
        CheckInputdata7();
        CheckInputdata8();
        CheckInputdata9();
        CheckInputdata10();
        CheckInputdata11();
        CheckInputdata12();
        CheckInputdata13();
        CheckInputdata14();
        CheckInputdata15();
        setAddress();
        setData();
        setPC();
        reset();
        checkMemReg();
        setACC();
        setIxr();
        displayData();
    }

    public static int getPcdata(){
        return pcdata;
    }
    public static int getAccData(){
        return accData;
    }

    public static int getIxrData(){
        return ixrData;
    }

    public static ArrayList getMem(){
        return memory;
    }


    protected void onRestoreInstaceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

    }


    public void move(){
        Intent intent = new Intent(this,ExecutionActivity.class);
        startActivity(intent);
    }


    public void memDetail(View v){
        Button membtn = (Button)findViewById(R.id.memDetailC);
        membtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MemActivity.class);
                startActivity(i);

            }
        });

    }


    public void setInputData(){
        final TextView inputDataText = (TextView)findViewById(R.id.inputdataText);
        inputBit =0;
        for (int a=0;a< dataBit.length;a++){
            inputBit ^= dataBit[a];
        }
        inputDataText.setText(Data.getZeroBit(inputBit));

    }


    public void setData(){
        Button databutton = (Button)findViewById(R.id.dataSet);
        databutton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView dataText = (TextView) findViewById(R.id.dataText);
                        if (memFlag == true) {
                            data3 =Data.getZeroBit(inputBit);
                            dataText.setText(Data.getZeroBit(inputBit));

                            //String addressBit = Integer.toHexString(address);

                            if (0x000f >= address) {
                                Data.zeroBit = "000";
                                addressNum = Data.zeroBit + address;
                            } else if (0x0010 <= address && 0x00f0 >= address) {
                                Data.zeroBit = "00";
                                addressNum = Data.zeroBit + address;
                            } else if (0x0100 <= address && 0x0f00 >= address) {
                                Data.zeroBit = "0";
                                addressNum = Data.zeroBit + address;
                            } else {
                                addressNum = Data.zeroBit + address;
                            }

                            memory.set(address, addressNum + "     " + data3);
                            memoryData.set(address,inputBit);

                        }

                    }
                }
        );

    }

    public void setAddress(){
        Button adbutton =(Button)findViewById(R.id.addressSet);
        adbutton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView addressText = (TextView)findViewById(R.id.addressText);
                        if(memFlag ==true) {
                            //addressText.setText(getInputDataAns());
                            address = inputBit;
                            addressText.setText(Data.getZeroBit(address));


                        }

                    }
                }
        );
    }

    public void displayData(){
        Button ddButton = (Button)findViewById(R.id.displayData);
        ddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ConsoleActivity.this, memory.get(address), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setPC(){
        Button pcbtn =(Button)findViewById(R.id.pc);
        pcbtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(regFlag == true) {
                            TextView pcText = (TextView) findViewById(R.id.pcText);
                            //TextView k16pcText =(TextView)findViewById(R.id.k16PC);
                            pcdata = inputBit;
                            pcText.setText(Integer.toHexString(pcdata));
                            pcText.setText(Data.getZeroBit(inputBit));

                        }
                    }
                }
        );
    }



    public void setACC(){
        Button accbtn = (Button)findViewById(R.id.acc);
        accbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(regFlag == true){
                    TextView accText = (TextView)findViewById(R.id.accText);
                    accData = inputBit;
                    Toast.makeText(ConsoleActivity.this,"ACC",Toast.LENGTH_SHORT).show();
                    accText.setText(Data.getZeroBit(accData));

                }
            }
        });

    }

    public void setIxr(){
        Button ixrbtn = (Button)findViewById(R.id.ixr);
        ixrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(regFlag == true){
                    TextView ixrText = (TextView)findViewById(R.id.ixrText);
                    ixrData = inputBit;
                    ixrText.setText(Data.getZeroBit(ixrData));

                }


            }

        });

    }

    public void reset(){
        Button rst = (Button)findViewById(R.id.reset);

        rst.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        inputBit =0;
                        Toast.makeText(ConsoleActivity.this, "reset", Toast.LENGTH_SHORT).show();
                        for(int i =0;i< dataBit.length;i++) {
                            dataBit[i] = 0;
                        }
                        ToggleButton tg0 =(ToggleButton)findViewById(R.id.data0);
                        tg0.setChecked(false);

                        ToggleButton tg1 =(ToggleButton)findViewById(R.id.data1);
                        tg1.setChecked(false);

                        ToggleButton tg2 =(ToggleButton)findViewById(R.id.data2);
                        tg2.setChecked(false);

                        ToggleButton tg3 =(ToggleButton)findViewById(R.id.data3);
                        tg3.setChecked(false);

                        ToggleButton tg4 =(ToggleButton)findViewById(R.id.data4);
                        tg4.setChecked(false);

                        ToggleButton tg5 =(ToggleButton)findViewById(R.id.data5);
                        tg5.setChecked(false);

                        ToggleButton tg6 =(ToggleButton)findViewById(R.id.data6);
                        tg6.setChecked(false);

                        ToggleButton tg7 =(ToggleButton)findViewById(R.id.data7);
                        tg7.setChecked(false);

                        ToggleButton tg8 =(ToggleButton)findViewById(R.id.data8);
                        tg8.setChecked(false);

                        ToggleButton tg9 =(ToggleButton)findViewById(R.id.data9);
                        tg9.setChecked(false);

                        ToggleButton tg10 =(ToggleButton)findViewById(R.id.data10);
                        tg10.setChecked(false);

                        ToggleButton tg11 =(ToggleButton)findViewById(R.id.data11);
                        tg11.setChecked(false);

                        ToggleButton tg12 =(ToggleButton)findViewById(R.id.data12);
                        tg12.setChecked(false);

                        ToggleButton tg13 =(ToggleButton)findViewById(R.id.data13);
                        tg13.setChecked(false);

                        ToggleButton tg14 =(ToggleButton)findViewById(R.id.data14);
                        tg14.setChecked(false);

                        ToggleButton tg15 =(ToggleButton)findViewById(R.id.data15);
                        tg15.setChecked(false);
                    }
                }
        );
    }

    public void checkMemReg(){
        ToggleButton memreg =(ToggleButton)findViewById(R.id.regMem);

        memreg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked){
                LinearLayout memSeFrame =(LinearLayout)findViewById(R.id.memSelectTopFrame);
                LinearLayout regSeFrame = (LinearLayout)findViewById(R.id.registerSelectTopFrame);
                ToggleButton memreg =(ToggleButton)findViewById(R.id.regMem);

                if(isChecked){
                    memFlag =false;
                    regFlag = true;
                    memSeFrame.setBackgroundResource(R.drawable.border);
                    regSeFrame.setBackgroundResource(R.drawable.border_lime);
                    memreg.setText("Reg.");
                    memreg.setTextColor(getResources().getColor(R.color.limeGreen));
                    Toast.makeText(ConsoleActivity.this,"Reg.モード",Toast.LENGTH_SHORT).show();

                }else{
                    memFlag =true;
                    regFlag =false;
                    memSeFrame.setBackgroundResource(R.drawable.border1);
                    regSeFrame.setBackgroundResource(R.drawable.border);
                    memreg.setTextColor(getResources().getColor(R.color.lightseaGreen));
                    Toast.makeText(ConsoleActivity.this,"Mem.モード",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    public void CheckInputdata0(){
        ToggleButton tg =(ToggleButton)findViewById(R.id.data0);
        tg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dataBit[0] = 0x0001;
                    setInputData();
                } else {
                    dataBit[0] = 0x0000;
                    setInputData();
                }
            }
        });
    }

    public void CheckInputdata1(){
        ToggleButton tg =(ToggleButton)findViewById(R.id.data1);

        tg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    dataBit[1] = 0x0002;
                    setInputData();
                }else{
                    dataBit[1] = 0x0000;
                    setInputData();
                }
            }
        });
    }

    public void CheckInputdata2(){
        ToggleButton tg =(ToggleButton)findViewById(R.id.data2);

        tg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    dataBit[2] = 0x0004;
                    setInputData();
                }else{
                    dataBit[2] = 0x0000;
                    setInputData();
                }
            }
        });
    }
    public void CheckInputdata3(){
        ToggleButton tg =(ToggleButton)findViewById(R.id.data3);

        tg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    dataBit[3] = 0x0008;
                    setInputData();
                }else{
                    dataBit[3] = 0x0000;
                    setInputData();
                }
            }
        });
    }
    public void CheckInputdata4(){
        ToggleButton tg =(ToggleButton)findViewById(R.id.data4);

        tg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    dataBit[4] = 0x0010;
                    setInputData();
                }else{
                    dataBit[4] = 0x0000;
                    setInputData();
                }
            }
        });
    }
    public void CheckInputdata5(){
        ToggleButton tg =(ToggleButton)findViewById(R.id.data5);

        tg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    dataBit[5] = 0x0020;
                    setInputData();
                }else{
                    dataBit[5] = 0x0000;
                    setInputData();
                }
            }
        });
    }
    public void CheckInputdata6(){
        ToggleButton tg =(ToggleButton)findViewById(R.id.data6);

        tg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    dataBit[6] = 0x0040;
                    setInputData();
                }else{
                    dataBit[6] = 0x0000;
                    setInputData();
                }
            }
        });
    }
    public void CheckInputdata7(){
        ToggleButton tg =(ToggleButton)findViewById(R.id.data7);

        tg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    dataBit[7] = 0x0080;
                    setInputData();
                }else{
                    dataBit[7] = 0x0000;
                    setInputData();
                }
            }
        });
    }
    public void CheckInputdata8(){
        ToggleButton tg =(ToggleButton)findViewById(R.id.data8);

        tg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    dataBit[8] = 0x0100;
                    setInputData();
                }else{
                    dataBit[8] = 0x0000;
                    setInputData();
                }
            }
        });
    }
    public void CheckInputdata9(){
        ToggleButton tg =(ToggleButton)findViewById(R.id.data9);

        tg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    dataBit[9] = 0x0200;
                    setInputData();
                }else{
                    dataBit[9] = 0x0000;
                    setInputData();
                }
            }
        });
    }
    public void CheckInputdata10(){
        ToggleButton tg =(ToggleButton)findViewById(R.id.data10);

        tg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    dataBit[10] = 0x0400;
                    setInputData();
                }else{
                    dataBit[10] = 0x0000;
                    setInputData();
                }
            }
        });
    }
    public void CheckInputdata11(){
        ToggleButton tg =(ToggleButton)findViewById(R.id.data11);

        tg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    dataBit[11] = 0x0800;
                    setInputData();
                }else{
                    dataBit[11] = 0x0000;
                    setInputData();
                }
            }
        });
    }
    public void CheckInputdata12(){
        ToggleButton tg =(ToggleButton)findViewById(R.id.data12);

        tg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    dataBit[12] = 0x1000;
                    setInputData();
                }else{
                    dataBit[12] = 0x0000;
                    setInputData();
                }
            }
        });
    }
    public void CheckInputdata13(){
        ToggleButton tg =(ToggleButton)findViewById(R.id.data13);

        tg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    dataBit[13] = 0x2000;
                    setInputData();
                }else{
                    dataBit[13] = 0x0000;
                    setInputData();
                }
            }
        });
    }
    public void CheckInputdata14(){
        ToggleButton tg =(ToggleButton)findViewById(R.id.data14);

        tg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    dataBit[14] = 0x4000;
                    setInputData();
                }else{
                    dataBit[14] = 0x0000;
                    setInputData();
                }
            }
        });
    }  public void CheckInputdata15(){
        ToggleButton tg =(ToggleButton)findViewById(R.id.data15);

        tg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    dataBit[15] = 0x8000;
                    setInputData();
                }else{
                    dataBit[15] = 0x0000;
                    setInputData();
                }
            }
        });
    }
}