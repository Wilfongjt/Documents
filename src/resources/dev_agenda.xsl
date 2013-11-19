<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : dev_agenda.xsl
    Created on : October 1, 2012, 6:54 PM
    Author     : wilfongj
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>
    
    <xsl:variable name="header" select="'Header'"/>
    
    <xsl:template match="/">
        <html>
            <head>
                <title>dev_agenda.xsl</title>
            </head>
            <body>
                <table>
                    <xsl:call-template name="show-header"/>   
                </table>
            </body>
        </html>
    </xsl:template>
    
    <xsl:template name="show-branch">    
        <xsl:for-each select="./*[@name=$header]">
            <xsl:call-template name="show-row"/>  
        </xsl:for-each>
    
    </xsl:template>
    
    <xsl:template name="show-row">    
    
        <xsl:for-each select="./*[@name=$location]">
            <tr>
                <td>
                    <xsl:value-of select="@name"/>
            
                </td>
                <td>
                    <xsl:for-each select="./*">
                        <xsl:value-of select="@name"/>
                        <br/>
                    </xsl:for-each>
            
                </td>
            </tr>
        </xsl:for-each>
    
    </xsl:template>
    
    
</xsl:stylesheet>
