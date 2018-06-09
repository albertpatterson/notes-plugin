package notes.service.model;

public interface NoteStorage {

    Note getNote(String path, int lineNo);

    Note[] getNotes();

    void putNote(String path, int lineNo, String note);

    void deleteNote(String path, int lineNo);
}
