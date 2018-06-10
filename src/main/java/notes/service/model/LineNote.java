package notes.service.model;

import java.util.Date;

public class LineNote extends Note {
    public int lineNo;

    public LineNote(String _filepath,int _lineNo, String _content) {
        super(_filepath, _content);
        lineNo = _lineNo;
    }

    public LineNote(String _filepath, int _lineNo, String _content, Date _update) {
        super(_filepath, _content, _update);
        lineNo = _lineNo;
    }
}
