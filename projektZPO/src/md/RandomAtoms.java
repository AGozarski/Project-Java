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
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomAtoms extends Application {


    LineChart<Number, Number> figureEnergy, figureEnergyPotKit, figureEnergyEla;
    private Button animacja, wykresy;
    private int amount = 10;
    private TextField amountField;
    private int counter;


    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Metody Numeryczne L14");
        VBox layout = new VBox();
        layout.setSpacing(15);
        layout.setPadding(new Insets(20, 20, 30, 30));

        amountField = new TextField();
        amountField.setText("10");
        amountField.setMinWidth(50);
        amountField.setMaxWidth(71);

        animacja = new Button("animacja");
        animacja.setPrefWidth(200);
        animacja.setOnAction(e -> {

            amount = Integer.parseInt(amountField.getText());

            RandomCircle c = new RandomCircle(100, amount);
            MD md1 = new MD(c.getfirstPlaceX(), c.getfirstPlaceY(), c.getfirstSpeedX(), c.getfirstSpeedY(), 100); //head-on-collision
            int pow = 5;
            float przes = 150f;
            double[][] punkty = md1.Verlet();
            double[] timeZ = md1.getTime();
            List<Double> potEnergy = md1.getPotEnergy();
            List<Double> kitEnergy = md1.getKinEnergy();
            List<Double> elaEnergy = md1.getElaEnergy();
            List<Circle> circules = new ArrayList<>();
            counter=0;
            for (int i = 0; i < amount; i++) {
                Circle c1 = new Circle();

                c1.setCenterX(przes + pow * punkty[i][0]);
                c1.setCenterY(przes + pow * punkty[i][1]);
                c1.setRadius(2);
                c1.setStrokeWidth(20);
                circules.add(c1);
            }
            Line line = new Line();
            line.setStartX(przes);
            line.setEndX(przes + 100 * pow);
            line.setStartY(przes);
            line.setEndY(przes);
            Line line2 = new Line();
            line2.setStartX(przes);
            line2.setEndX(przes + 100 * pow);
            line2.setStartY(przes + 100 * pow);
            line2.setEndY(przes + 100 * pow);
            Line line3 = new Line();
            line3.setStartX(przes);
            line3.setEndX(przes);
            line3.setStartY(przes);
            line3.setEndY(przes + 100 * pow);
            Line line4 = new Line();
            line4.setStartX(przes + 100 * pow);
            line4.setEndX(przes + 100 * pow);
            line4.setStartY(przes);
            line4.setEndY(przes + 100 * pow);


            List<Path> paths = new ArrayList<>();
            for (int i = 0; i < amount; i++) {
                Path path1 = new Path();
                path1.getElements().add(new MoveTo(przes + pow * punkty[i][0], przes + pow * punkty[i][1]));
                paths.add(path1);
            }

                    for (int i = 0; i < timeZ.length; i += amount) {
                        double[] x1 = new double[amount];
                        double[] y1 = new double[amount];
                        for (int j = 0; j < amount; j++) {
                                x1[j] = przes + pow * punkty[i + j][0];
                                y1[j] = przes + pow * punkty[i + j][1];
                                paths.get(j).getElements().add(new LineTo(x1[j], y1[j]));
                        }
                    }


            List<PathTransition> pathTransitionsList = new ArrayList<>();
            for (int i = 0; i < amount; i++) {
                PathTransition pathTransition1 = new PathTransition();

                pathTransition1.setNode(circules.get(i));
                pathTransition1.setPath(paths.get(i));
                pathTransition1.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
                pathTransition1.setCycleCount(1);
                pathTransition1.setAutoReverse(false);
                pathTransition1.setDuration(Duration.seconds(50));
                pathTransition1.play();
            }


            Group root = new Group(line, line2, line3, line4);
            for (int i = 0; i < amount; i++) {
                root.getChildren().add(circules.get(i));
            }
            Scene scene = new Scene(root, 800, 800);
            Stage stage2 = new Stage();
            stage2.setTitle("Path transition example");
            stage2.setScene(scene);
            stage2.show();


            XYChart.Series x3 = new XYChart.Series();//dla energii potencjalnej
            XYChart.Series x4 = new XYChart.Series();//dla energii kinetycznej
            XYChart.Series x5 = new XYChart.Series();//dla energii sprezystosci
            XYChart.Series x6 = new XYChart.Series();//dla energii calkowitej

            counter = 0;
            x3.getData().add(new XYChart.Data(0, potEnergy.get(0)));
            x4.getData().add(new XYChart.Data(0, kitEnergy.get(0)));
            x5.getData().add(new XYChart.Data(0, elaEnergy.get(0)));
            x5.getData().add(new XYChart.Data(0, (potEnergy.get(0)+kitEnergy.get(0)+elaEnergy.get(0))));

            for (int j = 0; j < potEnergy.size() - 1; j++) {
                if (counter % 10 == 0) {
                    x3.getData().add(new XYChart.Data(timeZ[j], potEnergy.get(j)));
                }
                counter++;
            }
            counter = 0;

            for (int j = 0; j < kitEnergy.size() - 1; j++) {
                if (counter % 10 == 0) {
                    x4.getData().add(new XYChart.Data(timeZ[j], kitEnergy.get(j)));
                }
                counter++;
            }

            counter = 0;
            for(int j=0; j<elaEnergy.size()-1;j++){
                if(counter%10==0){
                    x5.getData().add(new XYChart.Data(timeZ[j], elaEnergy.get(j)));
                }
                counter++;
            }
            counter = 0;
            for(int j=0; j<potEnergy.size()-1;j++){
                if(counter%10==0){
                    x6.getData().add(new XYChart.Data(timeZ[j], (potEnergy.get(j)+kitEnergy.get(j)+elaEnergy.get(j))));
                }
                counter++;
            }


            figureEnergyPotKit.setCreateSymbols(false);
            figureEnergyPotKit.getData().clear();
            x3.setName("Energia potencjalna");
            x4.setName("Energia kinetyczna");
            figureEnergyPotKit.setLegendVisible(true);
            //figureEnergyPot.getData().addAll(x3,x4);
            figureEnergyPotKit.getData().addAll(x3,x4);

            figureEnergyEla.setCreateSymbols(false);
            figureEnergyEla.getData().clear();
            x5.setName("Energia sprężystości");
            figureEnergyEla.setLegendVisible(true);
            //figureEnergyPot.getData().addAll(x3,x4);
            figureEnergyEla.getData().add(x5);

            figureEnergy.setCreateSymbols(false);
            figureEnergy.getData().clear();
            x6.setName("Emergia całkowita");
            figureEnergy.setLegendVisible(true);
            figureEnergy.getData().add(x6);


        });
        HBox hBox2 = new HBox(amountField, animacja);
        hBox2.setAlignment(Pos.CENTER);
        hBox2.setSpacing(15);
        layout.getChildren().add(hBox2);

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

        NumberAxis x4axis = new NumberAxis();
        x4axis.setLabel("Time");
        NumberAxis y4axis = new NumberAxis();
        y4axis.setLabel("Energia Całkowita");
        figureEnergy = new LineChart<Number, Number>(x4axis,y4axis);
        layout.getChildren().add(figureEnergy);


        Scene scene = new Scene(layout, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}


























