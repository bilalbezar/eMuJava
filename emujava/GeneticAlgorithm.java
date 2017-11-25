/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emujava;

import java.io.*;
import java.util.*;

/**
 *
 * @author jBillu
 * 
 * @version 1.0 October 20, 2013
 * 
 */

public class GeneticAlgorithm extends Thread {
    
    private ArrayList selectedPopulation;
    
    public static int TS_K = 5;     //This 'K' is for Tournament Selection
    
    public Target gaTarget;
    
    public int threadNumber;
    
    private ArrayList population;
    
    private int traceCount, iterationNumber, traceNumber;
    
    public GeneticAlgorithm() {
        population = new ArrayList();
        selectedPopulation = new ArrayList();
        traceCount = 1;
        iterationNumber = 0;
    } //END GeneticAlgorithm() CONSTRUCTOR
    
    public void setGATarget( Target _t ) {
        gaTarget = _t;
    } //END setGATarget() METHOD
    
    public void setThreadNumber( int _tn ) {
        threadNumber = _tn;
    } //END setThreadNumber() METHOD
    
    public void run() {
        if( EMConstants.GEN_TYPE.contains( "eMuJava" ) ) {
            this.executeGA();
        } else {
            this.executeRandomGeneration();
        } //END if-else STATEMENT
        GAManager.RUNNING_THREADS--;
    } //END run() METHOD
    
    //This GA gives all the targets equal number of attempts as number of input iterations by user
    public void executeGA() {
        IFOR:   for( int i=1; i<=EMConstants.MAX_ITERATIONS; i++ ) {
            this.generatePopulation( gaTarget );
            ProcessProgress.getProcessProgress().titleLabel.setText( "Executing Genetic Algorithm...Target: " + gaTarget.getMutationOperator() + "-" + gaTarget.getMutantNumber() + ", Iteration: " + (EMConstants.ITERATIONS_PERFORMED+1) + "/" + EMConstants.TOTAL_ITERATIONS );
            EMConstants.CURRENT_PROGRESS += EMConstants.PROGRESS_RATE;
            int currentValue = ( int )EMConstants.CURRENT_PROGRESS;
            ProcessProgress.getProcessProgress().ppBar.setValue( currentValue );
            EMuJava.jTextArea2.append( "******************************\n" );
            EMuJava.jTextArea2.append( "TARGET: " + gaTarget.getMutationOperator() + "-" + gaTarget.getMutantNumber() + " -- ITERATION: " + i + "\n" );
            EMuJava.jTextArea2.append( "******************************\n" );
            ProcessProgress.getProcessProgress().statusLabel.setText( "Generating population of test case..." );
            this.executeTestCases( gaTarget );
            ProcessProgress.getProcessProgress().statusLabel.setText( "Executing test cases..." );
            this.evaluateTestCases( gaTarget );
            ProcessProgress.getProcessProgress().statusLabel.setText( "Evaluating test cases..." );
            EMConstants.ITERATIONS_PERFORMED++;
            this.iterationNumber++;
            if( gaTarget.getAchieved() ) {
                break IFOR;
            } else {
                if( i%EMConstants.MUTATION_RATE==0 ) {
                    this.mutatePopulation();
                    ProcessProgress.getProcessProgress().statusLabel.setText( "Mutating test cases..." );
                } else {
                    this.performTournamentSelection();
                    this.crossoverPopulation();
                    ProcessProgress.getProcessProgress().statusLabel.setText( "Regenerating new test cases with corssover..." );
                } //END if-else STATEMENT
            } //END if-else STATEMENT
        } //END for LOOP FOR GA ITERATIONS

        if( !gaTarget.getAchieved() ) {
            GAManager.T++;
        } //END if STATEMENT
        
    } //END executeGA() METHOD
    
    public void executeRandomGeneration() {
        IFOR:   for( int i=1; i<=EMConstants.MAX_ITERATIONS; i++ ) {
            this.generatePopulation( gaTarget );
            ProcessProgress.getProcessProgress().titleLabel.setText( "Executing Random Testing...Target: " + gaTarget.getMutationOperator() + "-" + gaTarget.getMutantNumber() + ", Iteration: " + (EMConstants.ITERATIONS_PERFORMED+1) + "/" + EMConstants.TOTAL_ITERATIONS );
            EMConstants.CURRENT_PROGRESS += EMConstants.PROGRESS_RATE;
            int currentValue = ( int )EMConstants.CURRENT_PROGRESS;
            ProcessProgress.getProcessProgress().ppBar.setValue( currentValue );
            EMuJava.jTextArea2.append( "******************************\n" );
            EMuJava.jTextArea2.append( "TARGET: " + gaTarget.getMutationOperator() + "-" + gaTarget.getMutantNumber() + " -- ITERATION: " + i + "\n" );
            EMuJava.jTextArea2.append( "******************************\n" );
            ProcessProgress.getProcessProgress().statusLabel.setText( "Generating population of test case..." );
            this.executeTestCases( gaTarget );
            ProcessProgress.getProcessProgress().statusLabel.setText( "Executing test cases..." );
            this.evaluateTestCases( gaTarget );
            ProcessProgress.getProcessProgress().statusLabel.setText( "Evaluating test cases..." );
            EMConstants.ITERATIONS_PERFORMED++;
            this.iterationNumber++;
            if( gaTarget.getAchieved() ) {
                break IFOR;
            } //END if STATEMENT
        } //END for LOOP FOR GA ITERATIONS

        if( !gaTarget.getAchieved() ) {
            GAManager.T++;
        } //END if STATEMENT
    } //END executeRandomGeneration() METHOD
    
    public void performTournamentSelection() {
        int currentPopulation = this.population.size();
        int twentyPercent = ( int )( currentPopulation * EMConstants.OLD_POPULATION_RATE );
        selectedPopulation.clear();

        for( int l=0; l<this.population.size(); l++ ) {
            for( int k=l+1; k<this.population.size(); k++ ) {
                TestCase testCase = ( TestCase )this.population.get( l );  
                TestCase testCase2 = ( TestCase )this.population.get( k );
                if( testCase.getWeight()<testCase2.getWeight() ) {
                    this.population.remove( k );
                    this.population.remove( l );
                    this.population.add( l, testCase2 );
                    this.population.add( k, testCase );
                } //END if STATEMENT
            } //END inner FOR LOOP
        } //END outer FOR LOOP
        
        for( int t=0; t<twentyPercent; t++ ) {
            TestCase testCase = ( TestCase )this.population.get( 0 );
            this.population.remove( 0 );
            selectedPopulation.add( testCase );
        } //END for LOOP
        
        ArrayList tournamentPopulation = new ArrayList();
        while( selectedPopulation.size() < currentPopulation ) {
            tournamentPopulation.clear();
            int ts_k = 1;
            //Pick k number of Chromosomes from Population
            while( ts_k <= GeneticAlgorithm.TS_K ) {
                int random = ( int )( Math.random()*this.population.size() );
                TestCase testCase = ( TestCase )this.population.get( random );
                tournamentPopulation.add( testCase );
                ts_k++;
            } //END while LOOP
            
            //Find best Chromosome from k number of samples
            TestCase bestCase = ( TestCase )tournamentPopulation.get( 0 );
            for( int t=1; t<tournamentPopulation.size(); t++ ) {
                TestCase bCase = ( TestCase )tournamentPopulation.get( t );
                if( bCase.getWeight()>=bestCase.getWeight() ) {
                    bestCase = bCase;
                } //END if STATEMENT
            } //END for LOOP
            TestCase theBest = new TestCase( bestCase );
            selectedPopulation.add( theBest );
        } //END while LOOP
        
        ArrayList newList = ( ArrayList )selectedPopulation.clone();
        int i=0, j=newList.size()-1;
        for( int n=0; n<twentyPercent; n++ ) {
            TestCase tCase = ( TestCase )newList.get( n );
            selectedPopulation.set( i++, tCase );
        } //END for LOOP
        
        for( int n=twentyPercent;n<newList.size(); n++ ) {
            TestCase tCase = ( TestCase )newList.get( n );
            if( tCase.getStateFitness()==0.0 ) {
                selectedPopulation.set( i++, tCase );
            } else {
                selectedPopulation.set( j--, tCase );
            } //END if-else STATEMENT
        } //END for LOOP
    } //END performTournamentSelection() METHOD
    
