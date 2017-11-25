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
 * @version 1.0 August 25, 2013
 * 
 */
public class SourceCode {

    public void generateMutants( int classNumber ) {
        String skel = "";
        ClassComponents cComponents = null;
        if( classNumber==1 ) {
            skel = this.generateSekeleton( 1 );
            cComponents = EMController.create().getC1Components();
            this.generateOMutant( classNumber, skel );
        } else {
            skel = this.generateSekeleton( 2 );
            cComponents = EMController.create().getC2Components();
            this.generateOMutant( classNumber, skel );
        } //END if-else STATEMENT
        ArrayList mmList = cComponents.getMMList();
        
        ArrayList mutationOperatorsList = EMConstants.MUTATION_OPERATORS;
        for( int m=0; m<mutationOperatorsList.size(); m++ ) {
            String mutationOperator = ( String )mutationOperatorsList.get( m );
            if( mutationOperator.equals( "ROR" ) && classNumber==1 ) {
                try {
                    File file = new File( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "\\mutants\\ROR" );
                    file.mkdir();
                } catch( Exception e ) {
                    e.printStackTrace();
                } //END try-catch BLOCK
                
                int mutantNumber = 1;
                for( int mm=0; mm<mmList.size(); mm++ ) {
                    MemberMethod mMethod = ( MemberMethod )mmList.get( mm );
                    if( mMethod.getMethodName().startsWith( "toString" ) || mMethod.getMethodName().startsWith( "set" ) || mMethod.getMethodName().startsWith( "get" ) ) {
                        continue;
                    } //END if STATEMENT
                    ArrayList mmTokens = mMethod.getMethodTokens();
                    for( int mmt=0; mmt<mmTokens.size(); mmt++ ) {
                        Token token = ( Token )mmTokens.get( mmt );
                        if( token.getDescription().equals( "Relational Operator" ) ) {
                            if( token.getToken().equals( "==" ) ) {
                                Token tempToken = ( Token )mmTokens.get( mmt+1 );
                                if( !tempToken.getToken().equals( "this" ) ) {
                                    ArrayList mutatedTokenList = getRORToken( token.getToken() );
                                    for( int mtl=0; mtl<mutatedTokenList.size(); mtl++ ) {
                                        String mutatedToken = ( String )mutatedTokenList.get( mtl );
                                        this.generateMutant( classNumber, skel, mutantNumber, mm, mmt, mutatedToken, "ROR" );
                                        Target target = new Target();
                                        target.setMutationOperator( "ROR" );
                                        target.setMutantNumber( mutantNumber );
                                        EMConstants.TARGETS.add( target );
                                        mutantNumber++;
                                        EMConstants.TOTAL_MUTANTS++;                                        
                                    } //END for LOOP
                                } //END if STATEMENT
                            } else {
                                ArrayList mutatedTokenList = getRORToken( token.getToken() );
                                for( int mtl=0; mtl<mutatedTokenList.size(); mtl++ ) {
                                    String mutatedToken = ( String )mutatedTokenList.get( mtl );
                                    this.generateMutant( classNumber, skel, mutantNumber, mm, mmt, mutatedToken, "ROR" );
                                    Target target = new Target();
                                    target.setMutationOperator( "ROR" );
                                    target.setMutantNumber( mutantNumber );
                                    EMConstants.TARGETS.add( target );
                                    mutantNumber++;
                                    EMConstants.TOTAL_MUTANTS++;                                    
                                } //END for LOOP                             
                            }//END if-else STATEMENT
                        } //END if STATEMENT
                    } //END for LOOP
                } //END for LOOP
            } //END if STATEMENT
            if( mutationOperator.equals( "AOR" ) && classNumber==1 ) {
                try {
                    File file = new File( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "\\mutants\\AOR" );
                    file.mkdir();
                } catch( Exception e ) {
                    e.printStackTrace();
                } //END try-catch BLOCK
                
                int mutantNumber = 1;
                for( int mm=0; mm<mmList.size(); mm++ ) {
                    MemberMethod mMethod = ( MemberMethod )mmList.get( mm );
                    if( mMethod.getMethodName().startsWith( "toString" ) || mMethod.getMethodName().startsWith( "set" ) || mMethod.getMethodName().startsWith( "get" ) ) {
                        continue;
                    } //END if STATEMENT
                    ArrayList mmTokens = mMethod.getMethodTokens();
                    for( int mmt=0; mmt<mmTokens.size(); mmt++ ) {
                        Token token = ( Token )mmTokens.get( mmt );
                        if( token.getDescription().equals( "Arithmatic Operator" ) ) {
                            ArrayList mutatedTokenList = getAORToken( token.getToken() );
                            for( int mtl=0; mtl<mutatedTokenList.size(); mtl++ ) {
                                String mutatedToken = ( String )mutatedTokenList.get( mtl );
                                this.generateMutant( classNumber, skel, mutantNumber, mm, mmt, mutatedToken, "AOR" );
                                Target target = new Target();
                                target.setMutationOperator( "AOR" );
                                target.setMutantNumber( mutantNumber );
                                EMConstants.TARGETS.add( target );
                                mutantNumber++;
                                EMConstants.TOTAL_MUTANTS++;
                            } //END for LOOP
                        }//END if STATEMENT
                    } //END for LOOP
                } //END for LOOP
            } //END if STATEMENT
            if( mutationOperator.equals( "LCR" ) && classNumber==1 ) {
                try {
                    File file = new File( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "\\mutants\\LCR" );
                    file.mkdir();
                } catch( Exception e ) {
                    e.printStackTrace();
                } //END try-catch BLOCK
                
                int mutantNumber = 1;
                for( int mm=0; mm<mmList.size(); mm++ ) {
                    MemberMethod mMethod = ( MemberMethod )mmList.get( mm );
                    if( mMethod.getMethodName().startsWith( "toString" ) || mMethod.getMethodName().startsWith( "set" ) || mMethod.getMethodName().startsWith( "get" ) ) {
                        continue;
                    } //END if STATEMENT
                    ArrayList mmTokens = mMethod.getMethodTokens();
                    for( int mmt=0; mmt<mmTokens.size(); mmt++ ) {
                        Token token = ( Token )mmTokens.get( mmt );
                        if( token.getDescription().equals( "Logical Operator" ) ) {
                            String mutatedToken = getLCRToken( token.getToken() );
                            this.generateMutant( classNumber, skel, mutantNumber, mm, mmt, mutatedToken, "LCR" );
                            Target target = new Target();
                            target.setMutationOperator( "LCR" );
                            target.setMutantNumber( mutantNumber );
                            EMConstants.TARGETS.add( target );
                            mutantNumber++;
                            EMConstants.TOTAL_MUTANTS++;
                        } else {
                            System.out.println( token.getToken() + ": " + token.getDescription() );
                        } //END if STATEMENT
                    } //END for LOOP
                } //END for LOOP
            } //END if STATEMENT
            if( mutationOperator.equals( "UOI" ) && classNumber==1 ) {
                try {
                    File file = new File( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "\\mutants\\UOI" );
                    file.mkdir();
                } catch( Exception e ) {
                    e.printStackTrace();
                } //END try-catch BLOCK
                
                int mutantNumber = 1;
                for( int mm=0; mm<mmList.size(); mm++ ) {
                    MemberMethod mMethod = ( MemberMethod )mmList.get( mm );
                    if( mMethod.getMethodName().startsWith( "toString" ) || mMethod.getMethodName().startsWith( "set" ) || mMethod.getMethodName().startsWith( "get" ) ) {
                        continue;
                    } //END if STATEMENT
                    int tokenNumber = mMethod.getMethodHeaderCount()-1;
                    ArrayList statements = mMethod.getStatementsList();
                    for( int mmt=0; mmt<statements.size(); mmt++ ) {
                        Statement statement = ( Statement )statements.get( mmt );
                        ArrayList tokens = statement.getStatementTokens();
                        if( statement.getDescription().equals( "Assignment Statement" ) || statement.getDescription().equals( "Data Member Initialization" ) ) {
                            int t=0;
                            Token token = null;
                            do {
                                token = ( Token )tokens.get( t++ );
                                tokenNumber++;
                            } while( !token.getToken().equals( "=" ) );
                            for( ; t<tokens.size(); t++ ) {
                                token = ( Token )tokens.get( t );
                                tokenNumber++;
                                if( token.getDescription().equals( "Identifier" ) ) {
                                    String[] mutatedTokenList = getUOIToken();
                                    for( int mtl=0; mtl<2; mtl++ ) {
                                        String mutatedToken = mutatedTokenList[ mtl ];
                                        mutatedToken = mutatedToken + token.getToken();
                                        this.generateMutant( classNumber, skel, mutantNumber, mm, tokenNumber, mutatedToken, "UOI" );
                                        Target target = new Target();
                                        target.setMutationOperator( "UOI" );
                                        target.setMutantNumber( mutantNumber );
                                        EMConstants.TARGETS.add( target );
                                        mutantNumber++;
                                        EMConstants.TOTAL_MUTANTS++;
                                        mutatedToken = mutatedTokenList[ mtl ];
                                        mutatedToken = token.getToken() + mutatedToken;
                                        this.generateMutant( classNumber, skel, mutantNumber, mm, tokenNumber, mutatedToken, "UOI" );
                                        Target target2 = new Target();
                                        target2.setMutationOperator( "UOI" );
                                        target2.setMutantNumber( mutantNumber );
                                        EMConstants.TARGETS.add( target2 );
                                        mutantNumber++;
                                        EMConstants.TOTAL_MUTANTS++;
                                    } //END for LOOP
                                } //END if STATEMENT
                            } //END for LOOP
                        } else {
                            tokenNumber += tokens.size();
                        } //END if-else STATEMENT
                    } //END for LOOP
                } //END for LOOP
            } //END if STATEMENT
            if( mutationOperator.equals( "ABS" ) && classNumber==1 ) {
                try {
                    File file = new File( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "\\mutants\\ABS" );
                    file.mkdir();
                } catch( Exception e ) {
                    e.printStackTrace();
                } //END try-catch BLOCK
                
                int mutantNumber = 1;
                for( int mm=0; mm<mmList.size(); mm++ ) {
                    MemberMethod mMethod = ( MemberMethod )mmList.get( mm );
                    if( mMethod.getMethodName().startsWith( "toString" ) || mMethod.getMethodName().startsWith( "set" ) || mMethod.getMethodName().startsWith( "get" ) ) {
                        continue;
                    } //END if STATEMENT
                    int tokenNumber = mMethod.getMethodHeaderCount()-1;
                    ArrayList statements = mMethod.getStatementsList();
                    for( int mmt=0; mmt<statements.size(); mmt++ ) {
                        Statement statement = ( Statement )statements.get( mmt );
                        ArrayList tokens = statement.getStatementTokens();
                        if( statement.getDescription().equals( "Assignment Statement" ) || statement.getDescription().equals( "Data Member Initialization" ) ) {
                            int t=0;
                            Token token = null;
                            do {
                                token = ( Token )tokens.get( t++ );
                                tokenNumber++;
                            } while( !token.getToken().equals( "=" ) );
                            tokenNumber++;
                            String mutatedToken = "";
                            mutatedToken += "Math.abs( ";
                            int tempTokenNumber = t;
                            token = ( Token )tokens.get( tempTokenNumber++ );
                            do {
                                mutatedToken += token.getToken();
                                if( tempTokenNumber<tokens.size() ) {
                                    token = ( Token )tokens.get( tempTokenNumber++ );
                                } //END if STATEMENT
                            } while( !token.getToken().equals( ";" ) );
                            mutatedToken += " )";
                            this.generateMutant( classNumber, skel, mutantNumber, mm, tokenNumber, mutatedToken, "ABS" );
                            Target target = new Target();
                            target.setMutationOperator( "ABS" );
                            target.setMutantNumber( mutantNumber );
                            EMConstants.TARGETS.add( target );
                            tokenNumber = tokenNumber + (tokens.size() - t - 1);
                            mutantNumber++;
                            EMConstants.TOTAL_MUTANTS++;
                        } else {
                            tokenNumber += tokens.size();
                        } //END if-else STATEMENT
                    } //END for LOOP
                } //END for LOOP
            } //END if STATEMENT
            if( mutationOperator.equals( "IOP" ) && classNumber==2 ) {
                try {
                    File file = new File( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "\\mutants\\IOP" );
                    file.mkdir();
                } catch( Exception e ) {
                    e.printStackTrace();
                } //END try-catch BLOCK

                ClassComponents c1Components = EMController.create().getC1Components();
                ArrayList mm1List = c1Components.getMMList();

                int mutantNumber = 1;
                for( int mm=0; mm<mmList.size(); mm++ ) {
                    MemberMethod mMethod = ( MemberMethod )mmList.get( mm );
                    if( mMethod.getMethodName().startsWith( "toString" ) || mMethod.getMethodName().startsWith( "set" ) || mMethod.getMethodName().startsWith( "get" ) ) {
                        continue;
                    } //END if STATEMENT
                    String header2 = mMethod.getMethodHeader();
                    for( int mm1=0; mm1<mm1List.size(); mm1++ ) {
                        MemberMethod m1Method = ( MemberMethod )mm1List.get( mm1 );
                        String header1 = m1Method.getMethodHeader();
                        if( header2.equals( header1 ) ) {
                            String skel1 = this.generateSekeleton( 1 );
                            this.generateMutant( 1, skel1, mutantNumber, -1, -1, "", "IOP" );
                            String skel2 = this.generateSekeleton( 2 );
                            this.generateIOPMutant( 2, skel2, mutantNumber, mm, -1, "", "IOP" );
                            Target target = new Target();
                            target.setMutationOperator( "IOP" );
                            target.setMutantNumber( mutantNumber );
                            EMConstants.TARGETS.add( target );
                            mutantNumber++;
                            EMConstants.TOTAL_MUTANTS++;
                        } //END if STATEMENT
                    } //END for LOOP
                } //END for LOOP
            } else {
                try {
                    File file = new File( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "\\mutants\\IOP" );
                    file.mkdir();
                } catch( Exception e ) {
                    e.printStackTrace();
                } //END try-catch BLOCK
            } //END if STATEMENT
            
            if( mutationOperator.equals( "PNC" ) && classNumber==2 ) {
                try {
                    File file = new File( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "\\mutants\\PNC" );
                    file.mkdir();
                } catch( Exception e ) {
                    e.printStackTrace();
                } //END try-catch BLOCK

                ClassComponents c1Components = EMController.create().getC1Components();
                ArrayList mm1List = c1Components.getMMList();

                int mutantNumber = 1;
                for( int mm=0; mm<mmList.size(); mm++ ) {
                    MemberMethod mMethod = ( MemberMethod )mmList.get( mm );
                    if( mMethod.getMethodName().startsWith( "toString" ) || mMethod.getMethodName().startsWith( "set" ) || mMethod.getMethodName().startsWith( "get" ) ) {
                        continue;
                    } //END if STATEMENT

                    int tokenNumber = mMethod.getMethodHeaderCount()-1;
                    ArrayList statements = mMethod.getStatementsList();
                    for( int mmt=0; mmt<statements.size(); mmt++ ) {                        
                        Statement statement = ( Statement )statements.get( mmt );
                        ArrayList tokens = statement.getStatementTokens();
                        if( statement.getDescription().equals( "Object Creation" ) ) {
                            int t=0;
                            Token token = null;
                            do {
                                token = ( Token )tokens.get( t++ );
                                tokenNumber++;
                            } while( !token.getToken().equals( "new" ) );
                            tokenNumber++;
                            token = ( Token )tokens.get( t++ );
                            String mutatedToken = "";
                            String class1 = token.getToken() + ".java";
                            if( class1.equals( EMConstants.CLASS_1 ) ) {
                                String skel1 = this.generateSekeleton( 1 );
                                this.generateMutant( 1, skel1, mutantNumber, -1, -1, "", "PNC" );
                                mutatedToken = EMConstants.CLASS_2.substring( 0, EMConstants.CLASS_2.length() - 5 );
                                this.generateMutant( classNumber, skel, mutantNumber, mm, tokenNumber, mutatedToken, "PNC" );
                                Target target = new Target();
                                target.setMutationOperator( "PNC" );
                                target.setMutantNumber( mutantNumber );
                                EMConstants.TARGETS.add( target );
                                tokenNumber = tokenNumber + (tokens.size() - t);
                                mutantNumber++;
                                EMConstants.TOTAL_MUTANTS++;
                            } else {
                                tokenNumber = tokenNumber + (tokens.size() - t);
                            } //END if-else STATEMENT
                        } else {
                            tokenNumber += tokens.size();
                        } //END if-else STATEMENT
                    } //END for LOOP
                } //END for LOOP
            } else {
                try {
                    File file = new File( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "\\mutants\\PNC" );
                    file.mkdir();
                } catch( Exception e ) {
                    e.printStackTrace();
                } //END try-catch BLOCK
            } //END if STATEMENT        

            if( mutationOperator.equals( "OMD" ) && classNumber==1 ) {
                try {
                    File file = new File( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "\\mutants\\OMD" );
                    file.mkdir();
                } catch( Exception e ) {
                    e.printStackTrace();
                } //END try-catch BLOCK
                
                ClassComponents c1Components = EMController.create().getC1Components();
                ArrayList mm1List = c1Components.getMMList();

                int mutantNumber = 1;
                for( int mm=0; mm<mmList.size(); mm++ ) {
                    MemberMethod mMethod = ( MemberMethod )mmList.get( mm );
                    if( mMethod.getMethodName().startsWith( "toString" ) || mMethod.getMethodName().startsWith( "set" ) || mMethod.getMethodName().startsWith( "get" ) ) {
                        continue;
                    } //END if STATEMENT
                    String header2 = mMethod.getMethodHeader();
                    for( int mm1=0; mm1<mm1List.size(); mm1++ ) {
                        MemberMethod m1Method = ( MemberMethod )mm1List.get( mm1 );
                        String header1 = m1Method.getMethodHeader();
                        if( mMethod.getMethodName().equals( m1Method.getMethodName() ) && !header2.equals( header1 ) ) {
                            String skel2 = this.generateSekeleton( 1 );
                            this.generateOMDMutant( 1, skel2, mutantNumber, mm, -1, "", "OMD" );
                            Target target = new Target();
                            target.setMutationOperator( "OMD" );
                            target.setMutantNumber( mutantNumber );
                            EMConstants.TARGETS.add( target );
                            mutantNumber++;
                            EMConstants.TOTAL_MUTANTS++;
                        } //END if STATEMENT
                    } //END for LOOP
                } //END for LOOP
            } //END if STATEMENT

            if( mutationOperator.equals( "JID" ) && classNumber==1 ) {
                try {
                    File file = new File( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "\\mutants\\JID" );
                    file.mkdir();
                } catch( Exception e ) {
                    e.printStackTrace();
                } //END try-catch BLOCK

                int mutantNumber = 1;
                for( int mm=0; mm<mmList.size(); mm++ ) {
                    MemberMethod mMethod = ( MemberMethod )mmList.get( mm );
                    if( mMethod.getMethodName().startsWith( "toString" ) || mMethod.getMethodName().startsWith( "set" ) || mMethod.getMethodName().startsWith( "get" ) ) {
                        continue;
                    } //END if STATEMENT
                    int tokenNumber = mMethod.getMethodHeaderCount()-1;
                    ArrayList statements = mMethod.getStatementsList();
                    for( int mmt=0; mmt<statements.size(); mmt++ ) {                        
                        Statement statement = ( Statement )statements.get( mmt );
                        ArrayList tokens = statement.getStatementTokens();
                        if( statement.getDescription().equals( "Data Member Initialization" ) ) {
                            Token token = ( Token )tokens.get( 0 );
                            String mutatedToken = "// " + token.getToken();
                            tokenNumber++;
                            this.generateMutant( classNumber, skel, mutantNumber, mm, tokenNumber, mutatedToken, "JID" );
                            Target target = new Target();
                            target.setMutationOperator( "JID" );
                            target.setMutantNumber( mutantNumber );
                            EMConstants.TARGETS.add( target );
                            tokenNumber += tokens.size() - 1;
                            mutantNumber++;
                            EMConstants.TOTAL_MUTANTS++;
                        } else {
                            tokenNumber += tokens.size();
                        } //END if-else STATEMENT
                    } //END for LOOP
                } //END for LOOP
            } //END if STATEMENT
            
            if( mutationOperator.equals( "EOC" ) && classNumber==1 ) {
                try {
                    File file = new File( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "\\mutants\\EOC" );
                    file.mkdir();
                } catch( Exception e ) {
                    e.printStackTrace();
                } //END try-catch BLOCK

                int mutantNumber = 1;
                for( int mm=0; mm<mmList.size(); mm++ ) {
                    MemberMethod mMethod = ( MemberMethod )mmList.get( mm );
                    if( mMethod.getMethodName().startsWith( "toString" ) || mMethod.getMethodName().startsWith( "set" ) || mMethod.getMethodName().startsWith( "get" ) ) {
                        continue;
                    } //END if STATEMENT
                    int tokenNumber = mMethod.getMethodHeaderCount()-1;
                    int tempNumber = tokenNumber;
                    ArrayList statements = mMethod.getStatementsList();
                    for( int mmt=0; mmt<statements.size(); mmt++ ) {                        
                        Statement statement = ( Statement )statements.get( mmt );
                        ArrayList tokens = statement.getStatementTokens();
                        if( statement.getDescription().equals( "If Statement" ) || statement.getDescription().equals( "While Statementt" ) ) {
                            int t=0;
                            Token token = null;
                            do {
                                token = ( Token )tokens.get( t++ );
                                tempNumber++;
                            } while( !token.getToken().equals( "this" ) && t<tokens.size() );
                            tempNumber++;
                            if( t<tokens.size() ) {
                                t += 2;
                                tempNumber += 1;
                                token = ( Token )tokens.get( t++ );
//                                tokenNumber++;
                                String mutatedToken = "";
                                if( token.getToken().equals( "==" ) ) {
                                    t += 2;
                                    mutatedToken = ".equals( this." +  ( ( Token )tokens.get( t++ ) ).getToken() + " )";
                                    tempNumber += 3;
                                } else {
                                    t += 2;
                                    mutatedToken = "==" +  ( ( Token )tokens.get( t++ ) ).getToken() + "" + ( ( Token )tokens.get( t++ ) ).getToken() + "" + ( ( Token )tokens.get( t++ ) ).getToken();
                                    tempNumber++;
                                } //END if-else STATEMENT
                                this.generateMutant( classNumber, skel, mutantNumber, mm, tempNumber, mutatedToken, "EOC" );
                                Target target = new Target();
                                target.setMutationOperator( "EOC" );
                                target.setMutantNumber( mutantNumber );
                                EMConstants.TARGETS.add( target );
                                tokenNumber += tokens.size();
                                tempNumber = tokenNumber;
                                mutantNumber++;
                                EMConstants.TOTAL_MUTANTS++;
                            } //END if STATEMENT
                        } else {
                            tokenNumber += tokens.size();
                            tempNumber = tokenNumber;
                        }//END if STATEMENT
                    } //END for LOOP
                } //END for LOOP 
                
            } //END if STATEMENT
        } //END for LOOP
    } //END generateMutants() METHOD
    
