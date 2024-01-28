/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author admin
 */
public class UpdateInfoError {
    private String null_error;
    private String name_format_error;
    private String cccd_format_error;
    private String phone_format_error;
    private String password_format_error;
    private String email_format_error;
    private String date_invalid;
    private String dublicate_error;

    public UpdateInfoError() {
    }

    
    
    public UpdateInfoError(String null_error, String name_format_error, String cccd_format_error, String phone_format_error, String password_format_error, String email_format_error, String date_invalid, String dublicate_error) {
        this.null_error = null_error;
        this.name_format_error = name_format_error;
        this.cccd_format_error = cccd_format_error;
        this.phone_format_error = phone_format_error;
        this.password_format_error = password_format_error;
        this.email_format_error = email_format_error;
        this.date_invalid = date_invalid;
        this.dublicate_error = dublicate_error;
    }

    public String getNull_error() {
        return null_error;
    }

    public void setNull_error(String null_error) {
        this.null_error = null_error;
    }

    public String getName_format_error() {
        return name_format_error;
    }

    public void setName_format_error(String name_format_error) {
        this.name_format_error = name_format_error;
    }

    public String getCccd_format_error() {
        return cccd_format_error;
    }

    public void setCccd_format_error(String cccd_format_error) {
        this.cccd_format_error = cccd_format_error;
    }

    public String getPhone_format_error() {
        return phone_format_error;
    }

    public void setPhone_format_error(String phone_format_error) {
        this.phone_format_error = phone_format_error;
    }

    public String getPassword_format_error() {
        return password_format_error;
    }

    public void setPassword_format_error(String password_format_error) {
        this.password_format_error = password_format_error;
    }

    public String getEmail_format_error() {
        return email_format_error;
    }

    public void setEmail_format_error(String email_format_error) {
        this.email_format_error = email_format_error;
    }

    public String getDate_invalid() {
        return date_invalid;
    }

    public void setDate_invalid(String date_invalid) {
        this.date_invalid = date_invalid;
    }

    public String getDublicate_error() {
        return dublicate_error;
    }

    public void setDublicate_error(String dublicate_error) {
        this.dublicate_error = dublicate_error;
    }
    
    
}
