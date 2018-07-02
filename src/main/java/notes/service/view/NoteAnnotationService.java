package notes.service.view;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.components.ServiceManager;


/**
 * A service for showing annotations in a file indicating if a line has an associated note
 */
public interface NoteAnnotationService {

    /**
     * get the service instance (used by the Intellij Platform to inject service)
     * @return service for showing annotations in a file indicating if a line has an associated note
     */
    static NoteAnnotationService getInstance() {
        return ServiceManager.getService(NoteAnnotationService.class);
    }

    /**
     * show the annotations in the file
     * @param anActionEvent the event triggering creation of the annotations
     */
    void create(AnActionEvent anActionEvent);
}
