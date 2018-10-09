package com.sorinaidea.ghaichi.model;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import java.util.List;

/**
 * Created by mr-code on 6/28/2018.
 */

public class ServiceList extends ExpandableGroup<Service> {

    public ServiceList(String title, List<Service> items) {
        super(title, items);
    }
}