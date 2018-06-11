package notes.service.controller;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import notes.service.model.Note;

import java.util.stream.Stream;

public interface GenericNoteService<T extends Note> {

    void setProjectAndModules(Project project, Module[] modules);

    T getNote(String key);

    Stream<T> getNotesStream();

    void putNote(T note);

    void deleteNote(T note);
}
