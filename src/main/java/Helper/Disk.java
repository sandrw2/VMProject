package Helper;

import java.util.List;

public class Disk {
    static final boolean DEBUG = true;
    public static final int TOTAL_BLOCKS = 1024;
    public static final int BLOCK_SIZE = 512;
    Integer[][] Disk;
    public Disk(){
        Disk = new Integer[TOTAL_BLOCKS][BLOCK_SIZE];
    }

    public void init(List<String> ST, List<String>PT){
        //Case 1: PT resides in mem, page resides in mem
        //Case 2: PT resides in mem, page does not reside in mem
        //Case 3: PT does not reside in mem, page resides in mem
        //Case 4: PT does not reside in mem, page does not reside in mem
    }

}
