package notes;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public interface NoteListService {

    static NoteListService getInstance(@NotNull Project project) {
        return ServiceManager.getService(project, NoteListService.class);
    }

    void create(AnActionEvent e);
}
