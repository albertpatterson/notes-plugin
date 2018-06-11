package notes.service.view.impl;

import com.intellij.openapi.components.ServiceManager;
import notes.service.controller.GenericNoteService;
import notes.service.controller.LineNoteService;
import notes.service.model.LineNote;
import notes.service.view.LineNotePopupService;

public class LineNotePopupServiceImpl extends GenericNotePopupServiceImpl<LineNote> implements LineNotePopupService {

    @Override
    GenericNoteService<LineNote> getService(){
        return ServiceManager.getService(LineNoteService.class);
    }

    @Override
    protected String createTitle(LineNote lineNote) {
        return String.join("",
                new String[]{
                        "<html><div style=\"text-align:center; font-weight:bold\">Note</div>",
                        "path: " + lineNote.filepath,
                        "<br>line: " + (1 + lineNote.lineNo)});
    }
}
