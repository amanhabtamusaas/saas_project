package com.insa.service;

import com.insa.dto.apiDto.TenantDto;
import com.insa.dto.request.TrainingRequest;
import com.insa.dto.response.TrainingResponse;
import com.insa.entity.Employee;
import com.insa.entity.Training;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.TrainingMapper;
import com.insa.repository.EmployeeRepository;
import com.insa.repository.TrainingRepository;
import com.insa.utility.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;
    private final EmployeeRepository employeeRepository;
    private final TenantServiceClient tenantServiceClient;

    public TrainingResponse addTraining(Long tenantId,
                                        Long employeeId,
                                        TrainingRequest request,
                                        MultipartFile file) throws IOException {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById (employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        Training training = trainingMapper.mapToEntity (request, file);
        training.setTenantId (tenant.getId ());
        training.setEmployee (employee);
        training = trainingRepository.save (training);
        return trainingMapper.mapToDto (training);
    }

    public List<TrainingResponse> getAllTrainings(Long tenantId,
                                                  Long employeeId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        List<Training> trainings = trainingRepository.findByEmployeeId(employee.getId());
        return trainings.stream ()
                .filter (tr -> tr.getTenantId ().equals (tenant.getId ()))
                .map (trainingMapper::mapToDto)
                .toList();
    }

    public List<TrainingResponse> getEmployeeTrainings(Long tenantId,
                                                       String employeeId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        List<Training> trainings = trainingRepository.findByEmployeeId(employee.getId());
        return trainings.stream ()
                .filter (tr -> tr.getTenantId ().equals (tenant.getId ()))
                .map (trainingMapper::mapToDto)
                .toList();
    }

    public TrainingResponse getTrainingById(Long tenantId,
                                            Long employeeId,
                                            Long trainingId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        Training training = trainingRepository.findById (trainingId)
                .filter (tr -> tr.getTenantId ().equals (tenant.getId ()))
                .filter (tr -> tr.getEmployee ().getId ().equals (employee.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Training not found with id '" + trainingId + "' in the specified employee"));
        return trainingMapper.mapToDto (training);
    }

    public byte[] getTrainingCertificateById(Long tenantId,
                                           Long employeeId,
                                           Long trainingId,
                                           MediaType mediaType) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        Training training = trainingRepository.findById (trainingId)
                .filter (edu -> edu.getTenantId ().equals (tenant.getId ()))
                .filter (edu -> edu.getEmployee ().getId ().equals (employee.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Training not found with id '" + trainingId + "' in the specified employee"));

        switch (training.getFileType()) {
            case "application/pdf":
                mediaType = MediaType.valueOf (MediaType.APPLICATION_PDF_VALUE);
                break;
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
                mediaType = MediaType.valueOf(
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
                break;
            case "image/jpeg":
                mediaType = MediaType.valueOf (MediaType.IMAGE_JPEG_VALUE);
                break;
            case "image/png":
                mediaType = MediaType.valueOf (MediaType.IMAGE_PNG_VALUE);
                break;
            case "image/gif":
                mediaType = MediaType.valueOf (MediaType.IMAGE_GIF_VALUE);
                break;
            default:
                return "Unsupported file type".getBytes ();
        }
        return FileUtils.decompressFile (training.getFileBytes ());

    }

    public TrainingResponse updateTraining(Long tenantId,
                                           Long employeeId,
                                           Long trainingId,
                                           TrainingRequest request,
                                           MultipartFile file) throws IOException {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        Training training = trainingRepository.findById (trainingId)
                .filter (tr -> tr.getTenantId ().equals (tenant.getId ()))
                .filter (tr -> tr.getEmployee ().getId ().equals (employee.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Training not found with id '" + trainingId + "' in the specified employee"));
        training = trainingMapper.updateTraining (training, request, file);
        training = trainingRepository.save (training);
        return trainingMapper.mapToDto (training);
    }

    public void deleteTraining(Long tenantId,
                               Long employeeId,
                               Long trainingId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        Training training = trainingRepository.findById (trainingId)
                .filter (tr -> tr.getTenantId ().equals (tenant.getId ()))
                .filter (tr -> tr.getEmployee ().getId ().equals (employee.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Training not found with id '" + trainingId + "' in the specified employee"));
        trainingRepository.delete (training);
    }
}
