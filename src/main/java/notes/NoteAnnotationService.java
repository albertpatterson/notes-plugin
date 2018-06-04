package notes;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.components.ServiceManager;

public interface NoteAnnotationService {
    static NoteAnnotationService getInstance() {
        return ServiceManager.getService(NoteAnnotationService.class);
    }

    public void create(AnActionEvent e);
}
