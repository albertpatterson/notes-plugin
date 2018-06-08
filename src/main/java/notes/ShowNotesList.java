package notes;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;

public class ShowNotesList extends AnAction {


    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        if(project==null) return;

        NoteListService noteListService = ServiceManager.getService(project, NoteListService.class);
        noteListService.create(e);
    }
}
