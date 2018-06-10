package notes.service.model;

import java.util.Date;

public class Note{
    public String content;
    public Date updated;
    public String filepath;

    public Note(String _filepath, String _content){
        this(_filepath, _content, new Date());
    }

    public Note(String _filepath, String _content, Date _updated){
        filepath = _filepath;
        content = _content;
        updated = _updated;
    }
}