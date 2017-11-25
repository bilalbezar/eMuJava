/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emujava;

import emujava.*;
import static emujava.EMuJava.jScrollPane1;
import static emujava.EMuJava.jTree1;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;

/**
 *
 * @author jBillu
 */
public class EMProjectManager extends Thread {

    public void createNewProject() {
        try {
            File projectDir = new File( EMConstants.PROJECT_LOCATION + "/" + EMConstants.PROJECT_NAME );
            projectDir.mkdir();
            File projectDirs = new File( projectDir.getAbsolutePath() + "/source" );
            projectDirs.mkdir();
            projectDirs = new File( projectDir.getAbsolutePath() + "/classes" );
            projectDirs.mkdir();
            projectDirs = new File( projectDir.getAbsolutePath() + "/mutants" );
            projectDirs.mkdir();
            projectDirs = new File( projectDir.getAbsolutePath() + "/omutants" );
            projectDirs.mkdir();
            projectDirs = new File( projectDir.getAbsolutePath() + "/testcases" );
            projectDirs.mkdir();
            projectDirs = new File( projectDir.getAbsolutePath() + "/original" );
            projectDirs.mkdir();
            projectDirs = new File( projectDir.getAbsolutePath() + "/instrument" );
            projectDirs.mkdir();
            projectDirs = new File( projectDir.getAbsolutePath() + "/oinstrument" );
            projectDirs.mkdir();
            projectDirs = new File( projectDir.getAbsolutePath() + "/traces" );
            projectDirs.mkdir();
            
            JTextArea class1Area = new JTextArea();
            class1Area.setEditable( false );
        
            File sourceFile = new File( EMConstants.CLASS_1 );
            LineNumberReader lnr = new LineNumberReader( new FileReader( sourceFile ) );
            File javaFile = new File( projectDir.getAbsolutePath() + "/original/" + sourceFile.getName() );
            RandomAccessFile raf = new RandomAccessFile( javaFile, "rw" );
            String line = lnr.readLine();
            while( line!=null ) {
                raf.writeBytes( line );
                raf.writeByte( 13 );
                raf.writeByte( 10 );
                class1Area.append( line + "\n" );
                line = lnr.readLine();
            } //END while() LOOP
            lnr.close();
            raf.close();
            EMuJava.jTabbedPane2.setTitleAt( 0, "  " + sourceFile.getName() + "  ");
            EMuJava.jPanel5.add( new JScrollPane( class1Area ), BorderLayout.CENTER );
            EMuJava.jTabbedPane2.updateUI();
            EMuJava.jLabel6.setText( sourceFile.getName() );
            EMConstants.CLASS_1 = sourceFile.getName();
            
            if( !EMConstants.CLASS_2.equals( "" ) ) {
                JTextArea class2Area = new JTextArea();
                class2Area.setEditable( false );
                sourceFile = new File( EMConstants.CLASS_2 );
                lnr = new LineNumberReader( new FileReader( sourceFile ) );
                javaFile = new File( projectDir.getAbsolutePath() + "/original/" + sourceFile.getName() );
                raf = new RandomAccessFile( javaFile, "rw" );
                line = lnr.readLine();
                while( line!=null ) {
                    raf.writeBytes( line );
                    raf.writeByte( 13 );
                    raf.writeByte( 10 );
                    class2Area.append( line + "\n" );
                    line = lnr.readLine();
                } //END while() LOOP
                lnr.close();
                JPanel class2Panel = new JPanel();
                class2Panel.setLayout( new BorderLayout() );
                class2Panel.add( new JScrollPane( class2Area ), BorderLayout.CENTER );
                EMuJava.jTabbedPane2.addTab( "  " + sourceFile.getName() + "  ", class2Panel);
                EMuJava.jLabel7.setText( sourceFile.getName() );
                EMuJava.jTabbedPane2.updateUI();
                EMConstants.CLASS_2 = sourceFile.getName();
            } else {
                EMuJava.jLabel7.setText( "" );
            } //END if-else STATEMENT

            EMuJava.jLabel3.setText( EMConstants.PROJECT_NAME );
            EMuJava.jLabel4.setText( EMConstants.PROJECT_LOCATION );
            
            if( EMConstants.MUTATION_OPERATORS.contains( "ABS" ) ) {
                EMuJava.jLabel10.setFont( new Font( "Tahoma", Font.BOLD, 11 ) );
            } //END if STATEMENT
            if( EMConstants.MUTATION_OPERATORS.contains( "AOR" ) ) {
                EMuJava.jLabel11.setFont( new Font( "Tahoma", Font.BOLD, 11 ) );
            } //END if STATEMENT
            if( EMConstants.MUTATION_OPERATORS.contains( "LCR" ) ) {
                EMuJava.jLabel12.setFont( new Font( "Tahoma", Font.BOLD, 11 ) );
            } //END if STATEMENT
            if( EMConstants.MUTATION_OPERATORS.contains( "ROR" ) ) {
                EMuJava.jLabel13.setFont( new Font( "Tahoma", Font.BOLD, 11 ) );
            } //END if STATEMENT
            if( EMConstants.MUTATION_OPERATORS.contains( "UOI" ) ) {
                EMuJava.jLabel14.setFont( new Font( "Tahoma", Font.BOLD, 11 ) );
            } //END if STATEMENT
            if( EMConstants.MUTATION_OPERATORS.contains( "IOP" ) ) {
                EMuJava.jLabel15.setFont( new Font( "Tahoma", Font.BOLD, 11 ) );
            } //END if STATEMENT
            if( EMConstants.MUTATION_OPERATORS.contains( "PNC" ) ) {
                EMuJava.jLabel16.setFont( new Font( "Tahoma", Font.BOLD, 11 ) );
            } //END if STATEMENT
            if( EMConstants.MUTATION_OPERATORS.contains( "OMD" ) ) {
                EMuJava.jLabel17.setFont( new Font( "Tahoma", Font.BOLD, 11 ) );
            } //END if STATEMENT
            if( EMConstants.MUTATION_OPERATORS.contains( "JID" ) ) {
                EMuJava.jLabel18.setFont( new Font( "Tahoma", Font.BOLD, 11 ) );
            } //END if STATEMENT
            if( EMConstants.MUTATION_OPERATORS.contains( "EOC" ) ) {
                EMuJava.jLabel19.setFont( new Font( "Tahoma", Font.BOLD, 11 ) );
            } //END if STATEMENT
            
            EMuJava.jLabel24.setText( "" + EMConstants.POPULATION_SIZE + " Test Cases" );
            EMuJava.jLabel25.setText( EMConstants.GEN_TYPE );
            EMuJava.jLabel26.setText( "" + EMConstants.MUTATION_RATE + " Iterations" );
            EMuJava.jLabel27.setText( "" + EMConstants.MAX_ITERATIONS );
            
            EMuJava.jButton1.setEnabled( true );
        } catch( Exception e ) {
            e.printStackTrace();
        } //END try-catch BLOCk
    } //END createNewProject() METHOD
    
