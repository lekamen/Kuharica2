package hr.math.android.kuharica;

import java.util.List;

/**
 * Created by mabel on 27-Feb-18.
 */

public class Recept {

    private long id;
    private String imeRecepta;
    private String photoRecept;
    private List<String> sastojci;
    private List<String> upute;
    private String notes;

    public Recept() {}
    public Recept(long id, String imeRecepta, String photoRecept, List<String> sastojci, List<String> upute, String notes) {
        this.id = id;
        this.imeRecepta = imeRecepta;
        this.photoRecept = photoRecept;
        this.sastojci = sastojci;
        this.upute = upute;
        this.notes = notes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImeRecepta() {
        return imeRecepta;
    }

    public void setImeRecepta(String imeRecepta) {
        this.imeRecepta = imeRecepta;
    }

    public String getPhotoRecept() {
        return photoRecept;
    }

    public void setPhotoRecept(String photoRecept) {
        this.photoRecept = photoRecept;
    }

    public List<String> getSastojci() {
        return sastojci;
    }

    public void setSastojci(List<String> sastojci) {
        this.sastojci = sastojci;
    }

    public List<String> getUpute() {
        return upute;
    }

    public void setUpute(List<String> upute) {
        this.upute = upute;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
