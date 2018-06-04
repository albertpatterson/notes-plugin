package notes;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Editor;

public interface NotePopupService {
    static NotePopupService getInstance() {
        return ServiceManager.getService(NotePopupService.class);
    }

    public void create(String _path, int _lineNo, Editor editor);
}
