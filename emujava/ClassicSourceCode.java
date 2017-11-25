/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emujava;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author jBillu
 * 
 * @version 1.0 September 8, 2015
 * 
 */

public class ClassicSourceCode {
 
     public void instrumentAndTransformCode() {
        for( int mo=0; mo<EMConstants.MUTATION_OPERATORS.size(); mo++ ) {
            String mutationOperator = ( String )EMConstants.MUTATION_OPERATORS.get( mo );
            try {
                File mutantDir = new File( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "/mutants/" + mutationOperator );
                File[] mutantsDir = mutantDir.listFiles();
                File instDir = new File( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "/instrument/" + mutationOperator );
                if( !instDir.exists() ) {
                    instDir.mkdir();
                } //END if STATEMENT
                File oinstDir = new File( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "/oinstrument/" + mutationOperator );
                if( !oinstDir.exists() ) {
                    oinstDir.mkdir();
                } //END if STATEMENT

                for( int md=0; md<mutantsDir.length; md++ ) {
//                    File mutantFile = mutantsDir[ md ];
                    File mutantFile = new File( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "/mutants/" + mutationOperator + "/" + (md+1) );
                    File[] mutantFiles = mutantFile.listFiles();
                    ClassComponents cComponents = null;
                    for( int mf=0; mf<mutantFiles.length; mf++ ) {
                        int methodCount = 1;
                        int controlCount = 1;
                        int methodEndCount = 0;
                        boolean isMutatedMethod = false;
                        File codeFile = mutantFiles[ mf ];
                        File ocodeFile = new File( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "/omutants/" + codeFile.getName() );
                        if( codeFile.getName().equals( EMConstants.CLASS_1 ) ) {
                            cComponents = EMController.create().getC1Components();
                        } else {
                            cComponents = EMController.create().getC2Components();
                        }//END if STATEMENT
                        String instrumentedCode = "";
                        String oinstrumentedCode = "";
                        String mutatedMethod = "";
                        LineNumberReader lnr = new LineNumberReader( new FileReader( codeFile ) );
                        LineNumberReader olnr = new LineNumberReader( new FileReader( ocodeFile ) );
                        File instDirs = new File( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "/instrument/" + mutationOperator + "/" + (md+1) );
                        File oinstDirs = new File( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "/oinstrument/" + mutationOperator + "/" + (md+1) );
                        if( !instDirs.exists() ) {
                            instDirs.mkdir();
                        } //END if STATEMENT
                        if( !oinstDirs.exists() ) {
                            oinstDirs.mkdir();
                        } //END if STATEMENT
                        RandomAccessFile raf = new RandomAccessFile( instDirs.getAbsolutePath() + "/" + codeFile.getName(), "rw" );
                        String line = lnr.readLine();
                        RandomAccessFile oraf = new RandomAccessFile( oinstDirs.getAbsolutePath() + "/" + ocodeFile.getName(), "rw" );
                        String oline = "";
                        if( line.startsWith( "//" ) ) {
                            mutatedMethod = findMutatedMethod( line );
                            mutatedMethod = findMethodHeader( mutatedMethod, cComponents );
                            do {
                                if( line.contains( mutatedMethod ) ) {
                                    //Now we are in the method containing mutation
                                    isMutatedMethod = true;
                                    ArrayList tempList = new ArrayList();
                                    ArrayList otempList = new ArrayList();
                                    boolean mutation = false;
                                    int conditionCount = 0;
                                    ArrayList conditions = new ArrayList();
                                    ArrayList predicates = new ArrayList();
                                    int approach_level = 1;
                                    boolean ifWhileMutation = false;
                                    do {
                                        if( line.contains( "//Mutated with" ) && ( line.contains( "if(" ) || line.contains( "while(" ) ) ) {
                                            conditionCount++;
                                            predicates.add( line );
                                            ifWhileMutation = true;
                                            if( line.contains( "if(" ) ) {
                                                conditions.add( "if" );
                                            } else {
                                                conditions.add( "while" );                                                
                                            } //END if-else STATEMENT
                                        } //END if STATEMENT
                                        if( line.contains( "//Mutated with" ) ) {
                                            if( conditionCount==0 ) {
                                                String instrumentedString = "try {\n";
                                                instrumentedString += "java.io.File myFile = new java.io.File( traceFile );\n";
                                                instrumentedString += "java.io.RandomAccessFile raf = new java.io.RandomAccessFile( myFile, \"rw\" );\n";
                                                instrumentedString += "raf.skipBytes( ( int )myFile.length() );\n";
                                                instrumentedString += "raf.writeBytes( \"R: 0 0.0\" );\n";
                                                instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                instrumentedString += "raf.close();\n";
                                                instrumentedString += "} catch( Exception e ) { }\n";
                                                tempList.add( instrumentedString );
                                                instrumentedString = "try {\n";
                                                instrumentedString += "java.io.File myFile = new java.io.File( traceFile );\n";
                                                instrumentedString += "java.io.RandomAccessFile raf = new java.io.RandomAccessFile( myFile, \"rw\" );\n";
                                                instrumentedString += "raf.skipBytes( ( int )myFile.length() );\n";
                                                String oinstrumentedString = "try {\n";
                                                oinstrumentedString += "java.io.File myFile = new java.io.File( traceFile );\n";
                                                oinstrumentedString += "java.io.RandomAccessFile raf = new java.io.RandomAccessFile( myFile, \"rw\" );\n";
                                                oinstrumentedString += "raf.skipBytes( ( int )myFile.length() );\n";
                                                if( line.contains( "Mutated with ABS" ) ) {
                                                    instrumentedString += "raf.writeBytes( \"N: \" + " + getIdentifier( line ) + ");\n";
                                                    instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    instrumentedString += "raf.close();\n";
                                                    instrumentedString += "} catch( Exception e ) { }\n";
                                                    tempList.add( line );
                                                    tempList.add( instrumentedString );
                                                    oinstrumentedString += "raf.writeBytes( \"N: \" + " + getIdentifier( oline ) + ");\n";
                                                    oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    oinstrumentedString += "raf.close();\n";
                                                    oinstrumentedString += "} catch( Exception e ) { }\n";
                                                    otempList.add( oline );
                                                    otempList.add( oinstrumentedString );
                                                } else if( line.contains( "Mutated with AOR" ) ) {
                                                    instrumentedString += "raf.writeBytes( \"N: \" + " + getIdentifier( line ) + ");\n";
                                                    instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    instrumentedString += "raf.close();\n";
                                                    instrumentedString += "} catch( Exception e ) { }\n";
                                                    tempList.add( line );
                                                    tempList.add( instrumentedString );
                                                    oinstrumentedString += "raf.writeBytes( \"N: \" + " + getIdentifier( oline ) + ");\n";
                                                    oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    oinstrumentedString += "raf.close();\n";
                                                    oinstrumentedString += "} catch( Exception e ) { }\n";
                                                    otempList.add( oline );
                                                    otempList.add( oinstrumentedString );
                                                } else if( line.contains( "Mutated with ROR" ) ) {
                                                    instrumentedString += "raf.writeBytes( \"N: true\" );\n";
                                                    instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    instrumentedString += "raf.close();\n";
                                                    instrumentedString += "} catch( Exception e ) { }\n";
                                                    tempList.add( line );
                                                    tempList.add( instrumentedString );
                                                    oinstrumentedString += "raf.writeBytes( \"N: true\" );\n";
                                                    oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    oinstrumentedString += "raf.close();\n";
                                                    oinstrumentedString += "} catch( Exception e ) { }\n";
                                                    otempList.add( oline );
                                                    otempList.add( oinstrumentedString );
                                                } else if( line.contains( "Mutated with LCR" ) ) {
                                                    instrumentedString += "raf.writeBytes( \"N: true\" );\n";
                                                    instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    instrumentedString += "raf.close();\n";
                                                    instrumentedString += "} catch( Exception e ) { }\n";
                                                    tempList.add( line );
                                                    tempList.add( instrumentedString );
                                                    oinstrumentedString += "raf.writeBytes( \"N: true\" );\n";
                                                    oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    oinstrumentedString += "raf.close();\n";
                                                    oinstrumentedString += "} catch( Exception e ) { }\n";
                                                    otempList.add( oline );
                                                    otempList.add( oinstrumentedString );
                                                } else if( line.contains( "Mutated with UOI" ) ) {
                                                    instrumentedString += "raf.writeBytes( \"N: \" + " + getIdentifier( line ) + ");\n";
                                                    instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    instrumentedString += "raf.close();\n";
                                                    instrumentedString += "} catch( Exception e ) { }\n";
                                                    tempList.add( line );
                                                    tempList.add( instrumentedString );
                                                    oinstrumentedString += "raf.writeBytes( \"N: \" + " + getIdentifier( oline ) + ");\n";
                                                    oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    oinstrumentedString += "raf.close();\n";
                                                    oinstrumentedString += "} catch( Exception e ) { }\n";
                                                    otempList.add( oline );
                                                    otempList.add( oinstrumentedString );
                                                } else if( line.contains( "Mutated with JID" ) ) {
                                                    instrumentedString += "raf.writeBytes( \"N: \" + " + getIdentifier( line ) + ");\n";
                                                    instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    instrumentedString += "raf.close();\n";
                                                    instrumentedString += "} catch( Exception e ) { }\n";
                                                    tempList.add( line );
                                                    tempList.add( instrumentedString );
                                                    oinstrumentedString += "raf.writeBytes( \"N: \" + " + getIdentifier( oline ) + ");\n";
                                                    oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    oinstrumentedString += "raf.close();\n";
                                                    oinstrumentedString += "} catch( Exception e ) { }\n";
                                                    otempList.add( oline );
                                                    otempList.add( oinstrumentedString );
                                                } else if( line.contains( "Mutated with OMD" ) ) {
                                                    instrumentedString += "raf.writeBytes( \"N: \" + " + getIdentifier( line ) + ");\n";
                                                    instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    instrumentedString += "raf.close();\n";
                                                    instrumentedString += "} catch( Exception e ) { }\n";
                                                    tempList.add( line );
                                                    tempList.add( instrumentedString );
                                                    oinstrumentedString += "raf.writeBytes( \"N: \" + " + getIdentifier( oline ) + ");\n";
                                                    oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    oinstrumentedString += "raf.close();\n";
                                                    oinstrumentedString += "} catch( Exception e ) { }\n";
                                                    otempList.add( oline );
                                                    otempList.add( oinstrumentedString );
                                                } else if( line.contains( "Mutated with IOP" ) ) {
                                                    instrumentedString += "raf.writeBytes( \"N: \" + " + getIdentifier( line ) + ");\n";
                                                    instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    instrumentedString += "raf.close();\n";
                                                    instrumentedString += "} catch( Exception e ) { }\n";
                                                    tempList.add( line );
                                                    tempList.add( instrumentedString );
                                                    oinstrumentedString += "raf.writeBytes( \"N: \" + " + getIdentifier( oline ) + ");\n";
                                                    oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    oinstrumentedString += "raf.close();\n";
                                                    oinstrumentedString += "} catch( Exception e ) { }\n";
                                                    otempList.add( oline );
                                                    otempList.add( oinstrumentedString );
                                                } else if( line.contains( "Mutated with PNC" ) ) {
                                                    instrumentedString += "raf.writeBytes( \"N: \" + " + getObject( line ) + ".toString() );\n";
                                                    instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    instrumentedString += "raf.close();\n";
                                                    instrumentedString += "} catch( Exception e ) { }\n";
                                                    tempList.add( line );
                                                    tempList.add( instrumentedString );
                                                    oinstrumentedString += "raf.writeBytes( \"N: \" + " + getObject( oline ) + ".toString() );\n";
                                                    oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    oinstrumentedString += "raf.close();\n";
                                                    oinstrumentedString += "} catch( Exception e ) { }\n";
                                                    otempList.add( oline );
                                                    otempList.add( oinstrumentedString );
                                                } else if( line.contains( "Mutated with EOC" ) ) {
                                                    instrumentedString += "raf.writeBytes( \"N: true\" );\n";
                                                    instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    instrumentedString += "raf.close();\n";
                                                    instrumentedString += "} catch( Exception e ) { }\n";
                                                    tempList.add( line );
                                                    tempList.add( instrumentedString );
                                                    oinstrumentedString += "raf.writeBytes( \"N: true\" );\n";
                                                    oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    oinstrumentedString += "raf.close();\n";
                                                    oinstrumentedString += "} catch( Exception e ) { }\n";
                                                    otempList.add( oline );
                                                    otempList.add( oinstrumentedString );
                                                } //END if-else-if STATEMENTS
                                            } else {
                                                String instrumentedString = "try {\n";
                                                instrumentedString += "java.io.File myFile = new java.io.File( traceFile );\n";
                                                instrumentedString += "java.io.RandomAccessFile raf = new java.io.RandomAccessFile( myFile, \"rw\" );\n";
                                                instrumentedString += "raf.skipBytes( ( int )myFile.length() );\n";
                                                instrumentedString += "raf.writeBytes( \"R: 0 0.0\" );\n";
                                                instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                instrumentedString += "raf.close();\n";
                                                instrumentedString += "} catch( Exception e ) { }\n";
                                                tempList.add( instrumentedString );
                                                instrumentedString = "try {\n";
                                                instrumentedString += "java.io.File myFile = new java.io.File( traceFile );\n";
                                                instrumentedString += "java.io.RandomAccessFile raf = new java.io.RandomAccessFile( myFile, \"rw\" );\n";
                                                instrumentedString += "raf.skipBytes( ( int )myFile.length() );\n";
                                                String oinstrumentedString = "try {\n";
                                                oinstrumentedString += "java.io.File myFile = new java.io.File( traceFile );\n";
                                                oinstrumentedString += "java.io.RandomAccessFile raf = new java.io.RandomAccessFile( myFile, \"rw\" );\n";
                                                oinstrumentedString += "raf.skipBytes( ( int )myFile.length() );\n";
                                                if( line.contains( "Mutated with ABS" ) ) {
                                                    instrumentedString += "raf.writeBytes( \"N: \" + " + getIdentifier( line ) + ");\n";
                                                    instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    instrumentedString += "raf.close();\n";
                                                    instrumentedString += "} catch( Exception e ) { }\n";
                                                    tempList.add( line );
                                                    tempList.add( instrumentedString );
                                                    oinstrumentedString += "raf.writeBytes( \"N: \" + " + getIdentifier( oline ) + ");\n";
                                                    oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    oinstrumentedString += "raf.close();\n";
                                                    oinstrumentedString += "} catch( Exception e ) { }\n";
                                                    otempList.add( oline );
                                                    otempList.add( oinstrumentedString );
                                                } else if( line.contains( "Mutated with AOR" ) ) {
                                                    instrumentedString += "raf.writeBytes( \"N: \" + " + getIdentifier( line ) + ");\n";
                                                    instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    instrumentedString += "raf.close();\n";
                                                    instrumentedString += "} catch( Exception e ) { }\n";
                                                    tempList.add( line );
                                                    tempList.add( instrumentedString );
                                                    oinstrumentedString += "raf.writeBytes( \"N: \" + " + getIdentifier( oline ) + ");\n";
                                                    oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    oinstrumentedString += "raf.close();\n";
                                                    oinstrumentedString += "} catch( Exception e ) { }\n";
                                                    otempList.add( oline );
                                                    otempList.add( oinstrumentedString );
                                                } else if( line.contains( "Mutated with ROR" ) ) {
                                                    instrumentedString += "raf.writeBytes( \"N: true\" );\n";
                                                    instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    instrumentedString += "raf.close();\n";
                                                    instrumentedString += "} catch( Exception e ) { }\n";
                                                    tempList.add( line );
                                                    tempList.add( instrumentedString );
                                                    oinstrumentedString += "raf.writeBytes( \"N: true\" );\n";
                                                    oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    oinstrumentedString += "raf.close();\n";
                                                    oinstrumentedString += "} catch( Exception e ) { }\n";
                                                    otempList.add( oline );
                                                    otempList.add( oinstrumentedString );
                                                } else if( line.contains( "Mutated with LCR" ) ) {
                                                    instrumentedString += "raf.writeBytes( \"N: true\" );\n";
                                                    instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    instrumentedString += "raf.close();\n";
                                                    instrumentedString += "} catch( Exception e ) { }\n";
                                                    tempList.add( line );
                                                    tempList.add( instrumentedString );
                                                    oinstrumentedString += "raf.writeBytes( \"N: true\" );\n";
                                                    oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    oinstrumentedString += "raf.close();\n";
                                                    oinstrumentedString += "} catch( Exception e ) { }\n";
                                                    otempList.add( oline );
                                                    otempList.add( oinstrumentedString );
                                                } else if( line.contains( "Mutated with UOI" ) ) {
                                                    instrumentedString += "raf.writeBytes( \"N: \" + " + getIdentifier( line ) + ");\n";
                                                    instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    instrumentedString += "raf.close();\n";
                                                    instrumentedString += "} catch( Exception e ) { }\n";
                                                    tempList.add( line );
                                                    tempList.add( instrumentedString );
                                                    oinstrumentedString += "raf.writeBytes( \"N: \" + " + getIdentifier( oline ) + ");\n";
                                                    oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    oinstrumentedString += "raf.close();\n";
                                                    oinstrumentedString += "} catch( Exception e ) { }\n";
                                                    otempList.add( oline );
                                                    otempList.add( oinstrumentedString );
                                                } else if( line.contains( "Mutated with JID" ) ) {
                                                    instrumentedString += "raf.writeBytes( \"N: \" + " + getIdentifier( line ) + ");\n";
                                                    instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    instrumentedString += "raf.close();\n";
                                                    instrumentedString += "} catch( Exception e ) { }\n";
                                                    tempList.add( line );
                                                    tempList.add( instrumentedString );
                                                    oinstrumentedString += "raf.writeBytes( \"N: \" + " + getIdentifier( oline ) + ");\n";
                                                    oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    oinstrumentedString += "raf.close();\n";
                                                    oinstrumentedString += "} catch( Exception e ) { }\n";
                                                    otempList.add( oline );
                                                    otempList.add( oinstrumentedString );
                                                } else if( line.contains( "Mutated with OMD" ) ) {
                                                    instrumentedString += "raf.writeBytes( \"N: \" + " + getIdentifier( line ) + ");\n";
                                                    instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    instrumentedString += "raf.close();\n";
                                                    instrumentedString += "} catch( Exception e ) { }\n";
                                                    tempList.add( line );
                                                    tempList.add( instrumentedString );
                                                    oinstrumentedString += "raf.writeBytes( \"N: \" + " + getIdentifier( oline ) + ");\n";
                                                    oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    oinstrumentedString += "raf.close();\n";
                                                    oinstrumentedString += "} catch( Exception e ) { }\n";
                                                    otempList.add( oline );
                                                    otempList.add( oinstrumentedString );
                                                } else if( line.contains( "Mutated with IOP" ) ) {
                                                    instrumentedString += "raf.writeBytes( \"N: \" + " + getIdentifier( line ) + ");\n";
                                                    instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    instrumentedString += "raf.close();\n";
                                                    instrumentedString += "} catch( Exception e ) { }\n";
                                                    tempList.add( line );
                                                    tempList.add( instrumentedString );
                                                    oinstrumentedString += "raf.writeBytes( \"N: \" + " + getIdentifier( oline ) + ");\n";
                                                    oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    oinstrumentedString += "raf.close();\n";
                                                    oinstrumentedString += "} catch( Exception e ) { }\n";
                                                    otempList.add( oline );
                                                    otempList.add( oinstrumentedString );
                                                } else if( line.contains( "Mutated with PNC" ) ) {
                                                    instrumentedString += "raf.writeBytes( \"N: \" + " + getObject( line ) + ".toString() );\n";
                                                    instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    instrumentedString += "raf.close();\n";
                                                    instrumentedString += "} catch( Exception e ) { }\n";
                                                    tempList.add( line );
                                                    tempList.add( instrumentedString );
                                                    oinstrumentedString += "raf.writeBytes( \"N: \" + " + getObject( oline ) + ".toString() );\n";
                                                    oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    oinstrumentedString += "raf.close();\n";
                                                    oinstrumentedString += "} catch( Exception e ) { }\n";
                                                    otempList.add( oline );
                                                    otempList.add( oinstrumentedString );
                                                } else if( line.contains( "Mutated with EOC" ) ) {
                                                    instrumentedString += "raf.writeBytes( \"N: true\" );\n";
                                                    instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    instrumentedString += "raf.close();\n";
                                                    instrumentedString += "} catch( Exception e ) { }\n";
                                                    tempList.add( line );
                                                    tempList.add( instrumentedString );
                                                    oinstrumentedString += "raf.writeBytes( \"N: true\" );\n";
                                                    oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                    oinstrumentedString += "raf.close();\n";
                                                    oinstrumentedString += "} catch( Exception e ) { }\n";
                                                    otempList.add( oline );
                                                    otempList.add( oinstrumentedString );
                                                } //END if-else-if STATEMENTS    
                                                while( conditionCount>0 ) {
                                                    line = lnr.readLine();
                                                    oline = olnr.readLine();
                                                    if( line.contains( "if(" ) || line.contains( "while(" ) ) {
                                                        tempList.add( line );
                                                        otempList.add( oline );
                                                        String innerCondSuffString = "S: " + codeFile.getName().substring( 0, codeFile.getName().length()-5 ) + " " + this.isMethodDeclaration( mutatedMethod, cComponents );
                                                        instrumentedString = "try {\n";
                                                        instrumentedString += "java.io.File myFile = new java.io.File( traceFile );\n";
                                                        instrumentedString += "java.io.RandomAccessFile raf = new java.io.RandomAccessFile( myFile, \"rw\" );\n";
                                                        instrumentedString += "raf.skipBytes( ( int )myFile.length() );\n";
                                                        oinstrumentedString = "try {\n";
                                                        oinstrumentedString += "java.io.File myFile = new java.io.File( traceFile );\n";
                                                        oinstrumentedString += "java.io.RandomAccessFile raf = new java.io.RandomAccessFile( myFile, \"rw\" );\n";
                                                        oinstrumentedString += "raf.skipBytes( ( int )myFile.length() );\n";
                                                        if( line.contains( "if(" ) ) {
                                                            innerCondSuffString += " if " + controlCount;
                                                            instrumentedString += "raf.writeBytes( \"" + innerCondSuffString + "\" );\n";
                                                            instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                            instrumentedString += "raf.close();\n";
                                                            instrumentedString += "} catch( Exception e ) { }\n";
                                                            oinstrumentedString += "raf.writeBytes( \"" + innerCondSuffString + "\" );\n";
                                                            oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                            oinstrumentedString += "raf.close();\n";
                                                            oinstrumentedString += "} catch( Exception e ) { }\n";
                                                            tempList.add( instrumentedString );
                                                            otempList.add( oinstrumentedString );
                                                        } else if( line.contains( "while(" ) ) {
                                                            innerCondSuffString += " while " + controlCount;
                                                            instrumentedString += "raf.writeBytes( \"" + innerCondSuffString + "\" );\n";
                                                            instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                            instrumentedString += "raf.close();\n";
                                                            instrumentedString += "} catch( Exception e ) { }\n";
                                                            oinstrumentedString += "raf.writeBytes( \"" + innerCondSuffString + "\" );\n";
                                                            oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                            oinstrumentedString += "raf.close();\n";
                                                            oinstrumentedString += "} catch( Exception e ) { }\n";
                                                            tempList.add( instrumentedString );
                                                            otempList.add( oinstrumentedString );
                                                        } else if( line.contains( "} else {" ) ) {
                                                            innerCondSuffString += " else " + controlCount;
                                                            instrumentedString += "raf.writeBytes( \"" + innerCondSuffString + "\" );\n";
                                                            instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                            instrumentedString += "raf.close();\n";
                                                            instrumentedString += "} catch( Exception e ) { }\n";
                                                            oinstrumentedString += "raf.writeBytes( \"" + innerCondSuffString + "\" );\n";
                                                            oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                            oinstrumentedString += "raf.close();\n";
                                                            oinstrumentedString += "} catch( Exception e ) { }\n";
                                                            tempList.add( instrumentedString );
                                                            otempList.add( oinstrumentedString );
                                                        } //END if-else STATEMENT                
                                                        controlCount++;
                                                        int innerConditions = 1;
                                                        do {
                                                            line = lnr.readLine();
                                                            oline = olnr.readLine();
                                                            innerCondSuffString = "S: " + codeFile.getName().substring( 0, codeFile.getName().length()-5 ) + " " + this.isMethodDeclaration( mutatedMethod, cComponents );
                                                            instrumentedString = "try {\n";
                                                            instrumentedString += "java.io.File myFile = new java.io.File( traceFile );\n";
                                                            instrumentedString += "java.io.RandomAccessFile raf = new java.io.RandomAccessFile( myFile, \"rw\" );\n";
                                                            instrumentedString += "raf.skipBytes( ( int )myFile.length() );\n";
                                                            oinstrumentedString = "try {\n";
                                                            oinstrumentedString += "java.io.File myFile = new java.io.File( traceFile );\n";
                                                            oinstrumentedString += "java.io.RandomAccessFile raf = new java.io.RandomAccessFile( myFile, \"rw\" );\n";
                                                            oinstrumentedString += "raf.skipBytes( ( int )myFile.length() );\n";
                                                            if( line.contains( "if(" ) ) {
                                                                tempList.add( line );
                                                                otempList.add( oline );
                                                                innerCondSuffString += " if "  + controlCount;
                                                                instrumentedString += "raf.writeBytes( \"" + innerCondSuffString + "\" );\n";
                                                                instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                                instrumentedString += "raf.close();\n";
                                                                instrumentedString += "} catch( Exception e ) { }\n";
                                                                oinstrumentedString += "raf.writeBytes( \"" + innerCondSuffString + "\" );\n";
                                                                oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                                oinstrumentedString += "raf.close();\n";
                                                                oinstrumentedString += "} catch( Exception e ) { }\n";
                                                                tempList.add( instrumentedString );
                                                                otempList.add( oinstrumentedString );
                                                                innerConditions++;
                                                                controlCount++;
                                                            } else if( line.contains( "while(" ) ) {
                                                                tempList.add( line );
                                                                otempList.add( oline );
                                                                innerCondSuffString += " while "  + controlCount;
                                                                instrumentedString += "raf.writeBytes( \"" + innerCondSuffString + "\" );\n";
                                                                instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                                instrumentedString += "raf.close();\n";
                                                                instrumentedString += "} catch( Exception e ) { }\n";
                                                                oinstrumentedString += "raf.writeBytes( \"" + innerCondSuffString + "\" );\n";
                                                                oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                                oinstrumentedString += "raf.close();\n";
                                                                oinstrumentedString += "} catch( Exception e ) { }\n";
                                                                tempList.add( instrumentedString );
                                                                otempList.add( oinstrumentedString );
                                                                innerConditions++;
                                                                controlCount++;
                                                            } else if( line.contains( "} else {" ) ) {
                                                                tempList.add( line );
                                                                otempList.add( oline );
                                                                innerCondSuffString += " else " + controlCount;
                                                                instrumentedString += "raf.writeBytes( \"" + innerCondSuffString + "\" );\n";
                                                                instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                                instrumentedString += "raf.close();\n";
                                                                instrumentedString += "} catch( Exception e ) { }\n";
                                                                oinstrumentedString += "raf.writeBytes( \"" + innerCondSuffString + "\" );\n";
                                                                oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                                oinstrumentedString += "raf.close();\n";
                                                                oinstrumentedString += "} catch( Exception e ) { }\n";
                                                                tempList.add( instrumentedString );
                                                                otempList.add( oinstrumentedString );
                                                                controlCount++;
                                                            } else if( line.contains( "}" ) ) {
                                                                tempList.add( line );
                                                                otempList.add( oline );
                                                                innerConditions--;
                                                            } else {
                                                                tempList.add( line );
                                                                otempList.add( oline );
                                                            } //END if-else STATEMENT
                                                        } while( innerConditions>0 );
                                                    } else if( line.contains( "} else {" ) ) {
                                                        tempList.add( line );
                                                        otempList.add( oline );
                                                        String condition = ( String )predicates.get( predicates.size()-1 );
                                                        predicates.remove( predicates.size()-1 );
                                                        conditions.remove( conditions.size()-1 );

                                                        instrumentedString = "try {\n";
                                                        instrumentedString += "java.io.File myFile = new java.io.File( traceFile );\n";
                                                        instrumentedString += "java.io.RandomAccessFile raf = new java.io.RandomAccessFile( myFile, \"rw\" );\n";
                                                        instrumentedString += "raf.skipBytes( ( int )myFile.length() );\n";
                                                        instrumentedString += "float local_fitness = 0.0f;\n";
                                                        instrumentedString += "float fitness = 0.0f;\n";
                                                        instrumentedString += "int local_count = 0;\n";
                                                        oinstrumentedString = "try {\n";
                                                        oinstrumentedString += "java.io.File myFile = new java.io.File( traceFile );\n";
                                                        oinstrumentedString += "java.io.RandomAccessFile raf = new java.io.RandomAccessFile( myFile, \"rw\" );\n";
                                                        oinstrumentedString += "raf.skipBytes( ( int )myFile.length() );\n";
                                                        if( condition.contains( "this" ) ) {
                                                            String[] predComps = this.getReferenceConditionOperands( condition );
                                                            instrumentedString += "int stringWeight1 = 0;\n";
                                                            instrumentedString += "int stringWeight2 = 0;\n";
                                                            instrumentedString += "String _string1 = " + predComps[ 0 ] + ".toString();\n";
                                                            instrumentedString += "String _string2 = " + predComps[ 1 ] + ".toString();\n";
                                                            instrumentedString += "for( int _s1=0; _s1<_string1.length(); _s1++ ) {\n";
                                                            instrumentedString += "stringWeight1 += _string1.charAt( _s1 );\n";
                                                            instrumentedString += "}\n";
                                                            instrumentedString += "for( int _s2=0; _s2<_string2.length(); _s2++ ) {\n";
                                                            instrumentedString += "stringWeight2 += _string2.charAt( _s2 );\n";
                                                            instrumentedString += "}\n";
                                                            instrumentedString += "local_fitness = ( float)( stringWeight1 - stringWeight2 ) / ( stringWeight1 + stringWeight2 );\n";
                                                            instrumentedString += "local_fitness = Math.abs( local_fitness );\n";
                                                        } else {
                                                            ArrayList predicateList = getPredicateList( condition );
                                                            int localCounter = 0;
                                                            for( int p=0; p<predicateList.size(); p++ ) {
                                                                String predicate = ( String )predicateList.get( p );
                                                                String[] predComps = getPredicateComponents( predicate );
                                                                String operand1 = predComps[ 0 ];
                                                                String operator = predComps[ 1 ];
                                                                String operand2 = predComps[ 2 ];
                                                                instrumentedString += "if( !(" + predicate + ") ) { \n";
                                                                if( operator.equals( "<=" ) ) {
                                                                    instrumentedString += "fitness = ( float )( " + operand1 + " - " + operand2 + " ) / ( float )( " + operand1 + " + " + operand2 + "); \n";
                                                                    instrumentedString += "fitness = Math.abs( fitness );\n";
                                                                } else if( operator.equals( "<" ) ) {
                                                                    instrumentedString += "fitness = ( float )( (" + operand1 + "+1) - " + operand2 + " ) / ( float )( (" + operand1 + "+1) + " + operand2 + "); \n";
                                                                    instrumentedString += "fitness = Math.abs( fitness );\n";
                                                                } else if( operator.equals( ">=" ) ) {
                                                                    instrumentedString += "fitness = ( float )( " + operand2 + " - " + operand1 + " ) / ( float )( " + operand2 + " + " + operand1 + "); \n";
                                                                    instrumentedString += "fitness = Math.abs( fitness );\n";
                                                                } else if( operator.equals( ">" ) ) {
                                                                    instrumentedString += "fitness = ( float )( (" + operand2 + "+1) - " + operand1 + " ) / ( float )( (" + operand2 + "+1) + " + operand1 + "); \n";
                                                                    instrumentedString += "fitness = Math.abs( fitness );\n";
                                                                } else if( operator.equals( "==" ) ) {
                                                                    instrumentedString += "fitness = ( float )( " + operand2 + " - " + operand1 + " ) / ( float )( " + operand2 + " + " + operand1 + "); \n";
                                                                    instrumentedString += "fitness = Math.abs( fitness );\n";
                                                                } else if( operator.equals( "!=" ) ) {
                                                                    instrumentedString += "fitness = 0.5f;\n";
                                                                } //END if-else STATEMENT
                                                                instrumentedString += "local_fitness += fitness;\n";
                                                                instrumentedString += "local_count++;\n";
                                                                localCounter++;
                                                                instrumentedString += "}\n";
                                                            } //END for LOOP
                                                            if( localCounter>1 ) {
                                                                instrumentedString += "if( local_count>1 ) {\n";
                                                                instrumentedString += "local_fitness *= 0.5f;\n";
                                                                instrumentedString += "}\n";
                                                            } //END if STATEMENT
                                                        } //END if-else STATEMENT
                                                        
                                                        instrumentedString += "raf.writeBytes( \"R: " + approach_level + " \" + local_fitness );\n";
                                                        instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                        if( ifWhileMutation ) {
                                                            instrumentedString += "raf.writeBytes( \"N: false\" );\n";
                                                            instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                            oinstrumentedString += "raf.writeBytes( \"N: false\" );\n";
                                                            oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                        } else {
                                                            instrumentedString += "raf.writeBytes( \"N: c\" );\n";
                                                            instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                            instrumentedString += "raf.writeBytes( \"S: c\" );\n";
                                                        } //END if-else STATEMENT
                                                        instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                        instrumentedString += "raf.close();\n";
                                                        instrumentedString += "} catch( Exception e ) { }\n";
                                                        tempList.add( instrumentedString );
                                                        oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                        oinstrumentedString += "raf.close();\n";
                                                        oinstrumentedString += "} catch( Exception e ) { }\n";
                                                        otempList.add( oinstrumentedString );
                                                        
                                                        approach_level++;
                                                        conditions.add( "else" );
                                                        predicates.add( line );
                                                        conditionCount--;
                                                    } else if( line.contains( "}" ) ) {
                                                        String condition1 = ( String )conditions.get( conditions.size()-1 );
                                                        String condition = ( String )predicates.get( predicates.size()-1 );
                                                        if( condition1.equals( "if" ) ) {
                                                            predicates.remove( predicates.size()-1 );
                                                            conditions.remove( conditions.size()-1 );
                                                            tempList.add( "} else {" );
                                                            otempList.add( "} else {" );
                                                        } else if( condition1.equals( "while" ) ) {
                                                            tempList.add( "}" );
                                                            otempList.add( "}" );
                                                            String whilePredicate = getWhilePredicate( condition );
                                                            System.out.println( "WP: " + whilePredicate );
                                                            tempList.add( "if( !(" + whilePredicate + ") ) {" );
                                                            otempList.add( "if( !(" + whilePredicate + ") ) {" );
                                                        } else {
                                                            predicates.remove( predicates.size()-1 );
                                                            conditions.remove( conditions.size()-1 );
                                                            tempList.add( "}" );
                                                            otempList.add( "}" );
                                                            condition = ( String )predicates.get( predicates.size()-1 );
                                                            tempList.add( condition );
                                                            otempList.add( condition );
                                                        } //END if-else STATEMENT

                                                        instrumentedString = "try {\n";
                                                        instrumentedString += "java.io.File myFile = new java.io.File( traceFile );\n";
                                                        instrumentedString += "java.io.RandomAccessFile raf = new java.io.RandomAccessFile( myFile, \"rw\" );\n";
                                                        instrumentedString += "raf.skipBytes( ( int )myFile.length() );\n";
                                                        instrumentedString += "float local_fitness = 0.0f;\n";
                                                        instrumentedString += "float fitness = 0.0f;\n";
                                                        instrumentedString += "int local_count = 0;\n";
                                                        oinstrumentedString = "try {\n";
                                                        oinstrumentedString += "java.io.File myFile = new java.io.File( traceFile );\n";
                                                        oinstrumentedString += "java.io.RandomAccessFile raf = new java.io.RandomAccessFile( myFile, \"rw\" );\n";
                                                        oinstrumentedString += "raf.skipBytes( ( int )myFile.length() );\n";
                                                        if( condition.contains( "this" ) ) {
                                                            String[] predComps = this.getReferenceConditionOperands( condition );
                                                            instrumentedString += "int stringWeight1 = 0;\n";
                                                            instrumentedString += "int stringWeight2 = 0;\n";
                                                            instrumentedString += "String _string1 = " + predComps[ 0 ] + ".toString();\n";
                                                            instrumentedString += "String _string2 = " + predComps[ 1 ] + ".toString();\n";
                                                            instrumentedString += "for( int _s1=0; _s1<_string1.length(); _s1++ ) {\n";
                                                            instrumentedString += "stringWeight1 += _string1.charAt( _s1 );\n";
                                                            instrumentedString += "}\n";
                                                            instrumentedString += "for( int _s2=0; _s2<_string2.length(); _s2++ ) {\n";
                                                            instrumentedString += "stringWeight2 += _string2.charAt( _s2 );\n";
                                                            instrumentedString += "}\n";
                                                            instrumentedString += "local_fitness = ( float)( stringWeight1 - stringWeight2 ) / ( stringWeight1 + stringWeight2 );\n";
                                                            instrumentedString += "local_fitness = Math.abs( local_fitness );\n";
                                                        } else {
                                                            ArrayList predicateList = getPredicateList( condition );
                                                            int localCounter = 0;
                                                            for( int p=0; p<predicateList.size(); p++ ) {
                                                                String predicate = ( String )predicateList.get( p );
                                                                String[] predComps = getPredicateComponents( predicate );
                                                                String operand1 = predComps[ 0 ];
                                                                String operator = predComps[ 1 ];
                                                                String operand2 = predComps[ 2 ];
                                                                instrumentedString += "if( !(" + predicate + ") ) { \n";
                                                                if( operator.equals( "<=" ) ) {
                                                                    instrumentedString += "fitness = ( float )( " + operand1 + " - " + operand2 + " ) / ( float )( " + operand1 + " + " + operand2 + "); \n";
                                                                    instrumentedString += "fitness = Math.abs( fitness );\n";
                                                                } else if( operator.equals( "<" ) ) {
                                                                    instrumentedString += "fitness = ( float )( (" + operand1 + "+1) - " + operand2 + " ) / ( float )( (" + operand1 + "+1) + " + operand2 + "); \n";
                                                                    instrumentedString += "fitness = Math.abs( fitness );\n";
                                                                } else if( operator.equals( ">=" ) ) {
                                                                    instrumentedString += "fitness = ( float )( " + operand2 + " - " + operand1 + " ) / ( float )( " + operand2 + " + " + operand1 + "); \n";
                                                                    instrumentedString += "fitness = Math.abs( fitness );\n";
                                                                } else if( operator.equals( ">" ) ) {
                                                                    instrumentedString += "fitness = ( float )( (" + operand2 + "+1) - " + operand1 + " ) / ( float )( (" + operand2 + "+1) + " + operand1 + "); \n";
                                                                    instrumentedString += "fitness = Math.abs( fitness );\n";
                                                                } else if( operator.equals( "==" ) ) {
                                                                    instrumentedString += "fitness = ( float )( " + operand2 + " - " + operand1 + " ) / ( float )( " + operand2 + " + " + operand1 + "); \n";
                                                                    instrumentedString += "fitness = Math.abs( fitness );\n";
                                                                } else if( operator.equals( "!=" ) ) {
                                                                    instrumentedString += "fitness = 0.5f;\n";
                                                                } //END if-else STATEMENT
                                                                instrumentedString += "local_fitness += fitness;\n";
                                                                instrumentedString += "local_count++;\n";
                                                                localCounter++;
                                                                instrumentedString += "}\n";
                                                            } //END for LOOP
                                                            if( localCounter>1 ) {
                                                                instrumentedString += "if( local_count>1 ) {\n";
                                                                instrumentedString += "local_fitness *= 0.5f;\n";
                                                                instrumentedString += "}\n";
                                                            } //END if STATEMENT
                                                        } //END if-else STATEMENT
                                                        
                                                        instrumentedString += "raf.writeBytes( \"R: " + approach_level + " \" + local_fitness );\n";
                                                        instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                        if( ifWhileMutation ) {
                                                            instrumentedString += "raf.writeBytes( \"N: false\" );\n";
                                                            instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                            oinstrumentedString += "raf.writeBytes( \"N: false\" );\n";
                                                            oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                        } else {
                                                            instrumentedString += "raf.writeBytes( \"N: c\" );\n";
                                                            instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                            instrumentedString += "raf.writeBytes( \"S: c\" );\n";
                                                        } //END if-else STATEMENT
                                                        instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                        instrumentedString += "raf.close();\n";
                                                        instrumentedString += "} catch( Exception e ) { }\n";
                                                        tempList.add( instrumentedString );
                                                        oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                        oinstrumentedString += "raf.close();\n";
                                                        oinstrumentedString += "} catch( Exception e ) { }\n";
                                                        otempList.add( oinstrumentedString );
                                                        
                                                        tempList.add( "}" );
                                                        otempList.add( "}" );
                                                        approach_level++;
                                                        conditionCount--;
                                                    } else {
                                                        tempList.add( line );
                                                        otempList.add( oline );
                                                    } //END if-else STATEMENT
                                                } //END while LOOP
                                            } //END if-else STATEMENT
                                            
                                            mutation = true;
                                        } else if( line.contains( "if(" ) ) {
                                            tempList.add( line );
                                            otempList.add( oline );
                                            conditionCount++;
                                            conditions.add( "if" );
                                            predicates.add( line );
                                            if( mutation ) {
                                                String innerCondSuffString = "S: " + codeFile.getName().substring( 0, codeFile.getName().length()-5 ) + " " + this.isMethodDeclaration( mutatedMethod, cComponents ) + " if " + controlCount;
                                                String instrumentedString = "try {\n";
                                                instrumentedString += "java.io.File myFile = new java.io.File( traceFile );\n";
                                                instrumentedString += "java.io.RandomAccessFile raf = new java.io.RandomAccessFile( myFile, \"rw\" );\n";
                                                instrumentedString += "raf.skipBytes( ( int )myFile.length() );\n";
                                                instrumentedString += "raf.writeBytes( \"" + innerCondSuffString + "\" );\n";
                                                instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                instrumentedString += "raf.close();\n";
                                                instrumentedString += "} catch( Exception e ) { }\n";
                                                String oinstrumentedString = "try {\n";
                                                oinstrumentedString += "java.io.File myFile = new java.io.File( traceFile );\n";
                                                oinstrumentedString += "java.io.RandomAccessFile raf = new java.io.RandomAccessFile( myFile, \"rw\" );\n";
                                                oinstrumentedString += "raf.skipBytes( ( int )myFile.length() );\n";
                                                oinstrumentedString += "raf.writeBytes( \"" + innerCondSuffString + "\" );\n";
                                                oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                oinstrumentedString += "raf.close();\n";
                                                oinstrumentedString += "} catch( Exception e ) { }\n";
                                                tempList.add( instrumentedString );
                                                otempList.add( oinstrumentedString );             
                                                controlCount++;
                                            } //END if STATEMENT
                                        } else if( line.contains( "while(" ) ) {
                                            tempList.add( line );
                                            otempList.add( oline );
                                            conditionCount++;
                                            conditions.add( "while" );
                                            predicates.add( line );
                                            if( mutation ) {
                                                String innerCondSuffString = "S: " + codeFile.getName().substring( 0, codeFile.getName().length()-5 ) + " " + this.isMethodDeclaration( mutatedMethod, cComponents ) + " while " + controlCount;
                                                String instrumentedString = "try {\n";
                                                instrumentedString += "java.io.File myFile = new java.io.File( traceFile );\n";
                                                instrumentedString += "java.io.RandomAccessFile raf = new java.io.RandomAccessFile( myFile, \"rw\" );\n";
                                                instrumentedString += "raf.skipBytes( ( int )myFile.length() );\n";
                                                instrumentedString += "raf.writeBytes( \"" + innerCondSuffString + "\" );\n";
                                                instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                instrumentedString += "raf.close();\n";
                                                instrumentedString += "} catch( Exception e ) { }\n";
                                                String oinstrumentedString = "try {\n";
                                                oinstrumentedString += "java.io.File myFile = new java.io.File( traceFile );\n";
                                                oinstrumentedString += "java.io.RandomAccessFile raf = new java.io.RandomAccessFile( myFile, \"rw\" );\n";
                                                oinstrumentedString += "raf.skipBytes( ( int )myFile.length() );\n";
                                                oinstrumentedString += "raf.writeBytes( \"" + innerCondSuffString + "\" );\n";
                                                oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                oinstrumentedString += "raf.close();\n";
                                                oinstrumentedString += "} catch( Exception e ) { }\n";
                                                tempList.add( instrumentedString );
                                                otempList.add( oinstrumentedString );        
                                                controlCount++;
                                            } //END if STATEMENT
                                        } else if( line.contains( "} else {" ) ) {
                                            tempList.add( line );
                                            otempList.add( oline );
                                            conditions.add( "else" );
                                            predicates.add( line );
                                            if( mutation ) {
                                                String innerCondSuffString = "S: " + codeFile.getName().substring( 0, codeFile.getName().length()-5 ) + " " + this.isMethodDeclaration( mutatedMethod, cComponents ) + " else " + controlCount;
                                                String instrumentedString = "try {\n";
                                                instrumentedString += "java.io.File myFile = new java.io.File( traceFile );\n";
                                                instrumentedString += "java.io.RandomAccessFile raf = new java.io.RandomAccessFile( myFile, \"rw\" );\n";
                                                instrumentedString += "raf.skipBytes( ( int )myFile.length() );\n";
                                                instrumentedString += "raf.writeBytes( \"" + innerCondSuffString + "\" );\n";
                                                instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                instrumentedString += "raf.close();\n";
                                                instrumentedString += "} catch( Exception e ) { }\n";
                                                String oinstrumentedString = "try {\n";
                                                oinstrumentedString += "java.io.File myFile = new java.io.File( traceFile );\n";
                                                oinstrumentedString += "java.io.RandomAccessFile raf = new java.io.RandomAccessFile( myFile, \"rw\" );\n";
                                                oinstrumentedString += "raf.skipBytes( ( int )myFile.length() );\n";
                                                oinstrumentedString += "raf.writeBytes( \"" + innerCondSuffString + "\" );\n";
                                                oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                oinstrumentedString += "raf.close();\n";
                                                oinstrumentedString += "} catch( Exception e ) { }\n";
                                                tempList.add( instrumentedString );
                                                otempList.add( oinstrumentedString );    
                                                controlCount++;
                                            } //END if STATEMENT
                                        } else if( line.contains( "}" ) ) {
                                            if( conditionCount==0 || conditionCount==1 ) {
                                                tempList.add( line );
                                                otempList.add( oline );
                                                for( int t=0; t<tempList.size(); t++ ) {
                                                    String tempLine = ( String )tempList.get( t );
                                                    instrumentedCode += tempLine;
                                                    instrumentedCode += "\n";
                                                } //END for LOOP
                                                tempList.clear();
                                                for( int t=0; t<otempList.size(); t++ ) {
                                                    String otempLine = ( String )otempList.get( t );
                                                    oinstrumentedCode += otempLine;
                                                    oinstrumentedCode += "\n";
                                                } //END for LOOP
                                                otempList.clear();
                                            } else {
                                                tempList.add( line );
                                                otempList.add( oline );
                                            }//END if STATEMENT
                                            if( conditionCount==0 ) {
                                                mutation = true;
                                            } //END if STATEMENT
                                            conditionCount--;
                                        } else {
                                            if( conditionCount==0 ) {
                                                instrumentedCode += line;
                                                instrumentedCode += "\n";
                                                oinstrumentedCode += oline;
                                                oinstrumentedCode += "\n";
                                                if( line.contains( "return ") ) {
                                                    System.out.println( "Return: " + line );
                                                } //END if STATEMENT
                                            } else {
                                                tempList.add( line );
                                                otempList.add( oline );
                                            } //END if-else STATEMENT
                                        } //END if-else STATEMENT
                                        if( !mutation ) {
                                            line = lnr.readLine();
                                            oline = olnr.readLine();
                                        } //END if STATEMENT
                                    } while( line!=null && !mutation );
                                    while( mutation ) {
                                        for( int t=0; t<tempList.size(); t++ ) {
                                            String tempLine = ( String )tempList.get( t );
                                            instrumentedCode += tempLine;
                                            instrumentedCode += "\n";
                                        } //END for LOOP
                                        for( int t=0; t<otempList.size(); t++ ) {
                                            String otempLine = ( String )otempList.get( t );
                                            oinstrumentedCode += otempLine;
                                            oinstrumentedCode += "\n";
                                        } //END for LOOP
                                        mutation = false;
                                    } //END while LOOP
                                } else {
                                    String methodName = isMethodDeclaration( line, cComponents );
                                    if( isMutatedMethod && line.contains( "if(" ) ) {
                                        instrumentedCode += line;
                                        instrumentedCode += "\n";
                                        oinstrumentedCode += oline;
                                        oinstrumentedCode += "\n";
                                        String innerCondSuffString = "S: " + codeFile.getName().substring( 0, codeFile.getName().length()-5 ) + " " + this.isMethodDeclaration( mutatedMethod, cComponents ) + " if " + controlCount;
                                        String instrumentedString = "try {\n";
                                        instrumentedString += "java.io.File myFile = new java.io.File( traceFile );\n";
                                        instrumentedString += "java.io.RandomAccessFile raf = new java.io.RandomAccessFile( myFile, \"rw\" );\n";
                                        instrumentedString += "raf.skipBytes( ( int )myFile.length() );\n";
                                        instrumentedString += "raf.writeBytes( \"" + innerCondSuffString + "\" );\n";
                                        instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                        instrumentedString += "raf.close();\n";
                                        instrumentedString += "} catch( Exception e ) { }\n";
                                        String oinstrumentedString = "try {\n";
                                        oinstrumentedString += "java.io.File myFile = new java.io.File( traceFile );\n";
                                        oinstrumentedString += "java.io.RandomAccessFile raf = new java.io.RandomAccessFile( myFile, \"rw\" );\n";
                                        oinstrumentedString += "raf.skipBytes( ( int )myFile.length() );\n";
                                        oinstrumentedString += "raf.writeBytes( \"" + innerCondSuffString + "\" );\n";
                                        oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                        oinstrumentedString += "raf.close();\n";
                                        oinstrumentedString += "} catch( Exception e ) { }\n";
                                        instrumentedCode += instrumentedString;
                                        oinstrumentedCode += oinstrumentedString;
                                        controlCount++;
                                        methodEndCount++;
                                    } else if( isMutatedMethod && line.contains( "else" ) ) {
                                        instrumentedCode += line;
                                        instrumentedCode += "\n";
                                        oinstrumentedCode += oline;
                                        oinstrumentedCode += "\n";
                                        String innerCondSuffString = "S: " + codeFile.getName().substring( 0, codeFile.getName().length()-5 ) + " " + this.isMethodDeclaration( mutatedMethod, cComponents ) + " else " + controlCount;
                                        String instrumentedString = "try {\n";
                                        instrumentedString += "java.io.File myFile = new java.io.File( traceFile );\n";
                                        instrumentedString += "java.io.RandomAccessFile raf = new java.io.RandomAccessFile( myFile, \"rw\" );\n";
                                        instrumentedString += "raf.skipBytes( ( int )myFile.length() );\n";
                                        instrumentedString += "raf.writeBytes( \"" + innerCondSuffString + "\" );\n";
                                        instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                        instrumentedString += "raf.close();\n";
                                        instrumentedString += "} catch( Exception e ) { }\n";
                                        String oinstrumentedString = "try {\n";
                                        oinstrumentedString += "java.io.File myFile = new java.io.File( traceFile );\n";
                                        oinstrumentedString += "java.io.RandomAccessFile raf = new java.io.RandomAccessFile( myFile, \"rw\" );\n";
                                        oinstrumentedString += "raf.skipBytes( ( int )myFile.length() );\n";
                                        oinstrumentedString += "raf.writeBytes( \"" + innerCondSuffString + "\" );\n";
                                        oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                        oinstrumentedString += "raf.close();\n";
                                        oinstrumentedString += "} catch( Exception e ) { }\n";
                                        instrumentedCode += instrumentedString;
                                        oinstrumentedCode += oinstrumentedString;
                                        controlCount++;
                                        methodEndCount++;
                                    } else if( isMutatedMethod && line.contains( "while(" ) ) {
                                        instrumentedCode += line;
                                        instrumentedCode += "\n";
                                        oinstrumentedCode += oline;
                                        oinstrumentedCode += "\n";
                                        String innerCondSuffString = "S: " + codeFile.getName().substring( 0, codeFile.getName().length()-5 ) + " " + this.isMethodDeclaration( mutatedMethod, cComponents ) + " while " + controlCount;
                                        String instrumentedString = "try {\n";
                                        instrumentedString += "java.io.File myFile = new java.io.File( traceFile );\n";
                                        instrumentedString += "java.io.RandomAccessFile raf = new java.io.RandomAccessFile( myFile, \"rw\" );\n";
                                        instrumentedString += "raf.skipBytes( ( int )myFile.length() );\n";
                                        instrumentedString += "raf.writeBytes( \"" + innerCondSuffString + "\" );\n";
                                        instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                        instrumentedString += "raf.close();\n";
                                        instrumentedString += "} catch( Exception e ) { }\n";
                                        String oinstrumentedString = "try {\n";
                                        oinstrumentedString += "java.io.File myFile = new java.io.File( traceFile );\n";
                                        oinstrumentedString += "java.io.RandomAccessFile raf = new java.io.RandomAccessFile( myFile, \"rw\" );\n";
                                        oinstrumentedString += "raf.skipBytes( ( int )myFile.length() );\n";
                                        oinstrumentedString += "raf.writeBytes( \"" + innerCondSuffString + "\" );\n";
                                        oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                        oinstrumentedString += "raf.close();\n";
                                        oinstrumentedString += "} catch( Exception e ) { }\n";
                                        instrumentedCode += instrumentedString;
                                        oinstrumentedCode += oinstrumentedString;
                                        controlCount++;
                                        methodEndCount++;
                                    } else if( methodName!=null && line.contains( "{" ) ) {
                                        instrumentedCode += line;
                                        instrumentedCode += "\n";
                                        oinstrumentedCode += oline;
                                        oinstrumentedCode += "\n";
                                        String className = codeFile.getName().substring( 0, codeFile.getName().length()-5 );
                                        String sufficiencyString = "S: " + className + " " + methodName + " " + methodCount;
                                        methodCount++;
                                        String instrumentedString = "try {\n";
                                        instrumentedString += "java.io.File myFile = new java.io.File( traceFile );\n";
                                        instrumentedString += "java.io.RandomAccessFile raf = new java.io.RandomAccessFile( myFile, \"rw\" );\n";
                                        instrumentedString += "raf.skipBytes( ( int )myFile.length() );\n";
                                        instrumentedString += "raf.writeBytes( \"" + sufficiencyString + "\" );\n";
                                        instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                        instrumentedString += "raf.close();\n";
                                        instrumentedString += "} catch( Exception e ) { }\n";
                                        instrumentedCode += instrumentedString;
                                        instrumentedCode += "\n";
                                        String oinstrumentedString = "try {\n";
                                        oinstrumentedString += "java.io.File myFile = new java.io.File( traceFile );\n";
                                        oinstrumentedString += "java.io.RandomAccessFile raf = new java.io.RandomAccessFile( myFile, \"rw\" );\n";
                                        oinstrumentedString += "raf.skipBytes( ( int )myFile.length() );\n";
                                        oinstrumentedString += "raf.writeBytes( \"" + sufficiencyString + "\" );\n";
                                        oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                        oinstrumentedString += "raf.close();\n";
                                        oinstrumentedString += "";
                                        oinstrumentedString += "} catch( Exception e ) { }\n";
                                        oinstrumentedCode += oinstrumentedString;
                                        oinstrumentedCode += "\n";
                                        isMutatedMethod = false;
                                    } else if( isMutatedMethod && line.contains( "return" ) ) {
                                        String identifier = this.getReturnIdentifier( line );
                                        System.out.println( "Return 2: " + identifier );
                                        String innerCondSuffString = "S: " + codeFile.getName().substring( 0, codeFile.getName().length()-5 ) + " " + this.isMethodDeclaration( mutatedMethod, cComponents );
                                        String instrumentedString = "try {\n";
                                        instrumentedString += "java.io.File myFile = new java.io.File( traceFile );\n";
                                        instrumentedString += "java.io.RandomAccessFile raf = new java.io.RandomAccessFile( myFile, \"rw\" );\n";
                                        instrumentedString += "raf.skipBytes( ( int )myFile.length() );\n";
                                        instrumentedString += "raf.writeBytes( \"" + innerCondSuffString + " \" + " + identifier + " );\n";
                                        instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                        instrumentedString += "raf.close();\n";
                                        instrumentedString += "} catch( Exception e ) { }\n";
                                        String oinstrumentedString = "try {\n";
                                        oinstrumentedString += "java.io.File myFile = new java.io.File( traceFile );\n";
                                        oinstrumentedString += "java.io.RandomAccessFile raf = new java.io.RandomAccessFile( myFile, \"rw\" );\n";
                                        oinstrumentedString += "raf.skipBytes( ( int )myFile.length() );\n";
                                        oinstrumentedString += "raf.writeBytes( \"" + innerCondSuffString + " \" + " + identifier + " );\n";
                                        oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                        oinstrumentedString += "raf.close();\n";
                                        oinstrumentedString += "} catch( Exception e ) { }\n";
                                        instrumentedCode += instrumentedString;
                                        oinstrumentedCode += oinstrumentedString;
                                        instrumentedCode += line;
                                        instrumentedCode += "\n";
                                        oinstrumentedCode += oline;
                                        oinstrumentedCode += "\n";
                                        isMutatedMethod = false;
                                    } else if( isMutatedMethod && line.contains( "}" ) ) {
                                        if( methodEndCount==0 ) {
                                            String innerCondSuffString = "S: " + codeFile.getName().substring( 0, codeFile.getName().length()-5 ) + " " + this.isMethodDeclaration( mutatedMethod, cComponents ) ;
                                            String instrumentedString = "try {\n";
                                            instrumentedString += "java.io.File myFile = new java.io.File( traceFile );\n";
                                            instrumentedString += "java.io.RandomAccessFile raf = new java.io.RandomAccessFile( myFile, \"rw\" );\n";
                                            instrumentedString += "raf.skipBytes( ( int )myFile.length() );\n";
                                            instrumentedString += "raf.writeBytes( \"" + innerCondSuffString + " \" + this.toString() );\n";
                                            instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                            instrumentedString += "raf.close();\n";
                                            instrumentedString += "} catch( Exception e ) { }\n";
                                            String oinstrumentedString = "try {\n";
                                            oinstrumentedString += "java.io.File myFile = new java.io.File( traceFile );\n";
                                            oinstrumentedString += "java.io.RandomAccessFile raf = new java.io.RandomAccessFile( myFile, \"rw\" );\n";
                                            oinstrumentedString += "raf.skipBytes( ( int )myFile.length() );\n";
                                            oinstrumentedString += "raf.writeBytes( \"" + innerCondSuffString + "\" + this.toString() );\n";
                                            oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                            oinstrumentedString += "raf.close();\n";
                                            oinstrumentedString += "} catch( Exception e ) { }\n";
                                            instrumentedCode += instrumentedString;
                                            oinstrumentedCode += oinstrumentedString;
                                        } //END if STATEMENT
                                        instrumentedCode += line;
                                        instrumentedCode += "\n";
                                        oinstrumentedCode += oline;
                                        oinstrumentedCode += "\n";                                            
                                        methodEndCount--;
                                    } else {
                                        instrumentedCode += line;
                                        instrumentedCode += "\n";
                                        oinstrumentedCode += oline;
                                        oinstrumentedCode += "\n";
                                    } //END if-else STATEMENT
                                } //END if-else STATEMENT
                                line = lnr.readLine();
                                oline = olnr.readLine();
                            } while( line!=null );
                            raf.writeBytes( instrumentedCode );
                            raf.close();
                            oraf.writeBytes( oinstrumentedCode );
                            oraf.close();
                        } else {
                            oline = olnr.readLine();
                            do {
                                raf.writeBytes( line );
                                raf.writeBytes( "\n" );
                                oraf.writeBytes( oline );
                                oraf.writeBytes( "\n" );
                                String methodName = isMethodDeclaration( line, cComponents );
                                if( methodName!=null && line.contains( "{" ) ) {
                                    String className = codeFile.getName().substring( 0, codeFile.getName().length()-5 );
                                    String sufficiencyString = "S: " + className + " " + methodName + " " + methodCount;
                                    methodCount++;
                                    String instrumentedString = "try {\n";
                                    instrumentedString += "java.io.File myFile = new java.io.File( traceFile );\n";
                                    instrumentedString += "java.io.RandomAccessFile raf = new java.io.RandomAccessFile( myFile, \"rw\" );\n";
                                    instrumentedString += "raf.skipBytes( ( int )myFile.length() );\n";
                                    instrumentedString += "raf.writeBytes( \"" + sufficiencyString + "\" );\n";
                                    instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                    instrumentedString += "raf.close();\n";
                                    instrumentedString += "} catch( Exception e ) { }\n";
                                    String oinstrumentedString = "try {\n";
                                    oinstrumentedString += "java.io.File myFile = new java.io.File( traceFile );\n";
                                    oinstrumentedString += "java.io.RandomAccessFile raf = new java.io.RandomAccessFile( myFile, \"rw\" );\n";
                                    oinstrumentedString += "raf.skipBytes( ( int )myFile.length() );\n";                                    
                                    oinstrumentedString += "raf.writeBytes( \"" + sufficiencyString + "\" );\n";
                                    oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                    oinstrumentedString += "raf.close();\n";
                                    oinstrumentedString += "";
                                    oinstrumentedString += "} catch( Exception e ) { }\n";
                                    raf.writeBytes( instrumentedString );
                                    raf.writeBytes( "\n" );
                                    oraf.writeBytes( oinstrumentedString );
                                    oraf.writeBytes( "\n" );
                                } //END if STATEMENT
                                line = lnr.readLine();
                                oline = olnr.readLine();
                            } while( line!=null );
                            raf.close();
                            oraf.close();
                        } //END if-else STATEMENT
                    } //END for LOOP
                } //END for LOOP
            } catch( Exception exception ) {
                exception.printStackTrace();
            } //END try-catch BLOCk
        } //NED for LOOP
    } //END instrumentAndTransformCode() METHOD

