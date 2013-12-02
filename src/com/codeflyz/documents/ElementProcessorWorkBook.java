/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codeflyz.documents;

import com.codeflyz.documents.exports.WorkBook;
import com.codeflyz.documents.units.Constants;
import docdat.id.PseudoElement;
import docdat.id.PseudoElements;
import docdat.io.names.ProcessLogger;
import java.util.Date;

/**
 *
 * @author wilfongj
 */
public class ElementProcessorWorkBook extends ElementProcessor {

    public ElementProcessorWorkBook(Project prj, PseudoElements ES) {
        super(prj, ES, new LineEvaluatorWorkBook(prj, ES));

    }
    private String workSheetEnd = "";

    public String getWorkSheetEnd() {
        return workSheetEnd;
    }

    public void setWorkSheetEnd(String workSheetEnd) {
        this.workSheetEnd = workSheetEnd;
    }

    public void process() {

        WorkBook wb = new WorkBook();
        
        for(int i = getIncrementor() .getIndex(); i < getElemList().size(); i++ ) {
            getIncrementor() .setIndex(i);
            PseudoElement E = getElemList().getElement(i);
           /* if (getLineEvaluator().isInRecursives(E)) {
                 System.out.println("[recursions: " + getParameter(E) + "zz]");
               // System.out.println("out main ["+getIncremetor().getIndex()+ "]");
                i=getIncrementor().getIndex();
            }
            //setIndex(i);
            //      
           */ 
            buildThreeColArray(E);
            i=getIncrementor().getIndex();
        }
        
        if (getWorkSheetEnd().length() > 0) {
            // write the end of the last work sheet
            writeLine(getWorkSheetEnd());
            setWorkSheetEnd("");
        }
    }

    public void writeLine(String line) {

        ProcessLogger pl = new ProcessLogger(getProject().getOutputPath(), getProject().getOutputFileName());
        pl.WriteNoDate(line);

    }

    ////////////////////////////////// 
    //this version converts the tree into a three col line
    ////////////////
    public void buildThreeColArray(PseudoElement LE) {
        //System.out.println("buildThreeColArray");
        if (LE == null) {
            return;
        }

        if (getLineEvaluator().isInSkipLineOuput(LE)) {

            setSkipLineOutput(true);

        }

        getLineEvaluator().setElement(LE);
        if (getProject().getFileNameMax() == LE.getLevel()) {

            // write the close of previous worksheek
            // save to string 
            // writeh the start of the next

            ////getProject().deleteOutputFile(getProject().getOutputPath() + getProject().getOutputFileName(getLineEvaluator().getPathElementArray()));

            if (getWorkSheetEnd().length() > 0) {
                // write the end of the previous work sheet
                writeLine(getWorkSheetEnd());
                setWorkSheetEnd("");
            }

            WorkBook wb = new WorkBook();
            writeLine(wb.formatWorkSheetStart(getLineEvaluator().getSheetName()));

            setWorkSheetEnd(wb.formatWorkSheetEnd());

        }

        String line = getLineEvaluator().formatLine();


        if (line != null && !isSkipLineOutput()) { // check for lines that will get ommitted in output

            if (getProject().getFileNameMax() <= LE.getLevel()) {
                if (getProject().getFileNameMax() == LE.getLevel()) {
                    writeTitleBlock();
                }

                writeLine(line);
               // System.out.println(line);

            }
        }

        setSkipLineOutput(false);

    }

    public void writeTitleBlock() {

        WorkBook wb = new WorkBook();
        PseudoElement[] arr = new PseudoElement[3];

        arr[0] = new PseudoElement();
        arr[1] = new PseudoElement();
        arr[2] = new PseudoElement();

        for (int i = 0; i < getElemList().size(); i++) {
            PseudoElement E = getElemList().getElement(i);


            if (E.getLevel() == 1) {
                //System.out.println("Zero: " + E.getIdx());


                /////////  Top Title
                arr[0].setName("title");
                arr[1].setName(getElemList().getElement(0).getName());
                arr[2].setName("titleline");
                ////////  Run Date
                writeLine(wb.formatTopTitleLine(arr));
                arr[0].setName("run-date");
                arr[1].setName(new Date().toString());
                arr[2].setName("dataline");
                writeLine(wb.formatDateLine(arr));


            }

            PseudoElement PE = getElemList().getParent(E);
            PseudoElement PPE = getElemList().getParent(PE);
            if ((PE != null && PPE != null) && PPE.getPosition() == 0
                    && PE.getName().equalsIgnoreCase(Constants.Keywords.Description)) {
                arr[0].setName("description");
                arr[1].setName(E.getName());
                arr[2].setName("");
                writeLine(wb.formatDescriptionLine(arr));

            }

        }//for

    }


}
