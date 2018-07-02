package notes.service.controller;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import notes.service.model.Note;

import java.util.stream.Stream;

/**
 * A service for retrieving and updating note data
 * @param <T> The type of note data
 */
public interface GenericNoteService<T extends Note> {

    /**
     * Set the project and underlying modules, required to initialize the service
     * @param project the Intellij project
     * @param modules the modules included in the project
     */
    void setProjectAndModules(Project project, Module[] modules);

    /**
     * Retrieve a note
     * @param key the key corresponding to the note
     * @return the note
     */
    T getNote(String key);

    /**
     * get a Stream supplying all available notes about items in the project
     * @return Stream supplying all available notes about items in the project
     */
    Stream<T> getNotesStream();

    /**
     * add a note
     * @param note note to be added
     */
    void putNote(T note);

    /**
     * delete a note
     * @param note note to delete
     */
    void deleteNote(T note);
}
