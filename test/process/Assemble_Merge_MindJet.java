/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package process;

import docdat.assembler.text.TEXT_Assembler;
import docdat.id.MasterElementsList;




/**
 *
 * @author wilfongj
 */
public class Assemble_Merge_MindJet {

    public static void main(String[] args) throws Exception {
        System.out.println("PseudoElementsReaderTest 1");
        String project_path = "C:\\00-Code\\00-Projects\\Hackfest\\00-Mindjet\\";
        /// Merge ODT Documents


        try {
          // adding the MasterElementsList make replacements happen
            TEXT_Assembler a = new TEXT_Assembler(project_path  ,  new MasterElementsList() );
            a.assemble();
            
        } catch (Exception e) {
            System.out.println(e.toString());
        }  
    }
}