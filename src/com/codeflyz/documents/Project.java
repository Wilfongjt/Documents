/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codeflyz.documents;

import docdat.id.PseudoElement;

import java.io.File;

/**
 *
 * @author wilfongj
 */
public class Project {

    private String projectPath = "";
    private String outputFolder = "output/sheets/";
    private String outputFileName = "";
    private int fileNameMax = 2; // 
    private int lineStartIndex = fileNameMax - 1; // the line always starts at next element in the path
    private int pathElementMax = 20; // 1 is all under root name

    public Project(String prjPath) {

        setProjectPath(prjPath);

        makeOutputPath();
    }

    public int getLineStartIndex() {
        return lineStartIndex;
    }

    public void setLineStartIndex(int lineStartIndex) {
        this.lineStartIndex = lineStartIndex;
    }

    public void makeOutputPath() {
        File nf = new File(getOutputPath());
        System.out.println("new folder: " + nf.toString());
        if (!nf.exists()) {

            nf.mkdirs();
        }
    }

    public String getOutputPath() {
        return getProjectPath() + outputFolder;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String project_path) {

        this.projectPath = project_path;
    }

    public String getProjectName() {
        // just the file name no path
        String rc = "";
        String outpath = getProjectPath();
        //System.out.println("path:" + getProjectPath());


        for (int i = outpath.length() - 1; i >= 0; i--) {
            char ch = outpath.charAt(i);
            if (ch == '\\' || ch == '/') {
                if (i == outpath.length() - 1) {
                    // skip a / or \ on the very end
                } else {
                    break;
                }

            } else {
                rc = ch + rc;
            }

        }

        return rc ;
    }

    public int getFileNameMax() {
        return fileNameMax;
    }

    public void setFileNameMax(int fileNameMax) {
        this.fileNameMax = fileNameMax;
    }

    public int getPathElementMax() {
        return pathElementMax;
    }

    public void setPathElementMax(int sz) {
        if (sz < 1) {
            return;
        }
        pathElementMax = sz;

        // pathElementArray = new PseudoElement[getProject().getPathElementMax()];

    }

    public void deleteOutputFile(String fn) {
        File f = new File(fn);
        if (f.exists()) {
            f.delete();
        }
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    /*
     * use for csv single sheet per file
     */
    public String getOutputFileName(PseudoElement[] pathArray) {
        String rc = "";

        //System.out.println("path length: " +getPathElements().length);
        for (int i = 0; i < getFileNameMax(); i++) {

            if (i == 0) {
                rc = pathArray[i].getId();
            }
            if (rc.length() > 0) {
                rc += ".";
            }
            //System.out.println("i: " + i);
            if (pathArray[i] != null) {
                rc += pathArray[i].getName();
            }
        }
        rc += ".csv";
        return rc;
    }


    public boolean hasFullFileName(PseudoElement[] pathArray) {
        if (pathArray[getPathElementMax() - 1] != null) {
            return true;
        }
        return false;
    }
}
