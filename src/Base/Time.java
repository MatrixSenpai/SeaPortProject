package Base;

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