    public void crossoverPopulation() {
        for( int sp=0,sp2=(selectedPopulation.size()-1); sp<=sp2; sp++, sp2-- ) {
            TestCase testCase1 = ( TestCase )selectedPopulation.get( sp );
            TestCase testCase2 = ( TestCase )selectedPopulation.get( sp2 );
            
            if( testCase1.getTestCase().equals( testCase2.getTestCase() ) ) {
                int random = ( int )( selectedPopulation.size() * Math.random() );
                testCase2 = ( TestCase )selectedPopulation.get( random );
            } //END if STATEMENT
                       
            StringTokenizer tk1 = new StringTokenizer( testCase1.getTestCase(), ";" );
            StringTokenizer tk2 = new StringTokenizer( testCase2.getTestCase(), ";" );
            String firstToken = tk1.nextToken();
            tk2.nextToken();
            ArrayList tl1 = new ArrayList();
            while( tk1.hasMoreTokens() ) {
                tl1.add( tk1.nextToken() );
            } //END while LOOP
            String lastToken1 = ( String )tl1.get( tl1.size()-1 );
            tl1.remove( tl1.size()-1 );
            ArrayList tl2 = new ArrayList();
            while( tk2.hasMoreTokens() ) {
                tl2.add( tk2.nextToken() );
            } //END while LOOP
            String lastToken2 = ( String )tl2.get( tl2.size()-1 );
            tl2.remove( tl2.size()-1 );
            
            int minimum = tl1.size();
            if( tl1.size()>tl2.size() ) {
                minimum = tl2.size();
            } //END if STATEMENT
            int crossoverPoint = ( int )( minimum * Math.random() );
            
            String newTestCase1 = firstToken + ";";
            for( int i1=0; i1<crossoverPoint; i1++ ) {
                String s1 = ( String )tl1.get( i1 );
                newTestCase1 += s1 + ";";
            } //END for LOOP
            for( int i1=crossoverPoint; i1<tl2.size(); i1++ ) {
                String s1 = ( String )tl2.get( i1 );
                newTestCase1 += s1 + ";";
            } //END for LOOP
            newTestCase1 += lastToken2 + ";";
            
            String newTestCase2 = firstToken + ";";
            for( int i1=0; i1<crossoverPoint; i1++ ) {
                String s1 = ( String )tl2.get( i1 );
                newTestCase2 += s1 + ";";
            } //END for LOOP
            for( int i1=crossoverPoint; i1<tl1.size(); i1++ ) {
                String s1 = ( String )tl1.get( i1 );
                newTestCase2 += s1 + ";";
            } //END for LOOP
            newTestCase2 += lastToken1 + ";";
            
            testCase1.setTestCase( newTestCase1 );
            testCase2.setTestCase( newTestCase2 );
            
            if( EMConstants.GEN_TYPE.equals( "eMuJava v.2" ) ) {
                if( testCase1.getStateFitness()>0.0 || testCase2.getStateFitness()>0.0 ) {
                    String newTestCase3 = firstToken + ";";
                    for( int i1=0; i1<crossoverPoint; i1++ ) {
                        String s1 = ( String )tl1.get( i1 );
                        newTestCase3 += s1 + ";";
                    } //END for LOOP
                    for( int i1=0; i1<crossoverPoint; i1++ ) {
                        String s1 = ( String )tl2.get( i1 );
                        newTestCase3 += s1 + ";";
                    } //END for LOOP
                    newTestCase3 += lastToken2 + ";";
                    TestCase testCase3 = new TestCase( testCase1 );
                    testCase3.setTestCase( newTestCase3 );
                    selectedPopulation.add( testCase3 );

                    String newTestCase4 = firstToken + ";";
                    for( int i1=crossoverPoint; i1<tl2.size(); i1++ ) {
                        String s1 = ( String )tl2.get( i1 );
                        newTestCase4 += s1 + ";";
                    } //END for LOOP
                    for( int i1=crossoverPoint; i1<tl1.size(); i1++ ) {
                        String s1 = ( String )tl1.get( i1 );
                        newTestCase4 += s1 + ";";
                    } //END for LOOP
                    newTestCase4 += lastToken1 + ";";
                    TestCase testCase4 = new TestCase( testCase2 );
                    testCase4.setTestCase( newTestCase4 );
                    selectedPopulation.add( testCase4 );
                } //END if STATEMENT
            } //END if STATEMENT
            
        } //END for LOOP
        
        this.population.clear();
        for( int sp=0; sp<selectedPopulation.size(); sp++ ) {
            this.population.add( ( TestCase )selectedPopulation.get( sp ) );
        } //END for LOOP
        selectedPopulation.clear();
    } //END crossoverPopulation() METHOD

