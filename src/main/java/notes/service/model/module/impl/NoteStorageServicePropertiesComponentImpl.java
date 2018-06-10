package notes.service.model.module.impl;

import com.intellij.openapi.module.Module;
import notes.service.model.Note;
import notes.service.model.module.NoteStorageService;

import java.util.Date;

public class NoteStorageServicePropertiesComponentImpl extends PropertiesComponentStore<Note> implements NoteStorageService {
    public NoteStorageServicePropertiesComponentImpl(Module module) {
        super(module, "note");
    }

    public String getModulePath() {
        return modulePath;
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
    public Note getNote(String path) {
        final String key = getKey(path);
        return getValue(key);
    }

    private String getKey(String path) {
        return path;
    }

    @Override
    public Note[] getNotes() {
return null;    }

    @Override
    public void putNote(String path, String content) {
        final String key = getKey(path);
        final Note note = new Note(path, content);
        setValue(key, note);
    }

    @Override
    public void deleteNote(String path) {
        final String key = getKey(path);
        deleteValue(key);
    }
}
