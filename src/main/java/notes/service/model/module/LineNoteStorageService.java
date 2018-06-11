package notes.service.model.module;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleServiceManager;
import notes.service.model.LineNote;
import org.jetbrains.annotations.NotNull;

public interface LineNoteStorageService extends GenericNoteStorageService<LineNote>{

    static LineNoteStorageService getInstance(@NotNull Module module) {
        return ModuleServiceManager.getService(module, LineNoteStorageService.class);
    }
}
