/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codeflyz.documents;

import com.codeflyz.documents.units.FormatUtils;
import docdat.id.PseudoElement;
import docdat.id.PseudoElements;

/**
 *
 * @author wilfongj
 */
public class LineEvaluator {

    private Project project = null;
    private PseudoElements ElemList = null;
    private PseudoElement[] pathElementArray = null;
    private PseudoElement[] lineArray = null;
    
    private PseudoElement subTopic = null;
    //private EntityFactory entityFactory = null;
    // private String tags = ""; // format "[description][p]"
    private String units = "";
    private String labels = "";
    private String topics = "";
    private String subtopics = "";
    private String definitions = "";
    private String attributes = "";
    private String structuredBranches="";
    
    private String skipLineOuput = "";
    private String keywords = "";
    private int lineType = 0;
    //  private String entities = "";
    private Incrementor incrementor= null;

    public LineEvaluator(Project prj, PseudoElements elemlist) {

        setProject(prj);
        setElemList(elemlist);
        setLineArray(new PseudoElement[3]);
        setPathElementArray(new PseudoElement[getProject().getPathElementMax()]);
        //setEntityFactory(new EntityFactory(elemlist));
    }

    public Incrementor getIncrementor() {
        return incrementor;
    }

    public void setIncrementor(Incrementor incrementor) {
        this.incrementor = incrementor;
    }
    
    
    
    
    
    public boolean isInStructuredBranches(PseudoElement searchFor) {
        if (searchFor == null) {
            return false;
        }
        return getStructuredBranches().contains(searchFor.getName().toLowerCase());
    }
    public String getStructuredBranches() {
        return structuredBranches;
    }

    public void setStructuredBranches(String recursives) {
        this.structuredBranches = recursives;
    }
      
    
    public String getSheetName(){
        PseudoElement tab = getPathElementArray()[getProject().getFileNameMax()-1];
        if(tab == null){
            return "";
        }
        return tab.getName();
    }

    public int getLineType() {
        return lineType;
    }

    public void setLineType(int lineType) {
        this.lineType = lineType;
    }

