package notes.service.model.module;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleServiceManager;
import notes.service.model.NoteStorage;
import org.jetbrains.annotations.NotNull;

public interface ModuleNoteStorageService extends NoteStorage {

    static ModuleNoteStorageService getInstance(@NotNull Module module) {
        return ModuleServiceManager.getService(module, ModuleNoteStorageService.class);
    }
}