    public void mutatePopulation() {
        int twentyPercent = ( int )( EMConstants.POPULATION_SIZE * EMConstants.OLD_POPULATION_RATE );
        
        for( int l=0; l<this.population.size(); l++ ) {
            for( int k=l+1; k<this.population.size(); k++ ) {
                TestCase testCase = ( TestCase )this.population.get( l );  
                TestCase testCase2 = ( TestCase )this.population.get( k );
                if( testCase.getWeight()<testCase2.getWeight() ) {
                    this.population.remove( k );
                    this.population.remove( l );
                    this.population.add( l, testCase2 );
                    this.population.add( k, testCase );
                } //END if STATEMENT
            } //END inner FOR LOOP
        } //END outer FOR LOOP
        
        ArrayList tempPopulation = ( ArrayList )this.population.clone();
        this.population.clear();
        for( int tp=0; tp<twentyPercent ; tp++ ) {
            TestCase testCase = ( TestCase )tempPopulation.get( tp );
            population.add( testCase );
        } //END for LOOP
        
        for( int tp=twentyPercent; ( tp<tempPopulation.size() && this.population.size()<EMConstants.POPULATION_SIZE ) ; ) {
            TestCase testCase = ( TestCase )tempPopulation.get( tp );
            tempPopulation.remove( testCase );
            if( testCase.getStateFitness()==0.0 ) {
                ArrayList newTest = new ArrayList();
                StringTokenizer tokenizer1 = new StringTokenizer( testCase.getTestCase(), " " );
                while( tokenizer1.hasMoreTokens() ) {
                    String token = tokenizer1.nextToken();
                    if( !token.equals( " " ) ) {
                        newTest.add( token );
                    } //END if STATEMENT
                } //END while LOOP
                newTest.remove( newTest.size()-1 );
                String solution = "";
                for( int i=0; i<newTest.size(); i++ ) {
                    solution += ( String )newTest.get( i ) + " ";
                } //END for LOOP
                ClassComponents cComponents = null;
                String objectName = "";
                if( testCase.getClassName2().equals( "" ) ) {
                    cComponents = EMController.create().getC1Components();
                    objectName = testCase.getObjectName();
                } else {
                    cComponents = EMController.create().getC2Components();
                    objectName = testCase.getObjectName();
                } //END if-else STATEMENT
                String methodUnderTest = testCase.getMethod();
                ArrayList mmList = cComponents.getMMList();
                FOR: for( int mm=0; mm<mmList.size(); mm++ ) {
                    MemberMethod mMethod = ( MemberMethod )mmList.get( mm );
                    if( mMethod.getMethodName().equals( methodUnderTest ) ) {
                        solution += objectName + "." + mMethod.getMethodName() + "(";
                        ArrayList mTokens = mMethod.getMethodTokens();
                        int mt = 0;
                        Token token = null;
                        do {
                            token = ( Token )mTokens.get( mt++ );
                        } while( !token.getToken().equals( "(" ) );
                        token = ( Token )mTokens.get( mt++ );
                        while( !token.getToken().equals( ")" ) ) {
                            if( token.getToken().equals( "byte" ) || token.getToken().equals( "short" ) ) {
                                int value = ( int )( Math.random() * EMConstants.INT_RANGE );
                                solution += value;
                            } else if( token.getToken().equals( "int" ) || token.getToken().equals( "long" ) ) {
                                int value = ( int )( Math.random() * EMConstants.INT_RANGE );
                                int signRandom = ( int )( Math.random() * 100 );
                                if( signRandom%5==0 ) {
                                    value = value * -1;
                                } //END if STATEMENT
                                solution += value;
                            } else if( token.getToken().equals( "float" ) ) {
                                int value1 = ( int )( Math.random() * EMConstants.INT_RANGE );
                                int value2 = ( int )( Math.random() * 10 );
                                solution += value1 + "." + value2 + "f";
                            } else if( token.getToken().equals( "double" ) ) {
                                int value1 = ( int )( Math.random() * EMConstants.INT_RANGE );
                                int value2 = ( int )( Math.random() * 10 );
                                int signRandom = ( int )( Math.random() * 100 );
                                if( signRandom%5==0 ) {
                                    value1 = value1 * -1;
                                } //END if STATEMENT
                                solution += value1 + "." + value2;
                            } else if( token.getToken().equals( "char" ) ) {
                                int value = ( int )( Math.random() * 26 );
                                int tvalue = ( int )( Math.random() * 2 );
                                if( tvalue==0 ) {
                                    value += 65;
                                } else {
                                    value += 97;
                                } //END if-else STATEMENT
                                char ch = ( char )value;
                                solution += "\'" + ch + "\'";
                            } else if( token.getToken().equals( "String" ) ) {
                                int len = ( int )( Math.random() * 10 );
                                String value = "\"";
                                for( int l=0; l<len; l++ ) {
                                    int ivalue = ( int )( Math.random() * 26 );
                                    int tvalue = ( int )( Math.random() * 3 );
                                    if( tvalue==0 ) {
                                        ivalue += 65;
                                    } else if( tvalue==1 ) {
                                        ivalue += 97;
                                    } else {
                                        ivalue += 48;
                                    } //END if-else STATEMENT
                                    char ch = ( char )ivalue;
                                    value += ch;
                                } //END for LOOP
                                value += "\"";
                                solution += value;
                            } //END if-else STATEMENTS
                            mt++;
                            token = ( Token )mTokens.get( mt++ );
                            if( token.getToken().equals( "," ) ) {
                                solution += ",";
                                token = ( Token )mTokens.get( mt++ );
                            } //END if STATEMENT
                        } //END while LOOP
                        solution += ");";
                        break FOR;
                    } //END if STATEMENT
                } //NED for LOOP
                testCase.setTestCase( solution );
                this.population.add( testCase );
            } else {
                String className = testCase.getClassName();
                String objectName = testCase.getObjectName();
                String solution = "";
                ClassComponents cComponents = null;
                ClassComponents c2Components = null;
                if( testCase.getClassName2().equals( "" ) ) {
                    cComponents = EMController.create().getC1Components();
                } else {
                    cComponents = EMController.create().getC1Components();
                    c2Components = EMController.create().getC2Components();
                } //END if-else STATEMENT
                String methodUnderTest = testCase.getMethod();

                if( c2Components!=null ) {
                    String class2Name = c2Components.getClassName();
                    solution += class2Name + " " + objectName + " = new " + class2Name + "(); ";
                } else {
                    solution += className + " " + objectName + " = new " + className + "(); ";
                } //END if-else STATEMENT

                int methodCallSeqCount = (int )( Math.random() * EMConstants.METHOD_CLASS_SEQUENCE_COUNT );
                if( methodCallSeqCount<=1 ) {
                    methodCallSeqCount += 2;
                } //END if STATEMETN
                for( int m=1; m<=methodCallSeqCount; m++ ) {
                    int methodNumber = ( int )( Math.random() * cComponents.getMMList().size() );
                    MemberMethod mMethod = ( MemberMethod )cComponents.getMMList().get( methodNumber );
                    if( !mMethod.getMethodName().equals( methodUnderTest ) ) {
                        solution += objectName + "." + mMethod.getMethodName() + "(";
                        ArrayList mTokens = mMethod.getMethodTokens();
                        int mt = 0;
                        Token token = null;
                        do {
                            token = ( Token )mTokens.get( mt++ );
                        } while( !token.getToken().equals( "(" ) );
                        token = ( Token )mTokens.get( mt++ );
                        while( !token.getToken().equals( ")" ) ) {
                            if( token.getToken().equals( "byte" ) || token.getToken().equals( "short" ) ) {
                                int value = ( int )( Math.random() * EMConstants.INT_RANGE );
                                solution += value;
                            } else if( token.getToken().equals( "int" ) || token.getToken().equals( "long" ) ) {
                                int value = ( int )( Math.random() * EMConstants.INT_RANGE );
                                int signRandom = ( int )( Math.random() * 100 );
                                if( signRandom%5==0 ) {
                                    value = value * -1;
                                } //END if STATEMENT 
                                solution += value;
                            } else if( token.getToken().equals( "float" ) ) {
                                int value1 = ( int )( Math.random() * EMConstants.INT_RANGE );
                                int value2 = ( int )( Math.random() * 10 );
                                solution += value1 + "." + value2 + "f";
                            } else if( token.getToken().equals( "double" ) ) {
                                int value1 = ( int )( Math.random() * EMConstants.INT_RANGE );
                                int value2 = ( int )( Math.random() * 10 );
                                int signRandom = ( int )( Math.random() * 100 );
                                if( signRandom%5==0 ) {
                                    value1 = value1 * -1;
                                } //END if STATEMENT 
                                solution += value1 + "." + value2;
                            } else if( token.getToken().equals( "char" ) ) {
                                int value = ( int )( Math.random() * 26 );
                                int tvalue = ( int )( Math.random() * 2 );
                                if( tvalue==0 ) {
                                    value += 65;
                                } else {
                                    value += 97;
                                } //END if-else STATEMENT
                                char ch = ( char )value;
                                solution += "\'" + ch + "\'";
                            } else if( token.getToken().equals( "String" ) ) {
                                int len = ( int )( Math.random() * 10 );
                                String value = "\"";
                                for( int l=0; l<len; l++ ) {
                                    int ivalue = ( int )( Math.random() * 26 );
                                    int tvalue = ( int )( Math.random() * 3 );
                                    if( tvalue==0 ) {
                                        ivalue += 65;
                                    } else if( tvalue==1 ) {
                                        ivalue += 97;
                                    } else {
                                        ivalue += 48;
                                    } //END if-else STATEMENT
                                    char ch = ( char )ivalue;
                                    value += ch;
                                } //END for LOOP
                                value += "\"";
                                solution += value;
                            } //END if-else STATEMENTS
                            mt++;
                            token = ( Token )mTokens.get( mt++ );
                            if( token.getToken().equals( "," ) ) {
                                solution += ",";
                                token = ( Token )mTokens.get( mt++ );
                            } //END if STATEMENT
                        } //END while LOOP
                        solution += "); ";
                    } //END if STATEMENT
                } //END for LOOP

                if( c2Components!=null ) {
                    methodCallSeqCount = (int )( Math.random() * (EMConstants.METHOD_CLASS_SEQUENCE_COUNT/2) );
                    if( methodCallSeqCount<=1 ) {
                        methodCallSeqCount += 2;
                    } //END if STATEMENT
                    for( int m=1; m<=methodCallSeqCount; m++ ) {
                        int methodNumber = ( int )( Math.random() * c2Components.getMMList().size() );
                        MemberMethod mMethod = ( MemberMethod )c2Components.getMMList().get( methodNumber );
                        solution += objectName + "." + mMethod.getMethodName() + "(";
                        ArrayList mTokens = mMethod.getMethodTokens();
                        int mt = 0;
                        Token token = null;
                        do {
                            token = ( Token )mTokens.get( mt++ );
                        } while( !token.getToken().equals( "(" ) );
                        token = ( Token )mTokens.get( mt++ );
                        while( !token.getToken().equals( ")" ) ) {
                            if( token.getToken().equals( "byte" ) || token.getToken().equals( "short" ) ) {
                                int value = ( int )( Math.random() * EMConstants.INT_RANGE );
                                solution += value;
                            } else if( token.getToken().equals( "int" ) || token.getToken().equals( "long" ) ) {
                                int value = ( int )( Math.random() * EMConstants.INT_RANGE );
                                int signRandom = ( int )( Math.random() * 100 );
                                if( signRandom%5==0 ) {
                                    value = value * -1;
                                } //END if STATEMENT 
                                solution += value;
                            } else if( token.getToken().equals( "float" ) ) {
                                int value1 = ( int )( Math.random() * EMConstants.INT_RANGE );
                                int value2 = ( int )( Math.random() * 10 );
                                solution += value1 + "." + value2 + "f";
                            } else if( token.getToken().equals( "double" ) ) {
                                int value1 = ( int )( Math.random() * EMConstants.INT_RANGE );
                                int value2 = ( int )( Math.random() * 10 );
                                int signRandom = ( int )( Math.random() * 100 );
                                if( signRandom%5==0 ) {
                                    value1 = value1 * -1;
                                } //END if STATEMENT 
                                solution += value1 + "." + value2;
                            } else if( token.getToken().equals( "char" ) ) {
                                int value = ( int )( Math.random() * 26 );
                                int tvalue = ( int )( Math.random() * 2 );
                                if( tvalue==0 ) {
                                    value += 65;
                                } else {
                                    value += 97;
                                } //END if-else STATEMENT
                                char ch = ( char )value;
                                solution += "\'" + ch + "\'";
                            } else if( token.getToken().equals( "String" ) ) {
                                int len = ( int )( Math.random() * 10 );
                                String value = "\"";
                                for( int l=0; l<len; l++ ) {
                                    int ivalue = ( int )( Math.random() * 26 );
                                    int tvalue = ( int )( Math.random() * 3 );
                                    if( tvalue==0 ) {
                                        ivalue += 65;
                                    } else if( tvalue==1 ) {
                                        ivalue += 97;
                                    } else {
                                        ivalue += 48;
                                    } //END if-else STATEMENT
                                    char ch = ( char )ivalue;
                                    value += ch;
                                } //END for LOOP
                                value += "\"";
                                solution += value;
                            } //END if-else STATEMENTS
                            mt++;
                            token = ( Token )mTokens.get( mt++ );
                            if( token.getToken().equals( "," ) ) {
                                solution += ",";
                                token = ( Token )mTokens.get( mt++ );
                            } //END if STATEMENT
                        } //END while LOOP
                        solution += "); ";
                    } //END for LOOP
                } //END if STATEMENT

                ArrayList mmList = cComponents.getMMList();
                FOR: for( int mm=0; mm<mmList.size(); mm++ ) {
                    MemberMethod mMethod = ( MemberMethod )mmList.get( mm );
                    if( mMethod.getMethodName().equals( methodUnderTest ) ) {
                        solution += objectName + "." + mMethod.getMethodName() + "(";
                        ArrayList mTokens = mMethod.getMethodTokens();
                        int mt = 0;
                        Token token = null;
                        do {
                            token = ( Token )mTokens.get( mt++ );
                        } while( !token.getToken().equals( "(" ) );
                        token = ( Token )mTokens.get( mt++ );
                        while( !token.getToken().equals( ")" ) ) {
                            if( token.getToken().equals( "byte" ) || token.getToken().equals( "short" ) ) {
                                int value = ( int )( Math.random() * EMConstants.INT_RANGE );
                                solution += value;
                            } else if( token.getToken().equals( "int" ) || token.getToken().equals( "long" ) ) {
                                int value = ( int )( Math.random() * EMConstants.INT_RANGE );
                                int signRandom = ( int )( Math.random() * 100 );
                                if( signRandom%5==0 ) {
                                    value = value * -1;
                                } //END if STATEMENT 
                                solution += value;
                            } else if( token.getToken().equals( "float" ) ) {
                                int value1 = ( int )( Math.random() * EMConstants.INT_RANGE );
                                int value2 = ( int )( Math.random() * 10 );
                                solution += value1 + "." + value2 + "f";
                            } else if( token.getToken().equals( "double" ) ) {
                                int value1 = ( int )( Math.random() * EMConstants.INT_RANGE );
                                int value2 = ( int )( Math.random() * 10 );
                                int signRandom = ( int )( Math.random() * 100 );
                                if( signRandom%5==0 ) {
                                    value1 = value1 * -1;
                                } //END if STATEMENT 
                                solution += value1 + "." + value2;
                            } else if( token.getToken().equals( "char" ) ) {
                                int value = ( int )( Math.random() * 26 );
                                int tvalue = ( int )( Math.random() * 2 );
                                if( tvalue==0 ) {
                                    value += 65;
                                } else {
                                    value += 97;
                                } //END if-else STATEMENT
                                char ch = ( char )value;
                                solution += "\'" + ch + "\'";
                            } else if( token.getToken().equals( "String" ) ) {
                                int len = ( int )( Math.random() * 10 );
                                String value = "\"";
                                for( int l=0; l<len; l++ ) {
                                    int ivalue = ( int )( Math.random() * 26 );
                                    int tvalue = ( int )( Math.random() * 3 );
                                    if( tvalue==0 ) {
                                        ivalue += 65;
                                    } else if( tvalue==1 ) {
                                        ivalue += 97;
                                    } else {
                                        ivalue += 48;
                                    } //END if-else STATEMENT
                                    char ch = ( char )ivalue;
                                    value += ch;
                                } //END for LOOP
                                value += "\"";
                                solution += value;
                            } //END if-else STATEMENTS
                            mt++;
                            token = ( Token )mTokens.get( mt++ );
                            if( token.getToken().equals( "," ) ) {
                                solution += ",";
                                token = ( Token )mTokens.get( mt++ );
                            } //END if STATEMENT
                        } //END while LOOP
                        solution += ");";
                        break FOR;
                    } //END if STATEMENT
                } //NED for LOOP
                testCase.setTestCase( solution );
                this.population.add( testCase );
            } //END if-else STATEMENT
        } //END for LOOP
    } //END mutatePopulation() METHOD
    
