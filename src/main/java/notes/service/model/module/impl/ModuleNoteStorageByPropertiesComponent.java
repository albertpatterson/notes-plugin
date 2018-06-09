package notes.service.model.module.impl;

import com.intellij.openapi.module.Module;
import notes.service.model.Note;
import notes.service.model.module.ModuleNoteStorageService;

public class ModuleNoteStorageByPropertiesComponent implements ModuleNoteStorageService{

    public ModuleNoteStorageByPropertiesComponent(Module module) {

    }

    @Override
    public Note getNote(String path, int lineNo) {
        return null;
    }

    @Override
    public Note[] getNotes() {
        return new Note[0];
    }

    @Override
    public void putNote(String path, int lineNo, String note) {

    }

    @Override
    public void deleteNote(String path, int lineNo) {

    }
}
