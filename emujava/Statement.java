/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emujava;

import emujava.*;
import java.util.*;

/**
 *
 * @author jBillu
 * 
 * @version 1.0 August 23, 2013
 */
public class Statement {
    
    private ArrayList statementTokens = new ArrayList();
    private String description = "";
    
    public void addToken( Token token ) {
        statementTokens.add( token );
    } //END addToken() METHOD
    
    public ArrayList getStatementTokens() {
        return statementTokens;
    } //END getStatementTokens() METHOD

    public void setDescription( String desc ) {
        description = desc;
    } //END setDescription() METHOD
    
    public String getDescription() {
        return description;
    } //END getDescription() METHOD
    
}
