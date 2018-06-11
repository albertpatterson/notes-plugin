package notes.service.controller;

import com.intellij.openapi.components.ServiceManager;
import notes.service.model.LineNote;

public interface LineNoteService extends GenericNoteService<LineNote>{

    static LineNoteService getInstance() {
        return ServiceManager.getService(LineNoteService.class);
    }

    static String makeKey(String path, int lineNo) {
        return path + ":" + lineNo;
    }
}
