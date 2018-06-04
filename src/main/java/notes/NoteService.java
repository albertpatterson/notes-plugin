package notes;

import com.intellij.openapi.components.ServiceManager;

public interface NoteService {

    static NoteService getInstance() {
        return ServiceManager.getService(NoteService.class);
    }

    public String getNote(String path, int lineNo);

    public void putNote(String path, int lineNo, String note);

    public void deleteNote(String path, int lineNo);
}
