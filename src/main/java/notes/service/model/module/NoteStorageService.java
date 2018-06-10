package notes.service.model.module;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleServiceManager;
import notes.service.model.Note;
import org.jetbrains.annotations.NotNull;

public interface NoteStorageService {
    static NoteStorageService getInstance(@NotNull Module module) {
        return ModuleServiceManager.getService(module, NoteStorageService.class);
    }

    Note getNote(String path);

    Note[] getNotes();

    void putNote(String path, String content);

    void deleteNote(String path);

    String getModulePath();
}
