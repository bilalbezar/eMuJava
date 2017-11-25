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

public class ClassComponents {

    private ArrayList importsList;
    private ArrayList classHeader;
    private String className;
    private String classParent;
    private ArrayList dmList;
    private ArrayList mmList;
    private ArrayList ccList;

    public ClassComponents() {
        dmList = new ArrayList();
        mmList = new ArrayList();
        ccList = new ArrayList();
    } //END ClassComponents() CONSTRUCTOR

    public void setImportsList( ArrayList iList ) {
        importsList = iList;
    } //END setImportsList() METHOD
	
    public ArrayList getImportsList() {
        return importsList;
    } //END getImportsList() METHOD

    public void setClassHeader( ArrayList cHeader ) {
        classHeader = cHeader;
    } //END setClassHeader() METHOD

    public ArrayList getClassHeader() {
        return classHeader;
    } //END getClassHeader() METHOD

    public void setClassName( String cName ) {
        className = cName;
    } //END setClassName() METHOD

    public String getClassName() {
        return className;
    } //END getClassName() METHOD

    public void setClassParent( String pName ) {
        classParent = pName;
    } //END setClassParent() METHOD

    public String getClassParent() {
        return classParent;
    } //END getClassParent() METHOD

    public void extractClassComponents( ArrayList tokenList, int tokenNum ) {

        for( ; tokenNum<tokenList.size(); tokenNum++ ) {
            ArrayList tempList = new ArrayList();
            Token token = ( Token )tokenList.get( tokenNum );
            
            if( token.getToken().equals( "public" ) || token.getToken().equals( "private" ) || token.getToken().equals( "protected" ) || token.getToken().equals( "static" ) || token.getToken().equals( "final" ) ) {
                tempList.add( token );
                token = ( Token )tokenList.get( ++tokenNum );
            } //END if STATEMENT

            if( token.getToken().equals( "public" ) || token.getToken().equals( "private" ) || token.getToken().equals( "protected" ) || token.getToken().equals( "static" ) || token.getToken().equals( "final" ) ) {
                tempList.add( token );
                token = ( Token )tokenList.get( ++tokenNum );
            } //END if STATEMENT

            if( token.getToken().equals( "static" ) || token.getToken().equals( "public" ) || token.getToken().equals( "private" ) || token.getToken().equals( "protected" ) || token.getToken().equals( "final" ) ) {
                tempList.add( token );
                token = ( Token )tokenList.get( ++tokenNum );
            } //END if STATEMENT

            if( token.getToken().equals( "abstract" ) ) {
                do {
                    token = ( Token )tokenList.get( ++tokenNum );
                } while( token.getToken().equals( ";" ) );
                token = null;
                continue;
            } //END if STATEMENT

            if( token!=null && token.getToken().equals( "void" ) ) {
                tempList.add( token );
                MemberMethod mMethod = new MemberMethod();
                for( int f1=0; f1<tempList.size(); f1++ ) {
                    mMethod.addToken( ( Token )tempList.get( f1 ) );
                } //END for LOOP
                ArrayList mList = new ArrayList();
                DOWHILE: do {
                    token = ( Token )tokenList.get( ++tokenNum );
                    mMethod.addToken( token );
                    if( token.getToken().equals( "{" ) ) {
                        mList.add( token );
                    } else if( token.getToken().equals( "}" ) ) {
                        mList.remove( mList.size()-1 );
                        if( mList.size()==0 ) {
                            break DOWHILE;
                        } //END if STATEMENT
                    } //END if STATEMENT
                } while( tokenNum<tokenList.size() );
                mmList.add( mMethod );
                tempList.clear();
                token = null;
                continue;
            } //END if STATEMENT

            if( token!=null && token.getDescription().equals( "Data Type" ) ) {
                tempList.add( token );
                token = ( Token )tokenList.get( ++tokenNum );
                if( token.getDescription().equals( "Identifier" ) ) {
                    tempList.add( token );
                    token = ( Token )tokenList.get( ++tokenNum );
                    if( token.getToken().equals( "(" ) ) {
                        tempList.add( token );
                        MemberMethod mMethod = new MemberMethod();
                        for( int f1=0; f1<tempList.size(); f1++ ) {
                            mMethod.addToken( ( Token )tempList.get( f1 ) );
                        } //END for LOOP
                        tempList.clear();
                        ArrayList mList = new ArrayList();
                        DOWHILE: do {
                            token = ( Token )tokenList.get( ++tokenNum );
                            mMethod.addToken( token );
                            if( token.getToken().equals( "{" ) ) {
                                mList.add( token );
                            } else if( token.getToken().equals( "}" ) ) {
                                mList.remove( mList.size()-1 );
                                if( mList.size()==0 ) {
                                    break DOWHILE;
                                } //END if STATEMENT
                            } //END if STATEMENT
                        } while( tokenNum<tokenList.size() );
                        mmList.add( mMethod );
                        token = null;
                    } else {
                        tempList.add( token );
                        for( int f1=0; f1<tempList.size(); f1++ ) {
                            dmList.add( tempList.get( f1 ) );
                        } //END for LOOP
                        tempList.clear();
                        if( !token.getToken().equals( ";" ) ) {
                            do {
                                token = ( Token )tokenList.get( ++tokenNum );
                                dmList.add( token );
                            } while( !token.getToken().equals( ";" ) );
                        } //END if STATEMENT
                    } //END if-else STATEMENT
                } else {
                    tempList.add( token );
                    for( int f1=0; f1<tempList.size(); f1++ ) {
                        dmList.add( tempList.get( f1 ) );
                    } //END for LOOP
                    tempList.clear();
                    do {
                        token = ( Token )tokenList.get( ++tokenNum );
                        dmList.add( token );
                    } while( !token.getToken().equals( ";" ) );
                } //END if-else STATEMENT
            } //END if STATEMENT

            if( token!=null && token.getDescription().equals( "Identifier" ) ) {
                tempList.add( token );
                token = ( Token )tokenList.get( ++tokenNum );
                if( token.getToken().equals( "(" ) ) {
                    tempList.add( token );
                    ClassConstructor cConstructor = new ClassConstructor();
                    for( int f1=0; f1<tempList.size(); f1++ ) {
                        cConstructor.addToken( ( Token )tempList.get( f1 ) );
                    } //END for LOOP
                    tempList.clear();
                    ArrayList mList = new ArrayList();
                    DOWHILE: do {
                        token = ( Token )tokenList.get( ++tokenNum );
                        cConstructor.addToken( token );
                        if( token.getToken().equals( "{" ) ) {
                            mList.add( token );
                        } else if( token.getToken().equals( "}" ) ) {
                            mList.remove( mList.size()-1 );
                            if( mList.size()==0 ) {
                                break DOWHILE;
                            } //END if STATEMENT
                        } //END if STATEMENT
                    } while( tokenNum<tokenList.size() );
                    ccList.add( cConstructor );
                    token = null;
                } else if( token.getDescription().equals( "Identifier" ) ) {
                    tempList.add( token );
                    token = ( Token )tokenList.get( ++tokenNum );
                    if( token.getToken().equals( "(" ) ) {
                        tempList.add( token );
                        MemberMethod mMethod = new MemberMethod();
                        for( int f1=0; f1<tempList.size(); f1++ ) {
                            mMethod.addToken( ( Token )tempList.get( f1 ) );
                        } //END for LOOP
                        tempList.clear();
                        ArrayList mList = new ArrayList();
                        DOWHILE: do {
                            token = ( Token )tokenList.get( ++tokenNum );
                            mMethod.addToken( token );
                            if( token.getToken().equals( "{" ) ) {
                                mList.add( token );
                            } else if( token.getToken().equals( "}" ) ) {
                                mList.remove( mList.size()-1 );
                                if( mList.size()==0 ) {
                                    break DOWHILE;
                                } //END if STATEMENT
                            } //END if STATEMENT
                        } while( tokenNum<tokenList.size() );
                        mmList.add( mMethod );
                        token = null;
                    } else if( token.getToken().equals( ";" ) ) {
                        tempList.add( token );
                        for( int f1=0; f1<tempList.size(); f1++ ) {
                            dmList.add( tempList.get( f1 ) );
                        } //END for LOOP
                        tempList.clear();
                    } else {
                        tempList.add( token );
                        for( int f1=0; f1<tempList.size(); f1++ ) {
                            dmList.add( tempList.get( f1 ) );
                        } //END for LOOP
                        tempList.clear();
                        do {
                            token = ( Token )tokenList.get( ++tokenNum );
                            dmList.add( token );
                        } while( !token.getToken().equals( ";" ) );
                    } //END if-else STATEMENT
                } else if( token.getToken().equals( ";" ) ) {
                    tempList.add( token );
                    for( int f1=0; f1<tempList.size(); f1++ ) {
                        dmList.add( tempList.get( f1 ) );
                    } //END for LOOP
                    tempList.clear();
                } //END if-else STATEMENT
            } //END if STATEMENT
        } //END for LOOP

    } //END extractClassComponents() METHOD

    public ArrayList getDMList() {
        return dmList;
    } //EDN getDMList() METHOD

    public ArrayList getMMList() {
        return mmList;
    } //END getMemberMethodList() METHOD

    public ArrayList getCCList() {
        return ccList;
    } //END getClassConstructorList() METHOD
    
}
