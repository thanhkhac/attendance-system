/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author admin
 */
public class RequestTypeDTO {

    private int requestTypeID;
    private String requestTypeName;

    public RequestTypeDTO() {
    }

    public RequestTypeDTO(int requestTypeID, String requestTypeName) {
        this.requestTypeID = requestTypeID;
        this.requestTypeName = requestTypeName;
    }

    public int getRequestTypeID() {
        return requestTypeID;
    }

    public void setRequestTypeID(int requestTypeID) {
        this.requestTypeID = requestTypeID;
    }

    public String getRequestTypeName() {
        return requestTypeName;
    }

    public void setRequestTypeName(String requestTypeName) {
        this.requestTypeName = requestTypeName;
    }

}
