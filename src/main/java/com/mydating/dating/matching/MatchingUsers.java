package com.mydating.dating.matching;

import java.util.List;

import com.mydating.dating.util.UserGender;

import lombok.Data;

@Data
public class MatchingUsers {

	private int id;
	private String name;
	private String email;
	private long phoneNo;
	private int age;
	private UserGender gender; 
	private List<String> intrests;
	private int ageDiff;
	private int mic;
}
