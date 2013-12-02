/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codeflyz.documents;

/**
 *
 * @author wilfongj
 */
public class Incrementor {
    private int index = 0; // postion index in the elements list
        public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void incrementIndex() {
        this.index = this.index + 1;
    }
    public void deccrementIndex() {
        this.index = this.index - 1;
    }
}
