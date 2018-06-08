package notes.impl;

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
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import notes.Note;
import notes.NoteAnnotationService;
import notes.NotePopupService;
import notes.NoteService;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;

public class NoteAnnotationServiceImpl implements NoteAnnotationService {

    private NoteService noteService;
    private NotePopupService notePopupService;

    public NoteAnnotationServiceImpl(Project project) {
        noteService = ServiceManager.getService(project, NoteService.class);
        notePopupService = ServiceManager.getService(project, NotePopupService.class);
    }

    public void create(AnActionEvent e){

        String path = getFilePath(e);;
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        EditorGutter gutter = editor.getGutter();
        TextAnnotationGutterProvider textAnnotationGutterProvider = new TextAnnotationGutterProvider(){

            @Nullable
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

            @Nullable
            @Override
            public ColorKey getColor(int line, Editor editor) {
                return ColorKey.createColorKey("black", Color.black);
            }

            @Nullable
            @Override
            public Color getBgColor(int line, Editor editor) {
                Note note = noteService.getNote(path, line);
                return (note==null)?null:Color.white;
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
                notePopupService.create(path, lineNum, editor);
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
