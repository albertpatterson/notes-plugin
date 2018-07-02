package notes.service.view;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.components.ServiceManager;

/**
 * A service for displaying a list of note about files and lines in files
 */
public interface NoteListService {

    /**
     * get the service instance (used by the Intellij Platform to inject service)
     * @return service for displaying a list of note about files and lines in files
     */
    static NoteListService getInstance() {
        return ServiceManager.getService(NoteListService.class);
    }

    /**
     * show the list of notes
     * @param anActionEvent the action triggering creation of the list
     */
    void create(AnActionEvent anActionEvent);
}
