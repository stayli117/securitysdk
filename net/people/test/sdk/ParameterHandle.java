package net.people.test.sdk;

public abstract class ParameterHandle<T> {


    protected boolean isOpetion;
    protected boolean isBase64;
    protected boolean isSave;

    protected int index;
    protected T value;
    private String saveKey;

    public ParameterHandle() {
        isOpetion = false;
        isBase64 = false;
        isSave = false;
        index = 0;
    }


    abstract void apply(int p, Request.Builder builder, T value);

    public void setSaveKey(String saveKey) {
        this.saveKey = saveKey;
    }

    public String getSaveKey() {
        return saveKey;
    }

    static final class NoSecurity extends ParameterHandle<Object> {
        @Override
        void apply(int p, Request.Builder builder, Object value) {
            this.value = value;
            builder.addParameterHandle(p, this);
        }
    }

}
