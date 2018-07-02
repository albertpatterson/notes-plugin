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
import com.intellij.ui.components.JBTabbedPane;
import notes.service.controller.LineNoteService;
import notes.service.controller.NoteService;
import notes.service.model.LineNote;
import notes.service.model.Note;
import notes.service.view.NoteListService;
import notes.service.view.NotePopupService;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.Arrays;
import java.util.function.Function;

public class NoteListServiceImpl implements NoteListService {

    private final LineNoteService lineNoteService = ServiceManager.getService(LineNoteService.class);
    private final NoteService noteService = ServiceManager.getService(NoteService.class);
    private final NotePopupService notePopupService = ServiceManager.getService(NotePopupService.class);
    private JBPopup jbPopup;

    @Override
    public void create(AnActionEvent anActionEvent) {

        Project project = anActionEvent.getProject();
        if(project==null) return;
        Module[] modules = ModuleManager.getInstance(project).getModules();
        lineNoteService.setProjectAndModules(project, modules);
        noteService.setProjectAndModules(project, modules);

        final JComponent popupContent = createContents(anActionEvent);

        final JBPopupFactory jbPopupFactory = JBPopupFactory.getInstance();
        jbPopup = jbPopupFactory.createComponentPopupBuilder(popupContent, popupContent)
                .setResizable(false)
                .setTitle("Notes")
                .setMovable(false)
                .setFocusable(true)
                .setRequestFocus(true)
                .createPopup();

        jbPopup.showCenteredInCurrentWindow(anActionEvent.getProject());
    }

    private JPanel createNoteListPanel(AnActionEvent event, Note[] notes, Function<Note, String> createPreview) {
        final JPanel jPanel = new JPanel();

        final String[] previews = Arrays.stream(notes).map(createPreview).toArray(String[]::new);

        final JBList<String> jbList = new JBList<>(previews);

        jbList.addListSelectionListener((selectionEvent)->{
            final int idx = selectionEvent.getFirstIndex();
            final Note note = notes[idx];
            final String filepath = note.filepath;
            final File file = new File(filepath);
            final VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByIoFile(file);
            final Project project = event.getProject();
            if(project!=null && virtualFile!=null){
                new OpenFileDescriptor(event.getProject(), virtualFile).navigate(true);
                notePopupService.create(event, note);
            }
            jbPopup.closeOk(null);
        });

        jbList.addMouseMotionListener(new MouseMotionListener(){

            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                int index = jbList.locationToIndex(e.getPoint());
                if(index>-1){
                    jbList.setToolTipText(notes[index].content);
                }
            }
        });

        jbList.setFixedCellWidth(750);
        final JBScrollPane jbScrollPane = new JBScrollPane(jbList);

        jPanel.add(jbScrollPane);

        return jPanel;
    }

    private JPanel createLineNotesPanel(AnActionEvent event) {
        final LineNote[] lineNotes = lineNoteService.getNotesStream().toArray(LineNote[]::new);
        return createNoteListPanel(event, lineNotes, ln->String.join(": ", new String[]{ln.filepath, String.valueOf(1 + ((LineNote) ln).lineNo)}));
    }

    private JPanel createFileNotesPanel(AnActionEvent event) {
        final Note[] notes = noteService.getNotesStream().toArray(Note[]::new);
        return createNoteListPanel(event, notes, n->n.filepath);
    }

    private JComponent createContents(AnActionEvent e){

        final JPanel jPanel = new JPanel();

        JBTabbedPane jbTabbedPane = new JBTabbedPane();

        jbTabbedPane.addTab("Line Notes", createLineNotesPanel(e));
        jbTabbedPane.add("File Notes", createFileNotesPanel(e));

        jPanel.add(jbTabbedPane);
        return jPanel;
    }
}
