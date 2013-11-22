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
public class ElementProcessor1 {

    String project_path = "C:/00-Code/00-Projects/SHPO/GIS-Database/00-Estimates/";
    String sheet_path = project_path + "output-sheets/";
    private String skipOutput = "[description][hours][units]"; // check  
    private String skipWhenParent = "[description][hours]"; // check 
    private String parentParent = "[units][hours]"; // 
    private int index = 0;
    private int pathElementDepth = 1; // 1 is all under root name
    PseudoElements ElemList = null;
    PseudoElement[] pathElementArray = new PseudoElement[1];

    public ElementProcessor1(PseudoElements ES) {
        setElemList(ES);
    }

    public String getProject_path() {
        return project_path;
    }

    public void setProject_path(String project_path) {
        this.project_path = project_path;
    }

    public String getSheet_path() {
        return sheet_path;
    }

    public void setSheet_path(String sheet_path) {
        this.sheet_path = sheet_path;
    }

    public void setPathElementDepth(int sz) {
        if (sz < 1) {
            return;
        }
        pathElementDepth = sz;
        pathElementArray = new PseudoElement[pathElementDepth];

    }

    public int getPathElementDepth() {
        return pathElementDepth;
    }

    public PseudoElement[] getPathElements() {
        return pathElementArray;
    }

    public void setPathElements(PseudoElement E) {
        // System.out.println("setLevel: " + E.getLevel() +" <= "+getPathElementDepth());
        if (E.getLevel() <= getPathElementDepth()) {
            //System.out.println("setPath: level: " + E.getLevel() + " " + E.getName() ); 
            getPathElements()[E.getLevel() - 1] = E;
        }

    }

    public boolean hasFullFileName() {
        if (getPathElements()[getPathElementDepth() - 1] != null) {
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
        System.out.println(line);
            ProcessLogger pl = new ProcessLogger(getSheet_path() + getFileName());
            pl.WriteNoDate(line);
        //}
    }

    public void writeCSV(PseudoElement LE) {
        setPathElements(LE);
        //boolean hasChild = getElemList().hasChild(LE);

        index++;
        PseudoElement E = getElemList().getElement(index);
        PseudoElement PE = getElemList().getParent(E);
        PseudoElement PPE = getElemList().getParent(PE);

        boolean hasChild = getElemList().hasChild(E);


        if (index >= getElemList().size()) {
            return;  // the end
        }


        if (hasChild) {

            if (!skipOutput.contains(E.getName().toLowerCase())
                    && !skipWhenParent.contains(PE.getName().toLowerCase())) { // skip list
                String line = "\"\"" + ", " + "\"" + E.getName() + "\"";
                System.out.println(line);
                writeCSV(line);
            }

            writeCSV(E);
        } else { // no child
            String extra = "";
            if (isNumber(E.getName())) {
                if (parentParent.contains(PPE.getName().toLowerCase())) {
                    String line = "\"" + PPE.getName().toLowerCase() + "\"" + "," + "\"" + PE.getName() + "\"" + "," + E.getName(); // number
                    System.out.println(line); // number
                    writeCSV(line);
                }
            } else {

                String line = "\"" + PE.getName() + "\"" + ", " + "\"" + E.getName() + "\"";
                System.out.println(line);
                writeCSV(line);
            }
            writeCSV(E);
        }
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
        for (int i = 0; i < getPathElements().length; i++) {

            if (i > 0) {
                rc += ".";
            }
            //System.out.println("i: " + i);
            if (getPathElements()[i] != null) {
                rc += getPathElements()[i].getName();
            }
        }
        rc += ".csv";
        return rc;
    }
}
