package notes.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.components.ServiceManager;
import notes.service.view.NoteListService;

public class ShowNotesList extends AnAction {


    @Override
    public void actionPerformed(AnActionEvent e) {
        NoteListService noteListService = ServiceManager.getService(NoteListService.class);
        noteListService.create(e);
    }
}
