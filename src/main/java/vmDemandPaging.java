//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//public class vmDemandPaging {
//    public void vaTranslate(int va){
//        int s = getS(va);
//        int w = getW(va);
//        int p = getP(va);
//        int pw = getPW(va);
//        if(pw >= PM.get(2*s)){
//            //report error: VA is outside of the segment boundary
//        }
//
//        if(PM.get((2*s)+1) < 0){
//            //page fault: PT is not resident
//            //Allocate free frame f1 using list of free frames
//            //Update list of free frames
//            //Read disk block b = |PM[2s + 1]| into PM staring at location f1*512
//            //PM[2s + 1] = f1: Update ST entry
//        }
//        if(PM.get(PM.get(2s + 1)*512 + p) < 0){
//            //page fault: page is not resident
//            //Allocate free frame f2 using list of free frames
//            //Update list of free frames
//            //Read disk block b = |PM[PM[2s + 1]*512 + p]| into PM staring at f2*512
//            //PM[PM[2s + 1]*512 + p] = f2 :update PT entry
//        }
//        //Return PA = PM[PM[2s + 1]*512 + p]*512 + w
//
//    }
//
//    public void readBlock (){}
//    public int getS(int va){
//        return va;
//    }
//    public int getW(int va){
//        return va;
//    }
//    public int getP(int va){
//        return va;
//    }
//    public int getPW(int va){
//        return va;
//    }
//
//
//}
