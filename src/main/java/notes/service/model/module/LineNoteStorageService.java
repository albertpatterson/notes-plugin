package notes.service.model.module;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleServiceManager;
import notes.service.model.LineNote;
import org.jetbrains.annotations.NotNull;

public interface LineNoteStorageService {
    static LineNoteStorageService getInstance(@NotNull Module module) {
        return ModuleServiceManager.getService(module, LineNoteStorageService.class);
    }

    LineNote getLineNote(String path, int lineNo);

    LineNote[] getLineNotes();

    void putLineNote(String path, int lineNo, String content);

    void deleteLineNote(String path, int lineNo);

    String getModulePath();
}
