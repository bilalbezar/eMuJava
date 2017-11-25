/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emujava;

/**
 *
 * @author jBillu
 * 
 * @version 1.0 August 20, 2015
 * 
 */
public class GAManager extends Thread {
    
    public static int T=0;
    public static int RUNNING_THREADS = 0;
    
    public void run() {
        EMConstants.TIME_START = System.currentTimeMillis();
        int threadNumber = 1;
        EMConstants.TOTAL_ITERATIONS = EMConstants.MAX_ITERATIONS * EMConstants.TARGETS.size();
        EMConstants.PROGRESS_RATE = 45 / ( double )EMConstants.TOTAL_ITERATIONS;
        for( ; T<EMConstants.TARGETS.size() ; ) {
            while( GAManager.RUNNING_THREADS<EMConstants.GA_MAX_THREADS && T<EMConstants.TARGETS.size() ) {
                EMController.create().getProjectManager().updateStatisticsAndResults();
                Target target = ( Target )EMConstants.TARGETS.get( T );
                if( EMConstants.GEN_TYPE.contains( "eMuJava" ) || EMConstants.GEN_TYPE.contains( "Random" ) ) {
                    GeneticAlgorithm ga = new GeneticAlgorithm();
                    ga.setGATarget( target );
                    ga.setThreadNumber( threadNumber++ );
                    ga.start();
                } else {
                    ClassicGeneticAlgorithm cga = new ClassicGeneticAlgorithm();
                    cga.setGATarget( target );
                    cga.setThreadNumber( threadNumber++ );
                    cga.start();                    
                } //END if-else STATEMENT
                GAManager.RUNNING_THREADS++;
            } //END while LOOP
        } //END for LOOP
        
        while( RUNNING_THREADS>0 ) {
        } //END while LOOP

        ProcessProgress.getProcessProgress().statusLabel.setText( "Test case generation process completed..." );
        EMConstants.TIME_END = System.currentTimeMillis();
        ProcessProgress.getProcessProgress().ppBar.setValue( 95 );
        try {
            Thread.sleep( 500 );            
        } catch ( Exception e ) {
            e.printStackTrace();
        } //END try-catch BLOCK
        ProcessProgress.getProcessProgress().titleLabel.setText( "Updating GUI to display mutants" );
        EMController.create().getProjectManager().displayMutants();
        EMController.create().getProjectManager().updateStatisticsAndResults();
        ProcessProgress.getProcessProgress().statusLabel.setText( "EMuJava GUI updated..." );
        ProcessProgress.getProcessProgress().ppBar.setValue( 100 );        
        try {
            Thread.sleep( 500 );
        } catch( Exception e ) {
            e.printStackTrace();
        } //END try-catch BLOCK
        ProcessProgress.getProcessProgress().setVisible( false );
    } //END run() METHOD
}
