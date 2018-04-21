package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.AuthHistory;

//@Mapper
@Repository
public interface AuthHistoryDao {

	int insertAuthHistory(AuthHistory authHistory);

	List<AuthHistory> selectAuthHistoryByBugTicket(Integer id);
	
}
