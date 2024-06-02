package Helper;
public class Derive {
    int va;
    public Derive(int va){
        this.va = va;
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
