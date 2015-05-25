package es.bsc.autonomic.powermodeller;

import es.bsc.autonomic.powermodeller.configuration.CoreConfiguration;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;

import java.io.File;
import java.util.Set;

/**
 * Created by jsubirat on 21/11/14.
 */
public class TestCleanUp extends RunListener {

    /**
     * Source: http://www.rgagnon.com/javadetails/java-0483.html
     * @param path Directory to be deleted. It also deletes non-empty directories.
     * @return True if deletion is successful, false otherwise.
     */
    private static boolean deleteDirectory(File path) {
        if( path.exists() ) {
            File[] files = path.listFiles();
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                }
                else {
                    files[i].delete();
                }
            }
        }
        return( path.delete() );
    }


    @Override
    public void testRunFinished(Result result) throws Exception {
        System.out.println("*********************CIAOOOOOOOOOOOOOOO***********************");
        deleteDirectory(new File(CoreConfiguration.TEMPDIR));
    }

    /*
    @Test
    @Ignore
    public void cleanUp(){
        deleteDirectory(new File(CoreConfiguration.TEMPDIR));
    }*/

}
