package notes.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Editor;
import notes.service.view.NoteAnnotationService;

/**
 * Action to be performed in order to show annotations for notes about lines in a file
 */
public class ShowLineNoteAnnotations extends AnAction {

    /**
     * show the annotations for the lines of the file when this action is performed
     * @param anActionEvent automatically supplied (by the intellij platform) event
     */
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        NoteAnnotationService noteAnnotationService = ServiceManager.getService(NoteAnnotationService.class);
        noteAnnotationService.create(anActionEvent);
    }

    /**
     * Show the menu option only if an editor exists and annotations are not already showing
     * @param anActionEvent automatically supplied (by the intellij platform) event
     */
    @Override
    public void update(AnActionEvent anActionEvent) {
        Editor editor = anActionEvent.getData(CommonDataKeys.EDITOR);
        Boolean show = (editor != null) && !editor.getGutter().isAnnotationsShown();
        anActionEvent.getPresentation().setEnabledAndVisible(show);
    }
}
