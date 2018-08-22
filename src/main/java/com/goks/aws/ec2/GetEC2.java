package com.goks.aws.ec2;

import java.util.HashSet;
import java.util.Set;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.Tag;

public class GetEC2 {

    public static void getAllEC2() {
	for (Regions region : Regions.values()) {
	    if(!region.getName().contains("gov") && !region.getName().contains("cn-") ) {
		getEC2ForRegion(region.getName());
	    }
	}
    }
    
    public static void getEC2ForRegion(String region) {
	final AmazonEC2 ec2 = AmazonEC2ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new ProfileCredentialsProvider())
                .build();
	boolean done = false;
	Set<String> environments = new HashSet<String>();
	DescribeInstancesRequest request = new DescribeInstancesRequest();
	while(!done) {
	    DescribeInstancesResult response = ec2.describeInstances(request);
	    
	    System.out.println(region+": "+response.getReservations().size());
	    for(Reservation reservation : response.getReservations()) {
	        for(Instance instance : reservation.getInstances()) {
	            //System.out.println("instance id: "+instance.getInstanceId());
	            for(Tag tag: instance.getTags()) {
	        	if(tag.getKey().contains("environment")) {
	        	    environments.add(tag.getValue());
	        	}
	        	if(tag.getKey().contains("Name")) {
	        	    //System.out.println(tag.getValue());
	        	}
	            }
	        }
	    }

	    request.setNextToken(response.getNextToken());

	    if(response.getNextToken() == null) {
	        done = true;
	    }
	}
	for(String lab:environments) {
	    System.out.println(lab);
	}
    }

}