    public void generatePopulation( Target target ) {
        String methodUnderTest = this.retrieveMethodUnderTest( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "/instrument/" + target.getMutationOperator() + "/" + target.getMutantNumber() );
        ClassComponents cComponents = null;
        ClassComponents c2Components = null;
        try {
            String targetPath = EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "/instrument/" + target.getMutationOperator() + "/" + target.getMutantNumber();
            File file = new File( targetPath );
            String operator = target.getMutationOperator();
            if( operator.equals( "ABS" ) || operator.equals( "AOR" ) || operator.equals( "LCR" ) || operator.equals( "ROR" ) || operator.equals( "UOI" ) || operator.equals( "OMD" ) || operator.equals( "JID" ) || operator.equals( "ECC" ) ) {
                cComponents = EMController.create().getC1Components();
            } else {
                cComponents = EMController.create().getC1Components();
                c2Components = EMController.create().getC2Components();
            } //END if-else STATEMENT
        } catch( Exception e ) {
            e.printStackTrace();
        } //END try-catch BLOCK
        for( int p=this.population.size(); p<EMConstants.POPULATION_SIZE; p++ ) {
            TestCase testCase = new TestCase();
            String solution = "";
            String className = cComponents.getClassName();
            String objectName = className + "1";
            testCase.setClassName( className );
            testCase.setObjectName( objectName );
            testCase.setMethod( methodUnderTest );
            if( c2Components!=null ) {
                String class2Name = c2Components.getClassName();
                objectName = class2Name + "1";
                testCase.setClassName2( class2Name );
                testCase.setObjectName( objectName );
                solution += class2Name + " " + objectName + " = new " + class2Name + "(); ";
            } else {
                solution += className + " " + objectName + " = new " + className + "(); ";                
            } //END if-else STATEMENT
            int methodCallSeqCount = (int )( Math.random() * EMConstants.METHOD_CLASS_SEQUENCE_COUNT );
            if( methodCallSeqCount<=1 ) {
                methodCallSeqCount += 2;
            } //END if STATEMETN
            for( int m=1; m<=methodCallSeqCount; m++ ) {
                int methodNumber = ( int )( Math.random() * cComponents.getMMList().size() );
                MemberMethod mMethod = ( MemberMethod )cComponents.getMMList().get( methodNumber );
                if( !mMethod.getMethodName().equals( methodUnderTest ) ) {
                    solution += objectName + "." + mMethod.getMethodName() + "(";
                    ArrayList mTokens = mMethod.getMethodTokens();
                    int mt = 0;
                    Token token = null;
                    do {
                        token = ( Token )mTokens.get( mt++ );
                    } while( !token.getToken().equals( "(" ) );
                    token = ( Token )mTokens.get( mt++ );
                    while( !token.getToken().equals( ")" ) ) {
                        if( token.getToken().equals( "byte" )  || token.getToken().equals( "short" ) ) {
                            int value = ( int )( Math.random() * EMConstants.INT_RANGE );
                            solution += value;
                        } else if( token.getToken().equals( "int" ) || token.getToken().equals( "long" ) ) {
                            int value = ( int )( Math.random() * EMConstants.INT_RANGE );
                            int signRandom = ( int )( Math.random() * 100 );
                            if( signRandom%5==0 ) {
                                value = value * -1;
                            } //END if STATEMENT
                            solution += value;
                        } else if( token.getToken().equals( "float" ) ) {
                            int value1 = ( int )( Math.random() * EMConstants.INT_RANGE );
                            int value2 = ( int )( Math.random() * 10 );
                            solution += value1 + "." + value2 + "f";
                        } else if( token.getToken().equals( "double" ) ) {
                            int value1 = ( int )( Math.random() * EMConstants.INT_RANGE );
                            int value2 = ( int )( Math.random() * 10 );
                            int signRandom = ( int )( Math.random() * 100 );
                            if( signRandom%5==0 ) {
                                value1 = value1 * -1;
                            } //END if STATEMENT
                            solution += value1 + "." + value2;
                        } else if( token.getToken().equals( "char" ) ) {
                            int value = ( int )( Math.random() * 26 );
                            int tvalue = ( int )( Math.random() * 2 );
                            if( tvalue==0 ) {
                                value += 65;
                            } else {
                                value += 97;
                            } //END if-else STATEMENT
                            char ch = ( char )value;
                            solution += "\'" + ch + "\'";
                        } else if( token.getToken().equals( "String" ) ) {
                            int len = ( int )( Math.random() * 10 );
                            String value = "\"";
                            for( int l=0; l<len; l++ ) {
                                int ivalue = ( int )( Math.random() * 26 );
                                int tvalue = ( int )( Math.random() * 3 );
                                if( tvalue==0 ) {
                                    ivalue += 65;
                                } else if( tvalue==1 ) {
                                    ivalue += 97;
                                } else {
                                    ivalue += 48;
                                } //END if-else STATEMENT
                                char ch = ( char )ivalue;
                                value += ch;
                            } //END for LOOP
                            value += "\"";
                            solution += value;
                        } //END if-else STATEMENTS
                        mt++;
                        token = ( Token )mTokens.get( mt++ );
                        if( token.getToken().equals( "," ) ) {
                            solution += ",";
                            token = ( Token )mTokens.get( mt++ );
                        } //END if STATEMENT
                    } //END while LOOP
                    solution += "); ";
                } //END if STATEMENT
            } //END for LOOP

            if( c2Components!=null ) {
                methodCallSeqCount = (int )( Math.random() * (EMConstants.METHOD_CLASS_SEQUENCE_COUNT/2) );
                if( methodCallSeqCount<=1 ) {
                    methodCallSeqCount += 2;
                } //END if STATEMENT
                for( int m=1; m<=methodCallSeqCount; m++ ) {
                    int methodNumber = ( int )( Math.random() * c2Components.getMMList().size() );
                    MemberMethod mMethod = ( MemberMethod )c2Components.getMMList().get( methodNumber );
                    solution += objectName + "." + mMethod.getMethodName() + "(";
                    ArrayList mTokens = mMethod.getMethodTokens();
                    int mt = 0;
                    Token token = null;
                    do {
                        token = ( Token )mTokens.get( mt++ );
                    } while( !token.getToken().equals( "(" ) );
                    token = ( Token )mTokens.get( mt++ );
                    while( !token.getToken().equals( ")" ) ) {
                        if( token.getToken().equals( "byte" ) || token.getToken().equals( "short" ) ) {
                            int value = ( int )( Math.random() * EMConstants.INT_RANGE );
                            solution += value;
                        } else if( token.getToken().equals( "int" ) || token.getToken().equals( "long" ) ) {
                            int value = ( int )( Math.random() * EMConstants.INT_RANGE );
                            int signRandom = ( int )( Math.random() * 100 );
                            if( signRandom%5==0 ) {
                                value = value * -1;
                            } //END if STATEMENT
                            solution += value;
                        } else if( token.getToken().equals( "float" ) ) {
                            int value1 = ( int )( Math.random() * EMConstants.INT_RANGE );
                            int value2 = ( int )( Math.random() * 10 );
                            solution += value1 + "." + value2 + "f";
                        } else if( token.getToken().equals( "double" ) ) {
                            int value1 = ( int )( Math.random() * EMConstants.INT_RANGE );
                            int value2 = ( int )( Math.random() * 10 );
                            int signRandom = ( int )( Math.random() * 100 );
                            if( signRandom%5==0 ) {
                                value1 = value1 * -1;
                            } //END if STATEMENT                            
                            solution += value1 + "." + value2;
                        } else if( token.getToken().equals( "char" ) ) {
                            int value = ( int )( Math.random() * 26 );
                            int tvalue = ( int )( Math.random() * 2 );
                            if( tvalue==0 ) {
                                value += 65;
                            } else {
                                value += 97;
                            } //END if-else STATEMENT
                            char ch = ( char )value;
                            solution += "\'" + ch + "\'";
                        } else if( token.getToken().equals( "String" ) ) {
                            int len = ( int )( Math.random() * 10 );
                            String value = "\"";
                            for( int l=0; l<len; l++ ) {
                                int ivalue = ( int )( Math.random() * 26 );
                                int tvalue = ( int )( Math.random() * 3 );
                                if( tvalue==0 ) {
                                    ivalue += 65;
                                } else if( tvalue==1 ) {
                                    ivalue += 97;
                                } else {
                                    ivalue += 48;
                                } //END if-else STATEMENT
                                char ch = ( char )ivalue;
                                value += ch;
                            } //END for LOOP
                            value += "\"";
                            solution += value;
                        } //END if-else STATEMENTS
                        mt++;
                        token = ( Token )mTokens.get( mt++ );
                        if( token.getToken().equals( "," ) ) {
                            solution += ",";
                            token = ( Token )mTokens.get( mt++ );
                        } //END if STATEMENT
                    } //END while LOOP
                    solution += "); ";
                } //END for LOOP
            } //END if STATEMENT
            
            ArrayList mmList = cComponents.getMMList();
            FOR: for( int mm=0; mm<mmList.size(); mm++ ) {
                MemberMethod mMethod = ( MemberMethod )mmList.get( mm );
                if( mMethod.getMethodName().equals( methodUnderTest ) ) {
                    solution += objectName + "." + mMethod.getMethodName() + "(";
                    ArrayList mTokens = mMethod.getMethodTokens();
                    int mt = 0;
                    Token token = null;
                    do {
                        token = ( Token )mTokens.get( mt++ );
                    } while( !token.getToken().equals( "(" ) );
                    token = ( Token )mTokens.get( mt++ );
                    while( !token.getToken().equals( ")" ) ) {
                        if( token.getToken().equals( "byte" )  || token.getToken().equals( "short" ) ) {
                            int value = ( int )( Math.random() * EMConstants.INT_RANGE );
                            solution += value;
                        } else if( token.getToken().equals( "int" ) || token.getToken().equals( "long" ) ) {
                            int value = ( int )( Math.random() * EMConstants.INT_RANGE );
                            int signRandom = ( int )( Math.random() * 100 );
                            if( signRandom%5==0 ) {
                                value = value * -1;
                            } //END if STATEMENT
                            solution += value;
                        } else if( token.getToken().equals( "float" ) ) {
                            int value1 = ( int )( Math.random() * EMConstants.INT_RANGE );
                            int value2 = ( int )( Math.random() * 10 );
                            solution += value1 + "." + value2 + "f";
                        } else if( token.getToken().equals( "double" ) ) {
                            int value1 = ( int )( Math.random() * EMConstants.INT_RANGE );
                            int value2 = ( int )( Math.random() * 10 );
                            int signRandom = ( int )( Math.random() * 100 );
                            if( signRandom%5==0 ) {
                                value1 = value1 * -1;
                            } //END if STATEMENT
                            solution += value1 + "." + value2;
                        } else if( token.getToken().equals( "char" ) ) {
                            int value = ( int )( Math.random() * 26 );
                            int tvalue = ( int )( Math.random() * 2 );
                            if( tvalue==0 ) {
                                value += 65;
                            } else {
                                value += 97;
                            } //END if-else STATEMENT
                            char ch = ( char )value;
                            solution += "\'" + ch + "\'";
                        } else if( token.getToken().equals( "String" ) ) {
                            int len = ( int )( Math.random() * 10 );
                            String value = "\"";
                            for( int l=0; l<len; l++ ) {
                                int ivalue = ( int )( Math.random() * 26 );
                                int tvalue = ( int )( Math.random() * 3 );
                                if( tvalue==0 ) {
                                    ivalue += 65;
                                } else if( tvalue==1 ) {
                                    ivalue += 97;
                                } else {
                                    ivalue += 48;
                                } //END if-else STATEMENT
                                char ch = ( char )ivalue;
                                value += ch;
                            } //END for LOOP
                            value += "\"";
                            solution += value;
                        } //END if-else STATEMENTS
                        mt++;
                        token = ( Token )mTokens.get( mt++ );
                        if( token.getToken().equals( "," ) ) {
                            solution += ",";
                            token = ( Token )mTokens.get( mt++ );
                        } //END if STATEMENT
                    } //END while LOOP
                    solution += ");";
                    break FOR;
                } //END if STATEMENT
            } //NED for LOOP
            testCase.setTestCase( solution );
            this.population.add( testCase );
        } //END for LOOP
    } //END generatePopulation() METHOD
    
