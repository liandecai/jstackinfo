package com.qunar.fresh.bean;

/**
 * @author liandecai
 * @time Mar 4, 2014
 */
public class QThread {
    private String name;
    private String id;
    private String statue;
    private String waitOnConditonId;
    
    public QThread() {
        name ="";
        id = "";
        statue= "";
        waitOnConditonId = "";
    }
    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getStatue() {
        return statue;
    }

    public String getWaitOnConditonId() {
        return waitOnConditonId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStatue(String statue) {
        this.statue = statue;
    }

    public void setWaitOnConditonId(String waitOnConditonId) {
        this.waitOnConditonId = waitOnConditonId;
    }
    @Override
    public String toString() {
        return id +"|" + name + "|" + statue;
    }
}
