package notes;

import java.util.Date;

public class Note{
    public String content;
    public Date updated;
    public String filepath;
    public int lineNo;

    public Note(String _filepath, int _lineNo, String _content){
        this(_filepath, _lineNo, _content, new Date());
    }

    public Note(String _filepath, int _lineNo, String _content, Date _updated){
        filepath = _filepath;
        lineNo = _lineNo;
        content = _content;
        updated = _updated;
    }
}