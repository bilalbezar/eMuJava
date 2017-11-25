/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emujava;

/**
 *
 * @author jBillu
 * 
 * @version 1.0 August 19th, 2013
 * 
 */
public class Token {

    private String token;
    private String description;
    private int lineNumber;

    public Token() {
        token = "";
        description = "";
        lineNumber = 0;
    } //END Token() CNSTURCTOR

    public void setToken( String _token ) {
        token = _token;
    } //END setToken() METHOD

    public void setDescription( String _description ) {
        description = _description;
    } //END setDescription() METHOD

    public String getToken() {
        return token;
    } //END getToken() METHOD

    public String getDescription() {
        return description;
    } //END getDescription() METHOD

    public void setLineNumber( int _lineNumber ) {
        lineNumber = _lineNumber;
    } //END setLineNumber() METHOD

    public int getLineNumber() {
        return lineNumber;
    } //END getLineNumber() METHOD

    public String toString() {
        return token + "\t" + description + "\n";
    } //END toString() METHOD
}
