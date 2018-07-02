package notes.service.model;

import java.util.Date;

/**
 * A note about a line in a file
 */
public class LineNote extends Note {

    /**
     * the line in the file
     */
    public int lineNo;

    /**
     * @param filepath the path to the file
     * @param lineNo the line number
     * @param content the content of the note
     */
    public LineNote(String filepath,int lineNo, String content) {
        super(filepath, content);
        this.lineNo = lineNo;
    }

    /**
     * @param filepath the path to the file
     * @param lineNo the line number
     * @param content the content of the note
     * @param updated the date when the note was last updated
     */
    public LineNote(String filepath, int lineNo, String content, Date updated) {
        super(filepath, content, updated);
        this.lineNo = lineNo;
    }
}
