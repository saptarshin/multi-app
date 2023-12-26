package com.sample.process;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sample.poller.InputContent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TransactionsCache {
	
	private final int timeToLive;
	private ConcurrentHashMap<InputContent, List<Long>> localCache;
	
	public TransactionsCache(@Value("${transactions.cache.clean:120000}") int cleanInterval, @Value("${transactions.cache.expiry:600000}") int timeToLive) {
		localCache = new ConcurrentHashMap<>();
		this.timeToLive = timeToLive;
		
		Runnable cacheCleanRunnable = () ->
        {
        	while(true) {
        		try {
        			Thread.sleep(cleanInterval);
        			periodicCacheCleaning();
        		} catch (InterruptedException ex) {
        			log.error("Error occured while cleaning TransactionsCache ", ex);
        		}        		
        	}
        };
        Thread cacheCleanThread = new Thread(cacheCleanRunnable);
        cacheCleanThread.setDaemon(true);
        cacheCleanThread.start();
	}
	
	public boolean checkRepitions(Long currentTime, InputContent inputContent) {
		boolean isPresent = false;
		List<Long> oldOccurences = localCache.get(inputContent);
		if (oldOccurences != null && !oldOccurences.isEmpty()) {
			cleanListContents(currentTime, oldOccurences);
			if (oldOccurences.size() >= 4) {
				isPresent = true;
			}
			oldOccurences.add(currentTime);
		}
		else {
			oldOccurences = new ArrayList<>();
			oldOccurences.add(currentTime);
			localCache.put(inputContent, oldOccurences);
		}
		return isPresent;
	}
	
	private void cleanListContents(Long currentTime, List<Long> oldOccurences) {
		oldOccurences.removeIf(occu -> (currentTime - occu > timeToLive));
	}

	private void periodicCacheCleaning() {
		Long currentTime = System.currentTimeMillis();
		log.debug("Cleaning Cache");
		Iterator<Entry<InputContent, List<Long>>> iterator = localCache.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<InputContent, List<Long>> cacheEntry = iterator.next(); 
			List<Long> oldOccurences = cacheEntry.getValue();
			cleanListContents(currentTime, oldOccurences);
		    if (oldOccurences.isEmpty()) {
		    	iterator.remove();		    	
		    }
		}
	}
	
}
