package org.opentripplanner.analyst;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A histogram summing/counting all time values into 1-minute bins (0-60 seconds = min 1, 61-120 = min 2, etc.)
 */
public class Histogram implements Serializable {

    public final int[] counts;
    public final int[] sums;

    // TODO allow specifying breaks

    /**
     * Represent the distribution of the given times using n+1 numbers.
     * @param times the time at which each destination is reached. The array will be sorted in place.
     * @param weights the weight or magnitude of each destination reached. parallel to times.
     */
    public Histogram (int[] times, int[] weights) {
       
    	int tmpCounts[] = new int[1000];
    	int tmpSums[] = new int[1000];
    	
    	int uppperBound = 0;
    	
    	for(int i = 0; i < times.length; i++) {
    		
    		if(times[i] < 0 || times[i] == Integer.MAX_VALUE)
    			continue;
    	
    		int minuteBin = (int)Math.ceil(times[i] / 60.0);
    		
    		tmpCounts[minuteBin] += 1; 
    		tmpSums[minuteBin] += weights[i];
    				
    		if(minuteBin > uppperBound)
    			uppperBound = minuteBin;
    	}
    	
    	counts = new int[uppperBound];
    	sums = new int[uppperBound];

    	for(int i = 0; i < uppperBound; i++) {
    		counts[i] = tmpCounts[i];
    		sums[i] = tmpSums[i];
    	}   	
    }
}
