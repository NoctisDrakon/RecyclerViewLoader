package interfaces;

/**
 * Created by NoctisDrakon on 22/05/2017.
 */

public interface OnRefreshEventListener {
    public static final int REFRESH = 0;
    public static final int RELOAD = 1;

    void onRefreshEvent(int type);
}