    public String generateSekeleton( int classNumber ) {
        String skel = "";
        ClassComponents cComponents = null;
        if( classNumber==1 ) {
            cComponents = EMController.create().getC1Components();
        } else {
            cComponents = EMController.create().getC2Components();
        } //END if-else STATEMENT
        
        ArrayList import1List = cComponents.getImportsList();
        for( int i=0; i<import1List.size(); i++ ) {
            Token token = ( Token )import1List.get( i );
            skel = skel + token.getToken();
            if( token.getToken().equals( "import" ) ) {
                skel = skel + " ";
            } //END if STATEMENT
            if( token.getToken().equals( ";" ) ) {
                skel = skel + "\n";
            } //END if STATEMENT
        } //END for LOOP
        skel = skel + "\n";
        
        ArrayList cHeader = cComponents.getClassHeader();
        for( int i=0; i<cHeader.size(); i++ ) {
            Token token = ( Token )cHeader.get( i );
            skel = skel + token.getToken() + " ";        
        } //END for LOOP
        skel = skel + "{ \n";
        skel = skel + "\n";

        ArrayList dMembers = cComponents.getDMList();
        skel = this.addIndentation( skel, 1 );
        for( int i=0; i<dMembers.size(); i++ ) {
            Token token = ( Token )dMembers.get( i );
            if( token.getToken().equals( "," ) ) {
                if( skel.endsWith( " " ) ) {
                    skel = skel.substring( 0, skel.length()-1 ) + token.getToken() + " ";
                } else {
                    skel = skel + token.getToken() + " ";
                } //END if-else STATEMENT
            } else if( token.getToken().equals( ";" ) ) {
                if( skel.endsWith( " " ) ) {
                    skel = skel.substring( 0, skel.length()-1 ) + token.getToken() + "\n";
                } else {
                    skel = skel + token.getToken() + "\n";
                } //END if-else STATEMENT
                skel = this.addIndentation( skel, 1 );
            } else {
                skel = skel + token.getToken() + " ";
            }//END if STATEMENT
        } //END for LOOP
        skel = skel + "\n";
        
        ArrayList ccList = cComponents.getCCList();
        for( int c=0; c<ccList.size(); c++ ) {
            ClassConstructor cc = ( ClassConstructor )ccList.get( c );
            
            int indentCount = 1;
            skel = this.addIndentation( skel, indentCount );
            ArrayList ccTokens = cc.getConstructorTokens();
            for( int cct=0; cct<ccTokens.size(); cct++ ) {
                Token token = ( Token )ccTokens.get( cct );
                if( token.getDescription().equals( "Relational Operator" ) ) {
                    if( skel.endsWith( " " ) ) {
                        skel = skel.substring( 0, skel.length()-1 ) + token.getToken();
                    } else {
                        skel = skel + token.getToken();
                    } //END if-else STATEMENT
                } else if( token.getToken().equals( "." ) ) {
                    if( skel.endsWith( " " ) ) {
                        skel = skel.substring( 0, skel.length()-1 ) + token.getToken();
                    } else {
                        skel = skel + token.getToken();
                    } //END if-else STATEMENT
                } else if( token.getToken().equals( "(" ) || token.getToken().equals( "," ) ) {
                    if( skel.endsWith( " " ) ) {
                        skel = skel.substring( 0, skel.length()-1 ) + token.getToken() + " ";
                    } else {
                        skel = skel + token.getToken() + " ";
                    } //END if-else STATEMENT
                } else if( token.getToken().equals( ")" ) ) {
                    if( skel.endsWith( "( " ) ) {
                        skel = skel.substring( 0, skel.length()-1 ) + token.getToken() + " ";
                    } else {
                        skel = skel + token.getToken() + " ";
                    } //END if-else STATEMENT
                } else if( token.getToken().equals( ";" ) ) {
                    if( skel.endsWith( " " ) ) {
                        skel = skel.substring( 0, skel.length()-1 ) + token.getToken() + "\n";
                    } else {
                        skel = skel + token.getToken() + "\n";
                    } //END if-else STATEMENT
                    skel = this.addIndentation( skel, indentCount );
                } else if( token.getToken().equals( "}" ) ) {
                    indentCount--;
                    skel = this.removeIndentation( skel, 1 );
                    skel = skel + token.getToken() + "\n";
                    skel = this.addIndentation( skel, indentCount );
                } else if( token.getToken().equals( "{" ) ) {
                    skel = skel + token.getToken() + "\n";
                    indentCount++;
                    skel = this.addIndentation( skel, indentCount );
                } else if( token.getDescription().equals( "Data Type" ) ) {
                    skel = skel + token.getToken() + " ";
                } else if( token.getToken().equals( "else" ) ) {
                    skel = this.removeIndentation( skel, indentCount );
                    skel = skel.substring( 0, skel.length()-1 ) + " " + token.getToken() + " ";
                } else {
                    skel = skel + token.getToken() + " ";
                } //END if-else STATEMENT
            } //END for LOOP
            skel = skel + "\n";
        } //END for LOOP

        return skel;
    } //END generateSkeleton() METHOD
    
