package notes.service.model.module;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleServiceManager;
import notes.service.model.LineNote;
import org.jetbrains.annotations.NotNull;

/**
 * A service for retrieving and updating stored note data about lines in files
 */
public interface LineNoteStorageService extends GenericNoteStorageService<LineNote>{

    /**
     * get the service instance (used by the Intellij Platform to inject service)
     * @return service for retrieving and updating stored note data about lines in files
     */
    static LineNoteStorageService getInstance(@NotNull Module module) {
        return ModuleServiceManager.getService(module, LineNoteStorageService.class);
    }
}
