/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codeflyz.documents.units;

import docdat.utils.Attribute;

/**
 *
 * @author wilfongj
 */
public class Constants {

    public class LineTypes {

        public static final int TopTitleLine = 10; // one of the defined lables...skip
        public static final int Label = 1; // one of the defined lables...skip
        public static final int LabelChild = 2; // parent is a define label 
        public static final int Topic = 3;  // one of the defined Topics 
        public static final int TopicChild = 4; // parent is a defined Topic  
        public static final int SubTopic = 5; // one of the defined SubTopics 
        public static final int SubTopicChild = 6; // 
        public static final int Definition = 7; // one of the defined Definitions 
        public static final int DefinitionChild = 8; // 
       // public static final int DefinitionChildPropertyName = 9; // 
        public static final int Attribute = 9; //
        public static final int AttributeChild = 11; //
        public static final int StructuredBranch = 12; //
    }

    public class Attributes {

        public static final String Name = "name";
    }

    public class Keywords {

        public static final String Fields = "fields";
        public static final String Methods = "methods";
        public static final String Definition = "definition";
        public static final String Method = "method";
        public static final String Description = "description";
        public static final String Parameter = "parameter";
        public static final String Return = "return";
        
        public static final String Attributes = "[name][type][size][default]";
        public static final String Labels = "[" + Description + "][" + Definition + "]"; // these can be anywhere, 
        public static final String Topics = "[functions][processes][properties][interfaces]"; // thes are first off the root
        public static final String Subtopics = "[subproperties][" + Fields + "][subprocesses]"+Methods;
        public static final String Definitions = "[" + Definition + "]["+Method+"]";
        public static final String StructuredBranches="["+Parameter+"][" +Return+"]";  // these need to be paired with a method to do the recursion
        public static final String Units = "[hours]";
        public static final String Keywords = Topics + Subtopics + Labels + Units + Attributes;
        public final String[] KeywordArray = {Labels, Topics, Subtopics, Units, Attributes};
    }

    public class Skip {

        public static final String Output = "[description][name][type][size][default]";
    }

    public class Replace {

        public static final String WorksheetName = "[worksheetname]";
        public static final String Column1 = "[column1]";
        public static final String Column2 = "[column2]";
        public static final String Column3 = "[column3]";
    }

    public class WorkSheetParts {

