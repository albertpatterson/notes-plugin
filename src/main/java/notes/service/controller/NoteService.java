package notes.service.controller;

import com.intellij.openapi.components.ServiceManager;
import notes.service.model.Note;

/**
 * A service for retrieving and updating note data about files
 */
public interface NoteService extends GenericNoteService<Note>{

    /**
     * get the service instance (used by the Intellij Platform to inject service)
     * @return service for retrieving and updating note data about files
     */
    static NoteService getInstance() {
        return ServiceManager.getService(NoteService.class);
    }

    /**
     * Create a unique key for the note about the file
     * @param path the path to the file
     * @return a unique key
     */
    static String makeKey(String path) {
        return path;
    }
}
