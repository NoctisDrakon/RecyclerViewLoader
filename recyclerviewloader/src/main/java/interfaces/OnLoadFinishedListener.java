package interfaces;

import java.util.ArrayList;

public interface OnLoadFinishedListener<T> {
    public void onLoadFinished(ArrayList<T> result);
    public void onLoadError(Exception e);
}
