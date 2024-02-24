package model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class NewsDTO {

    private int newId;
    private String title;
    private String content;
    private String filePath;
    private Timestamp DateTime;
    private String createBy;

    public NewsDTO() {
    }

    public NewsDTO(int newId, String title, String content, String filePath, Timestamp DateTime, String createBy) {
        this.newId = newId;
        this.title = title;
        this.content = content;
        this.filePath = filePath;
        this.DateTime = DateTime;
        this.createBy = createBy;
    }

    public int getNewId() {
        return newId;
    }

    public void setNewId(int newId) {
        this.newId = newId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Timestamp getDateTime() {
        return DateTime;
    }

    public void setDateTime(Timestamp DateTime) {
        this.DateTime = DateTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @Override
    public String toString() {
        return "NewsDTO{" + "newId=" + newId + ", title=" + title + ", content=" + content + ", filePath=" + filePath + ", DateTime=" + DateTime + ", createBy=" + createBy + '}';
    }

    public String getFormattedDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return dateFormat.format(DateTime);
    }

}
