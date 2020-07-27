package model.entity;

import model.utility.ReplyManager;

import java.io.File;

public abstract class Post {

    protected String id;
    protected String title;
    protected String description;
    protected String creatorId;
    protected String status;
    protected File imageFile;
    protected ReplyManager repliesMg = new ReplyManager();

    //	Constructors
    public Post(String id, String title, String description, String creatorId, String status) {
        setId(id);
        setTitle(title);
        setDescription(description);
        setCreatorId(creatorId);
        setImageFile(new File("image/defaultImage.png"));
        setStatus("OPEN");
    }

    public Post(String id, String title, String description, String creatorId,String status,File imageFile) {
        this(id,title,description,creatorId,status);
        setImageFile(imageFile);

    }

    //	Accessor
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatorId() { // Need Implement Validation
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean closePost() {
        setStatus("CLOSED");
        return true;
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    // Methods getPostDetails()
    public String getPostDetails() {
        String s = "";
        s += "\nID : " + id;
        s += "\nTitle : " + title;
        s += "\nDescription : " + description;
        s += "\nCreatorID : " + creatorId;
        s += "\nStatus : " + status;
        return s.toString();
    }

    //	Abstract methods
    public abstract boolean handleReply(Reply reply);

    public abstract String getReplyDetails();

}