    public void generateOMutant( int classNumber, String skel ) {
        ClassComponents cComponents = null;
        if( classNumber==1 ) {
            cComponents = EMController.create().getC1Components();
        } else {
            cComponents = EMController.create().getC2Components();
        } //END if-else STATEMENT
        
        ArrayList mmList = cComponents.getMMList();
        
        for( int mm=0; mm<mmList.size(); mm++ ) {
            MemberMethod mMethod = ( MemberMethod )mmList.get( mm );
            int indentCount=1;
            skel = this.addIndentation( skel, indentCount );
            ArrayList mmTokens = mMethod.getMethodTokens();
            for( int mmt=0; mmt<mmTokens.size(); mmt++ ) {
                Token token = ( Token )mmTokens.get( mmt );
                if( token.getDescription().equals( "Relational Operator" ) ) {
                    if( skel.endsWith( " " ) ) {
                        skel = skel.substring( 0, skel.length()-1 ) + token.getToken();
                    } else {
                        skel = skel + token.getToken();
                    } //END if-else STATEMENT
                } else if( token.getToken().equals( "." ) ) {
                    if( skel.endsWith( " " ) ) {
                        skel = skel.substring( 0, skel.length()-1 ) + token.getToken();
                    } else {
                        skel = skel + token.getToken();
                    } //END if-else STATEMENT
                } else if( token.getToken().equals( "(" ) || token.getToken().equals( "," ) ) {
                    if( skel.endsWith( " " ) ) {
                        skel = skel.substring( 0, skel.length()-1 ) + token.getToken() + " ";
                    } else {
                        skel = skel + token.getToken() + " ";
                    } //END if-else STATEMENT
                } else if( token.getToken().equals( ")" ) ) {
                    if( skel.endsWith( "( " ) ) {
                        skel = skel.substring( 0, skel.length()-1 ) + token.getToken() + " ";
                    } else {
                        skel = skel + token.getToken() + " ";
                    } //END if-else STATEMENT
                } else if( token.getToken().equals( ";" ) ) {
                    if( skel.endsWith( " " ) ) {
                        skel = skel.substring( 0, skel.length()-1 ) + token.getToken() + "\n";
                    } else {
                        skel = skel + token.getToken() + "\n";
                    } //END if-else STATEMENT
                    skel = this.addIndentation( skel, indentCount );
                } else if( token.getToken().equals( "}" ) ) {
                    indentCount--;
                    skel = this.removeIndentation( skel, 1 );
                    skel = skel + token.getToken() + "\n";
                    skel = this.addIndentation( skel, indentCount );
                } else if( token.getToken().equals( "{" ) ) {
                    skel = skel + token.getToken() + "\n";
                    indentCount++;
                    skel = this.addIndentation( skel, indentCount );
                } else if( token.getDescription().equals( "Data Type" ) ) {
                    skel = skel + token.getToken() + " ";
                } else if( token.getToken().equals( "else" ) ) {
                    skel = this.removeIndentation( skel, indentCount );
                    skel = skel.substring( 0, skel.length()-1 ) + " " + token.getToken() + " ";
                } else {
                    skel = skel + token.getToken() + " ";
                } //END if-else STATEMENT
            } //END for LOOP
            skel = skel + "\n";
        } //END for LOOP

        skel = skel + "\n}";
        try {
            RandomAccessFile raf = null;
            if( classNumber==1 ) {
                raf = new RandomAccessFile( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "\\omutants\\" + EMConstants.CLASS_1, "rw" );
            } else {
                raf = new RandomAccessFile( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "\\omutants\\" + EMConstants.CLASS_2, "rw" );
            } //END if-else STATEMENT
            raf.writeBytes( skel );
            raf.close();
        } catch( Exception e ) {
            e.printStackTrace();
        } //END try-catch BLOCK
    
    } //END generateOMutant() METHOD
    