    public PseudoElement getRootElement() {
        return getElemList().getZeroElement();
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keyword) {
        this.keywords = keyword;
    }
    public boolean isInAttributes(PseudoElement searchFor) {
        if (searchFor == null) {
            return false;
        }
        return getAttributes().contains(searchFor.getName().toLowerCase());
    }
    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }
    
    
    
    
    public boolean isInDefinitions(PseudoElement searchFor) {
        if (searchFor == null) {
            return false;
        }
        return getDefinitions().contains(searchFor.getName().toLowerCase());
    }
    public String getDefinitions() {
        return definitions;
    }

    public void setDefinitions(String definitions) {
        this.definitions = definitions;
    }

    /* public EntityFactory getEntityFactory() {
     return entityFactory;
     }

     public void setEntityFactory(EntityFactory entityFactory) {
     this.entityFactory = entityFactory;
     }
     */
    // Fields
    //    -> some string <- entity
    /*
    public boolean isAnEntity(PseudoElement E) {

        if (E == null) {
            return false;
        }

        PseudoElement PE = getElemList().getParent(E);

        if (PE == null) {
            return false;
        }

        if (!isInSubTopics(PE)) {
            return false;
        }
        if (!isInTopics(E)) {
            return false;
        }
        return true;
    }
    */
    /*
     public String getEntities() {
     return entities;
     }

     public void setEntities(String entities) {
     this.entities = entities;
     }
     */

    public PseudoElement getSubTopic() {
        return subTopic;
    }

    public void setSubTopic(PseudoElement subTopic) {
        this.subTopic = subTopic;
    }

    public boolean isInSubTopics(PseudoElement searchFor) {
        if (searchFor == null) {
            return false;
        }
        return getSubTopics().contains(searchFor.getName().toLowerCase());
    }

    public boolean isInSubTopics(PseudoElement ParentsParent, PseudoElement searchFor) {
        if (searchFor == null) {
            return false;
        }
        if (ParentsParent == null) {
            return false;
        }
        if (!isInTopics(ParentsParent)) {
            return false;
        }
        return getSubTopics().contains(searchFor.getName().toLowerCase());
    }

    public String getSubtopics() {
        return subtopics;
    }

    public void setSubtopics(String subtopics) {
        this.subtopics = subtopics;
    }

    public String getSubTopics() {
        return subtopics;
    }

    public void setSubTopics(String subtopics) {
        this.subtopics = subtopics;
    }

    public PseudoElement getTopic() {
        if (getPathElementArray()[1] != null) {
            return getPathElementArray()[1];
        }
        return null;
    }

    public boolean isInTopics(PseudoElement searchFor) {
        if (searchFor == null) {
            return false;
        }
        if (searchFor.getLevel() != 2) {
            return false;
        }
        return getTopics().contains(searchFor.getName().toLowerCase());
    }

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }

    public boolean isInSkipLineOuput(PseudoElement searchFor) {
        if (searchFor == null) {
            return false;
        }
        return skipLineOuput.contains(searchFor.getName().toLowerCase());
    }

    public String getSkipLineOuput() {
        return skipLineOuput;
    }

    public void setSkipLineOuput(String skipLineOuput) {
        this.skipLineOuput = skipLineOuput;
    }

    /*   public boolean isInTags(PseudoElement searchFor) {
     if (searchFor == null) {
     return false;
     }
     return tags.contains(searchFor.getName().toLowerCase());
     }

     public String getTags() {
     return tags;
     }

     public void setTags(String tags) {
     this.tags = tags;
     }
     */
    ////////////////////
    // Units follow this pattern
    //  Unit -> definition of unith -> unit count
    //////////////////////////////////
    public boolean isInUnits(PseudoElement searchFor) {
        if (searchFor == null) {
            return false;
        }
        return units.contains(searchFor.getName().toLowerCase());
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    ////////////////////////
    // Labels follow this pattern
    // label -> item
    ////////////////////////////////////
    public boolean isInLabels(PseudoElement searchFor) {
        if (searchFor == null) {
            return false;
        }
        return labels.contains(searchFor.getName().toLowerCase());
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public PseudoElements getElemList() {
        return ElemList;
    }

    public void setElemList(PseudoElements ElemList) {
        this.ElemList = ElemList;
    }

    public PseudoElement[] getPathElementArray() {
        return pathElementArray;
    }

    public void setPathElementArray(PseudoElement[] arr) {
        pathElementArray = arr;
    }

    public PseudoElement[] getLineArray() {
        return lineArray;
    }

    public void setLineArray(PseudoElement[] lineArray) {
        this.lineArray = lineArray;
    }

    public void setLineElement(PseudoElement E) {
        // add code to sort out which column
        System.out.println("need to extend the LineEvaluator.setLineElement(PseudoElement E) method ");
    }

    public void setElement(PseudoElement E) {
        //System.out.println("setElement 1");
        setPathElement(E); // add this element to the path in the branch
        setLineElement(E);  // add to the out put line 
        

    }

    public void setPathElement(PseudoElement E) {
        // add code to sort out the list of elem in the current branch
        if (E.getLevel() <= getProject().getPathElementMax()) {
            // nullify everything past the current level
            for (int i = E.getLevel() - 1; i < getProject().getPathElementMax(); i++) {
                getPathElementArray()[i] = null;
            }
            // reset the Elem 
            getPathElementArray()[E.getLevel() - 1] = E;
        }
    }

    public String formatLine() {
        // add code to format the line
        System.out.println("need to extend the LineEvaluator.formatLine() method ");
        return "need to extend the LineEvaluator.formatLine() method ";
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
      protected String getSingular(String name) {
          return new FormatUtils().getSingular(name); 
    }
}
