package com.example.hellofx.session;

public abstract class Session {
    private static boolean isFile;
    private static boolean isFull;

    public static void setFile(boolean file) { isFile = file; }
    public static void setFull(boolean full) { isFull = full; }
    public static boolean isFile() {return isFile;}
    public static boolean isFull() {return isFull;}

    public abstract void close();

}
