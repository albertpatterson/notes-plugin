package notes.service.model.module.impl;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.vfs.VirtualFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public abstract class PropertiesComponentStore<T> {

    private String moduleDataKey;
    private String moduleKeysKey;
    protected String modulePath;
    protected PropertiesComponent propertiesComponent;
    private ArrayList<String> keys;

    protected PropertiesComponentStore(Module module, String dataId){

        VirtualFile virtualFile = module.getModuleFile();
        if(virtualFile==null) throw new Error("Module with null virtual file provide");
        modulePath = virtualFile.getParent().getPath();
        propertiesComponent = PropertiesComponent.getInstance(module.getProject());
        moduleDataKey = "notes-plugin-keys-" + dataId + "-" + modulePath;
        moduleKeysKey = moduleDataKey + "-keys";
        keys = getKeys();
    }

    private ArrayList<String> getKeys(){
        String[] keys = propertiesComponent.getValues(moduleKeysKey);
        if(keys==null){
            keys=new String[0];
            propertiesComponent.setValues(moduleKeysKey, keys);
        }
        return new ArrayList<>(Arrays.asList(keys));
    }

    protected void setKeys(String[] keys){
        propertiesComponent.setValues(moduleKeysKey, keys);
    }

    private String makeKey(String suffix) {
        return moduleDataKey +"-"+suffix;
    }

    protected Stream<T> getValuesStream(){
        return keys.stream().map(this::getValueFromFullKey);
    }

    abstract T decode(String encoding);

    protected T getValue(String suffix){
        final String key = makeKey(suffix);
        return getValueFromFullKey(key);
    }

    private T getValueFromFullKey(String key) {
        final String encoding = propertiesComponent.getValue(key);
        if(encoding==null) return null;
        T value = decode(encoding);
        return value;
    }

    protected void setValue(String suffix, T t) {
        final String key = makeKey(suffix);
        String encoding = encode(t);
        propertiesComponent.setValue(key, encoding);
        if(!keys.contains(key)) {
            keys.add(key);
            propertiesComponent.setValues(moduleKeysKey, keys.toArray(new String[keys.size()]));
        }
    }

    abstract String encode(T t);

    protected void deleteValue(String suffix) {
        final String key = makeKey(suffix);
        propertiesComponent.unsetValue(key);
        keys.remove(key);
        propertiesComponent.setValues(moduleKeysKey, keys.toArray(new String[keys.size()]));
    }
}