    public String findMutatedMethod( String line ) {
        String methodName = "";
        int index = 0;
        char ch = ' ';
        do {
            ch = line.charAt( index++ );
        } while( ch!='.' );
        do {
            ch = line.charAt( index++ );
            if( ch!='(' ) {
                methodName += ch;
            } //END if STATEMENT
        } while( ch!='(' );
        return methodName;
    } //END findMutatedMethod() METHOD
    
    public String findMethodHeader( String methodName, ClassComponents cComponents ) {
        ArrayList mmList = cComponents.getMMList();
        for( int mm=0; mm<mmList.size(); mm++ ) {
            MemberMethod mMethod = ( MemberMethod )mmList.get( mm );
            if( methodName.equals( mMethod.getMethodName() ) ) {
                return mMethod.getMethodHeader();
            } //END if STATEMENT
        } //END for LOOP
        return "";
    } //END findMethodHeader()    

    public String getIdentifier( String line ) {
        String identifier = "";
        char ch = ' ';
        int index = 0;
        line = line.trim();
        System.out.println( line );
        if( line.startsWith( "this." ) ) {
            index = 5;
        } else if( line.startsWith( "int" ) ) {
            index = 3;
        } else if( line.startsWith( "long" ) ) {
            index = 4;
        } else if( line.startsWith( "float" ) ) {
            index = 5;
        } else if( line.startsWith( "double" ) ) {
            index = 6;
        } else if( line.startsWith( "char" ) ) {
            index = 4;
        } else if( line.startsWith( "byte" ) ) {
            index = 4;
        } else if( line.startsWith( "boolean" ) ) {
            index = 7;
        }//END if STATEMENT
        if( line.contains( "=" ) ) {
            do {
                if( ch!=' ' && ch!='/' ) {
                    identifier += ch;
                } //END if STATEMENT
                ch = line.charAt( index++ );
            } while( ch!='=' );            
        } else {
            int rand = ( int )( Math.random() * 100 );
            identifier += rand;
        } //END if-else STATEMENT
        return identifier;
    } //END getIdentifier() METHOD
    
