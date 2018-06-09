package notes.service.view;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.components.ServiceManager;


public interface NoteAnnotationService {

    static NoteAnnotationService getInstance() {
        return ServiceManager.getService(NoteAnnotationService.class);
    }

    void create(AnActionEvent e);
}
