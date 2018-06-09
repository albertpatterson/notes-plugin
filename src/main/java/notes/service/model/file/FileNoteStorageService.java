package notes.service.model.file;

import com.intellij.openapi.components.ServiceManager;
import notes.service.model.NoteStorage;

public interface FileNoteStorageService extends NoteStorage {

    static FileNoteStorageService getInstance() {
        return ServiceManager.getService(FileNoteStorageService.class);
    }

}
