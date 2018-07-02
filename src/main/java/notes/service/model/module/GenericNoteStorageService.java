package notes.service.model.module;

import notes.service.model.Note;

/**
 * A service for retrieving and updating stored note data
 * @param <T> the type of note data
 */
public interface GenericNoteStorageService<T extends Note> {

    /**
     * Retrieve a note from storage
     * @param key the key corresponding to the note
     * @return the note
     */
    T getNote(String key);

    /**
     * get all stored notes for items belonging to the corresponding module
     * @return
     */
    T[] getNotes();

    /**
     * save a note to storage
     * @param note the note to save
     */
    void putNote(T note);

    /**
     * delete a note from storage
     * @param note the note to delete
     */
    void deleteNote(T note);

    /**
     * get the path to the corresponding module
     * @return the path to the corresponding module
     */
    String getModulePath();
}
