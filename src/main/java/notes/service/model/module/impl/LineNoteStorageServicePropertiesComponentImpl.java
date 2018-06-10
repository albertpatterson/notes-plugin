package notes.service.model.module.impl;

import com.intellij.openapi.module.Module;
import notes.service.model.LineNote;
import notes.service.model.module.LineNoteStorageService;

import java.util.Date;

public class LineNoteStorageServicePropertiesComponentImpl extends PropertiesComponentStore<LineNote> implements LineNoteStorageService {

    public LineNoteStorageServicePropertiesComponentImpl(Module module) {
        super(module, "line-note");
    }

    public String getModulePath() {
        return modulePath;
    }

    @Override
    LineNote decode(String encoding) {
        if (encoding == null) return null;

        String[] parts = encoding.split(":");
        final String filepath = parts[0];
        final int lineNo = Integer.parseInt(parts[1]);
        final Date updated = new Date(Long.parseLong(parts[2]));
        final String content = parts[3];
        return new LineNote(filepath, lineNo, content, updated);
    }

    @Override
    String encode(LineNote lineNote) {
        return String.join(":",
                new String[]{
                        lineNote.filepath,
                        String.valueOf(lineNote.lineNo),
                        String.valueOf(lineNote.updated.getTime()),
                        lineNote.content});
    }

    @Override
    public LineNote getLineNote(String path, int lineNo) {
        final String key = getKey(path, lineNo);
        return getValue(key);
    }

    private String getKey(String path, int lineNo) {
        return path + ":" + lineNo;
    }

    @Override
    public LineNote[] getLineNotes() {
        return getValuesStream().toArray(LineNote[]::new);
    }


    @Override
    public void putLineNote(String path, int lineNo, String content) {
        final String key = getKey(path, lineNo);
        final LineNote lineNote = new LineNote(path, lineNo, content);
        setValue(key, lineNote);
    }

    @Override
    public void deleteLineNote(String path, int lineNo) {
        final String key = getKey(path, lineNo);
        deleteValue(key);
    }
}
