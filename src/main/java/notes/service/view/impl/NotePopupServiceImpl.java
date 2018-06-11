package notes.service.view.impl;

import com.intellij.openapi.components.ServiceManager;
import notes.service.controller.GenericNoteService;
import notes.service.controller.NoteService;
import notes.service.model.Note;
import notes.service.view.NotePopupService;

public class NotePopupServiceImpl extends GenericNotePopupServiceImpl<Note> implements NotePopupService {


    @Override
    GenericNoteService<Note> getService() {
        return ServiceManager.getService(NoteService.class);
    }

    @Override
    String createTitle(Note note) {
        return String.join("",
                new String[]{
                        "<html><div style=\"text-align:center; font-weight:bold\">Note</div>",
                        "path: " + note.filepath
                });
    }
}