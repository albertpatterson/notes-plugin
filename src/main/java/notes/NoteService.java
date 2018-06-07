package notes;

import com.intellij.openapi.components.ServiceManager;

import java.util.stream.Stream;

public interface NoteService {

    static NoteService getInstance() {
        return ServiceManager.getService(NoteService.class);
    }

    Note getNote(String path, int lineNo);

    Note[] getNotes();

    String getNoteContent(String path, int lineNo);

    void putNote(String path, int lineNo, String note);

    void deleteNote(String path, int lineNo);
}
