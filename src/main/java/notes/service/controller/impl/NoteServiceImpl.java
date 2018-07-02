package notes.service.controller.impl;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleServiceManager;
import notes.service.controller.NoteService;
import notes.service.model.Note;
import notes.service.model.module.NoteStorageService;

public class NoteServiceImpl extends GenericNoteServiceImpl<Note, NoteStorageService> implements NoteService {

    @Override
    NoteStorageService getStorageService(Module module) {
        return ModuleServiceManager.getService(module, NoteStorageService.class);
    }

    @Override
    String getPath(String key) {
        if (!key.matches("([^:]*)")) {
            System.out.println("Invalid key for note: " + key);
            return null;
        }
        return key;
    }
}