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

    }

    public void vaTranslate(int va){
        int s = getS(va);
        int w = getW(va);
        int p = getP(va);
        int pw = getPW(va);
     }
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
