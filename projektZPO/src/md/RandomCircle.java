package md;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomCircle {

    private int widthBox;
    private double height;
    private int amount;
    private double[] listX;
    private double[] listY;
    private double[] listVX;
    private double[] listVY;
    private Random random;

    public RandomCircle(int widthBox, int amount){
        this.widthBox=widthBox;
        this.amount=amount;
        random= new Random();
        listX=new double[amount];
        listY=new double[amount];
        listVX=new double[amount];
        listVY=new double[amount];
        randomPlace();
        randomSpeed();
    }
    public double[] getfirstPlaceX(){
        return listX;
    }
    public double[] getfirstPlaceY(){
        return listY;
    }
    public double[] getfirstSpeedX(){
        return listVX;
    }
    public double[] getfirstSpeedY(){
        return listVY;
    }
    private void randomPlace(){
        for(int i=0;i<amount;i++){
            int x;
            int randX;
            int randY;
            do{
                x=0;
                randX=random.nextInt(widthBox);
                randY=random.nextInt(widthBox);

                for(int j=0;j<listX.length;j++){
                    if(randX==listX[j]+5 || randY==listY[j]){
                       x++;
                    }
                }
            }while(x!=0);
            listX[i]=randX;
            listY[i]=randY;
        }
    }
    private void randomSpeed(){
        for(int i=0;i<amount;i++) {
            double randX;
            double randY;
            randX=random.nextDouble()*2-1;
            randY=random.nextDouble()*2-1;
            listVX[i]=randX;
            listVY[i]=randY;

        }

    }
}
