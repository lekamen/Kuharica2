package hr.math.android.kuharica;

/**
 * Created by ivana on 2/28/18.
 */

public class SelectableRecept extends Recept {
    private boolean isSelected = false;

    public SelectableRecept(Recept recept, boolean isSelected ){
        super(recept.getId(), recept.getImeRecepta(), recept.getPhotoRecept(),
                recept.getSastojci(), recept.getUpute(), recept.getNotes());
        this.isSelected = isSelected;
    }

    public boolean isSelected(){
        return isSelected;
    }

    public void setSelected(boolean selected){
        isSelected = selected;
    }
}
