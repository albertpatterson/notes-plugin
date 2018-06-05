package notes;

import java.util.Date;

public class Note{
    public String content;
    public Date updated;

    public Note(String content){
        this(content, new Date());
    }

    public Note(String content, Date updated){
        this.content = content;
        this.updated = updated;
    }
}