package com.akhil.akhildixit.offlineatten.settergetter;

/**
 * Created by Akhil Dixit on 8/23/2017.
 */

public class AttendanceData {

    public   String rollnumber;
    public String date;
    public String status;

    public AttendanceData(String rollnumber,String date,String status)
    {
        this.rollnumber=rollnumber;
        this.status=status;
        this.date=date;
    }
}
