package notes.service.view;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.components.ServiceManager;

public interface NotePopupService {

    static NotePopupService getInstance() {
        return ServiceManager.getService(NotePopupService.class);
    }

    void create(AnActionEvent e, String _path, int _lineNo);
}