    public void executeTestCases( Target target ) {
        try {
            String targetPath1 = EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "/instrument/" + target.getMutationOperator() + "/" + target.getMutantNumber();
            String targetPath2 = EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "/oinstrument/" + target.getMutationOperator() + "/" + target.getMutantNumber();
            for( int t=0; t<this.population.size(); t++ ) {
                TestCase testCase = ( TestCase )this.population.get( t );
                String driverString = "import java.io.*;\n";
                driverString += "public class Driver"+ t + " {\n";
                driverString += "public static void main( String[] args )throws Exception {\n";
                String[] testCaseStrings = this.breakTestCase( testCase.toString() );
                driverString += testCaseStrings[ 0 ] + " args[0] " + testCaseStrings[ 1 ] + "\n";
                driverString += "}\n";
                driverString += "}\n";
                File driverI = new File( targetPath1 + "/Driver" + t + ".java" );
                RandomAccessFile rafi = new RandomAccessFile( driverI,"rw" );
                rafi.writeBytes( driverString );
                rafi.close();
                File driverO = new File( targetPath2 + "/Driver" + t + ".java" );
                RandomAccessFile rafo = new RandomAccessFile(  driverO,"rw" );
                rafo.writeBytes( driverString );
                rafo.close();
            } //END for LOOP
            
            Runtime.getRuntime().exec( "javac -cp " + targetPath1 + " " + targetPath1 + "/*.java" );
            File d1Check = new File( targetPath1 + "/Driver0.class" );
            int timeout = 1;
            while( !d1Check.exists() && timeout<=EMConstants.GA_TIMEOUT ) {
                timeout++;
            } //END of while LOOP

            Runtime.getRuntime().exec( "javac -cp " + targetPath2 + " " + targetPath2 + "/*.java" );
            File d2Check = new File( targetPath2 + "/Driver0.class" );
            timeout = 1;
            while( !d2Check.exists() && timeout<=EMConstants.GA_TIMEOUT ) {
                timeout++;
            } //END of while LOOP
            
            for( int t=0; t<this.population.size() ; ) {
                for( int a=1; a<=4 && t<this.population.size(); a++,t++ ) {
                    String ITraceFile = EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "/traces/" + this.threadNumber + "-" + this.traceCount + "Itrace.txt";
                    Runtime.getRuntime().exec( "java -cp " + targetPath1 + " Driver" + t + " " + ITraceFile );

                    String OTraceFile = EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "/traces/" + this.threadNumber + "-" + this.traceCount + "Otrace.txt";
                    Runtime.getRuntime().exec( "java -cp " + targetPath2 + " Driver" + t + " " + OTraceFile );

                    this.traceCount++;
                } //END for LOOP
                Thread.sleep( 500 );
            } //END for LOOP

            String TempITraceFile = EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "/traces/" + this.threadNumber + "-" + this.traceCount + "Itrace.txt";
            File filei = new File( TempITraceFile );
            timeout = 1;
            while( !filei.exists() && timeout<=EMConstants.GA_TIMEOUT ) {
                timeout++;
            } //END while LOOP

            for( int t=0; t<this.population.size(); t++ ) {
                File driverI = new File( targetPath1 + "/Driver" + t + ".java" );
                File driverIC = new File( targetPath1 + "/Driver" + t + ".class" );
                driverI.delete();
                driverIC.delete();
                File driverO = new File( targetPath2 + "/Driver" + t + ".java" );
                File driverOC = new File( targetPath2 + "/Driver" + t + ".class" );
                driverO.delete();
                driverOC.delete();
            } //END for LOOP
        } catch( Exception e ) {
            e.printStackTrace();
        } //END try-catch BLOCK

        int testCasesGen = Integer.parseInt( TestCaseAnalysis.jLabel11.getText().trim() ) + this.population.size();
        TestCaseAnalysis.jLabel11.setText( "" + testCasesGen );
    } //END executeTestCases() METHOD
    
