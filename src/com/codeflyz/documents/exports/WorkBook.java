/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codeflyz.documents.exports;

import com.codeflyz.documents.Project;
import com.codeflyz.documents.units.Constants;
import com.codeflyz.documents.units.FormatUtils;
import docdat.id.PseudoElement;

/**
 *
 * @author wilfongj
 */
public class WorkBook {

    public WorkBook() {
    }

    public String formatWorkBookStart() {
        return Constants.WorkSheetParts.WorkBookStart;

    }

    public String formatWorkSheetStart(String sheetName) {
        return Constants.WorkSheetParts.WorkSheetStart.replace(Constants.Replace.WorksheetName, sheetName);

    }

    public String formatTopTitleLine(PseudoElement[] ES) {
        String rc = Constants.WorkSheetParts.TopTitleLine;
        return getLine(rc, ES);
    }

    public String formatDateLine(PseudoElement[] ES) {
        String rc = Constants.WorkSheetParts.DateLine;
        return getLine(rc, ES);
    }

    public String formatDescriptionLine(PseudoElement[] ES) {
        String rc = Constants.WorkSheetParts.DescriptionLine;
        return getLine(rc, ES);
    }

    public String formatTopicLine(PseudoElement[] ES) {
        String rc = Constants.WorkSheetParts.TopicLine;
        return getLine(rc, ES);
    }

    public String formatTopicChildLine(PseudoElement[] ES) {
        String rc = Constants.WorkSheetParts.TopicChildLine;
        return getLine(rc, ES);
    }

    public String formatSubTopicLine(PseudoElement[] ES) {
        String rc = Constants.WorkSheetParts.SubTopicLine;
        return getLine(rc, ES);
    }

    public String formatSubTopicChildLine(PseudoElement[] ES) {
        String rc = Constants.WorkSheetParts.SubTopicChildLine;
        return getLine(rc, ES);
    }

    public String formatDefinitionLine(PseudoElement[] ES) {
        String rc = Constants.WorkSheetParts.DefinitionLine;
        return getLine(rc, ES);
    }

    public String formatDefinitionChildLine(PseudoElement[] ES) {
        String rc = Constants.WorkSheetParts.DefinitionChildLine;
        return getLine(rc, ES);
    }

    public String formatAttributeLine(PseudoElement[] ES) {
        String rc = Constants.WorkSheetParts.AttributeLine;
        return getLine(rc, ES);
    }

    public String formatAttributeChildLine(PseudoElement[] ES) {
        String rc = Constants.WorkSheetParts.AttributeLine;
        return getLine(rc, ES);
    }

    public String formatNormalLine(PseudoElement[] ES) {
        String rc = Constants.WorkSheetParts.NormalLine;
        System.out.println("formatNormalLine " + getLine(rc, ES));
        //System.out.println("normal end");
        return getLine(rc, ES);
    }

    public String getLine(String line, PseudoElement[] ES) {

        for (int i = 0; i < 3; i++) {
            String rpl = "[column" + (i + 1) + "]";
            PseudoElement E = ES[i];
            if (E != null) {
                if (i == 0) {

                    line = line.replace(rpl, new FormatUtils().getSingular(E.getName().toLowerCase()));
                } else {
                    line = line.replace(rpl, E.getName());
                }
            } else {
                line = line.replace(rpl, "");
            }
        }
        return line;
    }

    public String formatWorkSheetEnd() {
        return Constants.WorkSheetParts.WorkSheetEnd;
    }

    public String formatWorkBookEnd() {
        return Constants.WorkSheetParts.WorkBookEnd;
    }
}
