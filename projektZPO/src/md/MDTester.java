package md;

public class MDTester {

    public static void main(String[] args) {

        double[] x = {1, 1.9};
        double[] y = {50, 50};
        double[] vx = {1, -1};
        double[] vy = {0, 0};

        MD md = new MD(x, y, vx, vy, 100); //head-on-collision

        double [][] punkty = md.Verlet();

        System.out.println(punkty.length);
        System.out.println(punkty[1].length);
        for(int i=0; i<punkty.length;i++){
            for(int j=0; j<punkty[i].length;j++){
                System.out.println(punkty[i][j]);
            }
        }




    }//koniec maina

}
