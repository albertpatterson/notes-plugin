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
import notes.service.controller.LineNoteService;
import notes.service.model.LineNote;
import notes.service.model.Note;
import notes.service.view.LineNotePopupService;
import notes.service.view.NoteAnnotationService;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;

public class LineNoteAnnotationServiceImpl implements NoteAnnotationService {

    private final LineNoteService noteService = ServiceManager.getService(LineNoteService.class);
    private final LineNotePopupService lineNotePopupService = ServiceManager.getService(LineNotePopupService.class);

    public void create(AnActionEvent anActionEvent){

        Project project = anActionEvent.getProject();
        Editor editor = anActionEvent.getData(CommonDataKeys.EDITOR);
        String path = getFilePath(anActionEvent);

        if(project==null || editor==null || path==null) return;

        Module[] modules = ModuleManager.getInstance(project).getModules();
        noteService.setProjectAndModules(project, modules);


        EditorGutter gutter = editor.getGutter();
        TextAnnotationGutterProvider textAnnotationGutterProvider = new TextAnnotationGutterProvider(){

            @Override
            public String getLineText(int line, Editor editor) {
                Note note = noteService.getNote(LineNoteService.makeKey(path, line));
                return (note==null)?"+ ":">>";
            }

            @Nullable
            @Override
            public String getToolTip(int line, Editor editor) {
                Note note = noteService.getNote(LineNoteService.makeKey(path, line));
                return (note==null)?"":note.content;
            }

            @Override
            public EditorFontType getStyle(int line, Editor editor) {
                return null;
            }

            @Override
            public ColorKey getColor(int line, Editor editor) {
                return ColorKey.createColorKey("black", JBColor.BLACK);
            }

            @Nullable
            @Override
            public Color getBgColor(int line, Editor editor) {
                Note note = noteService.getNote(LineNoteService.makeKey(path, line));
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
                LineNote lineNote = noteService.getNote(LineNoteService.makeKey(path, lineNum));
                if(lineNote==null) lineNote = new LineNote(path, lineNum, "");
                lineNotePopupService.create(anActionEvent, lineNote);
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
        return (vf==null)?null:vf.getPath();
    }
}
