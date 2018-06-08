package notes;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public interface NoteService {

    static NoteService getInstance(@NotNull Project project) {
        return ServiceManager.getService(project, NoteService.class);
    }

    Note getNote(String path, int lineNo);

    Note[] getNotes();

    String getNoteContent(String path, int lineNo);

    void putNote(String path, int lineNo, String note);

    void deleteNote(String path, int lineNo);
}
