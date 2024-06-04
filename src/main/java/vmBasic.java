import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Helper.Derive;


public class vmBasic {
    static final boolean DEBUG = true;
    static final int PM_SIZE = 524288;
    //PM size = 524,288 integers
    Integer[] PM;
    public vmBasic(){
        PM = new Integer[PM_SIZE];
        Arrays.fill(PM, 0);
    }
    public void init(List<String> ST, List<String> PT) {

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
            //PM[PM[2s+1]*512+p] = f
            int pageEntryAddress = PM[2*s+1]*512+p;
            PM[pageEntryAddress] = f;
            if(DEBUG){
                String message = String.format("PageFrame: %d", PM[PM[2*s+1]*512+p]);
                System.out.println(message);
            }
        }


    }

    public int translateVA(int va){
        Derive d = new Derive(va);
        int s = d.getS(va);
        int w = d.getW(va);
        int p = d.getP(va);
        int pw = d.getPW(va);

        if(DEBUG){
            String message = String.format("s:%d, w:%d, p:%d, pw:%d, ST_size: %d", s, w, p, pw, PM[2*s]);
            System.out.print(message);
        }
        //ST size = PM[2s]
        int ST_size = PM[2*s];
        if(pw >= ST_size){
            return -1;
        }
        //ST frame  = PM[2s+1]
        int ST_frame = PM[(2*s)+1];
        //ST address = PM[2s+1]*512
        int ST_address = ST_frame*512;
        //page frame = PM[PM[2s+1]*512+p]
        int page_frame = PM[ST_address+p];
        //page address = PM[PM[2s+1]*512+p]*512
        int page_address = page_frame*512;
        //PA = PM[PM[2s+1]*512+p]*512 + w
        return page_address + w;
     }

    public int readPA(int PA) {
        return PM[PA];
    }
}
