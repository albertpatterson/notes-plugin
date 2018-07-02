package notes.service.controller.impl;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleServiceManager;
import notes.service.controller.LineNoteService;
import notes.service.model.LineNote;
import notes.service.model.module.LineNoteStorageService;

public class LineNoteServiceImpl extends GenericNoteServiceImpl<LineNote, LineNoteStorageService> implements LineNoteService {

    @Override
    LineNoteStorageService getStorageService(Module module) {
        return ModuleServiceManager.getService(module, LineNoteStorageService.class);
    }

    @Override
    String getPath(String key) {
        if(!key.matches("([^:]*):(\\d*)")){
            System.out.println("Invalid key for Line note: "+key);
            return null;
        }
        return key.split(":")[0];
    }
}
