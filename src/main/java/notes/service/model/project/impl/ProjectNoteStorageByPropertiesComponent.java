package notes.service.model.project.impl;

import com.intellij.openapi.project.Project;
import notes.service.model.Note;
import notes.service.model.project.ProjectNoteStorageService;

public class ProjectNoteStorageByPropertiesComponent implements ProjectNoteStorageService{

    public ProjectNoteStorageByPropertiesComponent(Project project){

    }

    @Override
    public Note getNote(String path, int lineNo) {
        return null;
    }

    @Override
    public Note[] getNotes() {
        return new Note[0];
    }

    @Override
    public void putNote(String path, int lineNo, String note) {

    }

    @Override
    public void deleteNote(String path, int lineNo) {

    }
}
