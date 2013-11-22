/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package process;

import docdat.id.PseudoElement;
import docdat.id.PseudoElements;
import docdat.io.names.ProcessLogger;

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

    String project_path = "C:/00-Code/00-Projects/SHPO/GIS-Database/00-Estimates/";
    String outputFolder = "output-sheets/";
    private String skipOutput = "[description][hours][units]"; // check  
    private String skipWhenParent = "[description][hours]"; // check 
    private String labels1 = "[description]"; // label is next to the item of interest 
    private String labels2 = "[description][units][hours]"; // label is two up from the item of interest
    private int index = 0; // postion index in the elements list
    private int pathElementMax = 20; // 1 is all under root name
    private int fileNameMax = 2; // 
    private int lineStartIndex = fileNameMax - 1; // the line always starts at next element in the path
    PseudoElements ElemList = null;
    PseudoElement[] pathElementArray = new PseudoElement[pathElementMax];
    PseudoElement[] lineArray = new PseudoElement[3];

    public PseudoElement[] getLineArray() {
        return lineArray;
    }

    public void setLineElement(PseudoElement E) {
        // getLineArray()[0] = null;
        getLineArray()[1] = E; // new stuff is always in the middle
        //getLineArray()[2] = null; // reset

        // Clear out the label if the parent and the parentparent don't match label
        PseudoElement PE = getElemList().getParent(E);
        PseudoElement PPE = getElemList().getParent(PE);


        // reset the label column
        boolean colResetFlag0 = false;
        if (getLineArray()[0] != null) {
            if (PE != null) {
                if (!getLineArray()[0].getName().equalsIgnoreCase(PE.getName())) {
                    colResetFlag0 = true;
                }
            }
            if (PPE != null) {
                if (!getLineArray()[0].getName().equalsIgnoreCase(PPE.getName())) {
                    colResetFlag0 = true;
                }
            }

        }

        // reset col 0 if it needs it
        if (colResetFlag0) {
            getLineArray()[0] = null;
        }

        if (labels1.contains(E.getName().toLowerCase())) {

            getLineArray()[0] = E;
            getLineArray()[1] = null;

        } else {

            if (isNumber(E.getName())) {
                getLineArray()[2] = E;
            } else {
                getLineArray()[2] = null; // reset
            }

        }
        /*


         PseudoElement PE = getElemList().getParent(E);
         if (PE != null) {
         if (labels1.contains(PE.getName().toLowerCase())) {
         getLineArray()[0] = PE;
         } else {
         PseudoElement PPE = getElemList().getParent(PE);
         if (PPE != null) {
         if (labels2.contains(PPE.getName().toLowerCase())) {
         getLineArray()[0] = PPE;
         }
         }
         }
         }
         */

    }

    public void setLineArray(PseudoElement[] lineArray) {
        this.lineArray = lineArray;
    }

    public ElementProcessor(PseudoElements ES) {
        setElemList(ES);
    }

    public int getLineStartIndex() {
        return lineStartIndex;
    }

    public void setLineStartIndex(int lineStartIndex) {
        this.lineStartIndex = lineStartIndex;
    }

    public int getFileNameMax() {
        return fileNameMax;
    }

    public void setFileNameMax(int fileNameMax) {
        this.fileNameMax = fileNameMax;
    }

    public String getOutputPath() {
        return getProject_path() + outputFolder;
    }

    public String getProject_path() {
        return project_path;
    }

    public void setProject_path(String project_path) {
        this.project_path = project_path;
    }

    public int getPathElementMax() {
        return pathElementMax;
    }

    public void setPathElementMax(int sz) {
        if (sz < 1) {
            return;
        }
        pathElementMax = sz;

        pathElementArray = new PseudoElement[pathElementMax];

    }

    public void setPathElement(PseudoElement E) {
        //System.out.println("setLevel: " + E.getLevel() +" <= "+getPathElementMax()   );
        if (E.getLevel() <= getPathElementMax()) {
            // nullify everything past the current level
            for (int i = E.getLevel() - 1; i < getPathElementMax(); i++) {
                getPathArray()[i] = null;
            }
            // reset the Elem 
            getPathArray()[E.getLevel() - 1] = E;
        }
    }

    public PseudoElement[] getPathArray() {
        return pathElementArray;
    }

    public void setPathArray(PseudoElement[] arr) {
        pathElementArray = arr;
    }

    public boolean hasFullFileName() {
        if (getPathArray()[getPathElementMax() - 1] != null) {
            return true;
        }
        return false;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public PseudoElements getElemList() {
        return ElemList;
    }

    public void setElemList(PseudoElements ElemList) {
        this.ElemList = ElemList;
    }

    public String formatCSVLine() {
        String rc = "";
        for (int i = 0; i < 3; i++) {
            PseudoElement E = getLineArray()[i];
            if (E != null) {
                if (rc.length() > 0) {
                    rc += ",";
                }

                if (isNumber(E.getName())) {
                    rc += E.getName();
                } else {
                    rc += "\"" + E.getName() + "\"";
                }

            } else {
                rc += "\"\"";
            }
        }
        return rc;
    }

    public String formatCSVAllLine() {
        String rc = "";
        for (int i = getLineStartIndex(); i < getPathElementMax(); i++) {
            PseudoElement E = getPathArray()[i];
            if (E != null) {
                if (rc.length() > 0) {
                    rc += ",";
                }
                if (isNumber(E.getName())) {
                    rc += E.getName();
                } else {
                    rc += "\"" + E.getName() + "\"";
                }
            }
        }
        return rc;
    }
    ///////////////////////////////
    ///////////////////////////////
    ///////////////////////////////

    public void writeCSV() {
        // pass the root of the tree 
        setIndex(0);

        PseudoElement TopE = getElemList().getElement(getIndex());
        String line = "Top:" + "," + TopE.getName();
        System.out.println(line);
        writeCSV(line);
        writeCSV(TopE);
    }

    public void writeCSV(String line) {
        //if (hasFullFileName()) {
        //System.out.println(line);

        ProcessLogger pl = new ProcessLogger(getOutputPath(), getFileName());
        pl.WriteNoDate(line);

        //}
    }

    public void writeCSV(PseudoElement LE) {

        if (index >= getElemList().size()) {
            return;  // the end
        }

        setPathElement(LE);

        setLineElement(LE);

        boolean hasChild = getElemList().hasChild(LE);

        // hit the bottom and do something
        if (!hasChild) {

            String line = formatCSVLine();
            System.out.println("A " + line);
            writeCSV(line);
        } else { // no child

            String line = formatCSVLine();
            System.out.println("B " + line);
            writeCSV(line);
        }

        index++;
        PseudoElement E = getElemList().getElement(index);
        writeCSV(E);

    }

    protected boolean isNumber(String val) {
        boolean rc = false;
        try {
            Integer n = new Integer(val);
            rc = true;
        } catch (Exception e) {
            // not a number
        } finally {
            return rc;
        }
    }

    protected String getFileName() {
        String rc = "";

        //System.out.println("path length: " +getPathElements().length);
        for (int i = 0; i < getFileNameMax(); i++) {

            if (i > 0) {
                rc += ".";
            }
            //System.out.println("i: " + i);
            if (getPathArray()[i] != null) {
                rc += getPathArray()[i].getName();
            }
        }
        rc += ".csv";
        return rc;
    }
}
