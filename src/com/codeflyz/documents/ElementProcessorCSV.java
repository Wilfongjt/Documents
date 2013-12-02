/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codeflyz.documents;

import com.codeflyz.documents.units.Constants;
import docdat.id.PseudoElement;
import docdat.id.PseudoElements;
import docdat.io.names.ProcessLogger;
import java.util.Date;


/*
 *
 * @author wilfongj
 * ElementProcessor (EP) walks the entire tree doc looking for a trigger element.
 * Once a trigger element is found it starts dumping info about the trigger to a CSV
 * EP handles one document at a time
 * EP plucks the 
 * 
 */
public class ElementProcessorCSV  extends ElementProcessor{

 /*   Project project = null;
    PseudoElements ElemList = null;
    private boolean skipLineOuput = false;
    private LineEvaluator lineEvaluator = null;
    private int index = 0; // postion index in the elements list
*/
    public ElementProcessorCSV(Project prj, PseudoElements ES) {

        super(prj,ES, new LineEvaluatorCSV(prj, ES) );

    }

    ///////////////////////////////
    ///////////////////////////////
    ///////////////////////////////
    public void process() {
        //System.out.println("Process");
        // pass the root of the tree 
        getIncrementor().setIndex(0);

        PseudoElement TopE = getElemList().getElement(getIncrementor().getIndex());
        String line = "\"" + new Date().toString() + "\"" + "," + "\"" + TopE.getName() + "\"";


        //writeLine(line);

        buildThreeColArray(TopE);
    }



    public void writeLine(String line) {


        ProcessLogger pl = new ProcessLogger(getProject().getOutputPath(), getProject().getOutputFileName(getLineEvaluator().getPathElementArray()));
        pl.WriteNoDate(line);

    }

    ////////////////////////////////// 
    //this version converts the tree into a three col line
    ////////////////
    public void buildThreeColArray(PseudoElement LE) {
        System.out.println("buildThreeColArray");
        //if(LE == null){return;}
        
        if (getIncrementor().getIndex() >= getElemList().size()) {
            return;  // the end
        }

        if (getLineEvaluator().isInSkipLineOuput(LE)) {

            setSkipLineOutput(true);

        }

        getLineEvaluator().setElement(LE);
        if (getProject().getFileNameMax() >= LE.getLevel()) {

            getProject().deleteOutputFile(getProject().getOutputPath() + getProject().getOutputFileName(getLineEvaluator().getPathElementArray()));

        }

        String line = getLineEvaluator().formatLine();
        if (!isSkipLineOutput()) { // check for lines that will get ommitted in output
            if (getProject().getFileNameMax() <= LE.getLevel()) {
                if (getProject().getFileNameMax() == LE.getLevel()) {
                    writeTitleBlock();
                }

                writeLine(line);
                System.out.println(line);

            }
        }

        setSkipLineOutput(false);
        getIncrementor().setIndex(getIncrementor().getIndex()+1);
        PseudoElement E = getElemList().getElement(getIncrementor().getIndex());
        buildThreeColArray(E);

    }
   public void writeTitleBlock() {

        for (int i = 0; i < getElemList().size(); i++) {
            PseudoElement E = getElemList().getElement(i);

            if (E.getLevel() == 1) {
                System.out.println("Zero: " + E.getIdx());
                writeLine("\"title\"" + "," + "\"" + getElemList().getElement(0).getName() + "\"");
                writeLine("\"run\"" + "," + "\"" + new Date().toString() + "\"");

            }
            PseudoElement PE = getElemList().getParent(E);
            PseudoElement PPE = getElemList().getParent(PE);
            if ((PE != null && PPE != null)
                    && PE.getName().equalsIgnoreCase(Constants.Keywords.Description)
                    && PPE.getIdx() == 0) {
                writeLine("\"description\"" + "," + "\"" + E.getName() + "\"");
            }

        }

    }
}
