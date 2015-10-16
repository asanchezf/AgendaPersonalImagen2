package activitys;

import android.support.annotation.NonNull;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by Susana on 16/10/2015.
 */

//Clase que controla eventos de movimiento del FAB
public class OnScrollUpDownListener implements AbsListView.OnScrollListener{

    private int previousScrollPosition;
    private int previousItemPosition;
    private int minDistance;
    private ListView listView;
    private Action action;

    public interface Action {
        void up();

        void down();
    }

    public OnScrollUpDownListener(@NonNull ListView lista, int minDistance, @NonNull Action action) {
        this.listView = lista;
        this.minDistance = minDistance;
        this.action = action;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //nothing here
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int currentScrollPosition = getCurrentScrollPosition();
        if (firstVisibleItem == previousItemPosition) {
            int scrolled = Math.abs(previousScrollPosition - currentScrollPosition);
            if (scrolled > minDistance) {
                if (previousScrollPosition > currentScrollPosition) {
                    action.up();
                } else {
                    action.down();
                }
            }
        } else if (firstVisibleItem > previousItemPosition) {
            action.up();
        } else {
            action.down();
        }
        previousScrollPosition = currentScrollPosition;
        previousItemPosition = firstVisibleItem;

    }

    private int getCurrentScrollPosition() {
        int pos = 0;
        if (listView.getChildAt(0) != null) {
            pos = listView.getChildAt(0).getTop();
        }
        return pos;
    }


}
