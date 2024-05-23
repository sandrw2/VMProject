import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class vmBasic {
    //PM size = 524,288 integers
    ArrayList<Integer> PM;
    public vmBasic(){

    }
    public void init(List<String> ST, List<String> PT) {
        for(int i = 0; i < ST.size()-3; i++){
            int s = Integer.parseInt(ST.get(i));
            int z = Integer.parseInt(ST.get(i+1));
            int f = Integer.parseInt(ST.get(i+2));
            //PM[2s] = z (segment size)
            PM.add(2*s, z);
            //PM[2s+1] = f (frame size)
            PM.add((2*s)+1, f);
        }
        for(int i = 0; i< PT.size()-3; i++){
            int s = Integer.parseInt(PT.get(i));
            int p = Integer.parseInt(PT.get(i+1));
            int f = Integer.parseInt(PT.get(i+2));
            //PM[PM[2s+1]*512+p] = f
            int location = (PM.get((2*s)+1)*512)+p;
            PM.add((PM.get((2*s)+1)*512)+p, f);
        }
    }

    public int vaTranslate(int va){
        int s = getS(va);
        int w = getW(va);
        int p = getP(va);
        int pw = getPW(va);
        //ST size = PM[2s]
        int ST_size = PM.get(2*s);
        if(pw >= ST_size){
            return -1;
        }else{
            //ST frame  = PM[2s+1]
            int ST_frame = PM.get((2*s)+1);
            //ST address = PM[2s+1]*512
            int ST_address = ST_frame*512;
            //page frame = PM[PM[2s+1]*512+p]
            int page_frame = PM.get(ST_address+p);
            //page address = PM[PM[2s+1]*512+p]*512
            int page_address = PM.get(page_frame*512);
            //PA = PM[PM[2s+1]*512+p]*512 + w
            return page_address + w;
        }
     }
    public int getS(int va){
        return va >>> 18;
    }
    public int getW(int va){
        return va & 511;
    }
    public int getP(int va){
        va = va >>> 9;
        return va & 511;
    }
    public int getPW(int va){
        return va & 262143;
    }
}
