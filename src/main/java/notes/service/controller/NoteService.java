package notes.service.controller;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleServiceManager;
import com.intellij.openapi.project.Project;
import notes.service.model.LineNote;
import org.jetbrains.annotations.NotNull;

public interface NoteService {

    static NoteService getInstance(@NotNull Module module) {
        return ModuleServiceManager.getService(module, NoteService.class);
    }

    void setProjectAndModules(Project project, Module[] modules);

    LineNote getNote(String path, int lineNo);

    LineNote[] getNotes();

    String getNoteContent(String path, int lineNo);

    void putNote(String path, int lineNo, String note);

    void deleteNote(String path, int lineNo);
}
