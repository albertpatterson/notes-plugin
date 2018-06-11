package notes.service.view;

import com.intellij.openapi.components.ServiceManager;
import notes.service.model.Note;

public interface NotePopupService extends GenericNotePopupService<Note>{

    static NotePopupService getInstance() {
        return ServiceManager.getService(NotePopupService.class);
    }

}
