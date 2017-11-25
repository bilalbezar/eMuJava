/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emujava;

import emujava.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author jBillu
 * 
 * @version 1.0 August 18, 2013
 * 
*/
public class EMController {
    
    private static EMController emController;
    private EMProjectManager projectManager = new EMProjectManager();
    private EMScanner emScanner = new EMScanner();
    private ClassComponents c1Components, c2Components;
    private SourceCode sourceCode = new SourceCode();
    private GAManager gaManager = new GAManager();
    private MutationAnalysis mutationAnalysis = new MutationAnalysis();
    private TestCaseAnalysis testCaseAnalysis = new TestCaseAnalysis();
    private ClassicSourceCode classicSourceCode = new ClassicSourceCode();

    private ArrayList class1Tokens, class2Tokens;
    
    private EMController() {
    } //END EMController() CONSTRUCTOR
    
    public static EMController create() {
        if( emController==null ) {
            emController = new EMController();
        } //END if STATEMENT
        return emController;
    } //END create() METHOD
    
    public void createNewProject() {
        projectManager.createNewProject();
    } //END createProject() METHOD
    
    public EMProjectManager getProjectManager() {
        return projectManager;
    } //END getProjectManager() METHOD
    
    public MutationAnalysis getMutationAnalysis() {
        return mutationAnalysis;
    } //END getMutationAnalysis() METHOD
    
    public TestCaseAnalysis getTestCaseAnalysis() {
        return testCaseAnalysis;
    } //END TestCaseAnalysis() METHOD
    
