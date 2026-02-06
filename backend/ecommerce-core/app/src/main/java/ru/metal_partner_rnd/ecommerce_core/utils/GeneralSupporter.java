package ru.metal_partner_rnd.ecommerce_core.utils;

import ru.metal_partner_rnd.ecommerce_core.utils.AuxiliaryFilesIntroducer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class GeneralSupporter implements AuxiliaryFilesIntroducer {

    public ArrayList<String> getFilesToInsert(String base) {
        ArrayList<String> files = new ArrayList<String>();

        files.add(base + "Content");

        return files;
    }

    public ArrayList<String> getJs(Integer type, String base) {
        ArrayList<String> pageJs = new ArrayList<String>();

        switch (type) {
            case 0: //start
                pageJs.add("home");
                break;
            case 1: //usual page
                pageJs.add("popUp");
                break;
            default: //other
                break;
        }

        return pageJs;
    }



    // public static void Construct(String cssFiles, String jsFiles) {

    //     Page pageFiles = new Page

    //     StringTokenizer cssTokenizer = new StringTokenizer(cssFiles);
    //     while (cssTokenizer.hasMoreTokens()) {
    //         this.css.add(cssTokenizer.nextToken());
    //     }
        
    //     StringTokenizer jsTokenizer = new StringTokenizer(jsFiles);
    //     while (jsTokenizer.hasMoreTokens()) {
    //         this.js.add(jsTokenizer.nextToken());
    //     }
    // }
}
