package com.goks.aws;

import com.goks.aws.S3.S3Read;
import com.goks.aws.ec2.GetEC2;

/**
 * @author Gokul
 *
 */
public class App {    
    /**
     * Main method for Entry
     * @param args Runtime Args to be passed
     */
    public static void main(final String[] args ) {
        //S3Read.getS3Info();
        GetEC2.getAllEC2();
    }
    
}