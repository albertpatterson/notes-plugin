package notes.service.controller.impl;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleServiceManager;
import com.intellij.openapi.project.Project;
import notes.service.controller.NoteService;
import notes.service.model.LineNote;
import notes.service.model.module.LineNoteStorageService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class NoteServiceImpl implements NoteService {

    private LineNoteStorageService[] lineNoteStorageServices;

    public void setProjectAndModules(Project project, Module[] modules){
        System.out.println("setProjectAndModules project");
        System.out.println(project);

        System.out.println("setProjectAndModules modules");
        ArrayList<LineNoteStorageService> lineNoteStorageServiceArrayList = new ArrayList<>();
        Arrays.stream(modules).forEach(m-> {
            System.out.println(m.getName());
            lineNoteStorageServiceArrayList.add(ModuleServiceManager.getService(m, LineNoteStorageService.class));
        });
        lineNoteStorageServices = lineNoteStorageServiceArrayList.toArray(new LineNoteStorageService[lineNoteStorageServiceArrayList.size()]);
        Arrays.sort(lineNoteStorageServices, new StorageServiceComparator());
        Arrays.stream(lineNoteStorageServices).forEach(s->System.out.println(s.getModulePath()));
    }

    private class StorageServiceComparator implements Comparator<LineNoteStorageService> {
        @Override
        public int compare(LineNoteStorageService service1, LineNoteStorageService service2) {
            return service2.getModulePath().compareTo(service1.getModulePath());
        }
    }

    private LineNoteStorageService chooseMostSpecificModuleService(String path) throws Exception {
        return Arrays.stream(lineNoteStorageServices)
                .filter(s->path.startsWith(s.getModulePath()))
                .findFirst()
                .orElseThrow(()->new Exception("No module found containing "+path));
    }

    public LineNote getNote(String path, int lineNo) {
        try {
            // todo: cache most specific service curently called many times
            return chooseMostSpecificModuleService(path).getLineNote(path, lineNo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public LineNote[] getNotes(){
        return Arrays.stream(lineNoteStorageServices).flatMap(s -> Arrays.stream(s.getLineNotes())).toArray(LineNote[]::new);
    }

    public String getNoteContent(String path, int lineNo){
        final LineNote lineNote = getNote(path, lineNo);
        return (lineNote==null)?null:lineNote.content;
    }

    public void putNote(String path, int lineNo, String content){
        try {
            chooseMostSpecificModuleService(path).putLineNote(path, lineNo, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void deleteNote(String path, int lineNo) {
        try {
            chooseMostSpecificModuleService(path).deleteLineNote(path, lineNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
