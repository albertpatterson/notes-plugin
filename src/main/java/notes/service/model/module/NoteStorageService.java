package notes.service.model.module;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleServiceManager;
import notes.service.model.Note;
import org.jetbrains.annotations.NotNull;

/**
 * A service for retrieving and updating stored note data about files
 */
public interface NoteStorageService extends GenericNoteStorageService<Note> {

    /**
     * get the service instance (used by the Intellij Platform to inject service)
     * @return service for retrieving and updating stored note data about files
     */
    static NoteStorageService getInstance(@NotNull Module module) {
        return ModuleServiceManager.getService(module, NoteStorageService.class);
    }
}
