package myk.project.Interfaces;

import myk.project.Pojo.IMainObject;

/**
 * Created by Mike on 2/15/2017.
 */

public interface DataRemovalInterface {
    void removeObject(IMainObject object, int position);
    void addPrice(IMainObject object, int position);

    String getUserId();
}
