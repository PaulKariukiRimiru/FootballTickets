package com.example.mike.footballtickets.Pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mike on 1/29/2017.
 */

public class CartList implements Serializable {
    List<IMainObject> cartobjects;

    public List<IMainObject> getCartobjects() {
        return cartobjects;
    }

    public void setCartobjects(List<IMainObject> cartobjects) {
        this.cartobjects = cartobjects;
    }
}
