package notes.service.controller;

import com.intellij.openapi.components.ServiceManager;
import notes.service.model.LineNote;

/**
 * A service for retrieving and updating note data about lines in files
 */
public interface LineNoteService extends GenericNoteService<LineNote>{

    /**
     * get the service instance (used by the Intellij Platform to inject service)
     * @return service for retrieving and updating note data about lines in files
     */
    static LineNoteService getInstance() {
        return ServiceManager.getService(LineNoteService.class);
    }

    /**
     * Create a unique key for the note about the line
     * @param path the path to the file containing the line
     * @param lineNo the number of the line
     * @return a unique key
     */
    static String makeKey(String path, int lineNo) {
        return path + ":" + lineNo;
    }
}