    public void generateOMDMutant( int classNumber, String skel, int mutantNumber, int methodNumber, int tokenNumber, String mutatedToken, String mutationOperator ) {
        ClassComponents cComponents = EMController.create().getC1Components();

        ArrayList mmList = cComponents.getMMList();

        String topComment = "";
        if( methodNumber>=0 ) {
            topComment = "//" + cComponents.getClassName() + ".";
        } //END if STATEMENT
        
        String oMethod = ( ( MemberMethod )mmList.get( methodNumber ) ).getMethodName();
    
        for( int mm=0; mm<mmList.size(); mm++ ) {
            if( mm==methodNumber ) {
                skel = skel.substring( 0, skel.length()-4 );
                skel = skel + "/* \n";
            } //END if STATEMENT

            MemberMethod mMethod = ( MemberMethod )mmList.get( mm );
            int indentCount=1;
            skel = this.addIndentation( skel, indentCount );
            ArrayList mmTokens = mMethod.getMethodTokens();
            for( int mmt=0; mmt<mmTokens.size(); mmt++ ) {
                Token token = ( Token )mmTokens.get( mmt );
                if( token.getDescription().equals( "Relational Operator" ) ) {
                    if( skel.endsWith( " " ) ) {
                        skel = skel.substring( 0, skel.length()-1 ) + token.getToken();
                    } else {
                        skel = skel + token.getToken();
                    } //END if-else STATEMENT
                } else if( token.getToken().equals( "." ) ) {
                    if( skel.endsWith( " " ) ) {
                        skel = skel.substring( 0, skel.length()-1 ) + token.getToken();
                    } else {
                        skel = skel + token.getToken();
                    } //END if-else STATEMENT
                } else if( token.getToken().equals( "(" ) || token.getToken().equals( "," ) ) {
                    if( skel.endsWith( " " ) ) {
                        skel = skel.substring( 0, skel.length()-1 ) + token.getToken() + " ";
                    } else {
                        skel = skel + token.getToken() + " ";
                    } //END if-else STATEMENT
                } else if( token.getToken().equals( ")" ) ) {
                    if( skel.endsWith( "( " ) ) {
                        skel = skel.substring( 0, skel.length()-1 ) + token.getToken() + " ";
                    } else {
                        skel = skel + token.getToken() + " ";
                    } //END if-else STATEMENT
                } else if( token.getToken().equals( ";" ) ) {
                    if( skel.endsWith( " " ) ) {
                        skel = skel.substring( 0, skel.length()-1 ) + token.getToken() + "\n";
                    } else {
                        skel = skel + token.getToken() + "\n";
                    } //END if-else STATEMENT
                    skel = this.addIndentation( skel, indentCount );
                } else if( token.getToken().equals( "}" ) ) {
                    indentCount--;
                    skel = this.removeIndentation( skel, 1 );
                    skel = skel + token.getToken() + "\n";
                    skel = this.addIndentation( skel, indentCount );
                } else if( token.getToken().equals( "{" ) ) {
                    skel = skel + token.getToken() + "\n";
                    indentCount++;
                    skel = this.addIndentation( skel, indentCount );
                } else if( token.getDescription().equals( "Data Type" ) ) {
                    skel = skel + token.getToken() + " ";
                } else if( token.getToken().equals( "else" ) ) {
                    skel = this.removeIndentation( skel, indentCount );
                    skel = skel.substring( 0, skel.length()-1 ) + " " + token.getToken() + " ";
                } else {
                    if( mmt>5 && token.getToken().equals( oMethod ) ) {
                        skel = skel + token.getToken();
                        do {
                            token = ( Token )mmTokens.get( ++mmt );
                            skel = skel + token.getToken();
                        } while( !token.getToken().equals( ";" ) );
                        skel = skel + "\t//Mutated with OMD\n";
                        skel = this.addIndentation( skel, indentCount );
                        
                        topComment += mMethod.getMethodName() + "()\n";
                        skel = topComment + skel;
                    } else {
                        skel = skel + token.getToken() + " ";
                    } //END if-else STATEMENT
                } //END if-else STATEMENT
            } //END for LOOP
            if( mm==methodNumber ) {
                skel = skel.substring( 0, skel.length()-3 ) + "*/\n";
            } else {
                skel = skel + "\n";
            }//END if STATEMENT                        
        } //END for LOOP
        skel = skel + "\n}";
        try {
            File file = new File( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "\\mutants\\" + mutationOperator + "\\" + mutantNumber );
            if( !file.exists() ) {
                file.mkdir();
            } //END if STATEMENT
            RandomAccessFile raf = new RandomAccessFile( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "\\mutants\\" + mutationOperator + "\\" + mutantNumber + "\\" + EMConstants.CLASS_1, "rw" );
            raf.writeBytes( skel );
            raf.close();
        } catch( Exception e ) {
            e.printStackTrace();
        } //END try-catch BLOCK 
    } //END generateOMDMutant() METHOD
    
