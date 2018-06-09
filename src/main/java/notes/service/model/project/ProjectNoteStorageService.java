package notes.service.model.project;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import notes.service.model.NoteStorage;
import org.jetbrains.annotations.NotNull;

public interface ProjectNoteStorageService extends NoteStorage {

    static ProjectNoteStorageService getInstance(@NotNull Project project) {
        return ServiceManager.getService(project, ProjectNoteStorageService.class);
    }

}
