package s55_22769.t_01.abdelrahman_mohamed.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class Note {

    private String id;
    private String title;
    private String content;
    private String userId;
    private LocalDateTime createdAt;

    public Note() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
    }

    public Note(String title, String content, String userId) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
    }

    public Note(String id, String title, String content, String userId, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