    public void generateIOPMutant( int classNumber, String skel, int mutantNumber, int methodNumber, int tokenNumber, String mutatedToken, String mutationOperator ) {
        ClassComponents cComponents = EMController.create().getC2Components();

        ArrayList mmList = cComponents.getMMList();

        String topComment = "";
        if( methodNumber>=0 ) {
            topComment = "//" + cComponents.getClassName() + ".";
            skel = topComment + skel;
        } //END if STATEMENT

        String oMethod = ( ( MemberMethod )mmList.get( methodNumber ) ).getMethodName();
        
        for( int mm=0; mm<mmList.size(); mm++ ) {
            if( mm==methodNumber ) {
                skel = skel.substring( 0, skel.length()-4 );
                skel = skel + "/* \n";
            } //END if STATEMENT

            MemberMethod mMethod = ( MemberMethod )mmList.get( mm );
            int indentCount=1;
            skel = this.addIndentation( skel, indentCount );
            ArrayList mmTokens = mMethod.getMethodTokens();
            for( int mmt=0; mmt<mmTokens.size(); mmt++ ) {
                Token token = ( Token )mmTokens.get( mmt );
                if( token.getDescription().equals( "Relational Operator" ) ) {
                    if( skel.endsWith( " " ) ) {
                        skel = skel.substring( 0, skel.length()-1 ) + token.getToken();
                    } else {
                        skel = skel + token.getToken();
                    } //END if-else STATEMENT
                } else if( token.getToken().equals( "." ) ) {
                    if( skel.endsWith( " " ) ) {
                        skel = skel.substring( 0, skel.length()-1 ) + token.getToken();
                    } else {
                        skel = skel + token.getToken();
                    } //END if-else STATEMENT
                } else if( token.getToken().equals( "(" ) || token.getToken().equals( "," ) ) {
                    if( skel.endsWith( " " ) ) {
                        skel = skel.substring( 0, skel.length()-1 ) + token.getToken() + " ";
                    } else {
                        skel = skel + token.getToken() + " ";
                    } //END if-else STATEMENT
                } else if( token.getToken().equals( ")" ) ) {
                    if( skel.endsWith( "( " ) ) {
                        skel = skel.substring( 0, skel.length()-1 ) + token.getToken() + " ";
                    } else {
                        skel = skel + token.getToken() + " ";
                    } //END if-else STATEMENT
                } else if( token.getToken().equals( ";" ) ) {
                    if( skel.endsWith( " " ) ) {
                        skel = skel.substring( 0, skel.length()-1 ) + token.getToken() + "\n";
                    } else {
                        skel = skel + token.getToken() + "\n";
                    } //END if-else STATEMENT
                    skel = this.addIndentation( skel, indentCount );
                } else if( token.getToken().equals( "}" ) ) {
                    indentCount--;
                    skel = this.removeIndentation( skel, 1 );
                    skel = skel + token.getToken() + "\n";
                    skel = this.addIndentation( skel, indentCount );
                } else if( token.getToken().equals( "{" ) ) {
                    skel = skel + token.getToken() + "\n";
                    indentCount++;
                    skel = this.addIndentation( skel, indentCount );
                } else if( token.getDescription().equals( "Data Type" ) ) {
                    skel = skel + token.getToken() + " ";
                } else if( token.getToken().equals( "else" ) ) {
                    skel = this.removeIndentation( skel, indentCount );
                    skel = skel.substring( 0, skel.length()-1 ) + " " + token.getToken() + " ";
                } else {
                    if( mmt>5 && token.getToken().equals( oMethod ) ) {
                        skel = skel + token.getToken();
                        do {
                            token = ( Token )mmTokens.get( ++mmt );
                            skel = skel + token.getToken();
                        } while( !token.getToken().equals( ";" ) );
                        skel = skel + "\t//Mutated with IOP\n";
                        skel = this.addIndentation( skel, indentCount );
                        
                        topComment += mMethod.getMethodName() + "()\n";
                        skel = topComment + skel;
                    } else {
                        skel = skel + token.getToken() + " ";
                    } //END if-else STATEMENT
                } //END if-else STATEMENT
            } //END for LOOP
            if( mm==methodNumber ) {
                skel = skel.substring( 0, skel.length()-3 ) + "*/\n";
            } else {
                skel = skel + "\n";
            }//END if STATEMENT                        
        } //END for LOOP
        skel = skel + "\n}";
        try {
            File file = new File( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "\\mutants\\" + mutationOperator + "\\" + mutantNumber );
            if( !file.exists() ) {
                file.mkdir();
            } //END if STATEMENT
            RandomAccessFile raf = new RandomAccessFile( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "\\mutants\\" + mutationOperator + "\\" + mutantNumber + "\\" + EMConstants.CLASS_2, "rw" );
            raf.writeBytes( skel );
            raf.close();
        } catch( Exception e ) {
            e.printStackTrace();
        } //END try-catch BLOCK 
    } //END generateIOPMutant() METHOD
    
