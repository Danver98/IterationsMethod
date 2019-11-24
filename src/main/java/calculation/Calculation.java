package calculation;

import start.App;

import java.util.concurrent.ForkJoinTask;
import java.util.function.Function;

public class Calculation  {
    private App app;
    private Function function;

    public Calculation(App app){
        this.app = app;
    }

    public Calculation(App app,Function f){
        this.app = app;
        this.function = f;
    }

    public void setFunction(Function f){
        this.function = f;
    }

    public Function getFunction() {
        return function;
    }

}