    public void startTheProcess() {
        ProcessProgress.getProcessProgress().setVisible( true );
        ProcessProgress.getProcessProgress().titleLabel.setText( "Preprocessing the source code" );
        projectManager.preprocessCode();
        ProcessProgress.getProcessProgress().statusLabel.setText( "Removing comnets from the code files..." );
        ProcessProgress.getProcessProgress().ppBar.setValue( 10 );
        ProcessProgress.getProcessProgress().titleLabel.setText( "Scanning the source code to identify tokens" );
        try {
            ProcessProgress.getProcessProgress().statusLabel.setText( "Scanning " + EMConstants.CLASS_1 );
            emScanner.scanFile( new File( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "\\source\\" + EMConstants.CLASS_1 ) );
            class1Tokens = emScanner.getTokenList();
            
            if( !EMConstants.CLASS_2.equals( "" ) ) {
                ProcessProgress.getProcessProgress().statusLabel.setText( "Scanning " + EMConstants.CLASS_2 );
                emScanner.scanFile( new File( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "\\source\\" + EMConstants.CLASS_2 ) );
                class2Tokens = emScanner.getTokenList();
            } //END if STATEMENT
            ProcessProgress.getProcessProgress().ppBar.setValue( 16 );
        } catch( Exception e ) {
            e.printStackTrace();
        } //END try-catch BLOCk

        ProcessProgress.getProcessProgress().titleLabel.setText( "Extracting class components of " + EMConstants.CLASS_1 );
        c1Components = new ClassComponents();
        ClassHeader c1Header = new ClassHeader();
        ProcessProgress.getProcessProgress().statusLabel.setText( "Extracting import list..." );
        ArrayList import1List = c1Header.extractImports( class1Tokens );
        for( int i=0; i<import1List.size(); i++ ) {
            Token token = ( Token )import1List.get( i );
        } //END for LOOP
        ProcessProgress.getProcessProgress().ppBar.setValue( 20 );

        c1Components.setImportsList( import1List );
        ProcessProgress.getProcessProgress().statusLabel.setText( "Extracting class header..." );
        ArrayList class1Header = c1Header.extractHeader( class1Tokens );
        for( int i=0; i<class1Header.size(); i++ ) {
            Token token = ( Token )class1Header.get( i );
        } //END for LOOP
        ProcessProgress.getProcessProgress().ppBar.setValue( 24 );
        c1Components.setClassHeader( class1Header );
        String class1Name = c1Header.getClassName( class1Header );
        c1Components.setClassName( class1Name );
        String class1Parent = c1Header.getClassParent( class1Header );
        c1Components.setClassParent( class1Parent );
        c1Components.extractClassComponents( class1Tokens, c1Header.getIndex()+1 );
        ProcessProgress.getProcessProgress().statusLabel.setText( "Extracting class data members..." );
        ArrayList method1List = ( ArrayList )c1Components.getMMList();
        ProcessProgress.getProcessProgress().ppBar.setValue( 28 );
        ProcessProgress.getProcessProgress().statusLabel.setText( "Extracting class constructors..." );
        ProcessProgress.getProcessProgress().ppBar.setValue( 32 );
        ProcessProgress.getProcessProgress().statusLabel.setText( "Extracting class methods..." );
        for( int mm=0; mm<method1List.size(); mm++ ) {
            MemberMethod mMethod = ( MemberMethod )method1List.get( mm );
            mMethod.identifyStatements();
            mMethod.printStatements();
        } //END for LOOP
        ProcessProgress.getProcessProgress().ppBar.setValue( 40 );
        
        if( !EMConstants.CLASS_2.equals( "" ) ) {
            ProcessProgress.getProcessProgress().titleLabel.setText( "Extracting class components of " + EMConstants.CLASS_2 );
            c2Components = new ClassComponents();
            ClassHeader c2Header = new ClassHeader();
            ArrayList import2List = c2Header.extractImports( class2Tokens );
            ProcessProgress.getProcessProgress().statusLabel.setText( "Extracting import list..." );
            c2Components.setImportsList( import2List );
            ArrayList class2Header = c2Header.extractHeader( class2Tokens );
            ProcessProgress.getProcessProgress().statusLabel.setText( "Extracting class header..." );
            c2Components.setClassHeader( class2Header );
            String class2Name = c2Header.getClassName( class2Header );
            c2Components.setClassName( class2Name );
            String class2Parent = c2Header.getClassParent(class2Header );
            c2Components.setClassParent( class2Parent );
            ProcessProgress.getProcessProgress().statusLabel.setText( "Extracting class data members..." );
            c2Components.extractClassComponents( class2Tokens, c2Header.getIndex()+1 );
            ArrayList method2List = ( ArrayList )c2Components.getMMList();
            ProcessProgress.getProcessProgress().statusLabel.setText( "Extracting class constructors..." );
            ProcessProgress.getProcessProgress().statusLabel.setText( "Extracting class methods..." );
            for( int mm=0; mm<method2List.size(); mm++ ) {
                MemberMethod mMethod = ( MemberMethod )method2List.get( mm );
                mMethod.identifyStatements();
                mMethod.printStatements();
            } //END for LOOP
            ProcessProgress.getProcessProgress().ppBar.setValue( 50 );
        } //END if STATEMENT
        
        ProcessProgress.getProcessProgress().titleLabel.setText( "Generating Mutants from class " + EMConstants.CLASS_1 );
        sourceCode.generateMutants( 1 );
        if( !EMConstants.CLASS_2.equals( "" ) ) {
            ProcessProgress.getProcessProgress().titleLabel.setText( "Generating Mutants from class " + EMConstants.CLASS_2 );
            sourceCode.generateMutants( 2 );
        } //END if STATEMENT
        ProcessProgress.getProcessProgress().ppBar.setValue( 60 );
        
        ProcessProgress.getProcessProgress().titleLabel.setText( "Instrumenting and Transforming the mutants" );
        if( EMConstants.GEN_TYPE.contains( "eMuJava" ) || EMConstants.GEN_TYPE.contains( "Random" ) ) {
            sourceCode.instrumentAndTransformCode();
        } else {
            classicSourceCode.instrumentAndTransformCode();
        } //END if-else STATEMENT
        ProcessProgress.getProcessProgress().statusLabel.setText( "Instrumentation and Transformation completed..." );
        ProcessProgress.getProcessProgress().ppBar.setValue( 80 );
        
        ProcessProgress.getProcessProgress().titleLabel.setText( "Compiling original and mutated instrumented code" );
        projectManager.compileProject();
        ProcessProgress.getProcessProgress().statusLabel.setText( "Compilation completed successfully..." );
        ProcessProgress.getProcessProgress().ppBar.setValue( 100 );
        
        try {
            Thread.sleep( 500 );
        } catch( Exception e ) {
            e.printStackTrace();
        } //END try-catch BLOCK
        ProcessProgress.getProcessProgress().setVisible( false ); 
        EMuJava.jButton1.setText( "Generate Test Cases" );
        EMController.create().getProjectManager().displayMutants();
    } //END startTheProcess() METHOD
    
    public void generateTestCases() {
        EMuJava.jButton1.setEnabled( false );
        ProcessProgress.getProcessProgress().setVisible( true );
        ProcessProgress.getProcessProgress().ppBar.setValue( 50 );
        ProcessProgress.getProcessProgress().titleLabel.setText( "Executing Genetic Algorithm" );
        gaManager.start();
    } //END generateTestcases() METHOD
    
    public ClassComponents getC1Components() {
        return c1Components;
    } //END getC!Components() METHOD
    
    public ClassComponents getC2Components() {
        return c2Components;
    } //END getC!Components() METHOD

} //END EMController CLASS
