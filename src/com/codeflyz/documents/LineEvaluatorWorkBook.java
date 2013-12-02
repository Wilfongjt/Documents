/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codeflyz.documents;

import com.codeflyz.documents.exports.WorkBook;
import com.codeflyz.documents.units.Constants;
import docdat.id.PseudoElement;
import docdat.id.PseudoElements;
import docdat.utils.Attribute;
import java.util.Date;

/**
 *
 * @author wilfongj
 */
public class LineEvaluatorWorkBook extends LineEvaluator {

    private int labelLevel = 0;
    private int topicLevel = 0;
    // private PseudoElement attributeLevelElement = null;
    private boolean suspendOutput = false;

    public LineEvaluatorWorkBook(Project prj, PseudoElements ES) {
        super(prj, ES);

        setLabels(Constants.Keywords.Labels);
        setTopics(Constants.Keywords.Topics);// are level 2 just  off the root
        setSubTopics(Constants.Keywords.Subtopics);
        setKeywords(Constants.Keywords.Keywords);
        setDefinitions(Constants.Keywords.Definitions);
        setAttributes(Constants.Keywords.Attributes);
        setStructuredBranches(Constants.Keywords.StructuredBranches);
        setSkipLineOuput(Constants.Skip.Output);
    }

    public boolean isSuspendOutput() {
        return suspendOutput;
    }

    public void setSuspendOutput(boolean suspentOutput) {
        this.suspendOutput = suspentOutput;
    }

    public int getTopicLevel() {
        return topicLevel;
    }

    public void setTopicLevel(int topicLevel) {
        this.topicLevel = topicLevel;
    }

    public int getLabelLevel() {
        return labelLevel;
    }

    public void setLabelLevel(int labelLevel) {
        this.labelLevel = labelLevel;
    }

    public boolean isFieldDefinition(PseudoElement E) {
        // deterimine if this E is equal to Definition and if one of its parents is Fields

        if (E == null) {
            //System.out.println("isFieldDefinition 1 ");
            return false;
        }
        if (!E.getName().equalsIgnoreCase(Constants.Keywords.Definition)) {
            return false;
        }
        //System.out.println("isFieldDefinition 2 [ E: " + E.getName() + "]<>[" + Constants.Keywords.Definition + "]");

        PseudoElement PE = getElemList().getParentFromAttribute(E, new Attribute(Constants.Attributes.Name, Constants.Keywords.Fields));
        if (PE == null) {
            // System.out.println("isFieldDefinition 3 ");
            return false;
        }
        return true;
    }

    public boolean isFieldDefinitionProperty(PseudoElement E) {
        //System.out.println("isFieldDefinitionProperty 1 ");
        // deterimine if this E is a property with a parent  that is Field Definition
        if (E == null) {
            return false;
        }
        //System.out.println("isFieldDefinitionProperty 2");
        PseudoElement PE = getElemList().getParent(E);

        if (PE == null) {
            return false;
        }
        if (PE.getName().equalsIgnoreCase(Constants.Keywords.Definition)) {
            return true;
        }
        // System.out.println("isFieldDefinitionProperty false ");

        return false;
    }

    public boolean isParent(PseudoElement E, String val) {
        if (E == null) {
            return false;
        }
        PseudoElement PE = getElemList().getParent(E);
        if (PE == null) {
            return false;
        }
        if (PE.getName().equalsIgnoreCase(val)) {
            return true;
        }
        return false;
    }

    public PseudoElement getTempElem(String name) {
        PseudoElement rc = new PseudoElement();
        rc.setName(name);
        return rc;
    }

