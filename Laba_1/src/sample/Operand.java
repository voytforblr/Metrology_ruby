package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Operand {
    public static void setN(int n) {
        Operand.n = n;
    }

    private static int n=0;


    private SimpleStringProperty operand;
    private SimpleIntegerProperty i;
    private SimpleIntegerProperty f2i;

    public Operand(String operand) {
        this.operand=new SimpleStringProperty(operand);
        n++;
        this.i=new SimpleIntegerProperty(n);
        this.f2i=new SimpleIntegerProperty(1);
    }

    public void setF2i(int f2i) {
        this.f2i.set(f2i);
    }

    public String getOperand() {
        return operand.get();
    }

    public SimpleStringProperty operandProperty() {
        return operand;
    }

    public int getI() {
        return i.get();
    }

    public SimpleIntegerProperty iProperty() {
        return i;
    }

    public int getF2i() {
        return f2i.get();
    }

    public SimpleIntegerProperty f2iProperty() {
        return f2i;
    }

    public static int getN() {
        return n;
    }

    public void f2i_increment(){
        this.f2i=new SimpleIntegerProperty((f2i.get()+1));
    }
}
