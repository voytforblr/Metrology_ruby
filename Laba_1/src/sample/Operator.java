package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Operator {

    public static void setN(int n) {
        Operator.n = n;
    }

    private static int n=0;
    private SimpleStringProperty operator;
    private SimpleIntegerProperty j;
    private SimpleIntegerProperty f1j;


    public Operator(String operator) {
        this.operator=new SimpleStringProperty(operator);
        n++;
        this.j=new SimpleIntegerProperty(n);
        this.f1j=new SimpleIntegerProperty(1);
    }

    public void setF1j(int f1j) {
        this.f1j.set(f1j);
    }

    public String getOperator() {
        return operator.get();
    }

    public SimpleStringProperty operatorProperty() {
        return operator;
    }

    public int getJ() {
        return j.get();
    }

    public SimpleIntegerProperty jProperty() {
        return j;
    }

    public int getF1j() {
        return f1j.get();
    }

    public SimpleIntegerProperty f1jProperty() {
        return f1j;
    }

    public static int getN() {
        return n;
    }

    public void f1j_increment(){
        this.f1j=new SimpleIntegerProperty((f1j.get()+1));
    }
}
