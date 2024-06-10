package com.example.test.date;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;


@Repository
public interface DateRepository extends JpaRepository<DateDTO, Object> {

	List<DateDTO> findByApplicantNum(int num);

	DateDTO findByReceiverNumAndApplicantNum(int receiverNum, int applicantNum);
	
	int deleteByReceiverNumAndApplicantNum(int receiverNum, int applicantNum);

	int deleteByNum(int num);

	DateDTO findByApplicantNumAndReceiverNum(int receiverNum, int applicantNum);

}
