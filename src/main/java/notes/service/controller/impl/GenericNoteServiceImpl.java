package notes.service.controller.impl;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import notes.service.controller.GenericNoteService;
import notes.service.model.Note;
import notes.service.model.module.GenericNoteStorageService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public abstract class GenericNoteServiceImpl<T extends Note, U extends GenericNoteStorageService<T>> implements GenericNoteService<T>{

    abstract U getStorageService(Module m);

    abstract String getPath(String Key);

    private ArrayList<U> storageServices = new ArrayList<>();

    @Override
    public void setProjectAndModules(Project project, Module[] modules){
        System.out.println("setProjectAndModules project");
        System.out.println(project);

        System.out.println("setProjectAndModules modules");
        storageServices.clear();
        Arrays.stream(modules).forEach(m-> {
            System.out.println(m.getName());
            storageServices.add(getStorageService(m));
        });

        storageServices.sort((s1, s2)->s2.getModulePath().compareTo(s1.getModulePath()));

        storageServices.stream().map(U::getModulePath).forEach(System.out::println);
    }

    @Override
    public T getNote(String key) {
        try {
            // todo: cache most specific service curently called many times
            return chooseMostSpecificModuleService(getPath(key)).getNote(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private U chooseMostSpecificModuleService(String path) throws Exception {
        return storageServices.stream()
                .filter(s->path.startsWith(s.getModulePath()))
                .findFirst()
                .orElseThrow(()->new Exception("No module found containing "+path));
    }

    @Override
    public Stream<T> getNotesStream() {
        return storageServices.stream().flatMap(s -> Arrays.stream(s.getNotes()));
    }

    @Override
    public void putNote(T note) {
        try {
            chooseMostSpecificModuleService(note.filepath).putNote(note);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteNote(T note) {
        try {
            chooseMostSpecificModuleService(note.filepath).deleteNote(note);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
