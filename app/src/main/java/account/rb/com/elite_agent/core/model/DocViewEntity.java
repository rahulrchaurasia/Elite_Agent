package account.rb.com.elite_agent.core.model;

/**
 * Created by Rajeev Ranjan on 29/05/2019.
 */
public class DocViewEntity {

    private String id;
    private String imagePath;
    private  String comment;
    private String type;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }




}
