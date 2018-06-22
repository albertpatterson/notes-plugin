package notes.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorGutter;
import notes.service.view.NoteAnnotationService;

public class ShowLineNoteAnnotations extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        NoteAnnotationService noteAnnotationService = ServiceManager.getService(NoteAnnotationService.class);
        noteAnnotationService.create(e);
    }

    @Override
    public void update(AnActionEvent e) {
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        if(editor == null) return;

        EditorGutter gutter = editor.getGutter();
        e.getPresentation().setEnabledAndVisible(!gutter.isAnnotationsShown());
    }
}
