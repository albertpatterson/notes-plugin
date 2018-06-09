package notes.service.view.impl;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorGutter;
import com.intellij.openapi.editor.EditorGutterAction;
import com.intellij.openapi.editor.TextAnnotationGutterProvider;
import com.intellij.openapi.editor.colors.ColorKey;
import com.intellij.openapi.editor.colors.EditorFontType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.JBColor;
import notes.service.controller.NoteService;
import notes.service.model.Note;
import notes.service.view.NoteAnnotationService;
import notes.service.view.NotePopupService;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;

public class NoteAnnotationServiceImpl implements NoteAnnotationService {

    private NoteService noteService = ServiceManager.getService(NoteService.class);
    private NotePopupService notePopupService = ServiceManager.getService(NotePopupService.class);

    public void create(AnActionEvent e){

        Project project = e.getProject();
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        if(project==null || editor==null) return;

        Module[] modules = ModuleManager.getInstance(project).getModules();
        noteService.setProjectAndModules(project, modules);

        String path = getFilePath(e);

        EditorGutter gutter = editor.getGutter();
        TextAnnotationGutterProvider textAnnotationGutterProvider = new TextAnnotationGutterProvider(){

            @Override
            public String getLineText(int line, Editor editor) {
                Note note = noteService.getNote(path, line);
                return (note==null)?"+ ":">>";
            }

            @Nullable
            @Override
            public String getToolTip(int line, Editor editor) {
                return noteService.getNoteContent(path, line);
            }

            @Override
            public EditorFontType getStyle(int line, Editor editor) {
                return null;
            }

            @Override
            public ColorKey getColor(int line, Editor editor) {
                return ColorKey.createColorKey("black", Color.black);
            }

            @Nullable
            @Override
            public Color getBgColor(int line, Editor editor) {
                Note note = noteService.getNote(path, line);
                return (note==null)?null: JBColor.WHITE;
            }

            @Override
            public List<AnAction> getPopupActions(int line, Editor editor) {
                return null;
            }

            @Override
            public void gutterClosed() {}
        };

        EditorGutterAction editorGutterAction = new EditorGutterAction(){

            @Override
            public void doAction(int lineNum) {
                editor.getCaretModel().moveToOffset(editor.getDocument().getLineStartOffset(lineNum));
                notePopupService.create(e, path, lineNum);
            }

            @Override
            public Cursor getCursor(int lineNum) {
                return null;
            }
        };

        gutter.registerTextAnnotation(textAnnotationGutterProvider, editorGutterAction);
    }

    private static String getFilePath(AnActionEvent e) {
        final VirtualFile vf = e.getData(CommonDataKeys.VIRTUAL_FILE);
        return vf.getPath();
    }
}
