/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class TotalTimeWorkDTO {
    private String Date;
    private float TotalTime;

    public TotalTimeWorkDTO() {
    }

    public TotalTimeWorkDTO(String Date, float TotalTime) {
        this.Date = Date;
        this.TotalTime = TotalTime;
    }

    public String getDate() {
        return Date;
    }

    public float getTotalTime() {
        return TotalTime;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public void setTotalTime(float TotalTime) {
        this.TotalTime = TotalTime;
    }
    
}