    public String getReturnIdentifier( String line ) {
        String identifier = "";
        StringTokenizer tokenizer = new StringTokenizer( line, " " );
        WHILE: while( tokenizer.hasMoreTokens() ) {
            identifier = tokenizer.nextToken();
            if( identifier.contains( ";" ) ) {
                break WHILE;
            } //END if STATEMENT
        } //END while LOOP
        return identifier.substring( 0, identifier.length()-1 );
    } //END getReturnIdentifier() METHOD

    public String getObject( String line ) {
        String object = "";
        StringTokenizer tokens = new StringTokenizer( line, " " );
        WHILE: while( tokens.hasMoreTokens() ) {
            String temp = tokens.nextToken();
            if( temp.equals( "=" ) ) {
                return object;
            } else {
                object = temp;
            } //END if-else STATEMENT
        } //END while LOOP
        return object;
    } //END getObject() METHOD

    public String isMethodDeclaration( String line, ClassComponents cComponents ) {
        ArrayList mmList = cComponents.getMMList();
        for( int m=0; m<mmList.size(); m++ ) {
            MemberMethod mm = ( MemberMethod )mmList.get( m );
            if( line.contains( mm.getMethodName() ) ) {
                return mm.getMethodName();
            } //END if STATEMENT
        } //END for LOOP
        return null;
    } //END isMethodDeclaration() METHOD
    