    public void preprocessCode() {
        try {
            File sourceDir = new File( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "/original" );
            File[] children = sourceDir.listFiles();
            File[] f = new File[ children.length ];
            for( int ch=0; ch<children.length; ch++ ) {
                f[ ch ] = children[ ch ];
//              ProcessProgress.getProcessProgress().statusLabel.setText( f.getName() );
                RandomAccessFile raf = new RandomAccessFile( f[ ch ], "rw" );
                String codeString = "";
                for( int a=1; a<=f[ ch ].length(); a++ ) {
                    byte b = raf.readByte();
                    if( b==47 ) {
                        if( a<f[ ch ].length() ) {
                            byte c = raf.readByte();
                            a++;
                            if( c==47 ) {
                                a++;
    LOOP:         	  	for( ; a<=f[ ch ].length(); a++ ) {
                                    c = raf.readByte();
                                    if( c==13 || c==10 ) {
                                        codeString += ( char )13;
                                        codeString += ( char )10;
                                        a++;
                                        break LOOP;
                                    } //END if STATEMENT
                                } //END for LOOP
                            } else if( c==42 ) {
                                a++;
LOOP:                           for( ; a<=f[ ch ].length(); a++ ) {
                                    c = raf.readByte();
                                    if( c==42 ) {
                                        if( a<f[ ch ].length() ) {
                                            byte d = raf.readByte();
                                            a++;
                                            if( d==47 ) {
                                                break LOOP;
                                            } //END if STATEMENT
                                        } //END if STATEMENT
                                    } //END if STATEMENT
                                } //END for LOOP
                            } else {
                                codeString += ( char )b;
                                codeString += ( char )c;
                            } //END if-else STATEMENT
                        } else {
                            codeString += ( char )b;
                        } //END if-else STATEMENT
                    } else {
                        codeString += ( char )b;
                    } //END if-else STATEMENT
                } //END for LOOP
                raf.close();
                raf = new RandomAccessFile( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "/source/" + f[ ch ].getName(), "rw" );
                raf.writeBytes( codeString );
                raf.close();
            } //END for LOOP
        } catch ( Exception exception ) {
            exception.printStackTrace();
        } //END try-catch BLOCK
    } //END preprocessCode() METHOD
    
