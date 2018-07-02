package notes.service.view;

import com.intellij.openapi.components.ServiceManager;
import notes.service.model.Note;

/**
 * Service for showing a popup for viewing, editing, or deleting a note about a file
 */
public interface NotePopupService extends GenericNotePopupService<Note>{

    /**
     * get the service instance (used by the Intellij Platform to inject service)
     * @return service for showing a popup for viewing, editing, or deleting a note about a file
     */
    static NotePopupService getInstance() {
        return ServiceManager.getService(NotePopupService.class);
    }

}