    public ArrayList getPredicateList( String condition ) {
        ArrayList predicateList = new ArrayList();
        String predicate = "";
        int p = 0;
        char ch = ' ';
        do {
            ch = condition.charAt( p++ );
        } while( ch!='(' );
        for( ; p<condition.length(); p++ ) {
            ch = condition.charAt( p );
            if( ch=='&' || ch=='|' ) {
                p++;
                predicateList.add( predicate );
                predicate = "";
            } else if( ch==')' ) {
                predicateList.add( predicate );
                predicate = "";
            } else if( ch!=' ' ) {
                predicate += ch;
            } //END if-else STATEMENT
        } //END for LOOP
        return predicateList;
    } //END getPredicateList() METHOD
    
    public String[] getPredicateComponents( String predicate ) {
        String []comps = new String[ 3 ];
        String temp = "";
        for( int p=0; p<predicate.length(); p++ ) {
            char ch = predicate.charAt( p );
            if( ch=='<' || ch=='>' ) {
                comps[ 0 ] = temp;
                char ch1 = predicate.charAt( ++p );
                if( ch1=='=' ) {
                    comps[ 1 ] = ch + "=";
                } else {
                    comps[ 1 ] = ch + "";
                    p--;
                }//END if-else STATEMENT
                temp = "";
            } else if( ch=='=' || ch=='!' ) {
                comps[ 0 ] = temp;
                comps[ 1 ] = ch + "=";
                p++;
                temp = "";
            } else {
                temp += ch;
            } //END if-else STATEMENT
        } //END for LOOP
        comps[ 2 ] = temp;
        return comps;
    } //END getPredicateComponents() METHOD

