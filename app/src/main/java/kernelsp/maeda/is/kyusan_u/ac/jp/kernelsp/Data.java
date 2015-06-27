package kernelsp.maeda.is.kyusan_u.ac.jp.kernelsp;

public class Data {
    static String zeroBit;
    static String inputDataAns;

    public static String getZeroBit(int inputBit){
        if(0x000f >= inputBit){
            zeroBit ="000";
            inputDataAns = zeroBit +Integer.toHexString(inputBit);
            return inputDataAns;

        }else if(0x000f < inputBit && 0x00f0 >= inputBit){
            zeroBit ="00";
            inputDataAns = zeroBit +Integer.toHexString(inputBit);
            return inputDataAns;

        }else if(0x00f0 < inputBit && 0x0f00 >= inputBit){
            zeroBit ="0";
            inputDataAns = zeroBit +Integer.toHexString(inputBit);
            return inputDataAns;

        }else {
            inputDataAns = Integer.toHexString(inputBit);
            return inputDataAns;

        }

    }




}
