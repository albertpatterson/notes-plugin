package notes.service.controller.impl;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import notes.service.controller.GenericNoteService;
import notes.service.model.Note;
import notes.service.model.module.GenericNoteStorageService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.WeakHashMap;
import java.util.stream.Stream;

public abstract class GenericNoteServiceImpl<T extends Note, U extends GenericNoteStorageService<T>> implements GenericNoteService<T>{

    /**
     * Get the storage service for a particular module
     * @param module the module to which the corresponding file belongs
     * @return the service that stores and retrieves the note data
     */
    abstract U getStorageService(Module module);

    /**
     * Get the path to the file corresponding to a note, based on the key used to store the note
     * @param key the key that can be used to look up the note from the storage serviceB
     * @return the path to the file corresponding to a note
     */
    abstract String getPath(String key);

    private final ArrayList<U> storageServices = new ArrayList<>();
    private final WeakHashMap<String, U> storageServiceCache = new WeakHashMap<>();

    @Override
    public void setProjectAndModules(Project project, Module[] modules){

        System.out.println("setProjectAndModules project");
        System.out.println(project);

        System.out.println("setProjectAndModules modules");
        setupStorageServices(modules);
    }

    @Override
    public T getNote(String key) {
        try {
            return getService(getPath(key)).getNote(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private U getService(String path) throws Exception {
        if(storageServiceCache.containsKey(path)) return storageServiceCache.get(path);

        U service = chooseMostSpecificModuleService(path);

        storageServiceCache.put(path, service);
        return service;
    }

    private U chooseMostSpecificModuleService(String path) throws Exception {
        return storageServices.stream()
                .filter(s->path.startsWith(s.getModulePath()))
                .findFirst()
                .orElseThrow(()->new Exception("No module found containing "+path));
    }

    private void setupStorageServices(Module[] modules) {

        storageServices.clear();

        Arrays.stream(modules).forEach(m-> {
                System.out.println(m.getModuleFilePath());
                storageServices.add(getStorageService(m));
        });

        storageServices.sort((s1, s2)->s2.getModulePath().compareTo(s1.getModulePath()));
    }

    @Override
    public Stream<T> getNotesStream() {
        return storageServices
                .stream()
                .flatMap(s -> Arrays.stream(s.getNotes()));
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
