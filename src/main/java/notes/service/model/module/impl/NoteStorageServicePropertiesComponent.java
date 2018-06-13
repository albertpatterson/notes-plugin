package notes.service.model.module.impl;

import com.intellij.openapi.module.Module;
import notes.service.model.Note;
import notes.service.model.module.NoteStorageService;

import java.util.Date;

public class NoteStorageServicePropertiesComponent extends PropertiesComponentStorageService<Note> implements NoteStorageService {
    public NoteStorageServicePropertiesComponent(Module module) {
        super(module, "note");
    }

    @Override
    String createUniqueKey(Note note) {
        return note.filepath;
    }

    @Override
    Note decode(String encoding) {
        if (encoding == null) return null;

        String[] parts = encoding.split(":");
        final String filepath = parts[0];
        final Date updated = new Date(Long.parseLong(parts[1]));
        final String content = parts[2];
        return new Note(filepath, content, updated);
    }

    @Override
    String encode(Note note) {
        return String.join(":",
                new String[]{
                        note.filepath,
                        String.valueOf(note.updated.getTime()),
                        note.content});
    }

    @Override
    public Note getNote(String key) {
        if(!key.matches("([^:]*)")){
            System.out.println("Invalid key for note: "+key);
            return null;
        }
        return getValue(key);
    }

    @Override
    public Note[] getNotes() {
        return getValuesStream().toArray(Note[]::new);
    }

    @Override
    public void putNote(Note note) {
        setValue(note);
    }

    @Override
    public void deleteNote(Note note) {
        deleteValue(note);
    }
}
