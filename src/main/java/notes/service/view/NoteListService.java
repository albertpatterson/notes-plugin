package notes.service.view;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.components.ServiceManager;

public interface NoteListService {

    static NoteListService getInstance() {
        return ServiceManager.getService(NoteListService.class);
    }

    void create(AnActionEvent e);
}
