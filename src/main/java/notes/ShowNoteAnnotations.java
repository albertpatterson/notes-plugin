package notes;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorGutter;
import com.intellij.openapi.project.Project;

public class ShowNoteAnnotations extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        if(project==null) return;

        NoteAnnotationService noteAnnotationService = ServiceManager.getService(project, NoteAnnotationService.class);
        noteAnnotationService.create(e);
    }

    @Override
    public void update(AnActionEvent e) {
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        EditorGutter gutter = editor.getGutter();

        e.getPresentation().setEnabledAndVisible(!gutter.isAnnotationsShown());
    }
}