    public void generateMutant( int classNumber, String skel, int mutantNumber, int methodNumber, int tokenNumber, String mutatedToken, String mutationOperator ) {
        ClassComponents cComponents = null;
        if( classNumber==1 ) {
            cComponents = EMController.create().getC1Components();
        } else {
            cComponents = EMController.create().getC2Components();
        } //END if-else STATEMENT
        
        ArrayList mmList = cComponents.getMMList();

        String topComment = "";
        if( methodNumber>=0 ) {
            topComment = "//" + cComponents.getClassName() + "." + ( ( MemberMethod )mmList.get( methodNumber ) ).getMethodName() + "()\n";
            skel = topComment + skel;
        } //END if STATEMENT
        
        for( int mm=0; mm<mmList.size(); mm++ ) {
            if( mm==methodNumber ) {
                MemberMethod mMethod = ( MemberMethod )mmList.get( mm );

                int indentCount=1;
                skel = this.addIndentation( skel, indentCount );
                ArrayList mmTokens = mMethod.getMethodTokens();
                for( int mmt=0; mmt<tokenNumber; mmt++ ) {
                    Token token = ( Token )mmTokens.get( mmt );
                    if( token.getDescription().equals( "Relational Operator" ) ) {
                        if( skel.endsWith( " " ) ) {
                            skel = skel.substring( 0, skel.length()-1 ) + token.getToken();
                        } else {
                            skel = skel + token.getToken();
                        } //END if-else STATEMENT
                    } else if( token.getToken().equals( "." ) ) {
                        if( skel.endsWith( " " ) ) {
                            skel = skel.substring( 0, skel.length()-1 ) + token.getToken();
                        } else {
                            skel = skel + token.getToken();
                        } //END if-else STATEMENT
                    } else if( token.getToken().equals( "(" ) || token.getToken().equals( "," ) ) {
                        if( skel.endsWith( " " ) ) {
                            skel = skel.substring( 0, skel.length()-1 ) + token.getToken() + " ";
                        } else {
                            skel = skel + token.getToken() + " ";
                        } //END if-else STATEMENT
                    } else if( token.getToken().equals( ")" ) ) {
                        if( skel.endsWith( "( " ) ) {
                            skel = skel.substring( 0, skel.length()-1 ) + token.getToken() + " ";
                        } else {
                            skel = skel + token.getToken() + " ";
                        } //END if-else STATEMENT
                    } else if( token.getToken().equals( ";" ) ) {
                        if( skel.endsWith( " " ) ) {
                            skel = skel.substring( 0, skel.length()-1 ) + token.getToken() + "\n";
                        } else {
                            skel = skel + token.getToken() + "\n";
                        } //END if-else STATEMENT
                        skel = this.addIndentation( skel, indentCount );
                    } else if( token.getToken().equals( "}" ) ) {
                        indentCount--;
                        skel = this.removeIndentation( skel, 1 );
                        skel = skel + token.getToken() + "\n";
                        skel = this.addIndentation( skel, indentCount );
                    } else if( token.getToken().equals( "{" ) ) {
                        skel = skel + token.getToken() + "\n";
                        indentCount++;
                        skel = this.addIndentation( skel, indentCount );
                    } else if( token.getDescription().equals( "Data Type" ) ) {
                        skel = skel + token.getToken() + " ";
                    } else if( token.getToken().equals( "else" ) ) {
                        skel = this.removeIndentation( skel, indentCount );
                        skel = skel.substring( 0, skel.length()-1 ) + " " + token.getToken() + " ";
                    } else {
                        skel = skel + token.getToken() + " ";
                    } //END if-else STATEMENT
                } //END for LOOP
                
                tokenNumber = tokenNumber + 1;
                if( mutationOperator.equals( "ABS" ) ) {
                    skel = skel + mutatedToken;
                } //END if STATEMENT
                if( mutationOperator.equals( "EOC" ) ) {
                    if( mutatedToken.contains( "==" ) ) {
                        tokenNumber += 6;
                    } else {
                        tokenNumber++;
                        skel = skel.substring( 0, skel.length()-6 );
                    } //END if-else STATEMENT
                    skel = skel.substring( 0, skel.length()-1 );
                } //END if STATEMENT
                do {
                    if( !mutationOperator.equals( "ABS" ) ) {
                        if( mutatedToken.equals( "." ) ) {
                            if( skel.endsWith( " " ) ) {
                                skel = skel.substring( 0, skel.length()-1 ) + mutatedToken;
                            } else {
                                skel = skel + mutatedToken;
                            } //END if-else STATEMENT
                        } else if( mutatedToken.equals( "(" ) || mutatedToken.equals( "," ) ) {
                            if( skel.endsWith( " " ) ) {
                                skel = skel.substring( 0, skel.length()-1 ) + mutatedToken + " ";
                            } else {
                                skel = skel + mutatedToken + " ";
                            } //END if-else STATEMENT
                        } else if( mutatedToken.equals( ")" ) ) {
                            if( skel.endsWith( "( " ) ) {
                                skel = skel.substring( 0, skel.length()-1 ) + mutatedToken + " ";
                            } else {
                                skel = skel + mutatedToken + " ";
                            } //END if-else STATEMENT
                        } else if( mutatedToken.equals( ";" ) ) {
                            if( skel.endsWith( " " ) ) {
                                skel = skel.substring( 0, skel.length()-1 ) + mutatedToken + "\n";
                            } else {
                                skel = skel + mutatedToken + "\n";
                            } //END if-else STATEMENT
                        } else if( mutatedToken.equals( "}" ) || mutatedToken.equals( "{" ) ) {
                            skel = skel + mutatedToken + "\n";
                        } else if( mutatedToken.equals( "<" ) || mutatedToken.equals( ">" ) || mutatedToken.equals( ">=" ) || mutatedToken.equals( ">=" ) || mutatedToken.equals( "==" ) || mutatedToken.equals( "!=" ) ) {
                            if( skel.endsWith( " " ) ) {
                                skel = skel.substring( 0, skel.length()-1 ) + mutatedToken;
                            } else {
                                skel = skel + mutatedToken;
                            } //END if-else STATEMENT
                        } else {
                            skel = skel + mutatedToken + " ";
                        } //END if-else STATEMENT
                    } //END if-else STATEMENT
                    mutatedToken = ( ( Token )mmTokens.get( tokenNumber ) ).getToken();
                    tokenNumber++;
                } while( !( mutatedToken.equals( ";" ) || mutatedToken.equals( "{" ) ) );
                if( mutatedToken.equals( ";" ) ) {
                    if( skel.endsWith( " " ) ) {
                        skel = skel.substring( 0, skel.length()-1 ) + mutatedToken + "\t//Mutated with " + mutationOperator + "\n";
                    } else {
                        skel = skel + mutatedToken + "\t//Mutated with " + mutationOperator + "\n";
                    } //END if-else STATEMENT
                    skel = this.addIndentation( skel, indentCount );
                } else if( mutatedToken.equals( "{" ) ) {
                    skel = skel + mutatedToken + "\t//Mutated with " + mutationOperator + "\n";
                    indentCount++;
                    skel = this.addIndentation( skel, indentCount );
                } //END if-else STATEMENT

                for( int mmt=tokenNumber; mmt<mmTokens.size(); mmt++ ) {
                    Token token = ( Token )mmTokens.get( mmt );
                    if( token.getDescription().equals( "Relational Operator" ) ) {
                        if( skel.endsWith( " " ) ) {
                            skel = skel.substring( 0, skel.length()-1 ) + token.getToken();
                        } else {
                            skel = skel + token.getToken();
                        } //END if-else STATEMENT
                    } else if( token.getToken().equals( "." ) ) {
                        if( skel.endsWith( " " ) ) {
                            skel = skel.substring( 0, skel.length()-1 ) + token.getToken();
                        } else {
                            skel = skel + token.getToken();
                        } //END if-else STATEMENT
                    } else if( token.getToken().equals( "(" ) || token.getToken().equals( "," ) ) {
                        if( skel.endsWith( " " ) ) {
                            skel = skel.substring( 0, skel.length()-1 ) + token.getToken() + " ";
                        } else {
                            skel = skel + token.getToken() + " ";
                        } //END if-else STATEMENT
                    } else if( token.getToken().equals( ")" ) ) {
                        if( skel.endsWith( "( " ) ) {
                            skel = skel.substring( 0, skel.length()-1 ) + token.getToken() + " ";
                        } else {
                            skel = skel + token.getToken() + " ";
                        } //END if-else STATEMENT
                    } else if( token.getToken().equals( ";" ) ) {
                        if( skel.endsWith( " " ) ) {
                            skel = skel.substring( 0, skel.length()-1 ) + token.getToken() + "\n";
                        } else {
                            skel = skel + token.getToken() + "\n";
                        } //END if-else STATEMENT
                        skel = this.addIndentation( skel, indentCount );
                    } else if( token.getToken().equals( "}" ) ) {
                        indentCount--;
                        skel = this.removeIndentation( skel, 1 );
                        skel = skel + token.getToken() + "\n";
                        skel = this.addIndentation( skel, indentCount );
                    } else if( token.getToken().equals( "{" ) ) {
                        skel = skel + token.getToken() + "\n";
                        indentCount++;
                        skel = this.addIndentation( skel, indentCount );
                    } else if( token.getDescription().equals( "Data Type" ) ) {
                        skel = skel + token.getToken() + " ";
                    } else if( token.getToken().equals( "else" ) ) {
                        skel = this.removeIndentation( skel, indentCount );
                        skel = skel.substring( 0, skel.length()-1 ) + " " + token.getToken() + " ";
                    } else {
                        skel = skel + token.getToken() + " ";
                    } //END if-else STATEMENT
                } //END for LOOP
                skel = skel + "\n";
            } else {
                MemberMethod mMethod = ( MemberMethod )mmList.get( mm );

                int indentCount=1;
                skel = this.addIndentation( skel, indentCount );
                ArrayList mmTokens = mMethod.getMethodTokens();
                for( int mmt=0; mmt<mmTokens.size(); mmt++ ) {
                    Token token = ( Token )mmTokens.get( mmt );
                    if( token.getDescription().equals( "Relational Operator" ) ) {
                        if( skel.endsWith( " " ) ) {
                            skel = skel.substring( 0, skel.length()-1 ) + token.getToken();
                        } else {
                            skel = skel + token.getToken();
                        } //END if-else STATEMENT
                    } else if( token.getToken().equals( "." ) ) {
                        if( skel.endsWith( " " ) ) {
                            skel = skel.substring( 0, skel.length()-1 ) + token.getToken();
                        } else {
                            skel = skel + token.getToken();
                        } //END if-else STATEMENT
                    } else if( token.getToken().equals( "(" ) || token.getToken().equals( "," ) ) {
                        if( skel.endsWith( " " ) ) {
                            skel = skel.substring( 0, skel.length()-1 ) + token.getToken() + " ";
                        } else {
                            skel = skel + token.getToken() + " ";
                        } //END if-else STATEMENT
                    } else if( token.getToken().equals( ")" ) ) {
                        if( skel.endsWith( "( " ) ) {
                            skel = skel.substring( 0, skel.length()-1 ) + token.getToken() + " ";
                        } else {
                            skel = skel + token.getToken() + " ";
                        } //END if-else STATEMENT
                    } else if( token.getToken().equals( ";" ) ) {
                        if( skel.endsWith( " " ) ) {
                            skel = skel.substring( 0, skel.length()-1 ) + token.getToken() + "\n";
                        } else {
                            skel = skel + token.getToken() + "\n";
                        } //END if-else STATEMENT
                        skel = this.addIndentation( skel, indentCount );
                    } else if( token.getToken().equals( "}" ) ) {
                        indentCount--;
                        skel = this.removeIndentation( skel, 1 );
                        skel = skel + token.getToken() + "\n";
                        skel = this.addIndentation( skel, indentCount );
                    } else if( token.getToken().equals( "{" ) ) {
                        skel = skel + token.getToken() + "\n";
                        indentCount++;
                        skel = this.addIndentation( skel, indentCount );
                    } else if( token.getDescription().equals( "Data Type" ) ) {
                        skel = skel + token.getToken() + " ";
                    } else if( token.getToken().equals( "else" ) ) {
                        skel = this.removeIndentation( skel, indentCount );
                        skel = skel.substring( 0, skel.length()-1 ) + " " + token.getToken() + " ";
                    } else {
                        skel = skel + token.getToken() + " ";
                    } //END if-else STATEMENT
                } //END for LOOP
                skel = skel + "\n";
            } //END if-else STATEMENT
        } //END for LOOP
        skel = skel + "\n}";
        try {
            File file = new File( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "/mutants/" + mutationOperator + "/" + mutantNumber );
            if( !file.exists() ) {
                file.mkdir();
            } //END if STATEMENT
            RandomAccessFile raf = null;
            if( classNumber==1 ) {
                raf = new RandomAccessFile( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "/mutants/" + mutationOperator + "/" + mutantNumber + "/" + EMConstants.CLASS_1, "rw" );
            } else {
                raf = new RandomAccessFile( EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "/mutants/" + mutationOperator + "/" + mutantNumber + "/" + EMConstants.CLASS_2, "rw" );
            } //END if-else STATEMENT
            raf.writeBytes( skel );
            raf.close();
        } catch( Exception e ) {
            e.printStackTrace();
        } //END try-catch BLOCK
    } //END generateMutant() METHOD
    
