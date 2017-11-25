/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emujava;

import java.util.*;

/**
 *
 * @author jBillu
 * 
 * @version 1.0 August 23, 2013
 * 
 */

public class ClassConstructor {

    private ArrayList constructorTokens;
    private ArrayList statementsList;
    
    public ClassConstructor() {
        constructorTokens = new ArrayList();
        statementsList = new ArrayList();
    } //END ClassConstructor() CONSTRUCTOR

    public String getConstructorHeader() {
        String header = "";
        int ct=0;
        DO1: do {
            Token token = ( Token )constructorTokens.get( ct++ );
            if( token.getToken().equals( "[" ) || token.getToken().equals( "]" ) || token.getToken().equals( "(" ) || token.getToken().equals( ")" ) || token.getToken().equals( "." ) ) {
                header = header + token.getToken();
            } else {
                header = header + " " + token.getToken();
            } //END if-else STATEMENT
            if( token.getToken().equals( "{" ) ) {
                break DO1;
            } //END if STATEMENT
        } while( ct<constructorTokens.size() );
        return header;
    } //END getConstructorHeader() METHOD

    public void addToken( Token token ) {
        constructorTokens.add( token );
    } //END addToken() METHOD

    public ArrayList getConstructorTokens() {
        return constructorTokens;
    } //END getConstructorTokens() METHOD

    public ArrayList getStatementsList() {
        return statementsList;
    } //END getStatementsList() METHOD

} //END ClassConstructor CLASS
