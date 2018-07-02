package notes.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.components.ServiceManager;
import notes.service.view.NoteListService;

/**
 * Action to be performed in order to show the list of notes
 */
public class ShowNotesList extends AnAction {

    /**
     * Show the list of notes when this action is performed
     * @param automatically supplied (by the intellij platform) event
     */
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        NoteListService noteListService = ServiceManager.getService(NoteListService.class);
        noteListService.create(anActionEvent);
    }
}
