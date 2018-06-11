package notes.service.model.module;

import notes.service.model.Note;

public interface GenericNoteStorageService<T extends Note> {

    T getNote(String path);

    T[] getNotes();

    void putNote(T note);

    void deleteNote(T note);

    String getModulePath();
}
