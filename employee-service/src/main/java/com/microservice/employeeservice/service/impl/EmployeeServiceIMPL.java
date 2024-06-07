package com.microservice.employeeservice.service.impl;

import com.microservice.employeeservice.dto.ApiResponseDTO;
import com.microservice.employeeservice.dto.DepartmentDTO;
import com.microservice.employeeservice.dto.EmployeeDTO;
import com.microservice.employeeservice.entity.Employee;
import com.microservice.employeeservice.repository.EmployeeRepository;
import com.microservice.employeeservice.service.APIClient;
import com.microservice.employeeservice.service.EmployeeService;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
public class EmployeeServiceIMPL implements EmployeeService {


    private EmployeeRepository employeeRepository;
//    private RestTemplate restTemplate;

//    private WebClient webClient;

    private APIClient apiClient;


    @Override
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee=new Employee(
                employeeDTO.getId(),
                employeeDTO.getFirstname(),
                employeeDTO.getLastname(),
                employeeDTO.getEmail(),
                employeeDTO.getDepartmentCode()
        );
        Employee savedEmployee=employeeRepository.save(employee);

        EmployeeDTO savedEmployeeDTO= new EmployeeDTO(
                savedEmployee.getId(),
                savedEmployee.getFirstname(),
                savedEmployee.getLastname(),
                savedEmployee.getEmail(),
                savedEmployee.getDepartmentCode()
        );
        return savedEmployeeDTO;
    }

    @Override
    public ApiResponseDTO getEmployeeById(Long id) {
        Employee employee= employeeRepository.findById(id).get();

        //communication with rest template
       /* ResponseEntity<DepartmentDTO> responseEntity =restTemplate.
                getForEntity("http://localhost:8080/api/departments/" + employee.getDepartmentCode(),
                        DepartmentDTO.class);


        DepartmentDTO departmentDTO=responseEntity.getBody();*/


        // using web client
        /*DepartmentDTO departmentDTO= webClient.get()
                .uri("http://localhost:8080/api/departments/" + employee.getDepartmentCode())
                .retrieve()
                .bodyToMono(DepartmentDTO.class)
                .block();*/


        DepartmentDTO departmentDTO=apiClient.getDepartment(employee.getDepartmentCode());


        EmployeeDTO employeeDTO= new EmployeeDTO(
                employee.getId(),
                employee.getFirstname(),
                employee.getLastname(),
                employee.getEmail(),
                employee.getDepartmentCode()
        );

        ApiResponseDTO apiResponseDTO = new ApiResponseDTO();
        apiResponseDTO.setEmployeeDTO(employeeDTO);
        apiResponseDTO.setDepartmentDTO(departmentDTO);
        return apiResponseDTO;
    }
}
