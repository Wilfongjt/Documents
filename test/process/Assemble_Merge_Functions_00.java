/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package process;

import docdat.assembler.text.TEXT_Assembler;
import docdat.id.MasterElementsList;
import docdat.id.PseudoElement;
import docdat.id.PseudoElements;
import docdat.io.names.ProcessLogger;
import docdat.utils.Attribute;
import docdat.utils.Attributes;
import java.io.File;
import static process.Assemble_Merge_Estimates_01.getLevelDescription;

/**
 *
 * @author wilfongj
 */
public class Assemble_Merge_Functions_00 {
     public static void main(String[] args) throws Exception {
        
         System.out.println("PseudoElementsReaderTest 1");
        String project_path = "C:/00-Code/00-Projects/SHPO/GIS-Database/00-Estimates/";
        String sheet_path = project_path + "output-sheets";

        /// Merge ODT Documents

        File f = new File(sheet_path);

        //File f = new File("C:\\test1\\");
        System.out.println("exists: " + f.exists());
        // make folder to hold output
        if (!f.exists()) {
            System.out.println("new folder: " + f.toString());

            f.mkdirs();
        }
        MasterElementsList me = new MasterElementsList();
        try {
            // adding the MasterElementsList make replacements happen
            TEXT_Assembler a = new TEXT_Assembler(project_path, me);
            a.assemble();

        } catch (Exception e) {
            System.out.println(e.toString());
        }

        int targetLevel = 4;
        int fileNameLevel = targetLevel - 1; // how many levels to include in the filename.
        PseudoElement T = null;
        String lineOne = "";
        int levelHourTotal = 0;
        String hoursIncludes = "";
        boolean firstSection = true;
        ProcessLogger csv = null;

        PseudoElement[] nameArray = new PseudoElement[fileNameLevel];


        for (int i = 0; i < me.size(); i++) {
            PseudoElements ES = me.getElements(i);
            firstSection = true;
            PseudoElement Parent = null;
            
            ElementProcessor EP = new ElementProcessor(ES);
            //EP.setPathElementMax(2); 
            EP.writeCSV();

        }//for

    }

    protected static String getLevelDescription(PseudoElements ES, PseudoElement E) {
        String rc = "";
        int stopLevel = E.getLevel();
        int nextLevel = E.getLevel() + 1;
        int nextElem = E.getIdx() + 1;
        PseudoElement NE = ES.getElement(nextElem);
        for (int k = nextElem; k < ES.size() && NE.getLevel() > stopLevel; k++) {
            NE = ES.getElement(k);


            if (NE.getLevel() - 1 == nextLevel) {
                PseudoElement P = ES.getParent(NE);
                if (P.getName().equalsIgnoreCase("description")) {
                    if (rc.length() > 0) {
                        rc += "; ";
                    }
                    rc += NE.getName();
                }
            }
        }
        return rc;
    }

    protected static String getSubList(PseudoElements ES, PseudoElement E) {
        String rc = "";
        int stopLevel = E.getLevel();
        int nextElem = E.getIdx() + 1;
        PseudoElement NE = ES.getElement(nextElem);
        for (int k = nextElem; k < ES.size() && NE.getLevel() > stopLevel; k++) {
            NE = ES.getElement(k);
            //  if (NE != null) {
            //     System.out.println(" name:  " + NE.getName());
            //  }
            if (NE.getLevel() - 1 == stopLevel) {
                if (!NE.getName().equalsIgnoreCase("description")) {
                    if (rc.length() > 0) {
                        rc += "; ";
                    }
                    rc += NE.getName();
                }
            }
        }
        return rc;
    }
    
    protected static Attributes getUnitsFrom(PseudoElements ES, PseudoElement E, String type) {
        Attributes rc = new Attributes();
        int stopLevel = E.getLevel();
        int nextElem = E.getIdx() + 1;
        int levelHourTotal = 0;


        PseudoElement NE = ES.getElement(nextElem);
        //System.out.println("getSumFrom 1 stopLevel: " + stopLevel + " nextElem: " + nextElem);


        for (int k = nextElem; k < ES.size() && NE.getLevel() > stopLevel; k++) {
            NE = ES.getElement(k);
            //if (NE != null) {
            //    System.out.println(" name:  " + NE.getName());
            // }
            if (isNumber(NE.getName())) {
                // is hours?
                PseudoElement P  = ES.getParent(NE) ;
                PseudoElement PP = ES.getParent(P) ; 
                if (PP != null  && PP.getName().toLowerCase().contains(type)) {

                    Attribute att = new Attribute();
                    att.setName(P.getName()); // a title 
                    att.setValue(NE.getName()); // a number
                    rc.add(att); 
                   // levelHourTotal += new Integer(NE.getName()).intValue();
                   // rc = new Integer(levelHourTotal);
                }
            }
        }
        //System.out.println("getSumFrom out ");
        return rc;
    }
    protected static Integer getSumFrom(PseudoElements ES, PseudoElement E, String type) {
        Integer rc = null;
        int stopLevel = E.getLevel();
        int nextElem = E.getIdx() + 1;
        int levelHourTotal = 0;


        PseudoElement NE = ES.getElement(nextElem);
        //System.out.println("getSumFrom 1 stopLevel: " + stopLevel + " nextElem: " + nextElem);


        for (int k = nextElem; k < ES.size() && NE.getLevel() > stopLevel; k++) {
            NE = ES.getElement(k);
            //if (NE != null) {
            //    System.out.println(" name:  " + NE.getName());
            // }
            if (isNumber(NE.getName())) {
                // is hours?
                PseudoElement PP = ES.getParent(ES.getParent(NE));
                if (PP != null && PP.getName().toLowerCase().contains(type)) {
                    // String hours = "\"\"," + "\"hours\",\"" + E.getName() + "\"";
                    //System.out.println(hours);
                    levelHourTotal += new Integer(NE.getName()).intValue();
                    rc = new Integer(levelHourTotal);
                }
            }
        }
        //System.out.println("getSumFrom out ");
        return rc;
    }

    protected static String getFileName(PseudoElement[] nameArray) {
        String rc = "";
        for (int i = 0; i < nameArray.length; i++) {
            if (i > 0) {
                rc += ".";
            }
            rc += nameArray[i].getName();
        }
        rc += ".estimate.csv";
        return rc;
    }

    protected static boolean isNumber(String val) {
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

    protected static String getCSVLine(PseudoElements ES, PseudoElement E, String csvline) {
        String rc = "";
        //  System.out.println("getCSVLine " + E );
        if (E == null || E.isZeroElement()) {
            return csvline;
        }

        PseudoElement PE = ES.getParent(E);
        if (PE == null) {
            rc = csvline;
        } else {
            rc = getCSVLine(ES, PE, PE.getId() + "\",\"" + csvline);
        }


        return rc;

    }
}
