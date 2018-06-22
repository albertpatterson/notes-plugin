package notes.service.model.module.impl;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.module.Module;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public abstract class PersistentStateComponentStorageService<T> implements PersistentStateComponent<StoredItem> {

    private StoredItem storedItem;

    abstract String createUniqueKey(T t);

    abstract String encode(T t);

    abstract T decode(String encoding);

    private String moduleDataKey;
    private String modulePath;

    PersistentStateComponentStorageService(Module module, String dataId) {
        modulePath = getParentDirectory(module);
        moduleDataKey = "notes-plugin-keys-" + dataId + "-" + modulePath;
        storedItem = new StoredItem();
    }

    private String getParentDirectory(Module module){
        File file = new File(module.getModuleFilePath());
        return file.getParentFile().getAbsolutePath();
    }

    public String getModulePath() {
        return modulePath;
    }

    private String makeKey(String suffix) {
        return moduleDataKey +"-"+suffix;
    }

    Stream<T> getValuesStream() {
        if(storedItem.values == null) return Stream.of();
        return storedItem.values.keySet().stream().map(this::getValueFromFullKey);
    }

    T getValue(String suffix) {
        final String key = makeKey(suffix);
        return getValueFromFullKey(key);
    }

    private T getValueFromFullKey(String key) {
        if(storedItem.values == null) return null;
        final String encoding = storedItem.values.get(key);
        if(encoding==null) return null;
        return decode(encoding);
    }

    void setValue(T t) {
        final String key = makeKey(createUniqueKey(t));
        String encoding = encode(t);
        storedItem.values.put(key, encoding);
    }

    void deleteValue(T t) {
        final String key = makeKey(createUniqueKey(t));
        storedItem.values.remove(key);
    }

    @Nullable
    @Override
    public StoredItem getState() {
        return storedItem;
    }

    @Override
    public void loadState(StoredItem loadedStoredItem) {
        XmlSerializerUtil.copyBean(loadedStoredItem, storedItem);
    }
}

class StoredItem{
    public Map<String, String> values;

    StoredItem(){
        values = new HashMap<>();
    }

    public Map<String, String> getValues(){
        return values;
    };

    public void setValues(Map<String, String> newValues){
        values = newValues;
    };
}
