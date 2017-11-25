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

public class ClassHeader {

    private int index;

    public ClassHeader() {
        index = 0;
    } //END ClassHeader() CONSTRUCTOR
   
    public ArrayList extractImports( ArrayList tokenList ) {
        ArrayList imports = new ArrayList();
        for( ; index<tokenList.size(); index++ ) {
            Token token = ( Token )tokenList.get( index );
            if( token.getToken().equals( "import" ) ) {
                imports.add( token );
                do {
                    token = ( Token )tokenList.get( ++index );
                    imports.add( token );
                } while( !token.getToken().equals( ";" ) );
            } else {
                return imports;
            } //END if STATEMENT
        } //END for LOOP
        return imports;
    } //END extractImports() METHOD

    public ArrayList extractHeader( ArrayList tokenList ) {
        ArrayList headerList = new ArrayList();
        for( ; index<tokenList.size(); index++ ) {
            Token token = ( Token )tokenList.get( index );
            if( !token.getToken().equals( "{" ) ) {
                headerList.add( token );
            } else {
                return headerList;
            } //END if STATEMENT
        } //END for LOOP
        return headerList;
    } //END extractImports() METHOD

    public String getClassName( ArrayList headerList ) {
        String className = "";
        for( int h=0; h<headerList.size(); h++ ) {
            Token token = ( Token )headerList.get( h );
            if( token.getDescription().equals( "Identifier" ) ) {
                className = token.getToken();
                return className;
            } //END if STATEMENT
        } //END for LOOP
        return className;
    } //END extractImports() METHOD
    
    public String getClassParent( ArrayList headerList ) {
        String classParent = "";
        for( int h=0; h<headerList.size(); h++ ) {
            Token token = ( Token )headerList.get( h );
            if( token.getToken().equals( "extends" ) ) {
                classParent = ( ( Token )headerList.get( h+1 ) ).getToken();
                return classParent;
            } //END if STATEMENT
        } //END for LOOP
        return classParent;
    } //END getClassParent() METHOD

    public int getIndex() {
        return index;
    } //END getIndex() METHOD

} //END ClassHeader CLASS
