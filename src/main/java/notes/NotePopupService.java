package notes;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public interface NotePopupService {

    static NotePopupService getInstance(@NotNull Project project) {
        return ServiceManager.getService(project, NotePopupService.class);
    }

    void create(String _path, int _lineNo, Editor editor);
}
