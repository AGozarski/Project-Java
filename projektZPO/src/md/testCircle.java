package md;

import java.util.List;

public class testCircle {

    public static void main(String[] args) {

        RandomCircle c= new RandomCircle(100,10);

       double[] l1;
        double[] l2;
        double[] l3;
        double[] l4;

        l1=c.getfirstPlaceX();
        l2=c.getfirstPlaceY();
        l3=c.getfirstSpeedX();
        l4=c.getfirstSpeedY();


        for(int i=0;i<l1.length;i++){
            System.out.println(l1[i]+" "+l2[i]+" "+l3[i]+" "+l4[i]);
        }

    }
}
