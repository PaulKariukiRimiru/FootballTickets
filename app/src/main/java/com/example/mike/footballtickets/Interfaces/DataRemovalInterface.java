package com.example.mike.footballtickets.Interfaces;

import com.example.mike.footballtickets.Pojo.IMainObject;

/**
 * Created by Mike on 2/15/2017.
 */

public interface DataRemovalInterface {
    void removeObject(IMainObject object, int position);
    void addPrice(IMainObject object, int position);
}