    public void setLineElement(PseudoElement E) {
        //  int o = 0;

        setLineType(0);
        PseudoElement undefined = new PseudoElement();
        undefined.setName("?????????");

        PseudoElement found = new PseudoElement();
        found.setName("FOUND");

        PseudoElement runDate = new PseudoElement();
        runDate.setName(new Date().toString());


        PseudoElement PE = getElemList().getParent(E);
        PseudoElement PPE = getElemList().getParent(PE);
        PseudoElement PPPE = getElemList().getParent(PPE);

        {
            String track = E.getName();
            if (PE != null) {
                track = PE.getName() + "<" + track;
            }
            if (PPE != null) {
                track = PPE.getName() + "<" + track;
            }
            if (PPPE != null) {
                track = PPPE.getName() + "<" + track;
            }
            //System.out.println("track: " + track);
        }




        { // LABELS
            if (isInLabels(E)) { // is E a lable
                setLineType(Constants.LineTypes.Label); //
            }
            if (isInLabels(PE)) { // E's parent is a label then this is a label value
                setLineType(Constants.LineTypes.LabelChild);
            }
        }
        { // SUBTOPIC 
            // SUBTOPIC Start Found
            if (isInSubTopics(E)) { // is E a subtopic
                setLineType(Constants.LineTypes.SubTopic);
            }

            if (isInSubTopics(PE)) { // is E's parent a subtopic then this is an entity name
                setLineType(Constants.LineTypes.SubTopicChild);
            }
        }
        { //TOPIC
            // TOPIC Start Found
            if (isInTopics(E)) { // is E a Topic
                setTopicLevel(E.getLevel());// topic are off the root
                setLineType(Constants.LineTypes.Topic);
            }
            if (isInTopics(PE)) { // is E's parent a topic
                setLineType(Constants.LineTypes.TopicChild);
            }
        }

        {  // DEFINITION
            if (isInDefinitions(E)) {
                setLineType(Constants.LineTypes.Definition);
            }
            if (isInDefinitions(PE)) {
                setLineType(Constants.LineTypes.DefinitionChild);
            }
            /*
             if (isInDefinitions(PPE)) {
             setLineType(Constants.LineTypes.DefinitionChildPropertyName);
             }
             */
        }
        {  // ATTRIBUTES


            if (isInAttributes(E)) {
                setLineType(Constants.LineTypes.Attribute);
                //setAttributeLevelElement(E);

            }
            if (isInAttributes(PE)) {
                setLineType(Constants.LineTypes.AttributeChild);
                if (isInAttributes(E)) {
                    setLineType(Constants.LineTypes.Attribute);
                }
            }
        }
        {
            if (isInStructuredBranches(E)) {
                setLineType(Constants.LineTypes.StructuredBranch);
                //setAttributeLevelElement(E);

            }
        }



        System.out.println("getLineType()=" + getLineType() + "  E=" + E.getName());
        PseudoElement peek = getPeek(E);

        switch (getLineType()) {

            case Constants.LineTypes.Label: // Labels get skipped so set both to null
                //System.out.println("setLineElement 1 "+ E);
                getLineArray()[0] = null;  // set blank
                getLineArray()[1] = null;  // set blank
                getLineArray()[2] = null;  // set blank
                break;
            case Constants.LineTypes.LabelChild: // Label Value
                getLineArray()[0] = PE;  // set blank
                getLineArray()[1] = E;
                getLineArray()[2] = getTempElem("labelChild");
                break;
            case Constants.LineTypes.Topic: // Topic 
                getLineArray()[0] = null;  // set blank
                getLineArray()[1] = E;
                getLineArray()[2] = getTempElem("Topic");
                break;
            case Constants.LineTypes.TopicChild: // Topicl Value
                getLineArray()[0] = PE;  // set blank
                getLineArray()[1] = E;
                getLineArray()[2] = getTempElem("TopicChild");
                break;
            case Constants.LineTypes.SubTopic: // SubTopic 
                getLineArray()[0] = null;  // set blank
                getLineArray()[1] = E;
                getLineArray()[2] = getTempElem("SubTopic");
                break;
            case Constants.LineTypes.SubTopicChild: // Subtopic Value aka entity name
                getLineArray()[0] = PE;  // set blank
                getLineArray()[1] = E;
                getLineArray()[2] = getTempElem("SubTopicChild");
                break;

            case Constants.LineTypes.Definition: // definition with field as parent 
                // found.setName(found.getName() + " " + o);
                getLineArray()[0] = null;  // set blank
                getLineArray()[1] = E; //entity.process(getElemList(), E);  
                getLineArray()[2] = getTempElem("Definition");
                break;

            case Constants.LineTypes.DefinitionChild: // property name of a definition
                //getLineArray()[0] = E;  // set blank
                getLineArray()[1] = E.clone();
                getLineArray()[1].setName("[" + getLineArray()[1].getName());
                getLineArray()[2] = getTempElem("DefChild");

                break;
            case Constants.LineTypes.StructuredBranch: // property name of a definition
                getLineArray()[0] = E;  // set blank
                getLineArray()[1] = E.clone();

                getLineArray()[1].setName(getParameter(E));

                getLineArray()[2] = getTempElem("StructuredBranch");

                break;

            case Constants.LineTypes.Attribute: // property name of a definition
                //getLineArray()[0] = null;  // set blank
                //getLineArray()[1] = E;

                // when E is an attribute and PE is NOT an attribute = start of a new attribute
                //     set 0 spot to null... clear it
                //     set 1 spot to null... clear it
                if (isInAttributes(E) && !isInAttributes(PE)) {
                    getLineArray()[0] = null;  // set blank
                    getLineArray()[1] = null;
                }
                // when E is an attribute and peek is an attribute // first att in a double attribute
                //     set 0 spot to E ... set the label
                //     set 1 spot to null... clear it
                if (isInAttributes(E) && isInAttributes(peek)) {
                    getLineArray()[0] = E;  // set blank
                    getLineArray()[1] = null;
                }
                // when E is and attribute  and PE is an attribute = second attrib in a double attribute
                //     if spot 1 is null then set spot 1 = E.clone 
                //     if spot 1 is not null then set spot 1 to the contents of spot 1 plus the contents of E 

                if (isInAttributes(E) && isInAttributes(PE)) {

                    if (getLineArray()[1] == null) {
                        getLineArray()[1] = E.clone();
                        getLineArray()[1].setName("");
                        setSuspendOutput(true);
                    } else {
                        //getLineArray()[1].setName(getLineArray()[1].getName() );
                    }

                }

                getLineArray()[2] = getTempElem("Attribute");

                break;
            case Constants.LineTypes.AttributeChild: // property name of a definition
                //getLineArray()[0] = E;  // set blank
                //getLineArray()[1] = E.clone();


                if (getLineArray()[1] == null) {
                    getLineArray()[1] = E.clone();
                    getLineArray()[1].setName("[" + PE.getName() + ": " + E.getName() + "]");
                } else {
                    if (getLineArray()[1].getName().length() == 0) {
                        //if (getTemp().length() == 0) {
                        getLineArray()[1].setName("[u" + PE.getName() + ":v " + E.getName() + "]");
                        // setTemp("[u" + PE.getName() + ":v " + E.getName() + "]");
                        // getLineArray()[1].setName();
                    } else {
                        getLineArray()[1].setName(getLineArray()[1].getName() + " [x" + PE.getName() + ":y " + E.getName() + "]");
                        // getLineArray()[1].setName(getTemp()+ " [x" + PE.getName() + ":y " + E.getName() + "]");
                    }
                }

                getLineArray()[2] = getTempElem("AttributeChild");
                break;

            /*
             case Constants.LineTypes.DefinitionChildPropertyName: // property value of a definition 
             //found.setName(found.getName() + " " + o);
             getLineArray()[0] = null;  // set blank
             getLineArray()[1].setName(getLineArray()[1].getName() + ": "+ E.getName() +"]");
             getLineArray()[2] = getTempElem("DefChildPropName");
             break;
                
             case Constants.LineTypes.DefinitionChildPropertyValue: // property value of a definition 
             //found.setName(found.getName() + " " + o);
             getLineArray()[0] = null;  // set blank
             if(){}
             getLineArray()[1].setName(getLineArray()[1].getName() + ": "+ E.getName() +"]");
                
             getLineArray()[2] = getTempElem("DefChildPropName");
             break;
             */
            default:
                getLineArray()[0] = undefined; // labels, units
                getLineArray()[1] = E; // desc text, titles, always in the middle
                getLineArray()[2] = getTempElem("normal");
        }
    }

