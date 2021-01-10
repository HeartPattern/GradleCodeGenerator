package io.heartpattern.gcg.api;

public final class Code {
    private final String packageName;
    private final String fileName;
    private final String content;
    public Code(
            String packageName,
            String fileName,
            String content
    ){
        this.packageName = packageName;
        this.fileName = fileName;
        this.content = content;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getFileName() {
        return fileName;
    }

    public String getContent() {
        return content;
    }
}
