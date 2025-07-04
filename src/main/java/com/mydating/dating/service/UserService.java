package com.mydating.dating.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mydating.dating.dao.UserDao;
import com.mydating.dating.entity.User;
import com.mydating.dating.matching.MatchingUsers;
import com.mydating.dating.util.UserGender;
import com.mydating.dating.util.UserSorting;

@Service
public class UserService {
@Autowired
UserDao userDao;

public ResponseEntity<?> saveUser(User user) {
	User savedUser=userDao.saveUser(user);
	return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
}

public ResponseEntity<?> findAllMaleUsers() {
 List<User> maleUsers=userDao.findAllMaleUsers();
	 if(maleUsers.isEmpty()) {
		 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No male user is present");
	 }else {
		 return ResponseEntity.status(HttpStatus.OK).body(maleUsers); 
	 }
 }

public ResponseEntity<?> findAllFemaleUsers() {
	List<User>femaleUsers=userDao.findAllFemaleUser();
	if(femaleUsers.isEmpty()) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No female user is found");
		
	}else {
		return ResponseEntity.status(HttpStatus.OK).body(femaleUsers); 
	}
	
}

public ResponseEntity<?> findBestmatch(int id, int top) {
	Optional<User> option =userDao.findUserById(id);
	if(option.isEmpty()) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid id....id ot found");
	}
	User user=option.get();
	
	List<User>users = null;
	if(user.getGender().equals(UserGender.MALE)) {
		users=userDao.findAllFemaleUser();	
		}
	else {
		users=userDao.findAllMaleUsers();
	}
	
	List<MatchingUsers> matchingUsers=new ArrayList<>();
	
	for(User u : users) {
		MatchingUsers mu=new MatchingUsers();
		
		mu.setId(u.getId());
		mu.setName(u.getName());
		mu.setEmail(u.getEmail());
		mu.setPhoneNo(u.getPhoneNo());
		mu.setAge(u.getAge());
		mu.setIntrests(u.getIntrests());
		mu.setGender(u.getGender());
		
		
		mu.setAgeDiff(Math.abs(user.getAge()-u.getAge()));
		
		List<String> intrests1=user.getIntrests();
		List<String> intrests2=u.getIntrests();
		
		int mic=0;
		for(String s:intrests1) {
			if(intrests2.contains(s)) {
				mic++;
			}
		}
		mu.setMic(mic);
		matchingUsers.add(mu);
		
	}
	Comparator<MatchingUsers> c =new UserSorting();
	Collections.sort(matchingUsers,c);
	List<MatchingUsers> result=new ArrayList<>();
	for(MatchingUsers mu: matchingUsers) {
		if(top==0) {
			break;
		}
		else {
			result.add(mu);
			top--;
		}
	}
	
	return ResponseEntity.status(HttpStatus.OK).body(result);
}


public ResponseEntity<?> findByAge(int age) {
	List<User> users = userDao.findByAge(age);
	if(users.isEmpty()) {
		return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("age not found");    
	}else {
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}
}

public ResponseEntity<?> searchByName(String letters) {
	List<User> user=userDao.searchByName("%"+letters+"%");
	if(user.isEmpty()) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no user found with letters:"+letters);
	}else {
		return ResponseEntity.status(HttpStatus.OK).body(user); 
	}
}

public ResponseEntity<?> searchByEmail(String letters) {
	List<User> user =userDao.searchByEmail("%"+letters+"%");
	if(user.isEmpty()) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no user found with letter:"+letters);
	}else {
	return ResponseEntity.status(HttpStatus.OK).body(user); 
	}
}





}
