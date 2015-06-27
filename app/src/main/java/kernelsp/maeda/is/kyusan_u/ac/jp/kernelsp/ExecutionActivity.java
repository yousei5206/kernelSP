package kernelsp.maeda.is.kyusan_u.ac.jp.kernelsp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;


public class ExecutionActivity extends FragmentActivity {

    int pc =0x0000;
    int acc =0x0000;
    int ixr =0x0000;
    int dbus;
    int state =0;
    int instState =0;
    int ir;
    int opeState;
    int instructionType;
    int type1,type2,type3,type4,type5,type6;
    int register;
    int indexRegister;
    int effectiveAddress;
    int addressConstant;
    int loadState;
    int mdr;
    int mar;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_execution);
        setPC();
        setAcc();
        InstructionFetch();

    }

    public void move(){
        Intent intent =new Intent(this,ConsoleActivity.class);
        startActivity(intent);
    }
    public void memDetail(View v){
        Button membtn = (Button)findViewById(R.id.memDetailE);
        membtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MemActivity.class);
                startActivity(i);

            }
        });

    }
    public void setPC(){
        pc =ConsoleActivity.getPcdata();
        TextView k16pcText =(TextView)findViewById(R.id.k16PC);
        k16pcText.setBackgroundResource(R.drawable.boder_yellow);
        k16pcText.setText(Data.getZeroBit(pc));


    }
    public void setAcc(){
        acc =ConsoleActivity.getAccData();
        TextView k16accText =(TextView)findViewById(R.id.k16ACC);
        k16accText.setText(Data.getZeroBit(acc));

    }
    public void setAbus(int abus){
        TextView k16AbusText =(TextView)findViewById(R.id.k16Abus);
        k16AbusText.setText(Data.getZeroBit(abus));
    }

    public void setDbus(int dbus){
        TextView k16DbusText =(TextView)findViewById(R.id.k16Dbus);
        k16DbusText.setText(Data.getZeroBit(dbus));
    }
    public void setMar(int mar){
        TextView k16MarText = (TextView)findViewById(R.id.k16MAR);
        k16MarText.setText(Data.getZeroBit(mar));

    }
    public void setMdr(int mdr){
        TextView k16MdrText = (TextView)findViewById(R.id.k16MDR);
        k16MdrText.setText(Data.getZeroBit(mdr));
    }

    public void setRbus(int rbus){
        TextView k16RbusText =(TextView)findViewById(R.id.k16Rbus);
        k16RbusText.setText(Data.getZeroBit(rbus));
    }

    public void setLbus(int lbus){
        TextView k16LbusText = (TextView)findViewById(R.id.k16Lbus);
        k16LbusText.setText(Data.getZeroBit(lbus));
    }

    public void setObus(int obus){
        TextView k16ObusText = (TextView)findViewById(R.id.k16Obus);
        k16ObusText.setText(Data.getZeroBit(obus));
    }

    public void setRWLED(boolean check){
        RadioButton rbRW =(RadioButton)findViewById(R.id.rw);
        rbRW.setChecked(check);
    }

    public void setMreqLED(boolean check){
        RadioButton mreq =(RadioButton)findViewById(R.id.mreq);
        mreq.setChecked(check);
    }

    public void setACKLED(boolean check){
        RadioButton ack =(RadioButton)findViewById(R.id.ack);
        ack.setChecked(check);
    }

    public void setIorqLED(boolean check){
        RadioButton iorq = (RadioButton)findViewById(R.id.iorq);
        iorq.setChecked(check);
    }

    public void InstructionFetch(){
        switch (state){
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
                pc =pc+1;
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
    public void operandFetch(){
        if(instructionType ==type1){
            switch (opeState){
                case 0: //EAの計算
                    indexRegister =sift(ir,10,10);//インデックスレジスタの値を抽出
                    addressConstant = sift(ir,9,0);//アドレス部を抽出
                    if(indexRegister ==0){
                        effectiveAddress = addressConstant;
                        mar =effectiveAddress;
                        setRbus(0);
                    }else if(indexRegister ==1){
                        effectiveAddress = addressConstant + ixr;
                        setRbus( indexRegister );
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


    public int sift(int data,int highIndex,int lowIndex){
        data <<= (16 + 15 - highIndex);
        data >>= (16 + 15 - highIndex);
        data >>= lowIndex;
        return data;

    }

    private void load(){
        switch (loadState){
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
