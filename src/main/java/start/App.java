package start;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.function.Function;

import java.util.function.Function;

public class App {
    private Function<Double,Double> function;
    private double step = 0.01;
    public static double EPSILON = 0.0000001;
    public static String alphaCode = "\u03B1"+":";
    public static String betaCode = "\u03B2"+":";
    public static String epsCode = "\u03B5"+":";
    public static String myuCode = "\u03BC"+":";
    private double alpha;
    private double beta;
    private double myu;
    private double epsilon;
    private double x0;
    private double A;
    private double B;
    private double C;
    private double D;
    private int M;
    private int N;
    private int P;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ScatterChart<Double,Double> scatterChart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private VBox vBoxAlpha;

    @FXML
    private Label labelAlpha;

    @FXML
    private TextField textAlpha;

    @FXML
    private RadioButton radioAlpha;

    @FXML
    private ToggleGroup fixedParameter;

    @FXML
    private VBox vBoxAlpha1;

    @FXML
    private Label labelBeta;

    @FXML
    private TextField textBeta;

    @FXML
    private RadioButton radioBeta;

    @FXML
    private VBox vBoxAlpha2;

    @FXML
    private Label labelMyu;

    @FXML
    private TextField textMyu;

    @FXML
    private RadioButton radioMyu;

    @FXML
    private VBox vBoxAlpha3;

    @FXML
    private Label labelEpsilon;

    @FXML
    private TextField textEpsilon;

    @FXML
    private RadioButton radioEpsilon;

    @FXML
    private Label labelA;

    @FXML
    private TextField textA;

    @FXML
    private Label labelB;

    @FXML
    private TextField textB;

    @FXML
    private Label labelC;

    @FXML
    private TextField textC;

    @FXML
    private Label labelD;

    @FXML
    private TextField textD;

    @FXML
    private Label labelX0;

    @FXML
    private TextField textX0;

    @FXML
    private Label labelM;

    @FXML
    private TextField textM;

    @FXML
    private Label labelN;

    @FXML
    private TextField textN;

    @FXML
    private Label labelP;

    @FXML
    private TextField textP;

    @FXML
    private Button buildButton;

    @FXML
    private Label chosenLabel;

    public void initialize() {
        labelAlpha.setText(alphaCode);
        labelBeta.setText(betaCode);
        labelEpsilon.setText(epsCode);
        labelMyu.setText(myuCode);
        fixedParameter.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
                    if (fixedParameter.getSelectedToggle() != null) {
                        RadioButton chosen = (RadioButton) fixedParameter.getSelectedToggle();
                        chosenLabel.setText("Now chosen: " + chosen.getText());
                    }
                }
        );
    }

    public Function<Double, Double> getFunction() {
        return function;
    }

    public double getA() {
        return A;
    }

    public double getB() {
        return B;
    }

    public double getC() {
        return C;
    }

    public double getD() {
        return D;
    }

    public double getAlpha() {
        return alpha;
    }

    public double getBeta() {
        return beta;
    }

    public double getEpsilon() {
        return epsilon;
    }

    public double getMyu() {
        return myu;
    }

    public double getX0() {
        return x0;
    }

    public int getM() {
        return M;
    }

    public int getN() {
        return N;
    }

    public int getP() {
        return P;
    }

    public double getStep() {
        return step;
    }

    public ToggleGroup getFixedParameter() {
        return fixedParameter;
    }


    private void setFixedParameter(double currentPoint) {
        switch (((RadioButton) fixedParameter.getSelectedToggle()).getText()) {
            case "Alpha":
                function = x -> currentPoint * Math.sin(beta * x) * Math.cos(epsilon / Math.pow(x - myu, 2));
                break;
            case "Beta":
                function = x -> alpha * Math.sin(currentPoint * x) * Math.cos(epsilon / Math.pow(x - myu, 2));
                break;
            case "Epsilon":
                function = x -> alpha * Math.sin(beta * x) * Math.cos(currentPoint / Math.pow(x - myu, 2));
                break;
            case "Myu":
                function = x -> alpha * Math.sin(beta * x) * Math.cos(epsilon / Math.pow(x - currentPoint, 2));
                break;
            default:
                break;
        }
    }

    private void calcPoints(){
        final XYChart.Series<Double, Double> series = new XYChart.Series<>();
        final XYChart.Series<Double, Double> seriesP = new XYChart.Series<>();
        int iter = 0 , iterP = 0;
        double point, yPrev = x0;
        step = 0.1;

        for (point = A; point <= B; point += step) {
            setFixedParameter(point);
            series.getData().add(new XYChart.Data<Double,Double>(point,x0));
            // фиксированный параметр
            while(iter < M){
                yPrev = function.apply(yPrev);
                iter++;
            }
            iter = 0;
            while(iter < N){
                yPrev = function.apply(yPrev);
                series.getData().add(new XYChart.Data<Double,Double>(point,yPrev));
                if(iterP == P - 1){
                    seriesP.getData().add(new XYChart.Data<Double,Double>(point,yPrev));
                    iterP=0;
                }
                else{
                    iterP++;
                }
                iter++;
            }
            yPrev = x0;
            iter = 0;
        }
        scatterChart.getData().addAll(series,seriesP);
    }

    @FXML
    public void buildGraphics(ActionEvent event) {
        try {
            alpha = Double.parseDouble(textAlpha.getText());
            beta = Double.parseDouble(textBeta.getText());
            myu = Double.parseDouble(textMyu.getText());
            epsilon = Double.parseDouble(textEpsilon.getText());
            A = Double.parseDouble(textA.getText());
            B = Double.parseDouble(textB.getText());
            C = Double.parseDouble(textC.getText());
            D = Double.parseDouble(textD.getText());
            x0 = Double.parseDouble(textX0.getText());
            M = Integer.parseInt(textM.getText());
            N = Integer.parseInt(textN.getText());
            P = Integer.parseInt(textP.getText());
        } catch (NumberFormatException e) {
            showMessage("Error while filling in fields or empty fields");
        }
        step = (B - A) / N;
        xAxis.setLowerBound(A);
        xAxis.setUpperBound(B);
        xAxis.setTickUnit(step);
        yAxis.setLowerBound(C);
        yAxis.setUpperBound(D);
        calcPoints();
    }



    private void showMessage(String s) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }
}