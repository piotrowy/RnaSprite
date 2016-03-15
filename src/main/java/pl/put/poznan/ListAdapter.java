/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.put.poznan;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author piotrowy
 */
public class ListAdapter extends XmlAdapter<ListAdapter.AdaptedList, List<List<? extends Row>>> {

    public static class AdaptedList {

        public List<Model> model = new ArrayList<>();

    }

    public static class Model {
        
        public List<? extends Row> value;

    }

    @Override
    public List<List<? extends Row>> unmarshal(AdaptedList adaptedList) throws Exception {
        List<List<? extends Row>> list = new ArrayList<>();
        list.addAll(adaptedList.model.stream().map(entry -> entry.value).collect(Collectors.toList()));
        return list;
    }

    @Override
    public AdaptedList marshal(List<List<? extends Row>> list) throws Exception {
        AdaptedList adaptedList = new AdaptedList();
        for(List<? extends Row> listModel : list) {
            Model entry = new Model();
            entry.value = listModel;
            adaptedList.model.add(entry);
        }
        return adaptedList;
    }

}
