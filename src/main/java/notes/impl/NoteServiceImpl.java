package notes.impl;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;
import notes.NoteService;
import notes.Note;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class NoteServiceImpl implements NoteService {

    // todo: use PersistentStateComponent
    private PropertiesComponent propertiesComponent;

    private ArrayList<String> keys;

    public NoteServiceImpl(Project project) {
        propertiesComponent = PropertiesComponent.getInstance(project);
        keys = getKeys();
    }

    public Note getNote(String path, int lineNo) {
        final String key = getKey(path, lineNo);
        return getNote(key);
    }

    private Note getNote(String key) {
        String encodedNote = propertiesComponent.getValue(key);
        return decodeNote(encodedNote);
    }

    public Note[] getNotes(){
        return keys.stream().map(k -> getNote(k)).toArray(Note[]::new);
    }

    public String getNoteContent(String path, int lineNo){
        final Note note = getNote(path, lineNo);
        return (note==null)?null:note.content;
    }

    public void putNote(String path, int lineNo, String content) {
        final String key = getKey(path, lineNo);
        Note note = new Note(path, lineNo, content);
        String encodedNote = encodeNote(note);
        propertiesComponent.setValue(key, encodedNote);

        if(!keys.contains(key)) {
            keys.add(key);
            updateKeys();
        }
    }

    public void deleteNote(String path, int lineNo){
        final String key = getKey(path, lineNo);
        propertiesComponent.unsetValue(key);

        keys.remove(key);
        updateKeys();
    }

    private String getKey(String path, int lineNo) {
        return "notes-plugin-"+path+":"+lineNo;
    }

    private String encodeNote(Note note){
        return String.join(":",
                    new String[]{
                            note.filepath,
                            String.valueOf(note.lineNo),
                            String.valueOf(note.updated.getTime()),
                            note.content});
    }

    private Note decodeNote(String encodedNote){
        if(encodedNote==null) return null;

        String[] parts = encodedNote.split(":");
        final String filepath = parts[0];
        final int lineNo = Integer.parseInt(parts[1]);
        final Date updated = new Date(Long.parseLong(parts[2]));
        final String content = parts[3];
        return new Note(filepath, lineNo, content, updated);
    }

    private ArrayList<String> getKeys(){
        String[] keys = propertiesComponent.getValues("notes-plugin-keys");
        if(keys==null){
            keys=new String[0];
            propertiesComponent.setValues("notes-plugin-keys", keys);
        }
        return new ArrayList<>(Arrays.asList(keys));
    }

    private void updateKeys(){
        propertiesComponent.setValues("notes-plugin-keys", keys.toArray(new String[keys.size()]));
    }
}
