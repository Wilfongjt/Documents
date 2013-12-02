/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codeflyz.documents.units;

/**
 *
 * @author wilfongj
 */
public class FormatUtils {

    public String getSingular(String name) {
        String rc = name;
        if (name.endsWith("s")) {
            rc = name.substring(0, name.length() - 1);
        }
        if (name.endsWith("es")) {
            rc = name.substring(0, name.length() - 2);
        }
        if (name.endsWith("ces")) {
            rc = name.substring(0, name.length() - 3);
            rc += "ce";
        }
        if (name.endsWith("ies")) {
            rc = name.replace("ies", "y");
        }
        return rc;
    }
}