    public String addIndentation( String skel, int indentCount ) {
        int i=1;
        while( i<=indentCount ) {
            int a=1;
            while( a<=3 ) {
                skel = skel + " ";
                a++;
            } //END while() LOOP
            i++;
        } //END while LOOP
        return skel;
    } //END addIndentation() METHOD

    public String removeIndentation( String skel, int indentCount ) {
        int i=1;
        while( i<=indentCount ) {
            skel = skel.substring( 0, skel.length()-3 );
            i++;
        } //END while LOOP
        return skel;
    } //END removeIndentation() METHOD   
    
    public ArrayList getRORToken( String token ) {
        String[] rorOperators = { "<", "<=", ">", ">=", "==", "!=" };
        ArrayList rorList = new ArrayList();
        rorList.add( rorOperators[ 0 ] );
        rorList.add( rorOperators[ 1 ] );
        rorList.add( rorOperators[ 2 ] );
        rorList.add( rorOperators[ 3 ] );
        rorList.add( rorOperators[ 4 ] );
        rorList.add( rorOperators[ 5 ] );
        if( token.equals( "<" ) || token.equals( "<=" ) ) {
            rorList.remove( "<" );
            rorList.remove( "<=" );
        } else if( token.equals( ">" ) || token.equals( ">=" ) ) {
            rorList.remove( ">" );
            rorList.remove( ">=" );            
        } else if( token.equals( "==" ) ) {
            rorList.remove( "==" );
        } else if( token.equals( "!=" ) ) {
            rorList.remove( "!=" );
        } //END if-else STATMENTS
        return rorList;
    } //END getRORToken()
   
