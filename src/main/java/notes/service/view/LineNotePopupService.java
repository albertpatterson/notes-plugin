package notes.service.view;

import com.intellij.openapi.components.ServiceManager;
import notes.service.model.LineNote;

public interface LineNotePopupService extends GenericNotePopupService<LineNote>{

    static LineNotePopupService getInstance() {
        return ServiceManager.getService(LineNotePopupService.class);
    }

}
