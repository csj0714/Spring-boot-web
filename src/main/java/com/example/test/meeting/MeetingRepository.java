package com.example.test.meeting;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;


@Repository
public interface MeetingRepository extends JpaRepository<MeetingDTO, Object> {

	List<MeetingDTO> findByApplicantNum(int num);

	MeetingDTO findByReceiverNumAndApplicantNum(int receiverNum, int applicantNum);
	
	int deleteByReceiverNumAndApplicantNum(int receiverNum, int applicantNum);

	int deleteByNum(int num);

	MeetingDTO findByApplicantNumAndReceiverNum(int receiverNum, int applicantNum);

}
