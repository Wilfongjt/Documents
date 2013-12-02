/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codeflyz.documents;

import com.codeflyz.documents.LineEvaluator;
import com.codeflyz.documents.Project;
import com.codeflyz.documents.units.Constants;
import docdat.id.PseudoElement;
import docdat.id.PseudoElements;
import docdat.utils.Attribute;
import java.util.Date;

/**
 *
 * @author wilfongj
 */
public class LineEvaluatorCSV extends LineEvaluator {

    private int labelLevel = 0;
    private int topicLevel = 0;
    
    public LineEvaluatorCSV(Project prj, PseudoElements ES) {
        super(prj, ES);

        setLabels(Constants.Keywords.Labels);
        setTopics(Constants.Keywords.Topics);// are level 2 just  off the root
        setSubTopics(Constants.Keywords.Subtopics);
        setKeywords(Constants.Keywords.Keywords);

        setSkipLineOuput(Constants.Skip.Output);
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

    public void setLineElement(PseudoElement E) {
        int o = 0;
        
        PseudoElement undefined = new PseudoElement();
        undefined.setName("?????????");
        
        PseudoElement found = new PseudoElement();
        found.setName("FOUND");
        
        PseudoElement runDate= new PseudoElement();
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
                o = 1;
            }
            if (isInLabels(PE)) { // E's parent is a label then this is a label value
                o = 2;
            }
        }
        { //TOPIC
            // TOPIC Start Found
            if (isInTopics(E)) { // is E a Topic
                setTopicLevel(E.getLevel());// topic are off the root
                o = 3;
            }
            if (isInTopics(PE)) { // is E's parent a topic
                o = 4;
            }
        }
        { // SUBTOPIC 
            // SUBTOPIC Start Found
            if (isInSubTopics(E)) { // is E a subtopic
                o = 5;
            }

            if (isInSubTopics(PE)) { // is E's parent a subtopic then this is an entity name
                o = 6;
            }
        }
        {  // DEFINITION
            if (isFieldDefinition(E)) { // is E an definition 
                // System.out.println("FieldDefinitionYes");
                o = 7;
            }

            // DEFINITION Property name
            if (isFieldDefinition(PE)) { // is E's parent is a definition 
              
                o = 8;
                
            }
            // DEFINITION Property name
            if (isFieldDefinition(PPE)) { // is E's parent is a definition 
              
                o = 9;
                
            }
        }
   //System.out.println("o="+o);
        switch (o) {

            case 1: // Labels get skipped so set both to null
                //System.out.println("setLineElement 1 "+ E);
                getLineArray()[0] = null;  // set blank
                getLineArray()[1] = null;  // set blank
                break;
            case 2: // Label Value
                getLineArray()[0] = PE;  // set blank
                getLineArray()[1] = E;
                break;
            case 3: // Topic 
                getLineArray()[0] = null;  // set blank
                getLineArray()[1] = E;
                break;
            case 4: // Topicl Value
                getLineArray()[0] = PE;  // set blank
                getLineArray()[1] = E;
                break;
            case 5: // SubTopic 
                getLineArray()[0] = null;  // set blank
                getLineArray()[1] = E;
                break;
            case 6: // Subtopic Value aka entity name
                getLineArray()[0] = PE;  // set blank
                getLineArray()[1] = E;
                break;

            case 7: // definition with field as parent 
                // found.setName(found.getName() + " " + o);
                getLineArray()[0] = null;  // set blank
                getLineArray()[1] = E; //entity.process(getElemList(), E);  
                break;

            case 8: // property name of a definition
                //getLineArray()[0] = E;  // set blank
                getLineArray()[1] = E.clone(); //entity.process(getElemList(), E);  
                getLineArray()[2] = null; 
                break;
            case 9: // property value of a definition 
                //found.setName(found.getName() + " " + o);
                getLineArray()[0] = null;  // set blank
                getLineArray()[1].setName( getLineArray()[1] .getName() + ": " + E.getName()); 
                 //getLineArray()[2] = E; 
                break;
            default:
                getLineArray()[0] = undefined; // labels, units
                getLineArray()[1] = E; // desc text, titles, always in the middle
                getLineArray()[2] = null; //  numbers  
        }
    }

    /////
    // this is a line formated as CSV
    /////////
    public String formatLine() {
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
                    if (i == 0) {
                        rc += "\"" + getSingular(E.getName().toLowerCase()) + "\"";
                    } else {
                        rc += "\"" + E.getName() + "\"";
                    }
                }

            } else {
                if (rc.length() > 0) {
                    rc += ",";
                }
                rc += "\"\"";
            }
        }
        return rc;
    }


}