    public PseudoElement getPeek(PseudoElement E) {
        PseudoElement rc = null;
        if (E == null) {
            return null;
        }
        if (E.getPosition() + 1 >= getElemList().size()) {
            return null;
        }
        rc = getElemList().getElement(E.getPosition() + 1);
        return rc;
    }

    /////
    // this is a line formated as CSV
    /////////
    public String formatLine() {
        return formatLine(getLineType(), getLineArray());
    }

    public String formatLine(int lntype, PseudoElement[] lineArr) {

        if (isSuspendOutput()) {
            setSuspendOutput(false);
            return null;
        }
        String rc = "";
        WorkBook wb = new WorkBook();

        switch (lntype) {
            case Constants.LineTypes.TopTitleLine: // Label Value
                rc = wb.formatTopTitleLine(lineArr);
                break;
            case Constants.LineTypes.Label:
                // Labels get skipped so set both to null
                break;
            case Constants.LineTypes.LabelChild: // Label Value
                rc = wb.formatDescriptionLine(lineArr);
                break;
            case Constants.LineTypes.Topic: // Topic 
                //System.out.println("formatTopic ");
                rc = wb.formatTopicLine(lineArr);

                break;
            case Constants.LineTypes.TopicChild: // Topicl Value
                rc = wb.formatTopicChildLine(lineArr);

                break;
            case Constants.LineTypes.SubTopic: // SubTopic 
                rc = wb.formatSubTopicLine(lineArr);
                break;
            case Constants.LineTypes.SubTopicChild: // Subtopic Value aka entity name
                rc = wb.formatSubTopicChildLine(lineArr);
                break;

            case Constants.LineTypes.Definition: // definition with field as parent 
                rc = wb.formatDefinitionLine(lineArr);
                break;

            case Constants.LineTypes.DefinitionChild: // property name of a definition
                rc = wb.formatDefinitionChildLine(lineArr);
                break;
            case Constants.LineTypes.StructuredBranch: // property value of a definition  
                rc = wb.formatAttributeChildLine(lineArr);
                break;

            case Constants.LineTypes.Attribute: // property value of a definition 
                rc = wb.formatAttributeLine(lineArr);
                break;
            case Constants.LineTypes.AttributeChild: // property value of a definition  
                rc = wb.formatAttributeLine(lineArr);
                break;
            /* */
            default:
                rc = wb.formatNormalLine(getLineArray());
        }
        return rc;
    }

    protected String getParameter(PseudoElement E) {
        int stopLevel = E.getLevel();
        String rc = "";

        //incrementIndex(); // move past parameter

        int i = getIncrementor().getIndex() + 1;
       // if (E.getName().equalsIgnoreCase(Constants.Keywords.Parameter)) {
            for (; i < getElemList().size(); i++) {

                PseudoElement NE = getElemList().getElement(i);

                if (NE.getLevel() <= stopLevel) {
                    i--;
                    break;
                }
                if (isInAttributes(NE)) {
                    rc += "[" + NE.getName() + ": ";
                } else {
                    rc += NE.getName() + "]";
                }

            }

            getIncrementor().setIndex(i);// reset the master index in main loop
        
        return rc;
    }
}
