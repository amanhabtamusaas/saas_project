package com.insa.mapper;

import com.insa.dto.request.TrainingRequest;
import com.insa.dto.response.TrainingResponse;
import com.insa.entity.Training;
import com.insa.utility.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TrainingMapper {

    public Training mapToEntity(TrainingRequest request,
                                MultipartFile file) throws IOException {
        Training training = new Training ();
        training.setTrainingTitle (request.getTrainingTitle ());
        training.setInstitution (request.getInstitution ());
        training.setSponsoredBy (request.getSponsoredBy ());
        training.setStartDate (request.getStartDate ());
        training.setEndDate (request.getEndDate ());

        if (file != null){
            training.setFileName (file.getOriginalFilename ());
            training.setFileType (file.getContentType ());
            training.setFileBytes (FileUtils.compressFile (file.getBytes ()));
        }

        return training;
    }

    public TrainingResponse mapToDto(Training training) {
        TrainingResponse response = new TrainingResponse ();
        response.setId (training.getId ());
        response.setTenantId (training.getTenantId ());
        response.setTrainingTitle (training.getTrainingTitle ());
        response.setInstitution (training.getInstitution ());
        response.setSponsoredBy (training.getSponsoredBy ());
        response.setStartDate (training.getStartDate ());
        response.setEndDate (training.getEndDate ());
        response.setCreatedAt (training.getCreatedAt ());
        response.setUpdatedAt (training.getUpdatedAt ());
        response.setCreatedBy (training.getCreatedBy ());
        response.setUpdatedBy (training.getUpdatedBy ());
        response.setEmployeeId (training.getEmployee ().getId ());
        response.setFileName(training.getFileName());
        response.setFileType(training.getFileType ());
        response.setFileBytes(training.getFileBytes());

        return response;
    }

    public Training updateTraining(Training training,
                                   TrainingRequest request,
                                   MultipartFile file) throws IOException {

        if (request.getTrainingTitle () != null)
            training.setTrainingTitle (request.getTrainingTitle ());
        if (request.getInstitution () != null)
            training.setInstitution (request.getInstitution ());
        if (request.getSponsoredBy () != null)
            training.setSponsoredBy (request.getSponsoredBy ());
        if (request.getStartDate () != null)
            training.setStartDate (request.getStartDate ());
        if (request.getEndDate () != null)
            training.setEndDate (request.getEndDate ());

        if (file != null && !file.isEmpty ()) {
            training.setFileName (file.getOriginalFilename ());
            training.setFileType (file.getContentType ());
            training.setFileBytes (FileUtils.compressFile (file.getBytes ()));
        }

        return training;
    }
}
