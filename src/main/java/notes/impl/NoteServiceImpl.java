package notes.impl;

import com.intellij.ide.util.PropertiesComponent;
import notes.NoteService;

public class NoteServiceImpl implements NoteService {

    // todo: use PersistentStateComponent
    final private PropertiesComponent propertiesComponent = PropertiesComponent.getInstance();

    public String getNote(String path, int lineNo) {
        final String key = getKey(path, lineNo);
        return propertiesComponent.getValue(key);
    }

    public void putNote(String path, int lineNo, String note) {
        final String key = getKey(path, lineNo);
        propertiesComponent.setValue(key, note);
    }

    public void deleteNote(String path, int lineNo){
        final String key = getKey(path, lineNo);
        propertiesComponent.unsetValue(key);
    }

    private String getKey(String path, int lineNo) {
        return "record-note-"+path+":"+lineNo;
    }

}
