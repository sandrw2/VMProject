import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class vmDemandPaging {
    static final boolean DEBUG = true;
    //PM size = 524,288 integers
    Integer[] PM;
    boolean[] freeFrames;
    public vmDemandPaging(){
        PM = new Integer[524288];
        Arrays.fill(PM, 0);
        freeFrames = new boolean[1024];
        //Frames 0 and 1 are empty
        freeFrames[0] = false;
        freeFrames[1] = false;
    }

    public void init(List<String> ST, List<String> PT){
        for(int i = 0; i <= ST.size()-3; i+=3){
            int s = Integer.parseInt(ST.get(i));
            int z = Integer.parseInt(ST.get(i+1));
            int f = Integer.parseInt(ST.get(i+2));
            //PM[2s] = z (segment size)
            PM[2*s] = z;
            //PM[2s+1] = f (frame location)
            PM[2*s+1] = f;
            //Frame f is no longer free
            freeFrames[f] = false;
            if(DEBUG){
                String message = String.format("SegmentSize: %d, FrameSize: %d", PM[2*s], PM[2*s+1]);
                System.out.println(message);
            }
        }
        for(int i = 0; i<= PT.size()-3; i+=3){
            int s = Integer.parseInt(PT.get(i));
            int p = Integer.parseInt(PT.get(i+1));
            int f = Integer.parseInt(PT.get(i+2));
            //PM[PM[2s+1]*512+p] = f
            int pageEntryAddress = PM[2*s+1]*512+p;
            PM[pageEntryAddress] = f;
            //Frame f is no longer free
            freeFrames[f] = false;
            if(DEBUG){
                String message = String.format("PageFrame: %d", PM[PM[2*s+1]*512+p]);
                System.out.println(message);
            }
        }
    }
    public int vaTranslate(int va){
        int s = getS(va);
        int w = getW(va);
        int p = getP(va);
        int pw = getPW(va);
        if(pw >= PM.get(2*s)){
            //report error: VA is outside the segment boundary
            return -1;
        }

        if(PM.get((2*s)+1) < 0){
            //page fault: PT is not resident
            //Allocate free frame f1 using list of free frames
            //Update list of free frames
            //Read disk block b = |PM[2s + 1]| into PM staring at location f1*512
            //PM[2s + 1] = f1: Update ST entry
        }
        if(PM.get(PM.get(2*s + 1)*512 + p) < 0){
            //page fault: page is not resident
            //Allocate free frame f2 using list of free frames
            //Update list of free frames
            //Read disk block b = |PM[PM[2s + 1]*512 + p]| into PM staring at f2*512
            //PM[PM[2s + 1]*512 + p] = f2 :update PT entry
        }
        //Return PA = PM[PM[2s + 1]*512 + p]*512 + w
        return 0;
    }

    public void readBlock (){}
    public int getS(int va){
        return va;
    }
    public int getW(int va){
        return va;
    }
    public int getP(int va){
        return va;
    }
    public int getPW(int va){
        return va;
    }


}
