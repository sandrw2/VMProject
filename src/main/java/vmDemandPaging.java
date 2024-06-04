import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Helper.Derive;

public class vmDemandPaging {
    static final boolean DEBUG = true;
    public static final int PM_SIZE = 524288;
    public static final int TOTAL_BLOCKS = 1024;
    public static final int BLOCK_SIZE = 512;
    Integer[] PM;
    Integer[][] D;
    ArrayList<Boolean> freeFrames;
    public vmDemandPaging(){
        //Init PM
        PM = new Integer[PM_SIZE];
        Arrays.fill(PM, 0);

        //Init freeFrames
        freeFrames = new ArrayList<Boolean>();
        //Frames 0 and 1 are empty
        freeFrames.set(0, false);
        freeFrames.set(1,false);

        //Init Disk
        D = new Integer[TOTAL_BLOCKS][BLOCK_SIZE];

    }

    public void init(List<String> ST, List<String> PT){

        //Case 1: PT resides in mem, page resides in mem
        //Case 2: PT resides in mem, page does not reside in mem
        //Case 3: PT does not reside in mem, page resides in mem
        //Case 4: PT does not reside in mem, page does not reside in mem
        for(int i = 0; i <= ST.size()-3; i+=3){
            int s = Integer.parseInt(ST.get(i));
            int z = Integer.parseInt(ST.get(i+1));
            int f = Integer.parseInt(ST.get(i+2));
            //PM[2s] = z (segment size)
            PM[2*s] = z;
            //PM[2s+1] = f (frame location)
            PM[2*s+1] = f;
            if(DEBUG){
                String message = String.format("SegmentSize: %d, FrameSize: %d", PM[2*s], PM[2*s+1]);
                System.out.println(message);
            }
        }

        for(int i = 0; i<= PT.size()-3; i+=3){
            int s = Integer.parseInt(PT.get(i));
            int p = Integer.parseInt(PT.get(i+1));
            int f = Integer.parseInt(PT.get(i+2));
            int pageEntryAddress;
            if(PM[(2*s)+1] > 0){
                //Case 1: PT resides in mem, page resides in mem
                //Case 2: PT resides in mem, page does not reside in mem
                //PM[PM[2s+1]*512+p] = f
                pageEntryAddress = PM[2*s+1]*512+p;
                PM[pageEntryAddress] = f;
            }else{
                //Case 3: PT does not reside in mem, page resides in mem
                //Case 4: PT does not reside in mem, page does not reside in mem
                //D[|PM[2s+1]|][p] = f
                pageEntryAddress = Math.abs(PM[2*s+1]);
                D[pageEntryAddress][p] = f;
            }

            if(DEBUG){
                String message = String.format("PageFrame: %d", PM[PM[2*s+1]*512+p]);
                System.out.println(message);
            }
        }
    }
    public int vaTranslate(int va){
        Derive derive = new Derive(va);
        int s = derive.getS(va);
        int w = derive.getW(va);
        int p = derive.getP(va);
        int pw = derive.getPW(va);
        int b;
        if(pw >= PM[2*s]){
            //report error: VA is outside the segment boundary
            return -1;
        }

        if(PM[(2*s)+1] < 0){
            //page fault: PT is not resident
            //Allocate free frame f1 using list of free frames
            //Find free frame f1
            int f1 = freeFrames.indexOf(true);
            //Update list of free frames
            freeFrames.set(f1, false);
            //copy PT from block b = |PM[2s + 1]| into PM starting at location f1*512
            //b = block to read from disk
            b = Math.abs(PM[(2*s)+1]);
            int PTBlock = f1*512;
            readBlock(b, PTBlock);
            //PM[2s + 1] = f1: Update ST entry
            PM[(2*s)+1] = f1;
        }
        if(PM[PM[2*s + 1]*512 + p] < 0){
            //page fault: page is not resident
            //Allocate free frame f2 using list of free frames
            //Find free frame f2
            int f2 = freeFrames.indexOf(true);
            //Update list of free frames
            freeFrames.set(f2, false);
            //Read disk block b = |PM[PM[2s + 1]*512 + p]| into PM staring at f2*512
            b = Math.abs(PM[(PM[(2*s) + 1]*512) + p]);
            int PGBlock = f2*512;
            readBlock(b, PGBlock);
            //PM[PM[2s + 1]*512 + p] = f2 :update PT entry
            PM[(PM[(2*s) + 1]*512) + p] = f2;
        }
        //Return PA = PM[PM[2s + 1] * 512 + p] * 512 + w
        //ST frame  = PM[2s+1]
        int ST_frame = PM[(2*s)+1];
        //ST address = PM[2s+1]*512
        int ST_address = ST_frame*512;
        //page frame = PM[PM[2s+1]*512+p]
        int page_frame = PM[ST_address+p];
        //page address = PM[PM[2s+1]*512+p]*512
        int page_address = page_frame*512;
        //PA = PM[PM[2s + 1] * 512 + p] * 512 + w
        return page_address + w;
    }

    public void readBlock (int b, int m){
        //copy D[b] --> PM[m]
        System.arraycopy(D[b], 0, PM, m, BLOCK_SIZE);
    }

}
