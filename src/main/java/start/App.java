package start;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.function.Function;

import java.util.function.Function;

public class App{
    private Function<Double,Double> function;
    private double step = 0.01;
    public static String alphaCode = "\u03B1"+":";
    public static String betaCode = "\u03B2"+":";
    public static String epsCode = "\u03B5"+":";
    public static String muCode = "\u03BC"+":";
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
    private LineChart<Double,Double> lineChart;

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
    public void buildGraphics(ActionEvent event){
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

        function = x -> alpha*Math.sin(beta*x)*Math.cos(epsilon / Math.pow(x-myu,2));
        final XYChart.Series<Double, Double> series = new XYChart.Series<>();

    }
}