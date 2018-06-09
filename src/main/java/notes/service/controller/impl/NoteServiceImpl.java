package notes.service.controller.impl;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleServiceManager;
import com.intellij.openapi.project.Project;
import notes.service.controller.NoteService;
import notes.service.model.Note;
import notes.service.model.file.FileNoteStorageService;
import notes.service.model.module.ModuleNoteStorageService;
import notes.service.model.project.ProjectNoteStorageService;

import java.util.ArrayList;
import java.util.Arrays;

public class NoteServiceImpl implements NoteService {

    private FileNoteStorageService fileNoteStorage = ServiceManager.getService(FileNoteStorageService.class);
    private ProjectNoteStorageService projectNoteStorageService;
    private ModuleNoteStorageService[] moduleNoteStorageServices;

    public void setProjectAndModules(Project project, Module[] modules){
        System.out.println("setProjectAndModules project");
        System.out.println(project);
        projectNoteStorageService = ServiceManager.getService(project, ProjectNoteStorageService.class);
        System.out.println(projectNoteStorageService);

        System.out.println("setProjectAndModules modules");
        ArrayList<ModuleNoteStorageService> moduleNoteStorageServiceArrayList = new ArrayList<>();
        Arrays.stream(modules).forEach(m-> {
            System.out.println(m.getName());
            moduleNoteStorageServiceArrayList.add(ModuleServiceManager.getService(m, ModuleNoteStorageService.class));
        });
        moduleNoteStorageServices = moduleNoteStorageServiceArrayList.toArray(new ModuleNoteStorageService[moduleNoteStorageServiceArrayList.size()]);
        Arrays.stream(moduleNoteStorageServices).forEach(System.out::println);
    }

    public Note getNote(String path, int lineNo) {
        return fileNoteStorage.getNote(path, lineNo);
    }

    public Note[] getNotes(){
        return fileNoteStorage.getNotes();
    }

    public String getNoteContent(String path, int lineNo){
        final Note note = getNote(path, lineNo);
        return (note==null)?null:note.content;
    }

    public void putNote(String path, int lineNo, String content) {
        fileNoteStorage.putNote(path, lineNo, content);
    }

    public void deleteNote(String path, int lineNo){
        fileNoteStorage.deleteNote(path, lineNo);
    }
}
