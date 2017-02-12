/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs480project;

/**
 *
 * @author Brian
 */
public class Log {
    private int rowid;
    private String month;
    private int date;
    private String time;
    private String user;
    private String drive;
    private String event;
 
    public Log(String month, int date, String time, String user, String drive, String event) {
        this.rowid = rowid;
        this.month = month;
        this.date = date;
        this.time = time;
        this.user = user;
        this.drive = drive;
        this.event = event;
    }

    public void setRowid(int rowid) {
        this.rowid = rowid;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setDrive(String drive) {
        this.drive = drive;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public int getRowid() {
        return rowid;
    }

    public String getMonth() {
        return month;
    }

    public int getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getUser() {
        return user;
    }

    public String getDrive() {
        return drive;
    }

    public String getEvent() {
        return event;
    }
    
    
    
    
}
