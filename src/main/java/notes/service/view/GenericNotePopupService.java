package notes.service.view;

import com.intellij.openapi.actionSystem.AnActionEvent;
import notes.service.model.Note;

public interface GenericNotePopupService <T extends Note> {

    void create(AnActionEvent e, T note);

}
