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
public class TestCase {
    
    private String testCase;
    private double state_fitness;
    private int approach_level;
    private double local_fitness;
    private String necessity_cost;
    private String sufficiency_cost;
    private String status;
    private boolean hasFitness;
    private double weight;
    private String className, className2;
    private String objectName, objectName2;
    private String method;
    
    public TestCase() {
        testCase = "";
        hasFitness = false;
        className = className2 = "";
        objectName = objectName2 = "";
        method = "";
        weight = 0.0;
    } //END testCase() CONSTRUCTOR
    
    public TestCase( TestCase tc ) {
        this.testCase = tc.getTestCase();
        this.state_fitness = tc.getStateFitness();
        this.approach_level = tc.getApproachLevel();
        this.local_fitness = tc.getLocalFitness();
        this.necessity_cost = tc.getNecessityCost();
        this.sufficiency_cost = tc.getSufficiencyCost();
        this.status = tc.getStatus();
        this.hasFitness = tc.getFitness();
        this.weight = tc.getWeight();
        this.className = tc.getClassName();
        this.className2 = tc.getClassName2();
        this.objectName = tc.getObjectName();
        this.objectName2 = tc.getObjectName2();
        this.method = tc.getMethod();
    } //END TestCase() METHOD
   
    public void setStateFitness( double sf ) {
        state_fitness = sf;
    } //END setStateFitness() METHOD

    public double getStateFitness() {
        return state_fitness;
    } //END getStateFitness() METHOD
    
    public void setApproachLevel( int al ) {
        approach_level = al;
    } //END setApproachLevel() METHOD
    
    public int getApproachLevel() {
        return approach_level;
    } //END getApproachLevel() METHOD

    public void setLocalFitness( double lf ) {
        local_fitness = lf;
    } //END setLocalFitness() METHOD
    
    public double getLocalFitness() {
        return local_fitness;
    } //END getLocalFitness() METHOD
    
    public void setNecessityCost( String nc ) {
        necessity_cost = nc;
    } //END setNecessityCost() METHOD
    
    public String getNecessityCost() {
        return necessity_cost;
    } //END getNecessityCost() METHOD
    
    public void setSufficiencyCost( String sc ) {
        sufficiency_cost = sc;
    } //END setSufficiencyCost() METHOD
    
    public String getSufficiencyCost() {
        return sufficiency_cost;
    } //END getSufficiencyCost() METHOD
    
    public void setStatus( String s ) {
        status = s;
    } //END setStatus() METHOD
    
    public String getStatus() {
        return status;
    } //END getStatus() METHOD
    
    public void setTestCase( String tc ) {
        testCase = tc;
    } //END setTestCase() METHOD
    
    public String getTestCase() {
        return testCase;
    } //END getTestCase() METHOD
    
    public String toString() {
        return testCase;
    } //END toString() METHOD
    
    public void setFitness( boolean hf ) {
        hasFitness = hf;
    } //END setFitness() METHOD
    
    public boolean getFitness() {
        return hasFitness;
    } //END getFitness() METHOD
    
    public void setWeight( double w ) {
        weight = w;
    } //END setWeight() METHOD

    public double getWeight() {
        return weight;
    } //END getWeight() METHOD
    
    public void setClassName( String cn ) {
        className = cn;
    } //END setClassName() METHOD
    
    public void setClassName2( String cn ) {
        className2 = cn;
    } //END setClassName() METHOD
    
    public void setObjectName( String on ) {
        objectName = on;
    } //END setObjectName() METHOD

    public void setObjectName2( String on ) {
        objectName2 = on;
    } //END setObjectName() METHOD
    
    public void setMethod( String m ) {
        method = m;
    } //END setMethod() METHOD
    
    public String getClassName() {
        return className; 
    } //END getClassName() METHOD

    public String getClassName2() {
        return className2; 
    } //END getClassName() METHOD

    public String getObjectName() {
        return objectName;
    } //END getObjectName() METHOD
    
    public String getObjectName2() {
        return objectName2;
    } //END getObjectName() METHOD
    
    public String getMethod() {
        return method;
    } //END getMethodn() METHOD
    
} //END TestCase CLASS