    public String[] getReferenceConditionOperands( String condition ) {
        String[] operands = new String[ 2 ];
        operands[ 0 ] = "";
        operands[ 1 ] = "";
        
        StringTokenizer tokenizer = new StringTokenizer( condition, "." );
        int index=0;
        while( tokenizer.hasMoreTokens() ) {
            index++;
            String token = tokenizer.nextToken();
            System.out.println( "TOKEN: " + token );
            if( condition.contains( "equals" ) && index==2 ) {
                operands[ 0 ] = token;
            } //END if CONDITION
            if( condition.contains( "==" ) && index==2 ) {
                char ch = ' ';
                int t = 1;
                ch = token.charAt( 0 );
                do {
                    operands[ 0 ] += ch;
                    ch = token.charAt( t++ );
                } while( ch!='=' );
                operands[ 0 ] = operands[ 0 ].trim();
            } //END if CONDITION
            if( ( condition.contains( "equals" ) && index==4 ) || ( condition.contains( "==" ) && index==3 ) ) {
                char ch = ' ';
                int t = 1;
                ch = token.charAt( 0 );
                do {
                    operands[ 1 ] += ch;
                    ch = token.charAt( t++ );
                } while( ch!=')' );
                operands[ 1 ] = operands[ 1 ].trim();
            } //END if CONDITION
        } //END while LOOP
        return operands;
    } //END getReferenceConditionOperands() METHOD
    
    public String getWhilePredicate( String condition ) {
        String predicate = "";
        int p = 0;
        char ch = ' ';
        do {
            ch = condition.charAt( p++ );
        } while( ch!='(' );
        for( ; p<condition.length(); p++ ) {
            ch = condition.charAt( p );
            if( ch!=')' ) {
                predicate += ch;
            } else {
                return predicate;
            } //END if-else STATEMENT
        } //END for LOOP
        
        return predicate;
    } //END getWhilePredicate() METHOD
    
}
