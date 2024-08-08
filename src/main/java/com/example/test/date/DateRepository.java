package com.example.test.date;

import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface DateRepository extends JpaRepository<DateDTO, Object> {

	List<DateDTO> findByApplicantNum(int num);

	DateDTO findByReceiverNumAndApplicantNum(int receiverNum, int applicantNum);
	
	int deleteByReceiverNumAndApplicantNum(int receiverNum, int applicantNum);

	int deleteByNum(int num);

	DateDTO findByApplicantNumAndReceiverNum(int receiverNum, int applicantNum);

	List<DateDTO> findByReceiverNum(int num);

	@Modifying
	@Transactional
	@Query("UPDATE DateDTO d SET d.accept = :accept WHERE d.receiverNum = :receiverNum AND d.applicantNum = :applicantNum")
	int updateAcceptField(String accept, int receiverNum, int applicantNum);
}
