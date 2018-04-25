package md;

//istota animacji: ma robic 1 krok verleta; jesli uplynal okreslony czas to wyswietl, jak nie to nast krok

import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.List;

import static java.lang.StrictMath.PI;
import static java.lang.StrictMath.sin;

public class MD {

    //masy sa jednostkowe (=1)

    private int nAtoms;
    private static int stepCounter; //liczy ilosc krokow Verleta
    private int boxWidth;
    private final double rCut2 = 4.0; //jesli czastki beda w wiekszej odl niz 2 to nie liczymy dla nich sily

    private static double h;
    private static int tk;
    private static int n;
    private double t=0;
    private double[] time;
    private  List<Double> kinEnergy=new ArrayList<>();
    private  List<Double> potEnergy=new ArrayList<>();
    private  List<Double> elaEnergy=new ArrayList<>();
    private  List<Double> vX; //co druga wartośc dla konretnego atomu
    private  List<Double> vY;

    private double elasticEnergy;
    private double wallStiffness=50;

    private double[] x, y, vx, vy, ax, ay;

    public MD(double[] xStart, double[] yStart, double[] vxStart, double[] vyStart, int boxWidth) { //poczatkowe polozenia i predkosci np 2 czastek

        nAtoms = xStart.length;
        this.boxWidth = boxWidth;

        x = xStart.clone(); // ogolnie clone() nie jest dobre
        y = yStart.clone();
        vx = vxStart.clone();
        vy = vyStart.clone();

        ax = new double[nAtoms];
        ay = new double[nAtoms];

        calculateAcceleration();

        h = 0.01;
        tk = 400;
        stepCounter = 0;
        n = (int) Math.floor(tk / h);

    }

    private void calculateAcceleration() { //w metodzie Verleta trzeba raz, na poczatku, obliczyc przyspieszenie

        //kasowanie starych wartosci przyspieszen
        for (int i = 0; i < nAtoms; i++) {
            ax[i] = 0;
            ay[i] = 0;
        }

        double potEn =0;
        for (int i = 0; i < nAtoms-1; i++) //3 zasada dynamiki Newtona
            for (int j = i + 1; j < nAtoms; j++) {
                double dx = x[i] - x[j];
                double dy = y[i] - y[j];

                double rij2 = dx * dx + dy * dy; //odleglosc miedzy x i y do kwadratu
                if (rij2 < rCut2) {
                    double fr2 = 1. / rij2;
                    double fr6 = fr2 * fr2 * fr2;
                    double fr = 48. * fr6 * (fr6 - 0.5) / rij2; //sprawdzic czy to poprawne
                    double fxi = fr * dx;
                    double fyi = fr * dy;
                    ax[i] += fxi;
                    ay[i] += fyi;
                    ax[j] -= fxi;
                    ay[j] -= fyi;
                    potEn+=(4*fr6*(fr6-1.0));

                }
            }
        potEnergy.add(potEn);

        elasticEnergy=0;
        for(int i=0;i<nAtoms;i++){
            double d=0;

            if(x[i]<0.5){
                d=0.5 -x[i];
                ax[i]+=wallStiffness*d;
                elasticEnergy+=0.5*wallStiffness*d*d;
            }
            if(x[i]>(boxWidth-0.5)){
                d=boxWidth-0.5 -x[i];
                ax[i]+=wallStiffness*d;
                elasticEnergy+=0.5*wallStiffness*d*d;
            }
            if(y[i]<0.5){
                d=0.5 -y[i];
                ay[i]+=wallStiffness*d;
                elasticEnergy+=0.5*wallStiffness*d*d;
            }
            if(y[i]>(boxWidth-0.5)){
                d=boxWidth-0.5 -y[i];
                ay[i]+=wallStiffness*d;
                elasticEnergy+=0.5*wallStiffness*d*d;
            }

        }
        elaEnergy.add(elasticEnergy);

    }


    public double[][] Verlet() {

        //private ArrayList<Double> Verlet(int k) { //k - ktore cialo
        //ArrayList<Double> daneOut = new ArrayList();
        potEnergy=new ArrayList<>();
        vX= new ArrayList<>();
        vY=new ArrayList<>();
        double[][] punkty = new double[n*nAtoms][2];
        double[] vxMid= new double[nAtoms];
        double[] vyMid=new double[nAtoms];
        time = new double[n];
// tutaj wstawic petle zalezna od czasu trwania.
        for(int k=0; k<n*nAtoms ; k+=nAtoms){
            for (int i = 0; i < nAtoms; i++) {

                vxMid[i] = vx[i] + h * ax[i] * 0.5;
                vyMid[i] = vy[i] + h * ay[i] * 0.5;
                x[i] += h * vxMid[i];
                y[i] += h * vyMid[i];

                punkty[k + i][0] = x[i];
                punkty[k + i][1] = y[i];

            }
            calculateAcceleration();

            for (int i = 0; i < nAtoms; i++) {

                vx[i] = vxMid[i] + h * 0.5 * ax[i];
                vy[i] = vyMid[i] + h * 0.5 * ay[i];

                vX.add(vx[i]);
                vY.add(vy[i]);

            }
            kineticEnergy();
            stepCounter++;
            time[k/nAtoms]=t;
            t += h;

        }
        //nowa tablica dwuwymiarowa do liczenia energii kinetycznej mv^2/2
        return punkty;
    }

    public void  kineticEnergy() {
        double eKin = 0;
        for (int i = 0; i < nAtoms; i++) {
            eKin += ((vx[i] * vx[i])  + (vy[i] * vy[i]));
        }
        eKin/=2.0;
        kinEnergy.add(eKin);
    }

    public double[] getTime(){
        return time;
    }

    public List<Double> getPotEnergy(){
        return potEnergy;
    }
    public List<Double> getKinEnergy(){
        return kinEnergy;
    }

    public List<Double> getElaEnergy() {
        return elaEnergy;
    }
}
