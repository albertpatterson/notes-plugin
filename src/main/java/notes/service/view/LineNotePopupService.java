package notes.service.view;

import com.intellij.openapi.components.ServiceManager;
import notes.service.model.LineNote;

/**
 * Service for showing a popup for viewing, editing, or deleting a note about a line in a file
 */
public interface LineNotePopupService extends GenericNotePopupService<LineNote>{

    /**
     * get the service instance (used by the Intellij Platform to inject service)
     * @return service for showing a popup for viewing, editing, or deleting a note about a line in a file
     */
    static LineNotePopupService getInstance() {
        return ServiceManager.getService(LineNotePopupService.class);
    }

}
