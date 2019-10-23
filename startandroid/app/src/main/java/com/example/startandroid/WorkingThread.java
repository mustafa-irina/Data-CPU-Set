package com.example.startandroid;

public class WorkingThread extends Thread{
    private volatile int cancellation = 0;

    public void Cancel(){
        cancellation = 1;
    }

    @Override
    public void run() {
        while (cancellation == 0) {
            // TODO add operations
        }
    }


}
