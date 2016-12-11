/*
 * Copyright (c) 2016 Matrix Studios.
 *  All code is licensed under the WTFPL.
 *  A copy of this license is available with this software or at http://wtfpl.net/
 *
 */

public class Time {

    private int time = 0; // measured in seconds

    public Time(int t) {
        time = t;
    }

    public String toString() {
        return String.format("%d %d:%d:%d", time / 60 / 60 / 24, (time / 60 / 60) % 24, (time / 60) % 60, time % 60);
    } // end method toString

    public int getTime(){
        return time;
    }
    public void setTime(int time) {
        this.time = time;
    }
} // end class MyTime

