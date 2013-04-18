package de.jotschi.acoustid.client;

import java.util.HashMap;
import java.util.Map;

public class SearchResult {
	
	private Map<Long, Long> resultMap = new HashMap<Long, Long>();
	
	public SearchResult(){
		
	}

	/**
	 * @return the resultMap
	 */
	public Map<Long, Long> getResultMap() {
		return resultMap;
	}

	/**
	 * @param resultMap the resultMap to set
	 */
	public void setResultMap(Map<Long, Long> resultMap) {
		this.resultMap = resultMap;
	}

}