    public String[] breakTestCase( String tc ) {
        String[] tcs = new String[ 2 ];
        String temp = "";
        for( int t=0; t<tc.length(); t++ ) {
            char ch = tc.charAt( t );
            if( ch!='(' ) {
                temp += ch;
            } else {
                temp += ch;
                tcs[ 0 ] = temp;
                tcs[ 1 ] = tc.substring( t+1 );
                break;
            } //END if-else STATEMENT
        } //END for LOOP
        return tcs;
    } //END breakTestCase() METHOD
    
    public void evaluateTestCases( Target target ) {
        for( int t=0; t<this.population.size(); t++ ) {
           TestCase testCase = ( TestCase )this.population.get( t );
           testCase.setWeight( 0.0 );
        } //END for LOOP

        try {
            FOR: for( int t=0; t<this.population.size(); t++ ) {
                TestCase testCase = ( TestCase )this.population.get( t );
                File fileI = new File( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "/traces/" + this.threadNumber + "-" + (this.traceNumber + t + 1) + "Itrace.txt" );
                File fileO = new File( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "/traces/" + this.threadNumber + "-" + (this.traceNumber + t + 1) + "Otrace.txt" );
                if( fileI.exists() && fileO.exists() ) {
                    LineNumberReader lnrI = new LineNumberReader( new FileReader( fileI ) );
                    LineNumberReader lnrO = new LineNumberReader( new FileReader( fileO ) );
                    String lineI = lnrI.readLine();
                    String lineO = lnrO.readLine();
                    if( lineI!=null ) {
                        DO: do {
                            if( lineI.startsWith( "R: ") ) {
                                break DO;
                            } //END if STATEMENT
                            lineI = lnrI.readLine();
                        } while( lineI!=null && !lineI.startsWith( "R:" ) );
                        if( lineI==null ) {
                            EMuJava.jTextArea2.append( "Test Case# " + (t+1) + "\n" );
                            EMuJava.jTextArea2.append( ( (TestCase )this.population.get( t ) ).toString() );
                            EMuJava.jTextArea2.append( "\nFITNESS: Cannot be Calculated!" );
                            testCase.setWeight( -10.0 );
                            EMuJava.jTextArea2.append( "\nWEIGHT: -10.0\n\n" );
                        } else if( lineI.equals( "R: 0.0 0 0.0" ) ) {
                            EMuJava.jTextArea2.append( "Test Case# " + (t+1) + "\n" );
                            EMuJava.jTextArea2.append( ( (TestCase )this.population.get( t ) ).toString() );
                            testCase.setStateFitness( 0.0 );
                            testCase.setApproachLevel( 0 );
                            testCase.setLocalFitness( 0.0 );
                            testCase.setWeight( 4.0 );
                            lineI = lnrI.readLine();
                            DO1: do {
                                if( lineI!=null && lineI.startsWith( "N: " ) ) {
                                    break DO1;
                                } //END if STATEMENT
                                lineI = lnrI.readLine();
                            } while( lineI!=null && !lineI.startsWith( "N: " ) );
                            DO2: do {
                                if( lineO!=null && lineO.startsWith( "N: " ) ) {
                                    break DO2;
                                } //END if STATEMENT
                                lineO = lnrO.readLine();
                            } while( lineO!=null && !lineO.startsWith( "N: " ) );
                            if( lineI!=null && lineO!=null ) {
                                boolean isSufficient = false;
                                StringTokenizer tokenI = new StringTokenizer( lineI, " " );
                                StringTokenizer tokenO = new StringTokenizer( lineO, " " );
                                tokenI.nextToken();
                                tokenO.nextToken();
                                WHILE: while( tokenI.hasMoreTokens() ) {
                                    String token1 = tokenI.nextToken();
                                    String token2 = tokenO.nextToken();
                                    if( !token1.equals( token2 ) ) {
                                        testCase.setNecessityCost( "0.0" );
                                        testCase.setWeight( testCase.getWeight()+1.0 );
                                        isSufficient = true;
                                        break WHILE;
                                    } //END if STATEMENT
                                } //END while LOOP
                                if( isSufficient ) {
                                    lineI = lnrI.readLine();
                                    DO1: do {
                                        if( lineI!=null && lineI.startsWith( "S: " ) ) {
                                            break DO1;
                                        } //END if STATEMENT
                                        lineI = lnrI.readLine();
                                    } while( lineI!=null && !lineI.startsWith( "S: " ) );
                                    ArrayList listI = new ArrayList();
                                    while( lineI!=null ) {
                                        if( !lineI.equals( "" ) ) {
                                            listI.add( lineI );
                                        } //END if STATEMENT
                                        lineI = lnrI.readLine();
                                    } //END while LOOP
                                    lineO = lnrO.readLine();
                                    DO1: do {
                                        if( lineO!=null && lineO.startsWith( "S: " ) ) {
                                            break DO1;
                                        } //END if STATEMENT
                                        lineO = lnrO.readLine();
                                    } while( lineO!=null && !lineO.startsWith( "S: " ) );
                                    ArrayList listO = new ArrayList();
                                    while( lineO!=null ) {
                                        if( !lineO.equals( "" ) ) {
                                            listO.add( lineO );
                                        } //END if STATEMENT
                                        lineO = lnrO.readLine();
                                    } //END while LOOP
                                    if( listI.size()>0 && listO.size()>0 ) {
                                        testCase.setFitness( true );
                                        String suffI = ( String )listI.get( listI.size()-1 );
                                        String suffO = ( String )listO.get( listO.size()-1 );
                                        if( suffI.equals( suffO ) ) {
                                            if( listI.size()==listO.size() ) {
                                                boolean isAlive = true;
                                                int suff_cost = 0;
                                                FORA: for( int x=0; x<listI.size(); x++ ) {
                                                    String pathI = ( String )listI.get( x );
                                                    String pathO = ( String )listO.get( x );
                                                    if( pathI.equals( pathO ) ) {
                                                        suff_cost++;
                                                    } else {
                                                        isAlive = false;
                                                    }//END if STATEMENT
                                                } //END for LOOP
                                                if( isAlive ) {
                                                    testCase.setSufficiencyCost( suff_cost + "" );
                                                    testCase.setStatus( "Normal" );
                                                } else {
                                                    testCase.setSufficiencyCost( suff_cost + "" );
                                                    testCase.setStatus( "Suspicious" );
                                                    testCase.setWeight( testCase.getWeight()+1.0 );
                                                    target.setSuspicious(true );
                                                } //END if-else STATEMENT
                                            } else {
                                                int suff_cost = 0;
                                                if( listI.size()<listO.size() ) {
                                                    FORA: for( int x=0; x<listI.size(); x++ ) {
                                                        String pathI = ( String )listI.get( x );
                                                        String pathO = ( String )listO.get( x );
                                                        if( pathI.equals( pathO ) ) {
                                                            suff_cost++;
                                                        } //END if STATEMENT
                                                    } //END for LOOP
                                                } else {
                                                    FORA: for( int x=0; x<listO.size(); x++ ) {
                                                        String pathI = ( String )listI.get( x );
                                                        String pathO = ( String )listO.get( x );
                                                        if( pathI.equals( pathO ) ) {
                                                            suff_cost++;
                                                        } //END if STATEMENT
                                                    } //END for LOOP                                                    
                                                } //END if-else STATEMENT
                                                testCase.setSufficiencyCost( suff_cost + "" );
                                                testCase.setStatus( "Suspicious" );
                                                testCase.setWeight( testCase.getWeight()+1.0 );
                                                target.setSuspicious( true );
                                            } //END if-else STATEMENT
                                            testCase.setWeight( testCase.getWeight()-( testCase.getApproachLevel()+testCase.getLocalFitness()+testCase.getStateFitness() ) );
                                            EMuJava.jTextArea2.append( "\nFITNESS: [(" + testCase.getStateFitness() + ", [" + testCase.getApproachLevel() + ", " + testCase.getLocalFitness() + "]) ; " + testCase.getNecessityCost() + "; (" + testCase.getSufficiencyCost() + ", " + testCase.getStatus() + ")]" );
                                            EMuJava.jTextArea2.append( "\nWEIGHT: " + testCase.getWeight() + "\n\n" );
                                        } else {
                                            testCase.setSufficiencyCost( "0" );
                                            testCase.setWeight( testCase.getWeight()+1.0 );
                                            testCase.setStatus( "Normal" );
                                            target.setTestCase( testCase );
                                            target.setAchieved( true );
                                            target.setStatus( "Killed" );
                                            EMConstants.ACHIEVED_TARGETS.add( target );
                                            EMConstants.TARGETS.remove( target );
                                            EMConstants.EFFECTIVE_TESTCASES.add( testCase );
                                            testCase.setWeight( testCase.getWeight()-( testCase.getApproachLevel()+testCase.getLocalFitness()+testCase.getStateFitness() ) );
                                            EMuJava.jTextArea3.append( "TARGET: " + target.getMutationOperator() + "-" + target.getMutantNumber() + ",\tITERATION: " + (EMConstants.ITERATIONS_PERFORMED+1) + "\n" );
                                            EMuJava.jTextArea3.append( ( (TestCase )this.population.get( t ) ).toString() + "\n\n" );
                                            EMuJava.jTextArea2.append( "\nFITNESS: [(" + testCase.getStateFitness() + ", [" + testCase.getApproachLevel() + ", " + testCase.getLocalFitness() + "]) ; " + testCase.getNecessityCost() + "; (" + testCase.getSufficiencyCost() + ", " + testCase.getStatus() + ")]" );
                                            EMuJava.jTextArea2.append( "\nWEIGHT: " + testCase.getWeight() + "\n\n" );
                                            ProcessProgress.getProcessProgress().statusLabel.setText( "Target has achieved!!!" );
                                            break FOR;
                                        } //END if-else STATEMENT
                                    } else {
                                        EMuJava.jTextArea2.append( "\nFITNESS: Cannot be Calculated!" );
                                        testCase.setWeight( -10.0 );
                                        EMuJava.jTextArea2.append( "\nWEIGHT: -10.0\n\n" );
                                    } //END if-else STATEMENT
                                } else {
                                    testCase.setFitness( true );
                                    lineI = lineI.substring( 3 );
                                    lineO = lineO.substring( 3 );
                                    if( lineI.equals( "true" ) || lineI.equals( "false" ) ) {
                                        testCase.setNecessityCost( "0.5" );
                                    } else {
                                        double weightI = 0.0;
                                        double weightO = 0.0;
                                        for( int i=0; i<lineI.length(); i++ ) {
                                            weightI += lineI.charAt( i );
                                        } //END for LOOP
                                        for( int o=0; o<lineO.length(); o++ ) {
                                            weightO += lineO.charAt( o );
                                        } //END for LOOP
                                        double necessity_cost = ( weightI - weightO ) / ( weightI + weightO );
                                        necessity_cost = Math.abs( necessity_cost );
                                        String nc = necessity_cost + "";
                                        if( nc.length()>4 ) {
                                            testCase.setNecessityCost( nc.substring( 0, 4 ) );
                                        } else {
                                            testCase.setNecessityCost( nc );
                                        } //END if-else STATEMENT
                                    } //END if-else STATEMENT
                                    testCase.setSufficiencyCost( "c" );
                                    testCase.setStatus( "Normal" );
                                    testCase.setWeight( testCase.getWeight()-( testCase.getApproachLevel()+testCase.getLocalFitness()+testCase.getStateFitness() ) );
                                    EMuJava.jTextArea2.append( "\nFITNESS: [(" + testCase.getStateFitness() + ", [" + testCase.getApproachLevel() + ", " + testCase.getLocalFitness() + "]) ; " + testCase.getNecessityCost() + "; (c, Normal)]" );
                                    EMuJava.jTextArea2.append( "\nWEIGHT: " + testCase.getWeight() + "\n\n" );
                                } //END if-else STATEMENT
                            } else {
                                testCase.setWeight( -10.0 );
                                EMuJava.jTextArea2.append( "\nFITNESS: Cannot be Calculated!" );
                                EMuJava.jTextArea2.append( "\nWEIGHT: -10.0\n\n" );
                            } //END if-else STATEMENT
                        } else {
                            testCase.setFitness( true );
                            EMuJava.jTextArea2.append( "Test Case# " + (t+1) + "\n" );
                            EMuJava.jTextArea2.append( ( (TestCase )this.population.get( t ) ).toString() );
                            StringTokenizer tokenizer = new StringTokenizer( lineI, " " );
                            tokenizer.nextToken();
                            String token = tokenizer.nextToken();
                            testCase.setStateFitness( Double.parseDouble( token ) );
                            if( token.equals( "0.0" ) ) {
                                testCase.setWeight( testCase.getWeight()+2.0 );
                            } //END if STATEMENT
                            token = tokenizer.nextToken();
                            testCase.setApproachLevel( Integer.parseInt( token ) );
                            if( token.equals( "0" ) ) {
                                testCase.setWeight( testCase.getWeight()+1.0 );
                            } //END if STATEMENT
                            token = tokenizer.nextToken();
                            testCase.setLocalFitness(Double.parseDouble(token ) );
                            if( token.equals( "0.0" ) ) {
                                testCase.setWeight( testCase.getWeight()+1.0 );
                            } //END if STATEMENT
                            testCase.setNecessityCost( "c" );
                            testCase.setSufficiencyCost( "c" );
                            testCase.setStatus( "Normal" );
                            testCase.setWeight( testCase.getWeight()-( testCase.getApproachLevel()+testCase.getLocalFitness()+testCase.getStateFitness() ) );
                            EMuJava.jTextArea2.append( "\nFITNESS: [(" + testCase.getStateFitness() + ", [" + testCase.getApproachLevel() + ", " + testCase.getLocalFitness() + "]) ; c ; (c, Normal)]" );
                            EMuJava.jTextArea2.append( "\nWEIGHT: " + testCase.getWeight() + "\n\n" );
                        } //END if-else STATEMENT
                    } else {
                        EMuJava.jTextArea2.append( "Test Case# " + (t+1) + "\n" );
                        EMuJava.jTextArea2.append( ( (TestCase )this.population.get( t ) ).toString() );
                        EMuJava.jTextArea2.append( "\nFITNESS: Cannot be Calculated!" );
                        testCase.setWeight( -10.0 );
                        EMuJava.jTextArea2.append( "\nWEIGHT: -10.0\n\n" );
                    } //END if-else LOOP
                } else {
                    EMuJava.jTextArea2.append( "Test Case# " + (t+1) + "\n" );
                    EMuJava.jTextArea2.append( ( (TestCase )this.population.get( t ) ).toString() );
                    EMuJava.jTextArea2.append( "\nFITNESS: Cannot be Calculated!" );
                    testCase.setWeight( -10.0 );
                    EMuJava.jTextArea2.append( "\nWEIGHT: -10.0\n\n" );
                } //END if-else STATEMENT
            } //END for LOOP
        } catch( Exception e ) {
            e.printStackTrace();
        } //END try-catch BLOCK
        this.traceNumber += this.population.size();
        
        try {
            File traceDir = new File( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "/traces/" );
            File[] traceFiles = traceDir.listFiles();
            for( int t=0; t<traceFiles.length; t++ ) {
                if( traceFiles[ t ].exists() ) {
                    traceFiles[ t ].delete();
                } //END if STATEMENT
            } //END for LOOP
        } catch( Exception exception ) {
            exception.printStackTrace();
        } //END try-catch BLOCK
    } //END evaluateTestCases() METHOD

    public String retrieveMethodUnderTest( String target ) {
        String line = "";
        try {
            LineNumberReader lnr = null;
            if( target.contains( "/IOP/" ) || target.contains( "/PNC/" ) ) {
                lnr = new LineNumberReader( new FileReader( target + "/" + EMConstants.CLASS_2 ) );
            } else {
                lnr = new LineNumberReader( new FileReader( target + "/" + EMConstants.CLASS_1 ) );
            } //END if-else STATEMENT
            line = lnr.readLine();
            lnr.close();
        } catch( Exception e ) {
            e.printStackTrace();
        } //END try-catch BLOCK
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
    } //END retrieveMethodUnderTest() METHOD
} //END GeneticAlgorithm CLASS
