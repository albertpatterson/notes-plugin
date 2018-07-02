package notes.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import notes.service.controller.NoteService;
import notes.service.model.Note;
import notes.service.view.NotePopupService;

/**
 * Action to be performed in order to show a file note
 */
public class ShowFileNote extends AnAction {

    private final NotePopupService notePopupService = ServiceManager.getService(NotePopupService.class);
    private final NoteService noteService = ServiceManager.getService(NoteService.class);

    /**
     * show a note popup for a file note when this action is performed
     * @param anActionEvent automatically supplied (by the intellij platform) event
     */
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {

        Project project = anActionEvent.getProject();
        if(project==null) return;

        Module[] modules = ModuleManager.getInstance(project).getModules();
        noteService.setProjectAndModules(project, modules);

        String filepath = getTargetPath(anActionEvent);
        Note note = noteService.getNote(filepath);
        if(note==null) note = new Note(filepath, "");

        notePopupService.create(anActionEvent, note);
    }

    /**
     * Set the presentation depending on whether a note is available for the corresponding file
     * @param anActionEvent automatically supplied (by the intellij platform) event
     */
    @Override
    public void update(AnActionEvent anActionEvent) {

        Project project = anActionEvent.getProject();
        if(project==null) return;

        Module[] modules = ModuleManager.getInstance(project).getModules();
        noteService.setProjectAndModules(project, modules);

        String filepath = getTargetPath(anActionEvent);
        Note note = noteService.getNote(filepath);

        if(note==null) return;

        Presentation presentation = anActionEvent.getPresentation();
        presentation.setText("View Note");
        presentation.setDescription("View the note about this item");
    }

    /**
     * get the path of a the file for which the action was performed
     * @param anActionEvent automatically supplied (by the intellij platform) event
     * @return the path of a the file for which the action was performed
     */
    private String getTargetPath(AnActionEvent anActionEvent) {
        VirtualFile virtualFile = anActionEvent.getData(CommonDataKeys.VIRTUAL_FILE);
        if(virtualFile==null) return null;

        return virtualFile.getPath();
    }

}
