package notes.service.view.impl;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;
import notes.service.controller.LineNoteService;
import notes.service.model.LineNote;
import notes.service.view.NoteListService;
import notes.service.view.NotePopupService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.Arrays;

public class NoteListServiceImpl implements NoteListService {

    private LineNoteService lineNoteService = ServiceManager.getService(LineNoteService.class);
    private NotePopupService notePopupService = ServiceManager.getService(NotePopupService.class);
    private JBPopup jbPopup;

    @Override
    public void create(AnActionEvent e) {

        Project project = e.getProject();
        if(project==null) return;
        Module[] modules = ModuleManager.getInstance(project).getModules();
        lineNoteService.setProjectAndModules(project, modules);

        final JComponent popupContent = createContents(e);

        final JBPopupFactory jbPopupFactory = JBPopupFactory.getInstance();
        jbPopup = jbPopupFactory.createComponentPopupBuilder(popupContent, popupContent)
                .setResizable(false)
                .setTitle("Notes")
                .setMovable(false)
                .setFocusable(true)
                .setRequestFocus(true)
                .createPopup();

        jbPopup.showCenteredInCurrentWindow(e.getProject());
    }

    private JComponent createContents(AnActionEvent e){

        final JPanel jPanel = new JPanel();


        final LineNote[] lineNotes = lineNoteService.getNotesStream().toArray(LineNote[]::new);

        final String[] previews = Arrays.stream(lineNotes).map(
                note->String.join(": ", new String[]{note.filepath, String.valueOf(1+note.lineNo)})
        ).toArray(String[]::new);


        final JBList<String> jbList = new JBList<>(previews);

        jbList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent selectionEvent) {
                final int idx = selectionEvent.getFirstIndex();
                final LineNote note = lineNotes[idx];
                final String filepath = note.filepath;
                final File file = new File(filepath);
                final VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByIoFile(file);
                final Project project = e.getProject();
                if(project!=null && virtualFile!=null){
                    new OpenFileDescriptor(e.getProject(), virtualFile).navigate(true);
                    notePopupService.create(e, note);
                }
                jbPopup.closeOk(null);
            }
        });

        jbList.addMouseMotionListener(new MouseMotionListener(){

            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                int index = jbList.locationToIndex(e.getPoint());
                if(index>-1){
                    jbList.setToolTipText(lineNotes[index].content);
                }
            }
        });

        final JBScrollPane jbScrollPane = new JBScrollPane(jbList);

        jPanel.add(jbScrollPane);

        return jPanel;
    }
}
