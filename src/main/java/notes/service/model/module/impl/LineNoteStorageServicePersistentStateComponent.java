package notes.service.model.module.impl;

import com.intellij.openapi.components.State;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleServiceManager;
import notes.service.model.LineNote;
import notes.service.model.module.LineNoteStorageService;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

@State(
        name = "LineNoteStorageServicePersistentStateComponent"
)
public class LineNoteStorageServicePersistentStateComponent extends PersistentStateComponentStorageService<LineNote> implements LineNoteStorageService {


    static LineNoteStorageServicePersistentStateComponent getInstance(@NotNull Module project) {
        return ModuleServiceManager.getService(project, LineNoteStorageServicePersistentStateComponent.class);
    }


    public LineNoteStorageServicePersistentStateComponent(Module module) {
        super(module, "line-note");
    }

    @Override
    String createUniqueKey(LineNote lineNote) {
        return lineNote.filepath + ":" + lineNote.lineNo;
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
    public LineNote getNote(String key) {
        if(!key.matches("([^:]*):(\\d*)")){
            System.out.println("Invalid key for Line note: "+key);
            return null;
        }
        return getValue(key);
    }

    @Override
    public LineNote[] getNotes() {
        return getValuesStream().toArray(LineNote[]::new);
    }

    @Override
    public void putNote(LineNote lineNote) {
        setValue(lineNote);
    }

    @Override
    public void deleteNote(LineNote lineNote) {
        deleteValue(lineNote);
    }
}

