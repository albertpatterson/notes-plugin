package notes.service.controller;

import com.intellij.openapi.components.ServiceManager;
import notes.service.model.Note;

public interface NoteService extends GenericNoteService<Note>{

    static NoteService getInstance() {
        return ServiceManager.getService(NoteService.class);
    }

    static String makeKey(String path) {
        return path;
    }
}
