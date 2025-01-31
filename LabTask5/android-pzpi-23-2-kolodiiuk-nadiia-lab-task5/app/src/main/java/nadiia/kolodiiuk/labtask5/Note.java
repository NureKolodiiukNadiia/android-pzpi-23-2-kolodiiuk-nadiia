package nadiia.kolodiiuk.labtask5;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Note implements Serializable {

    private int id;
    private String name;
    private String description;
    private Priority priority;
    private String time;
    private String date;
    private byte[] image;
    private String createdFor;
    private String createdAt;

    public Note(String name, String description,
                Priority priority, String time, String date,
                byte[] image, String createdFor) {
        this.id = -1; // Initialize with invalid ID
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.time = time;
        this.date = date;
        this.image = image;
        this.createdFor = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yy"));
        this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yy"));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getCreatedFor() {
        return createdFor;
    }
    
    public void setCreatedFor(String createdFor) {
        this.createdFor = createdFor;
    }

    public String getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