    public void displayMutants() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode( "Mutant Programs (" + EMConstants.TOTAL_MUTANTS + ")"  );
        DefaultMutableTreeNode killNode = new DefaultMutableTreeNode( "Killed" );
        root.add( killNode );
        DefaultMutableTreeNode suspiciousNode = null;
        if( EMConstants.GEN_TYPE.equals( "Proposed Work" ) || EMConstants.GEN_TYPE.equals( "Random" ) ) {
            suspiciousNode = new DefaultMutableTreeNode( "Suspicious" );
            root.add( suspiciousNode );
        } //END if STATEMENT
        DefaultMutableTreeNode aliveNode = new DefaultMutableTreeNode( "Alive" );
        root.add( aliveNode );

        for( int n=0; n<EMConstants.ACHIEVED_TARGETS.size(); n++ ) {
            Target target = ( Target )EMConstants.ACHIEVED_TARGETS.get( n );
            killNode.add( new DefaultMutableTreeNode( target.getMutationOperator() + "-" + target.getMutantNumber() ) );
        } //END for LOOP
        for( int n=0; n<EMConstants.TARGETS.size(); n++ ) {
            Target target = ( Target )EMConstants.TARGETS.get( n );
            if( target.getSuspicious() ) {
                suspiciousNode.add( new DefaultMutableTreeNode( target.getMutationOperator() + "-" + target.getMutantNumber() ) );
            } else {
                aliveNode.add( new DefaultMutableTreeNode( target.getMutationOperator() + "-" + target.getMutantNumber() ) );
            } //END if-else STATEMENT
        } //END for LOOP
        
        EMuJava.jTree1 = new JTree( root );
        EMuJava.jScrollPane1 = new JScrollPane( jTree1 );
        EMuJava.jSplitPane2.setLeftComponent( jScrollPane1 );
        EMuJava.jSplitPane2.updateUI();
        
