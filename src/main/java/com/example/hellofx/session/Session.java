package com.example.hellofx.session;

public abstract class Session {
    private boolean isFile;
    private boolean isFull;

    public void setFile(boolean isFile) { this.isFile = isFile; }
    public void setFull(boolean isFull) { this.isFull = isFull; }
    public boolean isFile() {return isFile;}
    public boolean isFull() {return isFull;}

    public void close(){
        this.isFile = false;
        this.isFull = false;
    }

}
