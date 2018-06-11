package notes.service.view.impl;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.JBPopupListener;
import com.intellij.openapi.ui.popup.LightweightWindowEvent;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBScrollPane;
import notes.service.controller.GenericNoteService;
import notes.service.model.Note;
import notes.service.view.GenericNotePopupService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;

public abstract class GenericNotePopupServiceImpl<T extends Note> implements GenericNotePopupService<T> {

    abstract GenericNoteService<T> getService();

    abstract String createTitle(T note);

    private void setProjectAndModules(Project project, Module[] modules) {
        getService().setProjectAndModules(project, modules);
    }

    private void deleteNote(T note) {
        getService().deleteNote(note);
    }

    private void putNote(T note){
        getService().putNote(note);
    };

    @Override
    public void create(AnActionEvent e, T note) {
        Project project = e.getProject();
        if (project == null) return;
        Module[] modules = ModuleManager.getInstance(project).getModules();
        setProjectAndModules(project, modules);

        Editor editor = e.getData(CommonDataKeys.EDITOR);

        final JBPopupFactory jbPopupFactory = JBPopupFactory.getInstance();
        PopupContent popupContent = createContent(note);
        JBPopup jbPopup = jbPopupFactory.createComponentPopupBuilder(popupContent.mainPanel, popupContent.noteText)
                .setResizable(false)
                .setTitle(createTitle(note))
                .setMovable(true)
                .setFocusable(true)
                .setRequestFocus(true)
                .createPopup();

        registerListeners(popupContent, jbPopup, note);

        if (editor != null) {
            jbPopup.showInBestPositionFor(editor);
        } else {
            jbPopup.showInFocusCenter();
        }
    }

    private class PopupContent {
        JPanel mainPanel;
        JTextArea noteText;
        JButton closeButton;
        JButton editButton;
        JButton deleteButton;
        Boolean editted = false;

        PopupContent(JPanel _mainPanel, JTextArea _noteText, JButton _closeButton, JButton _editButton, JButton _deleteButton) {
            mainPanel = _mainPanel;
            noteText = _noteText;
            closeButton = _closeButton;
            editButton = _editButton;
            deleteButton = _deleteButton;
        }
    }

    private PopupContent createContent(T note) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        JTextArea noteTextArea = new JTextArea(20, 50);

        Boolean editable = note.content.isEmpty();

        noteTextArea.setText(note.content);
        noteTextArea.setColumns(75);
        noteTextArea.setLineWrap(true);
        noteTextArea.setEnabled(editable);

        JBScrollPane scrollPane = new JBScrollPane(noteTextArea);
        mainPanel.add(scrollPane);

        JPanel buttonGroup = new JPanel();

        JButton closeButton = new JButton();
        closeButton.setText("Close");
        buttonGroup.add(closeButton);

        JButton editButton = new JButton();
        editButton.setText("Edit");
        buttonGroup.add(editButton);

        JButton deleteButton = new JButton();
        deleteButton.setText("Delete");
        deleteButton.setBackground(JBColor.RED);
        buttonGroup.add(deleteButton);
        mainPanel.add(buttonGroup);

        return new PopupContent(mainPanel, noteTextArea, closeButton, editButton, deleteButton);
    }

    private interface PopupActions {
        void close(ActionEvent event);
    }

    private void registerListeners(PopupContent popupContent, JBPopup jbPopup, T note) {

        popupContent.noteText.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                popupContent.editted = true;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                popupContent.editted = true;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        PopupActions popupActions = new PopupActions() {
            @Override
            public void close(ActionEvent event) {
                jbPopup.closeOk(null);
            }
        };

        popupContent.closeButton.addActionListener(popupActions::close);

        popupContent.editButton.addActionListener(e -> popupContent.noteText.setEnabled(!popupContent.noteText.isEnabled()));

        popupContent.deleteButton.addActionListener(e -> {
            deleteNote(note);
            popupActions.close(e);
        });

        jbPopup.addListener(new JBPopupListener.Adapter() {
            @Override
            public void onClosed(LightweightWindowEvent event) {
                note.content = popupContent.noteText.getText();
                if (popupContent.editted) putNote(note);
            }
        });
    }
}
