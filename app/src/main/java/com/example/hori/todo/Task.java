package com.example.hori.todo;

/**
 * Created by hori on 2015/01/11.
 */
public class Task {
    public int id;
    public String formula;

    public Task(int id,String formula){
        this.id = id;
        this.formula = formula;
    }

    public int getId(){
        return id;
    }
    public String getFormula(){
        return formula;
    }
}

