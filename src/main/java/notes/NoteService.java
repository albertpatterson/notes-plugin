package notes;

import com.intellij.openapi.components.ServiceManager;

import java.util.stream.Stream;

public interface NoteService {

    static NoteService getInstance() {
        return ServiceManager.getService(NoteService.class);
    }

    public Note getNote(String path, int lineNo);

    public Stream<Note> getNotes();

    public String getNoteContent(String path, int lineNo);

    public void putNote(String path, int lineNo, String note);

    public void deleteNote(String path, int lineNo);
}
