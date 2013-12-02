/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codeflyz.documents;

import docdat.id.PseudoElement;
import docdat.id.PseudoElements;


/*
 *
 * @author wilfongj
 * ElementProcessor (EP) walks the entire tree doc looking for a trigger element.
 * Once a trigger element is found it starts dumping info about the trigger to a CSV
 * EP handles one document at a time
 * EP plucks the 
 * 
 */
public class ElementProcessor {

    Project project = null;
    PseudoElements ElemList = null;
    private boolean skipLineOuput = false;
    private LineEvaluator lineEvaluator = null;
    private Incrementor incrementor= new Incrementor();

    public ElementProcessor(Project prj, PseudoElements ES, LineEvaluator LEV) {

        setProject(prj);
        setElemList(ES);
        LEV.setIncrementor(getIncrementor());
        setLineEvaluator(LEV);


    }

    public Incrementor getIncrementor() {
        return incrementor;
    }

    public void setIncrementor(Incrementor incrementor) {
        this.incrementor = incrementor;
    }

    public boolean isSkipLineOutput() {
        return skipLineOuput;
    }

    public void setSkipLineOutput(boolean skipLineOuput) {
        this.skipLineOuput = skipLineOuput;
    }

    public PseudoElements getElemList() {
        return ElemList;
    }

    public void setElemList(PseudoElements ElemList) {
        this.ElemList = ElemList;
    }

    public LineEvaluator getLineEvaluator() {
        return lineEvaluator;
    }

    public void setLineEvaluator(LineEvaluator lineEvaluator) {
        this.lineEvaluator = lineEvaluator;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }


    ///////////////////////////////
    ///////////////////////////////
    ///////////////////////////////

    public void process() {
    }

    public void writeLine(String line) {
    }

    ////////////////////////////////// 
    //this version converts the tree into a three col line
    ////////////////
    public void buildThreeColArray(PseudoElement LE) {
    }

    public void writeTitleBlock() {
        // "writeTitleBlock";
    }
}
