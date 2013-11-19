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
import java.io.File;

/**
 *
 * @author wilfongj
 */
public class Assemble_Merge_Estimates_0 {

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
        int fileNameLevel = 3; // how many levels to include in the filename.
        PseudoElement T = null;
        int levelHourTotal = 0;
        String hoursIncludes = "";
        boolean firstSection = true;
        ProcessLogger csv = null;

        PseudoElement[] nameArray = new PseudoElement[fileNameLevel];


        for (int i = 0; i < me.size(); i++) {
            PseudoElements ES = me.getElements(i);
            firstSection = true;
            for (int k = 0; k < ES.size(); k++) {
                PseudoElement E = ES.getElement(k);

                // write the last line/ total before creating next csv file
                if (E.getLevel() == targetLevel) {

                    // take care of totals if the is one before moving on to the next section


                    if (!firstSection) {
                        String hours = "\"hours\"," + "\"" + hoursIncludes + "\"," + levelHourTotal;
                        System.out.println(hours);
                        csv.WriteNoDate(hours);
                        levelHourTotal = 0;
                        hoursIncludes = "";
                    }


                }

                if (E.getLevel() - 1 == targetLevel) {
                    if (!E.getName().equalsIgnoreCase("description")) {
                        if (hoursIncludes.length() > 0) {
                            hoursIncludes += "; ";
                        }
                        hoursIncludes += E.getName();
                    }
                }


                // collect names for file
                // these will be elems with level less than target
                if (E.getLevel() <= fileNameLevel) {
                    nameArray[E.getLevel() - 1] = E;
                    if (E.getLevel() == fileNameLevel && !E.getName().equalsIgnoreCase("description")) {
                        if (csv != null) {
                            String hours = "\"hours\"," + "\"" + hoursIncludes + "\"," + levelHourTotal;
                            System.out.println(hours);
                            csv.WriteNoDate(hours);
                            levelHourTotal = 0;
                        }

                        /////////////////////////// handle new file
                        System.out.println("filename:" + getFileName(nameArray));
                        File outf = new File(sheet_path + "/" + getFileName(nameArray));
                        csv = new ProcessLogger(outf);
                        csv.KillLog();
                    }
                }

                // get the parent of the target level
                if (E.getLevel() == targetLevel - 1) {
                    T = E;
                    // get the parts of the file name

                }

                // inspect the target level elem
                if (E.getLevel() == targetLevel) {

                    // take care of totals if the is one before moving on to the next section

                    ///////////////////////////////
                    String parentName = "";
                    if (T != null) {
                        parentName = T.getName();
                    }

                    //String outline = 
                    //System.out.println(" level: " + E.getLevel() + " k: " + k + "   " + parentName + ", " + E.getName());

                    csv.WriteNoDate("\"\"");// insert space
                    // output the target line  
                    String outline = "\"" + parentName + "\",\"" + E.getName() + "\"";
                    System.out.println(outline);
                    csv.WriteNoDate(outline);
                    firstSection = false; // show all sums, totals after this

                }

                if (E.getLevel() > targetLevel) {

                    PseudoElement P = ES.getParent(E);

                    if (P != null && P.getName().toLowerCase().contains("description")) {
                        if ((P.getLevel() - 1) == targetLevel) {
                            String outline = "\"Description\",\"" + E.getName() + "\"";
                            System.out.println(outline);
                            csv.WriteNoDate(outline);
                        }
                    }

                    if (isNumber(E.getName())) {
                        // is hours?
                        PseudoElement PP = ES.getParent(ES.getParent(E));
                        if (PP != null && PP.getName().toLowerCase().contains("hour")) {
                            // String hours = "\"\"," + "\"hours\",\"" + E.getName() + "\"";
                            //System.out.println(hours);
                            levelHourTotal += new Integer(E.getName()).intValue();
                        }
                    }
                }

            } // for

            String hours = "\"hours\"," + "\"" + hoursIncludes + "\"," + levelHourTotal;
            System.out.println(hours);
            csv.WriteNoDate(hours);
            levelHourTotal = 0;

        }//for

    }

    protected static int getSumFrom() {
        int rc = 0;

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