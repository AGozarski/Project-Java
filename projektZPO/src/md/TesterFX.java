package md;

import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class TesterFX extends Application {

    private TextField txtX1pocz;
    private TextField txtY1pocz;
    private TextField txtVX1pocz;
    private TextField txtVY1pocz;
    private TextField txtX2pocz;
    private TextField txtY2pocz;
    private TextField txtVX2pocz;
    private TextField txtVY2pocz;

    private Label labelX1pocz;
    private Label labelY1pocz;
    private Label labelVX1pocz;
    private Label labelVY1pocz;
    private Label labelX2pocz;
    private Label labelY2pocz;
    private Label labelVX2pocz;
    private Label labelVY2pocz;


    LineChart<Number, Number> figure, figureEnergyPotKit, figureEnergyEla;
    Button btnDraw,animacja;

    @Override
    public  void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Metody Numeryczne L13");
        VBox layout = new VBox();
        layout.setSpacing(15);
        layout.setPadding(new Insets(20, 20, 30, 30));

        labelX1pocz = new Label("Położenie X pierwszej cząstki");
        labelY1pocz = new Label("Położenie Y pierwszej cząstki");
        labelVX1pocz = new Label("Prędkość początkowa X pierwszej cząstki");
        labelVY1pocz = new Label("Prędkość początkowa Y pierwszej cząstki");
        txtX1pocz = new TextField();
        txtX1pocz.setText("10");
        txtX1pocz.setMinWidth(50);
        txtX1pocz.setMaxWidth(71);
        txtY1pocz = new TextField();
        txtY1pocz.setText("50");
        txtY1pocz.setMinWidth(50);
        txtY1pocz.setMaxWidth(71);
        txtVX1pocz = new TextField();
        txtVX1pocz.setText("1");
        txtVX1pocz.setMinWidth(50);
        txtVX1pocz.setMaxWidth(71);
        txtVY1pocz = new TextField();
        txtVY1pocz.setText("0");
        txtVY1pocz.setMinWidth(50);
        txtVY1pocz.setMaxWidth(71);
        HBox hBox = new HBox(labelX1pocz, txtX1pocz, labelY1pocz, txtY1pocz);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(15);
        layout.getChildren().add(hBox);
        HBox hBox4 = new HBox( labelVX1pocz, txtVX1pocz, labelVY1pocz,txtVY1pocz);
        hBox4.setAlignment(Pos.CENTER);
        hBox4.setSpacing(15);
        layout.getChildren().add(hBox4);

        labelX2pocz = new Label("Położenie X drugiej cząstki");
        labelY2pocz = new Label("Położenie Y drugiej cząstki");
        labelVX2pocz = new Label("Prędkość początkowa X drugiej cząstki");
        labelVY2pocz = new Label("Prędkość początkowa Y drugiej cząstki");
        txtX2pocz = new TextField();
        txtX2pocz.setText("90");
        txtX2pocz.setMinWidth(50);
        txtX2pocz.setMaxWidth(71);
        txtY2pocz = new TextField();
        txtY2pocz.setText("50");
        txtY2pocz.setMinWidth(50);
        txtY2pocz.setMaxWidth(71);
        txtVX2pocz = new TextField();
        txtVX2pocz.setText("-1");
        txtVX2pocz.setMinWidth(50);
        txtVX2pocz.setMaxWidth(71);
        txtVY2pocz = new TextField();
        txtVY2pocz.setText("0");
        txtVY2pocz.setMinWidth(50);
        txtVY2pocz.setMaxWidth(71);
        HBox hBox1 = new HBox(labelX2pocz, txtX2pocz, labelY2pocz, txtY2pocz);
        hBox1.setAlignment(Pos.CENTER);
        hBox1.setSpacing(15);
        layout.getChildren().add(hBox1);
        HBox hBox5 = new HBox( labelVX2pocz, txtVX2pocz, labelVY2pocz,txtVY2pocz);
        hBox5.setAlignment(Pos.CENTER);
        hBox5.setSpacing(15);
        layout.getChildren().add(hBox5);


        btnDraw = new Button("Draw");
        btnDraw.setPrefWidth(200);
        btnDraw.setOnAction(e->{



            XYChart.Series x1 = updateParam().get(0);
            XYChart.Series x2 = updateParam().get(1);
            XYChart.Series x3 = updateParam().get(2);
            XYChart.Series x4 = updateParam().get(3);
            XYChart.Series x5 = updateParam().get(4);
            XYChart.Series x6 = updateParam().get(5);

            figure.setCreateSymbols(false);
            figure.getData().clear();
            x1.setName("położenie x pierwszej cząstki ");
            x2.setName("położenie x drugiej cząstki ");
            figure.setLegendVisible(true);
            figure.getData().addAll(x1,x2);

            figureEnergyPotKit.setCreateSymbols(false);
            figureEnergyPotKit.getData().clear();
            x3.setName("Energia potencjalna");
            x4.setName("Energia kinetyczna");
            x6.setName("Emergia całkowita");
            figureEnergyPotKit.setLegendVisible(true);
            //figureEnergyPot.getData().addAll(x3,x4);
            figureEnergyPotKit.getData().addAll(x3,x4,x6);

            figureEnergyEla.setCreateSymbols(false);
            figureEnergyEla.getData().clear();
            x5.setName("Energia sprężystości");
            figureEnergyEla.setLegendVisible(true);
            //figureEnergyPot.getData().addAll(x3,x4);
            figureEnergyEla.getData().add(x5);


        });
        animacja=new Button("animacja");
        animacja.setPrefWidth(200);
        animacja.setOnAction(e->{

            Circle circle1 = new Circle();
            circle1.setCenterX(420f);
            circle1.setCenterY(150f);
            circle1.setRadius(7);

            circle1.setFill(Color.GREEN);
            circle1.setStrokeWidth(20);

            Circle circle2 = new Circle();
            circle2.setCenterX(420f);
            circle2.setCenterY(150f);
            circle2.setRadius(7);

            circle2.setFill(Color.RED);
            circle2.setStrokeWidth(20);

//            double[] xz = {10, 90};
//            double[] yz = {50, 50};
//            double[] vxz = {1, -1};
//            double[] vyz = {0, 0};

            double[] xz = {Double.parseDouble(txtX1pocz.getText()), Double.parseDouble(txtX2pocz.getText())};
            double[] yz = {Double.parseDouble(txtY1pocz.getText()), Double.parseDouble(txtY2pocz.getText())};
            double[] vxz = {Double.parseDouble(txtVX1pocz.getText()), Double.parseDouble(txtVX2pocz.getText())};
            double[] vyz = {Double.parseDouble(txtVY1pocz.getText()), Double.parseDouble(txtVY2pocz.getText())};
            MD md1 = new MD(xz, yz, vxz, vyz, (int)(xz[0]+xz[1])); //head-on-collision

            double[][] punkty=md1.Verlet();
            double[] timeZ= md1.getTime();

            //Path class
            Path path1 = new Path();
            path1.getElements().add(new MoveTo(140f+ 5*punkty[1][0],150f+5*punkty[1][1]));

            //Path class line
            Path path2 = new Path();
            path2.getElements().add(new MoveTo(140f+ 5*punkty[0][0],150f+5*punkty[0][1]));


            for (int i=1;i<timeZ.length;i+=2){


                double x1= 140f+5*punkty[i][0];
                double y1= 150f+5*punkty[i][1];
                double x2= 140f+5*punkty[i+1][0];
                double y2= 150f+5*punkty[i+1][1];
                path1.getElements().add(new LineTo(x1,y1));
                path2.getElements().add(new LineTo(x2,y2));

            }
            PathTransition pathTransition1 = new PathTransition();

            pathTransition1.setNode(circle1);
            pathTransition1.setPath(path1);
            pathTransition1.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
            pathTransition1.setCycleCount(1);
            pathTransition1.setAutoReverse(false);


            PathTransition pathTransition2 = new PathTransition();

            pathTransition2.setNode(circle2);
            pathTransition2.setPath(path2);
            pathTransition2.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
            pathTransition2.setCycleCount(1);
            pathTransition2.setAutoReverse(false);


            pathTransition1.setDuration(Duration.millis(5000));
            pathTransition2.setDuration(Duration.millis(5000));

            pathTransition1.play();
            pathTransition2.play();


            Group root = new Group(circle1,circle2);
            Scene scene = new Scene(root,800,800);
            Stage stage2 = new Stage();
            stage2.setTitle("Path transition example");
            stage2.setScene(scene);
            stage2.show();

        });


        HBox hBox2 = new HBox(btnDraw,animacja);
        hBox2.setAlignment(Pos.CENTER);
        hBox2.setSpacing(15);
        layout.getChildren().add(hBox2);

        NumberAxis x = new NumberAxis();
        x.setLabel("Time");
        NumberAxis y = new NumberAxis();
        y.setLabel("Values");
        figure = new LineChart<Number, Number>(x, y);
        layout.getChildren().add(figure);

        NumberAxis x2axis = new NumberAxis();
        x2axis.setLabel("Time");
        NumberAxis y2axis = new NumberAxis();
        y2axis.setLabel("Energia");
        figureEnergyPotKit = new LineChart<Number, Number>(x2axis, y2axis);
        layout.getChildren().add(figureEnergyPotKit);

        NumberAxis x3axis = new NumberAxis();
        x3axis.setLabel("Time");
        NumberAxis y3axis = new NumberAxis();
        y3axis.setLabel("Energia Sprężystości");
        figureEnergyEla = new LineChart<Number, Number>(x3axis, y3axis);
        layout.getChildren().add(figureEnergyEla);

        Scene scene = new Scene(layout, 1400, 1000);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private List<XYChart.Series> updateParam() {
        List<XYChart.Series> dataSeries = new ArrayList<>();
        double[] xPocz = {Double.parseDouble(txtX1pocz.getText()), Double.parseDouble(txtX2pocz.getText())};
        double[] yPocz = {Double.parseDouble(txtY1pocz.getText()), Double.parseDouble(txtY2pocz.getText())};
        double[] vxPocz = {Double.parseDouble(txtVX1pocz.getText()), Double.parseDouble(txtVX2pocz.getText())};
        double[] vyPocz = {Double.parseDouble(txtVY1pocz.getText()), Double.parseDouble(txtVY2pocz.getText())};
        MD md = new MD(xPocz, yPocz, vxPocz, vyPocz, 100); //head-on-collision
        double [][] punkty = md.Verlet();
        double [] czas = md.getTime();
        List<Double> potEnergy = md.getPotEnergy();
        List<Double> kitEnergy = md.getKinEnergy();
        List<Double> elaEnergy = md.getElaEnergy();
        XYChart.Series x1 = new XYChart.Series();
        XYChart.Series x2 = new XYChart.Series();
        XYChart.Series x3 = new XYChart.Series();//dla energii potencjalnej
        XYChart.Series x4 = new XYChart.Series();//dla energii kinetycznej
        XYChart.Series x5 = new XYChart.Series();//dla energii sprezystosci
        XYChart.Series x6 = new XYChart.Series();//dla energii calkowitej
        int counter = 0;
        x1.getData().add(new XYChart.Data(0,punkty[0][0]));
        x2.getData().add(new XYChart.Data(0,punkty[1][0]));
        for(int i=0; i<czas.length; i+=2){
            if(counter%5==0){
                x1.getData().add(new XYChart.Data(czas[i], punkty[i][0]));
                x2.getData().add(new XYChart.Data(czas[i], punkty[i+1][0]));
            }
            counter++;
        }
        counter = 0;
        x3.getData().add(new XYChart.Data(0, potEnergy.get(0)));
        x4.getData().add(new XYChart.Data(0, kitEnergy.get(0)));
        x5.getData().add(new XYChart.Data(0, elaEnergy.get(0)));
        x5.getData().add(new XYChart.Data(0, (potEnergy.get(0)+kitEnergy.get(0)+elaEnergy.get(0))));

        for (int j = 0; j < potEnergy.size() - 1; j++) {
            if (counter % 10 == 0) {
                x3.getData().add(new XYChart.Data(czas[j], potEnergy.get(j)));
            }
            counter++;
        }
        counter = 0;

        for (int j = 0; j < kitEnergy.size() - 1; j++) {
            if (counter % 10 == 0) {
                x4.getData().add(new XYChart.Data(czas[j], kitEnergy.get(j)));
            }
            counter++;
        }

        counter = 0;
        for(int j=0; j<elaEnergy.size()-1;j++){
            if(counter%10==0){
                x5.getData().add(new XYChart.Data(czas[j], elaEnergy.get(j)));
            }
            counter++;
        }
        counter = 0;
        for(int j=0; j<potEnergy.size()-1;j++){
            if(counter%10==0){
                x6.getData().add(new XYChart.Data(czas[j], (potEnergy.get(j)+kitEnergy.get(j)+elaEnergy.get(j))));
            }
            counter++;
        }
        dataSeries.add(x1);
        dataSeries.add(x2);
        dataSeries.add(x3);
        dataSeries.add(x4);
        dataSeries.add(x5);
        dataSeries.add(x6);
        return dataSeries;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
