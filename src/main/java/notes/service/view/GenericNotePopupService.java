package notes.service.view;

import com.intellij.openapi.actionSystem.AnActionEvent;
import notes.service.model.Note;

/**
 * Service for showing a popup for viewing, editing, or deleting a note
 * @param <T> the type of note
 */
public interface GenericNotePopupService <T extends Note> {

    /**
     * show a popup for viewing, editing, or deleting a note
     * @param anActionEvent the action triggering the creation of the popup
     * @param note the note to display
     */
    void create(AnActionEvent anActionEvent, T note);

}
