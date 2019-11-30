package calculation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import start.App;

import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.function.Function;

public class Calculation extends RecursiveTask<ScatterChart<Double,Double>>{
    private App app;
    private int processorsNumber = Runtime.getRuntime().availableProcessors();
    private double start;
    private double end;
    public static int THRESHOLD = 100;
    private Function function;
    private ScatterChart scatterChart;

    public Calculation(App app,double start , double end){
        this.app = app;
        function = app.getFunction();
        this.scatterChart = new ScatterChart( new NumberAxis(app.getA() , app.getB() , app.getxAxis().getTickUnit()) , new NumberAxis(app.getC() , app.getD(),app.getyAxis().getTickUnit()));
        this.start = start;
        this.end = end;
    }

    public Calculation(App app, int threshold){
        this.app = app;
        function = app.getFunction();
    }

    public Calculation(App app,Function f){
        this.app = app;
        this.function = f;
    }

    private void calcPoints(){
        ObservableList<XYChart.Data> data = FXCollections.observableArrayList();
        ObservableList<XYChart.Data> dataP = FXCollections.observableArrayList();
        int iter = 0 , iterP = 0;
        double point, yPrev = app.getX0();
        double step  = app.getStep();
        for(point = start; point < end + step;point+=step,iter = 0,iterP = 0,yPrev = app.getX0()){
            app.setFixedParameter(point);
            while(iter < app.getM()){
                yPrev = app.apply(yPrev,point);

                iter++;
            }
            iter = 0;
            while(iter < app.getN()){
                yPrev = app.apply(yPrev,point);
                if(iterP != app.getP()-1){
                    data.add(new XYChart.Data<Double,Double>(point,yPrev));
                    iterP++;
                }
                else{
                    dataP.add(new XYChart.Data<Double,Double>(point,yPrev));
                    iterP=0;
                }
                iter++;
            }
        }
        XYChart.Series series = new XYChart.Series();
        XYChart.Series seriesP = new XYChart.Series();
        series.setData(data);
        seriesP.setData(dataP);
        scatterChart.getData().addAll(series,seriesP);
    }

    @Override
    protected ScatterChart<Double,Double> compute(){
         if( Math.abs((end - start)/app.getStep()) <= THRESHOLD){             // на участке точек меньше THRESHOLD
            calcPoints();
         }
         else {
             //ForkJoinTask.invokeAll(new Calculation(app,start , (start+end)/2) , new Calculation(app,(start+end)/2 , end));
             Calculation left = new Calculation(app,start , (start+end)/2);
             Calculation right = new Calculation(app,(start+end)/2 , end);
             left.fork();
             right.fork();
             scatterChart = left.join();// эттака
         }
         return scatterChart;
    }


    public void setFunction(Function f){
        this.function = f;
    }

    public Function getFunction() {
        return function;
    }

}
