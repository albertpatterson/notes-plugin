package notes.service.model;

import java.util.Date;

/**
 * A note about a file
 */
public class Note{

    /**
     * the content of the note
     */
    public String content;

    /**
     * date when the note was last updated
     */
    public Date updated;

    /**
     * the path to the file
     */
    public String filepath;

    /**
     * @param filepath the path to the file
     * @param content the content of the note
     */
    public Note(String filepath, String content){
        this(filepath, content, new Date());
    }

    /**
     * @param filepath the path to the file
     * @param content the content of the note
     * @param updated the date when the note was last updated
     */
    public Note(String filepath, String content, Date updated){
        this.filepath = filepath;
        this.content = content;
        this.updated = updated;
    }
}