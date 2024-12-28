package com.employee_service.service;

import com.employee_service.dto.clientDto.TenantDto;
import com.employee_service.dto.request.TrainingRequest;
import com.employee_service.dto.response.TrainingResponse;
import com.employee_service.model.Employee;
import com.employee_service.model.Training;
import com.employee_service.exception.ResourceNotFoundException;
import com.employee_service.mapper.TrainingMapper;
import com.employee_service.repository.TrainingRepository;
import com.employee_service.utility.FileUtil;
import com.employee_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;
    private final ValidationUtil validationUtil;

    public TrainingResponse addTraining(UUID tenantId,
                                        UUID employeeId,
                                        TrainingRequest request,
                                        MultipartFile file) throws IOException {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        Training training = trainingMapper.mapToEntity(tenant, employee, request, file);
        training = trainingRepository.save(training);
        return trainingMapper.mapToDto(training);
    }

    public List<TrainingResponse> getAllTrainings(UUID tenantId,
                                                  UUID employeeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        List<Training> trainings = trainingRepository.findByEmployeeId(employee.getId());
        return trainings.stream()
                .filter(tr -> tr.getTenantId().equals(tenant.getId()))
                .map(trainingMapper::mapToDto)
                .toList();
    }

    public List<TrainingResponse> getEmployeeTrainings(UUID tenantId,
                                                       String employeeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeByEmployeeId(tenant.getId(), employeeId);
        List<Training> trainings = trainingRepository.findByEmployeeId(employee.getId());
        return trainings.stream()
                .filter(tr -> tr.getTenantId().equals(tenant.getId()))
                .map(trainingMapper::mapToDto)
                .toList();
    }

    public TrainingResponse getTrainingById(UUID tenantId,
                                            UUID employeeId,
                                            UUID trainingId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        Training training = getTrainingById(tenant.getId(), employee, trainingId);
        return trainingMapper.mapToDto(training);
    }

    public byte[] getTrainingCertificateById(UUID tenantId,
                                             UUID employeeId,
                                             UUID trainingId,
                                             MediaType mediaType) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        Training training = getTrainingById(tenant.getId(), employee, trainingId);
        switch (training.getFileType()) {
            case "application/pdf":
                mediaType = MediaType.APPLICATION_PDF;
                break;
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
                mediaType = MediaType.valueOf(
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
                break;
            case "image/jpeg":
                mediaType = MediaType.IMAGE_JPEG;
                break;
            case "image/png":
                mediaType = MediaType.IMAGE_PNG;
                break;
            case "image/gif":
                mediaType = MediaType.IMAGE_GIF;
                break;
            default:
                throw new IllegalArgumentException("Unsupported file type: " + training.getFileType());
        }
        byte[] fileBytes = FileUtil.decompressFile(training.getFileBytes());
        if (fileBytes.length == 0) {
            throw new ResourceNotFoundException("Training file is not available");
        }
        return fileBytes;
    }

    public TrainingResponse updateTraining(UUID tenantId,
                                           UUID employeeId,
                                           UUID trainingId,
                                           TrainingRequest request,
                                           MultipartFile file) throws IOException {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        Training training = getTrainingById(tenant.getId(), employee, trainingId);
        training = trainingMapper.updateTraining(training, request, file);
        training = trainingRepository.save(training);
        return trainingMapper.mapToDto(training);
    }

    public void deleteTraining(UUID tenantId,
                               UUID employeeId,
                               UUID trainingId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        Training training = getTrainingById(tenant.getId(), employee, trainingId);
        trainingRepository.delete(training);
    }

    private Training getTrainingById(UUID tenantId, Employee employee, UUID trainingId) {

        return trainingRepository.findById(trainingId)
                .filter(tr -> tr.getTenantId().equals(tenantId))
                .filter(tr -> tr.getEmployee().equals(employee))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Training not found with id '" + trainingId + "'"));
    }
}