        public static final String WorkBookStart = "<?xml version=\"1.0\"?>\n"
                + "<?mso-application progid=\"Excel.Sheet\"?>\n"
                + "<Workbook xmlns=\"urn:schemas-microsoft-com:office:spreadsheet\"\n"
                + " xmlns:o=\"urn:schemas-microsoft-com:office:office\"\n"
                + " xmlns:x=\"urn:schemas-microsoft-com:office:excel\"\n"
                + " xmlns:ss=\"urn:schemas-microsoft-com:office:spreadsheet\"\n"
                + " xmlns:html=\"http://www.w3.org/TR/REC-html40\">\n"
                + " <DocumentProperties xmlns=\"urn:schemas-microsoft-com:office:office\">\n"
                + "  <Author>wilfongj</Author>\n"
                + "  <LastAuthor>wilfongj</LastAuthor>\n"
                + "  <Created>2013-11-27T18:55:38Z</Created>\n"
                + "  <LastSaved>2013-11-27T19:40:23Z</LastSaved>\n"
                + "  <Version>14.00</Version>\n"
                + " </DocumentProperties>\n"
                + " <OfficeDocumentSettings xmlns=\"urn:schemas-microsoft-com:office:office\">\n"
                + "  <AllowPNG/>\n"
                + " </OfficeDocumentSettings>\n"
                + " <ExcelWorkbook xmlns=\"urn:schemas-microsoft-com:office:excel\">\n"
                + "  <WindowHeight>9240</WindowHeight>\n"
                + "  <WindowWidth>8715</WindowWidth>\n"
                + "  <WindowTopX>540</WindowTopX>\n"
                + "  <WindowTopY>165</WindowTopY>\n"
                + "  <ProtectStructure>False</ProtectStructure>\n"
                + "  <ProtectWindows>False</ProtectWindows>\n"
                + " </ExcelWorkbook>\n"
                + " <Styles>\n"
                + "  <Style ss:ID=\"Default\" ss:Name=\"Normal\">\n"
                + "   <Alignment ss:Vertical=\"Bottom\"/>\n"
                + "   <Borders/>\n"
                + "   <Font ss:FontName=\"Calibri\" x:Family=\"Swiss\" ss:Size=\"11\" ss:Color=\"#000000\"/>\n"
                + "   <Interior/>\n"
                + "   <NumberFormat/>\n"
                + "   <Protection/>\n"
                + "  </Style>\n"
                + "  <Style ss:ID=\"s57\">\n"
                + "   <Alignment ss:Vertical=\"Center\"/>\n"
                + "   <Font ss:FontName=\"Calibri\" x:Family=\"Swiss\" ss:Color=\"#4F81BD\"/>\n"
                + "  </Style>\n"
                + "  <Style ss:ID=\"s58\">\n"
                + "   <Alignment ss:Vertical=\"Bottom\" ss:WrapText=\"1\"/>\n"
                + "   <Font ss:FontName=\"Calibri\" x:Family=\"Swiss\" ss:Size=\"24\" ss:Color=\"#000000\"/>\n"
                + "  </Style>\n"
                + "  <Style ss:ID=\"s59\">\n"
                + "   <Alignment ss:Vertical=\"Bottom\" ss:WrapText=\"1\"/>\n"
                + "   <Font ss:FontName=\"Calibri\" x:Family=\"Swiss\" ss:Color=\"#808080\"/>\n"
                + "  </Style>\n"
                + "  <Style ss:ID=\"s60\">\n"
                + "   <Alignment ss:Vertical=\"Bottom\" ss:WrapText=\"1\"/>\n"
                + "  </Style>\n"
                + "  <Style ss:ID=\"s61\">\n"
                + "   <Alignment ss:Vertical=\"Bottom\" ss:WrapText=\"1\"/>\n"
                + "   <Font ss:FontName=\"Calibri\" x:Family=\"Swiss\" ss:Color=\"#4F81BD\"/>\n"
                + "  </Style>\n"
                + "  <Style ss:ID=\"s62\">\n"
                + "   <Alignment ss:Vertical=\"Bottom\" ss:WrapText=\"1\"/>\n"
                + "   <Font ss:FontName=\"Calibri\" x:Family=\"Swiss\" ss:Size=\"16\" ss:Color=\"#000000\"/>\n"
                + "  </Style>\n"
                + "  <Style ss:ID=\"s63\">\n"
                + "   <Alignment ss:Vertical=\"Bottom\" ss:WrapText=\"1\"/>\n"
                + "   <Font ss:FontName=\"Calibri\" x:Family=\"Swiss\" ss:Size=\"18\" ss:Color=\"#000000\"/>\n"
                + "  </Style>\n"
                + "  <Style ss:ID=\"s64\">\n"
                + "   <Alignment ss:Vertical=\"Bottom\" ss:WrapText=\"1\"/>\n"
                + "   <Font ss:FontName=\"Calibri\" x:Family=\"Swiss\" ss:Size=\"11\" ss:Color=\"#000000\"\n"
                + "    ss:Bold=\"1\"/>\n"
                + "  </Style>\n"
                + "  <Style ss:ID=\"s65\">\n"
                + "   <Alignment ss:Vertical=\"Bottom\" ss:WrapText=\"1\"/>\n"
                + "   <Font ss:FontName=\"Calibri\" x:Family=\"Swiss\" ss:Size=\"11\" ss:Color=\"#808080\"\n"
                + "    ss:Bold=\"1\"/>\n"
                + "  </Style>\n"
                + "  <Style ss:ID=\"s67\">\n"
                + "   <Alignment ss:Vertical=\"Bottom\" ss:WrapText=\"1\"/>\n"
                + "   <Font ss:FontName=\"Calibri\" x:Family=\"Swiss\" ss:Size=\"10\" ss:Color=\"#808080\"\n"
                + "    ss:Bold=\"1\"/>\n"
                + "  </Style>\n"
                + "  <Style ss:ID=\"s73\">\n"
                + "   <Font ss:FontName=\"Calibri\" x:Family=\"Swiss\" ss:Color=\"#4F81BD\"/>\n"
                + "  </Style>\n"
                + "  <Style ss:ID=\"s77\">\n"
                + "   <Font ss:FontName=\"Calibri\" x:Family=\"Swiss\" ss:Size=\"11\" ss:Color=\"#808080\"/>\n"
                + "  </Style>\n"
                + " </Styles>";
        public static final String WorkSheetStart = "<Worksheet ss:Name=\"" + Replace.WorksheetName + "\">\n"
                + "  <Table ss:ExpandedColumnCount=\"3\" ss:ExpandedRowCount=\"5000\" x:FullColumns=\"1\"\n"
                + "   x:FullRows=\"1\" ss:DefaultRowHeight=\"15\">\n"
                + "   <Column ss:AutoFitWidth=\"0\" ss:Width=\"71.25\"/>\n"
                + "   <Column ss:StyleID=\"s60\" ss:AutoFitWidth=\"0\" ss:Width=\"192\"/>";
        //<Worksheet ss:Name  =        \"" + Replace.WorksheetName + "\"> <Table ss:ExpandedColumnCount=\"2\" ss:ExpandedRowCount=\"33\" x:FullColumns=\"1\"   x:FullRows=\"1\" ss:DefaultRowHeight=\"15\">   <Column ss:AutoFitWidth=\"0\" ss:Width=\"71.25\"/>   <Column ss:StyleID=\"s60\" ss:AutoFitWidth=\"0\" ss:Width=\"192\"/>";
        public static final String TopTitleLine = "<Row ss:Height=\"31.5\">\n"
                + "    <Cell ss:StyleID=\"s57\"><Data ss:Type=\"String\">" + Replace.Column1 + "</Data></Cell>\n"
                + "    <Cell ss:StyleID=\"s58\"><Data ss:Type=\"String\">" + Replace.Column2 + "</Data></Cell>\n"
                + "    <Cell><Data ss:Type=\"String\">" + Replace.Column3 + "</Data></Cell>\n"
                + "   </Row>";
        public static final String DateLine = "   <Row>\n"
                + "    <Cell ss:StyleID=\"s57\"><Data ss:Type=\"String\">" + Replace.Column1 + "</Data></Cell>\n"
                + "    <Cell ss:StyleID=\"s59\"><Data ss:Type=\"String\">" + Replace.Column2 + "</Data></Cell>\n"
                + "    <Cell><Data ss:Type=\"String\">" + Replace.Column3 + "</Data></Cell>\n"
                + "   </Row>";
        public static final String DescriptionLine = "   <Row ss:Height=\"39\">\n"
                + "    <Cell ss:StyleID=\"s57\"><Data ss:Type=\"String\">" + Replace.Column1 + "</Data></Cell>\n"
                + "    <Cell ss:StyleID=\"s61\"><Data ss:Type=\"String\">" + Replace.Column2 + "</Data></Cell>\n"
                + "    <Cell ss:StyleID=\"s73\"><Data ss:Type=\"String\">" + Replace.Column3 + "</Data></Cell>\n"
                + "   </Row>";
        public static final String TopicLine = "   <Row ss:Height=\"23.25\">\n"
                + "    <Cell ss:StyleID=\"s57\"><Data ss:Type=\"String\">" + Replace.Column1 + "</Data></Cell>\n"
                + "    <Cell ss:StyleID=\"s63\"><Data ss:Type=\"String\">" + Replace.Column2 + "</Data></Cell>\n"
                + "    <Cell><Data ss:Type=\"String\">" + Replace.Column3 + "</Data></Cell>\n"
                + "   </Row>";
        public static final String TopicChildLine = "   <Row ss:Height=\"21\">\n"
                + "    <Cell ss:StyleID=\"s57\"><Data ss:Type=\"String\">" + Replace.Column1 + "</Data></Cell>\n"
                + "    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\">" + Replace.Column2 + "</Data></Cell>\n"
                + "    <Cell><Data ss:Type=\"String\">" + Replace.Column3 + "</Data></Cell>\n"
                + "   </Row>";
        public static final String SubTopicLine = "<Row>\n"
                + "    <Cell ss:StyleID=\"s57\"><Data ss:Type=\"String\">" + Replace.Column1 + "</Data></Cell>\n"
                + "    <Cell ss:StyleID=\"s65\"><Data ss:Type=\"String\">" + Replace.Column2 + "</Data></Cell>\n"
                + "    <Cell ss:StyleID=\"s67\"><Data ss:Type=\"String\">" + Replace.Column3 + "</Data></Cell>\n"
                + "   </Row>";
        public static final String SubTopicChildLine = "   <Row>\n"
                + "    <Cell ss:StyleID=\"s57\"><Data ss:Type=\"String\">" + Replace.Column1 + "</Data></Cell>\n"
                + "    <Cell ss:StyleID=\"s64\"><Data ss:Type=\"String\">" + Replace.Column2 + "</Data></Cell>\n"
                + "    <Cell ss:StyleID=\"s77\"><Data ss:Type=\"String\">" + Replace.Column3 + "</Data></Cell>\n"
                + "   </Row>";
        public static final String DefinitionLine = "<Row>\n"
                + "    <Cell ss:StyleID=\"s57\"><Data ss:Type=\"String\">" + Replace.Column1 + "</Data></Cell>\n"
                + "    <Cell ss:StyleID=\"s65\"><Data ss:Type=\"String\">" + Replace.Column2 + "</Data></Cell>\n"
                + "    <Cell ss:StyleID=\"s67\"><Data ss:Type=\"String\">" + Replace.Column3 + "</Data></Cell>\n"
                + "   </Row>";
        public static final String DefinitionChildLine = "<Row>\n"
                + "    <Cell ss:StyleID=\"s57\"><Data ss:Type=\"String\">" + Replace.Column1 + "</Data></Cell>\n"
                + "    <Cell><Data ss:Type=\"String\">" + Replace.Column2 + "</Data></Cell>\n"
                + "    <Cell><Data ss:Type=\"String\">" + Replace.Column3 + "</Data></Cell>\n"
                + "   </Row>";
        public static final String AttributeLine = "<Row>\n"
                + "    <Cell ss:StyleID=\"s57\"><Data ss:Type=\"String\">" + Replace.Column1 + "</Data></Cell>\n"
                + "    <Cell><Data ss:Type=\"String\">" + Replace.Column2 + "</Data></Cell>\n"
                + "    <Cell><Data ss:Type=\"String\">" + Replace.Column3 + "</Data></Cell>\n"
                + "   </Row>";
        public static final String NormalLine = "   <Row>\n"
                + "    <Cell ss:StyleID=\"s57\"><Data ss:Type=\"String\">" + Replace.Column1 + "</Data></Cell>\n"
                + "    <Cell><Data ss:Type=\"String\">" + Replace.Column2 + "</Data></Cell>\n"
                + "    <Cell><Data ss:Type=\"String\">" + Replace.Column3 + "</Data></Cell>\n"
                + "   </Row>";
        public static final String WorkSheetEnd = "  </Table>\n"
                + "  <WorksheetOptions xmlns=\"urn:schemas-microsoft-com:office:excel\">\n"
                + "   <PageSetup>\n"
                + "    <Header x:Margin=\"0.3\"/>\n"
                + "    <Footer x:Margin=\"0.3\"/>\n"
                + "    <PageMargins x:Bottom=\"0.75\" x:Left=\"0.7\" x:Right=\"0.7\" x:Top=\"0.75\"/>\n"
                + "   </PageSetup>\n"
                + "   <Selected/>\n"
                + "   <TopRowVisible>3</TopRowVisible>\n"
                + "   <Panes>\n"
                + "    <Pane>\n"
                + "     <Number>3</Number>\n"
                + "     <ActiveRow>7</ActiveRow>\n"
                + "     <ActiveCol>2</ActiveCol>\n"
                + "    </Pane>\n"
                + "   </Panes>\n"
                + "   <ProtectObjects>False</ProtectObjects>\n"
                + "   <ProtectScenarios>False</ProtectScenarios>\n"
                + "  </WorksheetOptions>\n"
                + " </Worksheet>";
        public static final String WorkBookEnd = "</Workbook>";
    }
}
