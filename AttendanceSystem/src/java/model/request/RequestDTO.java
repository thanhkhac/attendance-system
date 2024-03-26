package model.request;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RequestDTO {
    private int requestID;
    private int employeeID;
    private String title;
    private LocalDateTime sentDate;
    private int typeID;
    private String content;
    private String filePath;
    private Boolean status;
    private String processNote;
    private Integer respondedBy;

    public RequestDTO(int requestID, int employeeID, String title, LocalDateTime sentDate, int typeID, String content, String filePath, Boolean status, String processNote, Integer respondedBy) {
        this.requestID = requestID;
        this.employeeID = employeeID;
        this.title = title;
        this.sentDate = sentDate;
        this.typeID = typeID;
        this.content = content;
        this.filePath = filePath;
        this.status = status;
        this.processNote = processNote;
        this.respondedBy = respondedBy;
    }

    public RequestDTO() {
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDateTime sentDate) {
        this.sentDate = sentDate;
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getProcessNote() {
        return processNote;
    }

    public void setProcessNote(String processNote) {
        this.processNote = processNote;
    }

    public Integer getRespondedBy() {
        return respondedBy;
    }

    public void setRespondedBy(Integer respondedBy) {
        this.respondedBy = respondedBy;
    }

    @Override
    public String toString() {
        return "RequestDTO{" + "requestID=" + requestID + ", employeeID=" + employeeID + ", title=" + title + ", sentDate=" + sentDate + ", typeID=" + typeID + ", content=" + content + ", filePath=" + filePath + ", status=" + status + ", processNote=" + processNote + ", respondedBy=" + respondedBy + '}';
    }
    
    
    
    
    
    
    
    
}
