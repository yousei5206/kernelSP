package kernelsp.maeda.is.kyusan_u.ac.jp.kernelsp;

public class Data {
    static String zeroBit;
    static String inputDataAns;

    public static String getZeroBit(int inputBit){
        if(0x000f >= inputBit){
            zeroBit ="000";
            inputDataAns = zeroBit +Integer.toHexString(inputBit).toUpperCase();
            return inputDataAns;

        }else if(0x0010 <= inputBit && 0x00ff >= inputBit){
            zeroBit ="00";
            inputDataAns = zeroBit +Integer.toHexString(inputBit).toUpperCase();
            return inputDataAns;

        }else if(0x0100 <= inputBit && 0x0fff >= inputBit){
            zeroBit ="0";
            inputDataAns = zeroBit +Integer.toHexString(inputBit).toUpperCase();
            return inputDataAns;

        }else {
            inputDataAns = Integer.toHexString(inputBit).toUpperCase();
            return inputDataAns;

        }

    }

}
