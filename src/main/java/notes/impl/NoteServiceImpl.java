package notes.impl;

import com.intellij.ide.util.PropertiesComponent;
import notes.NoteService;
import notes.Note;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Stream;

public class NoteServiceImpl implements NoteService {

    // todo: use PersistentStateComponent
    final private PropertiesComponent propertiesComponent = PropertiesComponent.getInstance();

    private ArrayList<String> keys = getKeys();

    public Note getNote(String path, int lineNo) {
        final String key = getKey(path, lineNo);
        return getNote(key);
    }

    private Note getNote(String key) {
        String encodedNote = propertiesComponent.getValue(key);
        return decodeNote(encodedNote);
    }

    public Stream<Note> getNotes(){
        return keys.stream().map(k -> getNote(k));
    }

    public String getNoteContent(String path, int lineNo){
        final Note note = getNote(path, lineNo);
        return (note==null)?null:note.content;
    }

    public void putNote(String path, int lineNo, String content) {
        final String key = getKey(path, lineNo);
        Note note = new Note(content);
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
        return note.updated.getTime() + ":" + note.content;
    }

    private Note decodeNote(String encodedNote){
        if(encodedNote==null) return null;

        String[] parts = encodedNote.split(":", 2);
        Date updated = new Date(Long.parseLong(parts[0]));
        String content = parts[1];
        return new Note(content, updated);
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
