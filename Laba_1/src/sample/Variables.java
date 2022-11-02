package sample;

public class Variables {

    String var;

    public void setType_full(String type_full) {
        this.type_full = type_full;
    }

    public void setType_io(String type_io) {
        this.type_io = type_io;
    }

    String type_full;

    public String getType_full() {
        return type_full;
    }

    public String getType_io() {
        return type_io;
    }

    String type_io;

    public Variables(String var, String type_full, String type_io, int count) {
        this.var = var;
        this.type_full = type_full;
        this.type_io = type_io;
        this.count = count;
    }


    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    int count;


}
