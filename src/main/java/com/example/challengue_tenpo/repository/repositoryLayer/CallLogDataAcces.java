package com.example.challengue_tenpo.repository.repositoryLayer;

import com.example.challengue_tenpo.model.CallLog;
import com.example.challengue_tenpo.repository.CallLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CallLogDataAcces {

    @Autowired
    private CallLogRepository callLogRepository;

    public List<CallLog> findAll() {
        return callLogRepository.findAll();
    }

    public void save(CallLog callLog) {
        callLogRepository.save(callLog);
    }

}
