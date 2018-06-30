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

public class ShowFileNote extends AnAction {

    private final NotePopupService notePopupService = ServiceManager.getService(NotePopupService.class);
    private final NoteService noteService = ServiceManager.getService(NoteService.class);

    @Override
    public void actionPerformed(AnActionEvent e) {

        Project project = e.getProject();
        if(project==null) return;

        Module[] modules = ModuleManager.getInstance(project).getModules();
        noteService.setProjectAndModules(project, modules);

        String filepath = getTargetPath(e);
        Note note = noteService.getNote(filepath);
        if(note==null) note = new Note(filepath, "");

        notePopupService.create(e, note);
    }

    @Override
    public void update(AnActionEvent e) {

        Project project = e.getProject();
        if(project==null) return;

        Module[] modules = ModuleManager.getInstance(project).getModules();
        noteService.setProjectAndModules(project, modules);

        String filepath = getTargetPath(e);
        Note note = noteService.getNote(filepath);

        if(note==null) return;

        Presentation presentation = e.getPresentation();
        presentation.setText("View Note");
        presentation.setDescription("View the note about this item");
    }

    private String getTargetPath(AnActionEvent event) {
        VirtualFile virtualFile = event.getData(CommonDataKeys.VIRTUAL_FILE);
        if(virtualFile==null) return null;

        return virtualFile.getPath();
    }

}
