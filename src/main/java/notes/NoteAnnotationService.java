package notes;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public interface NoteAnnotationService {

    static NoteAnnotationService getInstance(@NotNull Project project) {
        return ServiceManager.getService(project, NoteAnnotationService.class);
    }

    public void create(AnActionEvent e);
}
