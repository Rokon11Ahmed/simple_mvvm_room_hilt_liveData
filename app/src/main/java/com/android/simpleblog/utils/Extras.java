package com.android.simpleblog.utils;

import com.android.simpleblog.common.models.Author;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class Extras {

    public static String arrayListTOString(List<String> arrayList){
        String output = "";
        for (String s: arrayList){
         output += s+",";
        }
        return output;
    }

    public static ArrayList<String> stringToArrayList(String string){
        ArrayList<String> arrayList = new ArrayList<>();
        String[] array = string.split(",");
        for (String s: array){
            arrayList.add(s);
        }
        return arrayList;
    }
    public static Author stringTOAuthor(String string){
        Author author = new Author();
        String[] array = string.split(",");
        author.setId(Integer.parseInt(array[0]));
        author.setName(array[1]);
        author.setAvatar(array[2]);
        author.setProfession(array[3]);
        return author;
    }
}
