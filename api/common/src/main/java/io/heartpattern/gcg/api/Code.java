package io.heartpattern.gcg.api;

/**
 * Data class that hold generated source code.
 */
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

    /**
     * Get package name of code. This information is used to determine the location of source file.
     * @return Package name
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * Get file name of code. This information is used to determine the name of source file.
     * @return File name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Get source code content. This information is used t odetermine the content of source file.
     * @return Source code content
     */
    public String getContent() {
        return content;
    }
}
