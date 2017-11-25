/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emujava;

/**
 *
 * @author jBillu
 * 
 * @version 1.0 October 20, 2013
 * 
 */
public class Target {
    
    private String mutationOperator;
    private int mutantNumber;
    private TestCase testCase;
    private String status;
    private boolean isSuspicious;
    private boolean isAchieved;
    
    public Target() {
        mutationOperator = "";
        testCase = new TestCase();
        isSuspicious = false;
        status = "Alive";
        isAchieved = false;
    } //END Target() METHOD

    public void setMutationOperator( String mo ) {
        mutationOperator = mo;
    } //END setMutationOperator() METHOD
    
    public String getMutationOperator() {
        return mutationOperator;
    } //END getMutationOperator() METHOD
    
    public void setMutantNumber( int mn ) {
        mutantNumber = mn;
    } //END setMutantNumber() METHOD
    
    public int getMutantNumber() {
        return mutantNumber;
    } //END getMutantNumber() METHOD
    
    public String toString() {
        return mutationOperator + " " + mutantNumber;
    } //END toString() METHOD
    
    public void setStatus( String s ) {
        status = s;
    } //END setStatus() METHOD
    
    public String getStatus() {
        return status;
    } //END getStatus() METHOD
    
    public void setTestCase( TestCase tc ) {
        testCase = tc;
    } //END setTestCase() METHOD
    
    public TestCase getTestCase() {
        return testCase;
    } //END getTestCase() METHOD
    
    public void setSuspicious( boolean ia ) {
        isSuspicious = ia;
    } //END isSuspicious() METHOD
    
    public boolean getSuspicious() {
        return isSuspicious;
    } //END getSuspicious() METHOD
    
    public void setAchieved( boolean ia ) {
        isAchieved = ia;
    } //END setAchieved() METHOD
    
    public boolean getAchieved() {
        return isAchieved;
    } //END getAchieved() METHOD
    
} //END Target CLASS
