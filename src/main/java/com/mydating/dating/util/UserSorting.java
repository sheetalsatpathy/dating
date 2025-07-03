package com.mydating.dating.util;

import java.util.Comparator;

import com.mydating.dating.matching.MatchingUsers;

public class UserSorting implements Comparator<MatchingUsers>{
@Override
public int compare (MatchingUsers o1,MatchingUsers o2) {
	if(o1.getAgeDiff()<o2.getAgeDiff()) {
		return -1;
	}
	else if(o1.getAgeDiff()>o2.getAgeDiff()) {
		return 1;
	}else if(o1.getAgeDiff()==o2.getAgeDiff()) {
		if(o1.getMic()<o2.getMic()) {
			return 1;
		}else if(o1.getMic()>o2.getMic()) {
			return -1;
		}
	}
	return 0; 
}
}
