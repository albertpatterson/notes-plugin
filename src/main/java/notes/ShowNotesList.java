package notes;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.components.ServiceManager;

public class ShowNotesList extends AnAction {

    private static NoteListService noteListService = ServiceManager.getService(NoteListService.class);

    @Override
    public void actionPerformed(AnActionEvent e) {
        noteListService.create(e);
    }
}
