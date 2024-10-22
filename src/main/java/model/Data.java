package model;

public class Data {

    private  int id;
    private  String email;
    private  String fileName;
    private  String path;

    public Data(int id, String email, String fileName, String path) {
        this.id = id;
        this.email = email;
        this.fileName = fileName;
        this.path = path;
    }

    public Data(int id, String fileName, String path) {
        this.id = id;
        this.fileName = fileName;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
