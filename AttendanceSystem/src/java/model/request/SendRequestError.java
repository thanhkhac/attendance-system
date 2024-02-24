/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.request;

/**
 *
 * @author admin
 */
public class SendRequestError {

    private String nullValue_error;
    private String invalidDate_error;
    private String invalidTime_error;
    private String invalidRole_error;
    private String reasonLength_error;
    private String fileSize_error;

    public SendRequestError() {
    }

    public SendRequestError(String nullValue_error, String invalidDate_error, String invalidTime_error, String invalidRole_error, String reasonLength_error, String fileSize_error) {
        this.nullValue_error = nullValue_error;
        this.invalidDate_error = invalidDate_error;
        this.invalidTime_error = invalidTime_error;
        this.invalidRole_error = invalidRole_error;
        this.reasonLength_error = reasonLength_error;
        this.fileSize_error = fileSize_error;
    }

    public String getNullValue_error() {
        return nullValue_error;
    }

    public void setNullValue_error(String nullValue_error) {
        this.nullValue_error = nullValue_error;
    }

    public String getInvalidDate_error() {
        return invalidDate_error;
    }

    public void setInvalidDate_error(String invalidDate_error) {
        this.invalidDate_error = invalidDate_error;
    }

    public String getInvalidTime_error() {
        return invalidTime_error;
    }

    public void setInvalidTime_error(String invalidTime_error) {
        this.invalidTime_error = invalidTime_error;
    }

    public String getInvalidRole_error() {
        return invalidRole_error;
    }

    public void setInvalidRole_error(String invalidRole_error) {
        this.invalidRole_error = invalidRole_error;
    }

    public String getReasonLength_error() {
        return reasonLength_error;
    }

    public void setReasonLength_error(String reasonLength_error) {
        this.reasonLength_error = reasonLength_error;
    }

    public String getFileSize_error() {
        return fileSize_error;
    }

    public void setFileSize_error(String fileSize_error) {
        this.fileSize_error = fileSize_error;
    }

}