    public ArrayList getAORToken( String token ) {
        String[] rorOperators = { "+", "-", "/", "*", "%" };
        ArrayList rorList = new ArrayList();
        rorList.add( rorOperators[ 0 ] );
        rorList.add( rorOperators[ 1 ] );
        rorList.add( rorOperators[ 2 ] );
        rorList.add( rorOperators[ 3 ] );
        rorList.add( rorOperators[ 4 ] );
        if( token.equals( "+" ) ) {
            rorList.remove( "+" );
        } else if( token.equals( "-" ) ) {
            rorList.remove( "-" );
        } else if( token.equals( "*" ) ) {
            rorList.remove( "*" );
        } else if( token.equals( "/" ) ) {
            rorList.remove( "/" );
        } else if( token.equals( "%" ) ) {
            rorList.remove( "%" );
        } //END if-else STATMENTS
        return rorList;
    } //END getAORToken()

    public String getLCRToken( String token ) {
        if( token.equals( "&&" ) ) {
            return "||";
        } else {
            return "&&";
        } //END if-else STATEMENT 
    } //END getLCRToken() METHOD

    public String[] getUOIToken() {
        String[] tokens = { "++", "--" };
        return tokens;
    } //END getUOIToken() METHOD
    
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
                            System.err.print( "Line: " + line );
                            mutatedMethod = findMutatedMethod( line );
                            System.err.print( "Method: " + mutatedMethod );
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
                                                instrumentedString += "raf.writeBytes( \"R: 0.0 0 0.0\" );\n";
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
                                                instrumentedString += "raf.writeBytes( \"R: 0.0 0 0.0\" );\n";
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
                                                            instrumentedString += "java.io.File myFile = new java.io.File( traceFile);\n";
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
                                                        instrumentedString += "float state_fitness = 0.0f;\n";
                                                        instrumentedString += "float local_fitness = 0.0f;\n";
                                                        instrumentedString += "float fitness = 0.0f;\n";
                                                        instrumentedString += "int state_count = 0;\n";
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
                                                            instrumentedString += "state_fitness = ( float)( stringWeight1 - stringWeight2 ) / ( stringWeight1 + stringWeight2 );\n";
                                                            instrumentedString += "state_fitness = Math.abs( state_fitness );\n";
                                                        } else {
                                                            ArrayList predicateList = getPredicateList( condition );
                                                            int stateCounter = 0;
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
                                                                if( isStateVariable( operand1, cComponents ) ) {
                                                                    instrumentedString += "state_fitness += fitness;\n";
                                                                    instrumentedString += "state_count++;\n";
                                                                    stateCounter++;
                                                                } else {
                                                                    instrumentedString += "local_fitness += fitness;\n";
                                                                    instrumentedString += "local_count++;\n";
                                                                    localCounter++;
                                                                } //END if-else STATEMENT
                                                                instrumentedString += "}\n";
                                                            } //END for LOOP
                                                            if( stateCounter>1 ) {
                                                                 instrumentedString += "if( state_count>1 ) {\n";
                                                                 instrumentedString += "state_fitness *= 0.5f;\n";
                                                                 instrumentedString += "}\n";
                                                            } //END if STATEMENT
                                                            if( localCounter>1 ) {
                                                                instrumentedString += "if( local_count>1 ) {\n";
                                                                instrumentedString += "local_fitness *= 0.5f;\n";
                                                                instrumentedString += "}\n";
                                                            } //END if STATEMENT
                                                        } //END if-else STATEMENT
                                                        
                                                        instrumentedString += "raf.writeBytes( \"R: \" + state_fitness + \" " + approach_level + " \" + local_fitness );\n";
                                                        instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                        if( ifWhileMutation ) {
                                                            instrumentedString += "raf.writeBytes( \"N: false\" );\n";
                                                            instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                            oinstrumentedString += "raf.writeBytes( \"N: false\" );\n";
                                                            oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                        } else {
                                                            instrumentedString += "raf.writeBytes( \"N: c\" );\n";
                                                            instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                            instrumentedString += "raf.writeBytes( \"S: c Normal\" );\n";
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
                                                        System.out.println( "CONDITION: " + condition );
                                                        System.out.println( "PREDICATE: " + condition1 );
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
                                                        instrumentedString += "float state_fitness = 0.0f;\n";
                                                        instrumentedString += "float local_fitness = 0.0f;\n";
                                                        instrumentedString += "float fitness = 0.0f;\n";
                                                        instrumentedString += "int state_count = 0;\n";
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
                                                            instrumentedString += "state_fitness = ( float)( stringWeight1 - stringWeight2 ) / ( stringWeight1 + stringWeight2 );\n";
                                                            instrumentedString += "state_fitness = Math.abs( state_fitness );\n";
                                                        } else {
                                                            ArrayList predicateList = getPredicateList( condition );
                                                            int stateCounter = 0;
                                                            int localCounter = 0;
                                                            for( int p=0; p<predicateList.size(); p++ ) {
                                                                String predicate = ( String )predicateList.get( p );
                                                                System.out.println( "PREDICATE: " + predicate );
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
                                                                if( isStateVariable( operand1, cComponents ) ) {
                                                                    instrumentedString += "state_fitness += fitness;\n";
                                                                    instrumentedString += "state_count++;\n";
                                                                    stateCounter++;
                                                                } else {
                                                                    instrumentedString += "local_fitness += fitness;\n";
                                                                    instrumentedString += "local_count++;\n"; 
                                                                    localCounter++;
                                                                } //END if-else STATEMENT
                                                                instrumentedString += "}\n";
                                                            } //END for LOOP
                                                            if( stateCounter>1 ) {
                                                                 instrumentedString += "if( state_count>1 ) {\n";
                                                                 instrumentedString += "state_fitness *= 0.5f;\n";
                                                                 instrumentedString += "}\n";
                                                            } //END if STATEMENT
                                                            if( localCounter>1 ) {
                                                                instrumentedString += "if( local_count>1 ) {\n";
                                                                instrumentedString += "local_fitness *= 0.5f;\n";
                                                                instrumentedString += "}\n";
                                                            } //END if STATEMENT
                                                        } //END if-else STATEMENT
                                                        
                                                        instrumentedString += "raf.writeBytes( \"R: \" + state_fitness + \" " + approach_level + " \" + local_fitness );\n";
                                                        instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                        if( ifWhileMutation ) {
                                                            instrumentedString += "raf.writeBytes( \"N: false\" );\n";
                                                            instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                            oinstrumentedString += "raf.writeBytes( \"N: false\" );\n";
                                                            oinstrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                        } else {
                                                            instrumentedString += "raf.writeBytes( \"N: c\" );\n";
                                                            instrumentedString += "raf.writeBytes( \"\\n\" );\n";
                                                            instrumentedString += "raf.writeBytes( \"S: c Normal\" );\n";
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
    
    public boolean isStateVariable( String operand1, ClassComponents cComponents ) {
        ArrayList dmList = cComponents.getDMList();
        for( int d=0; d<dmList.size(); d++ ) {
            Token token = ( Token )dmList.get( d );
            if( token.getToken().equals( operand1 ) ) {
                return true;
            } //END if STATEMENT
        } //END for LOOP
        
        return false;
    } //END isStateVariable(( METHOD
    
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

} //END SourceCode CLASS
