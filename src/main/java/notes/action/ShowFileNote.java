package notes.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.vfs.VirtualFile;

public class ShowFileNote extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {

        String path = getTargetPath(e);
        System.out.println(path);
    }

    @Override
    public void update(AnActionEvent e) {

        String path = getTargetPath(e);
        if(path==null) return;

        if (path.equals("/Users/apatterson/Documents/personal/code/plugin-test/second-module")) {
            return;
        }

        Presentation presentation = e.getPresentation();
        presentation.setText("Add Note");
        presentation.setDescription("Add a note about this item");
    }

    private String getTargetPath(AnActionEvent event) {
        VirtualFile virtualFile = event.getData(CommonDataKeys.VIRTUAL_FILE);
        if(virtualFile==null) return null;

        String path = virtualFile.getPath();
        return path;
    }

}