        EMuJava.jTree1.addTreeSelectionListener( 
            new TreeSelectionListener() {
                public void valueChanged( TreeSelectionEvent event ) {
		    TreePath path = event.getPath();
		    Object elements[] = path.getPath();
		    if( elements.length>=3 ) {
                        String mutants = ( ( DefaultMutableTreeNode )elements[ 2 ] ).toString();
		    	String operator = mutants.charAt( 0 ) + "" + mutants.charAt( 1 ) + "" + mutants.charAt( 2 );
		    	String mutant = mutants.substring( 4 );

                        int mutNum = Integer.parseInt( mutant );
                        try {
                            File file = new File( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "/mutants/" + operator + "/" + mutNum  );
                            File files[] = file.listFiles();
                            File mutFile = files[ 0 ];
                            JTextArea mutantArea = new JTextArea();
                            mutantArea.setEditable( false );
                            LineNumberReader lnr = new LineNumberReader( new FileReader( mutFile ) );
                            String line = lnr.readLine();
                            while( line!=null ) {
                                mutantArea.append( line + "\n" );
                                line = lnr.readLine();
                            } //END while() LOOP
                            lnr.close();
                            EMuJava.jPanel10 = new JPanel();
                            EMuJava.jPanel10.setLayout( new GridLayout( 1, 2, 10, 10 ) );
                            EMuJava.jScrollPane5 = new JScrollPane( mutantArea );
                            EMuJava.jScrollPane5.setBorder( BorderFactory.createTitledBorder( mutFile.getName() ) );
                            EMuJava.jScrollPane5.setBorder( BorderFactory.createTitledBorder( null, " " + mutFile.getName() + " ", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color( 0, 0, 102 ) ) );
                            EMuJava.jPanel10.add( EMuJava.jScrollPane5 );
                            if( files.length==2 ) {
                                mutFile = files[ 1 ];
                                JTextArea mutantArea2 = new JTextArea();
                                mutantArea2.setEditable( false );
                                lnr = new LineNumberReader( new FileReader( mutFile ) );
                                line = lnr.readLine();
                                while( line!=null ) {
                                    mutantArea2.append( line + "\n" );
                                    line = lnr.readLine();
                                } //END while() LOOP
                                lnr.close();
                                EMuJava.jScrollPane6 = new JScrollPane( mutantArea2 );
                                EMuJava.jScrollPane6.setBorder( BorderFactory.createTitledBorder( mutFile.getName() ) );
                                EMuJava.jScrollPane6.setBorder( BorderFactory.createTitledBorder( null, " " + mutFile.getName() + " ", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color( 0, 0, 102 ) ) );
                                EMuJava.jPanel10.add( EMuJava.jScrollPane6 );
                                EMuJava.jSplitPane2.setRightComponent( EMuJava.jPanel10 );
                                EMuJava.jSplitPane2.updateUI();
                            } else {
                                EMuJava.jScrollPane6 = new JScrollPane();
                                EMuJava.jScrollPane6.setBorder( BorderFactory.createTitledBorder( "  Class 2  " ) );
                                EMuJava.jPanel10.add( EMuJava.jScrollPane6 );
                                EMuJava.jSplitPane2.setRightComponent( EMuJava.jPanel10 );
                                EMuJava.jSplitPane2.updateUI();
                            } //END if-else STATEMENT
                        } catch( Exception e ) {
                            e.printStackTrace();
                        } //END try-catch BLOCK
		    } //end of if statement

                }
            }
        );
    } //END displayMutants() METHOD

    public void updateStatisticsAndResults() {
        if( EMConstants.CLASS_2.equals( "" ) ) {
            MutationAnalysis.jLabel8.setText( "1" );
        } else {
            MutationAnalysis.jLabel8.setText( "2" );
        } //END if-else STATEMENT
        
        int appliedOperators = 0;
        File mutantDir = new File( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "/mutants" );
        File[] mutantDirs = mutantDir.listFiles();
        for( int md=0; md<mutantDirs.length; md++ ) {
            File mutant = mutantDirs[ md ];
            File mutants = new File( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "/mutants/" + mutant.getName() );
            if( mutants.listFiles().length>0 ) {
                appliedOperators++;
            } //END if STATEMENT
        } //END for LOOP
        MutationAnalysis.jLabel9.setText( "" + appliedOperators );
        
        MutationAnalysis.jLabel10.setText( "" + EMConstants.TOTAL_MUTANTS );
        
        MutationAnalysis.jLabel11.setText( "" + EMConstants.ACHIEVED_TARGETS.size() );
        
        int suspicious = 0;
        int equivalent = 0;
        for( int t=0; t<EMConstants.TARGETS.size(); t++ ) {
            Target target = ( Target )EMConstants.TARGETS.get( t );
            if( target.getSuspicious() ) {
                suspicious++;
            } else {
                equivalent++;
            } //END if-else STATEMENT
        } //END for LOOP
        if( EMConstants.GEN_TYPE.equals( "Proposed Work" ) || EMConstants.GEN_TYPE.equals( "Random" ) ) {
            MutationAnalysis.jLabel12.setText( "" + suspicious );
        } else {
            MutationAnalysis.jLabel12.setText( "NA" );
        } //END if-else STATEMENT
        MutationAnalysis.jLabel13.setText( "" + equivalent );
        
        int nonEquivalent = EMConstants.TARGETS.size() + EMConstants.ACHIEVED_TARGETS.size();
        int mutScore = 0;
        if( nonEquivalent>0 ) {
            mutScore = ( EMConstants.ACHIEVED_TARGETS.size() * 100 ) / nonEquivalent;
        } //END if STATEMENT
        MutationAnalysis.jLabel14.setText( "" + mutScore + "%" );
        MutationAnalysis.PAINT = true;
        EMController.create().getMutationAnalysis().repaint();
        
        TestCaseAnalysis.jLabel10.setText( "" + EMConstants.ITERATIONS_PERFORMED );
        
        TestCaseAnalysis.jLabel12.setText( "" + EMConstants.EFFECTIVE_TESTCASES.size() );
        
        if( EMConstants.ACHIEVED_TARGETS.size()>0 ) {
            double precision = ( double )EMConstants.ACHIEVED_TARGETS.size() / ( double )( EMConstants.ITERATIONS_PERFORMED * EMConstants.POPULATION_SIZE );
            String precisionS = "" + precision;
            if( precisionS.length()>4 ) {
                precisionS = precisionS.charAt( 0 ) + "" + precisionS.charAt( 1 ) + "" + precisionS.charAt( 2 ) + "" + precisionS.charAt( 3 );
            } //END if STATEMENT
            TestCaseAnalysis.jLabel13.setText( precisionS );            
        } //END if STATEMENT
        
        long elapsedTime = EMConstants.TIME_END - EMConstants.TIME_START;
        elapsedTime -= 5000;
        elapsedTime /= 1000;
        long hours = elapsedTime / 3600;
        elapsedTime %= 3600;
        long minutes = elapsedTime / 60;
        elapsedTime %= 60;
        if( hours>=0 && minutes>=0 && elapsedTime>=0 ) {
            TestCaseAnalysis.jLabel14.setText( hours + " hours, " + minutes + " minutes, " + elapsedTime + " seconds" );
        } //END if STATEMENT
        TestCaseAnalysis.PAINT = true;
        EMController.create().getTestCaseAnalysis().repaint();
        
        if( GAManager.RUNNING_THREADS==0 ) {
            try {
                String fileName = EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "/Results.txt";
                File file = new File( fileName );
                if( file.exists() ) {
                    file.delete();
                } //END if STATEMENT
                
                RandomAccessFile raf = new RandomAccessFile( file, "rw" );
                raf.writeBytes( "Number of Classes: " );
                if( EMConstants.CLASS_2.equals( "" ) ) {
                    raf.writeBytes( "1" );
                } else {
                    raf.writeBytes( "2" );
                } //END if-else STATEMENT
                raf.writeBytes( "\n" );
                raf.writeBytes( "Class Names: " );
                raf.writeBytes( EMConstants.CLASS_1 );
                if( !EMConstants.CLASS_2.equals( "" ) ) {
                    raf.writeBytes( ", " + EMConstants.CLASS_2 );
                } //END if STATEMENT
                raf.writeBytes( "\n" );
                raf.writeBytes( "Mutation Operators: " );
                String operatorsList = "";
                operatorsList = ( String )EMConstants.MUTATION_OPERATORS.get( 0 );
                for( int o=1; o<EMConstants.MUTATION_OPERATORS.size(); o++ ) {
                    operatorsList = operatorsList + ", " + ( String )EMConstants.MUTATION_OPERATORS.get( o );
                } //END for LOOP
                raf.writeBytes( operatorsList );
                raf.writeBytes( "\n" );
                raf.writeBytes( "Generation Type: " );
                raf.writeBytes( EMConstants.GEN_TYPE );
                raf.writeBytes( "\n" );
                raf.writeBytes( "Population Size: " );
                raf.writeBytes( "" + EMConstants.POPULATION_SIZE );
                raf.writeBytes( "\n" );
                raf.writeBytes( "Maximum Iterations: " );
                raf.writeBytes( "" + EMConstants.MAX_ITERATIONS );
                raf.writeBytes( "\n" );
                raf.writeBytes( "Mutation Rate: " );
                raf.writeBytes( "" + EMConstants.MUTATION_RATE );
                raf.writeBytes( "\n" );
                raf.writeBytes( "\n" );
                raf.writeBytes( "===================" );
                raf.writeBytes( "\n" );
                raf.writeBytes( "Results" );
                raf.writeBytes( "\n" );
                raf.writeBytes( "===================" );
                raf.writeBytes( "\n" );
                raf.writeBytes( "Number of Mutants: " );
                raf.writeBytes( "" + EMConstants.TOTAL_MUTANTS );
                raf.writeBytes( "\n" );
                raf.writeBytes( "Killed Mutants: " );
                raf.writeBytes( "" + EMConstants.ACHIEVED_TARGETS.size() );
                raf.writeBytes( "\n" );
                raf.writeBytes( "Alive Mutants: " );
                raf.writeBytes( "" + equivalent );
                raf.writeBytes( "\n" );
                if( EMConstants.GEN_TYPE.equals( "Proposed Work" ) ) {
                    raf.writeBytes( "Suspicious Mutants: " );
                    raf.writeBytes( "" + suspicious );
                    raf.writeBytes( "\n" );                    
                } //END if STATEMENT
                raf.writeBytes( "Iterations Performed: " );
                raf.writeBytes( "" + EMConstants.ITERATIONS_PERFORMED );
                raf.writeBytes( "\n" );
                raf.writeBytes( "Test Cases Executed: " );
                raf.writeBytes( "" + EMConstants.ITERATIONS_PERFORMED * EMConstants.POPULATION_SIZE );
                raf.writeBytes( "\n" );
                raf.writeBytes( "Mutation Score: " );
                raf.writeBytes( "" + mutScore + "%" );
                raf.writeBytes( "\n" );
                raf.writeBytes( "Effective Test Cases: " );
                raf.writeBytes( "" + EMConstants.EFFECTIVE_TESTCASES.size() );
                raf.writeBytes( "\n" );
                raf.writeBytes( "Time Consumed: " );
                if( hours>=0 && minutes>=0 && elapsedTime>=0 ) {
                    raf.writeBytes( hours + " hours, " + minutes + " minutes, " + elapsedTime + " seconds" );
                } else {
                    raf.writeBytes( "0 seconds" );
                } //END if-else STATEMENT
                raf.writeBytes( "\n" );
                raf.close();
            } catch( Exception e ) {
                e.printStackTrace();
            } //END try-catch BLOCK
        } //END if STATEMENT
    } //END updateStatisticsAndResults() METHOD
    
    public void compileProject() {
        this.start();
    } //END compileProject() METHOD
    
    public void run() {
        try {
            File instrumDir = new File( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "/instrument" );
            File[] instrumDirs = instrumDir.listFiles();
            for( int i=0; i<instrumDirs.length; i++ ) {
                File instrumMutant = instrumDirs[ i ];
                File[] instrumMutants = instrumMutant.listFiles();
                for( int im=0; im<instrumMutants.length; im++ ) {
                    File mutant = instrumMutants[ im ];
                    String mutantPath = mutant.getAbsolutePath();
                    Runtime.getRuntime().exec( "javac " + mutantPath + "\\*.java" );
                    Thread.sleep( 1000 );
                } //END for LOOP
            } //END for LOOP
        } catch( Exception exception ) {
            exception.printStackTrace();
        } //END try-catch BLOCk
        try {
            File instrumDir = new File( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "/oinstrument" );
            File[] instrumDirs = instrumDir.listFiles();
            for( int i=0; i<instrumDirs.length; i++ ) {
                File instrumMutant = instrumDirs[ i ];
                File[] instrumMutants = instrumMutant.listFiles();
                for( int im=0; im<instrumMutants.length; im++ ) {
                    File mutant = instrumMutants[ im ];
                    String mutantPath = mutant.getAbsolutePath();
                    Runtime.getRuntime().exec( "javac " + mutantPath + "\\*.java" );
                    Thread.sleep( 1000 );
                } //END for LOOP
            } //END for LOOP
        } catch( Exception exception ) {
            exception.printStackTrace();
        } //END try-catch BLOCk
    } //END run() METHOD
    
} //END EMProjectManager CLASS
