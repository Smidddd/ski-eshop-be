package sk.umb.eshop.customer.service;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import sk.umb.eshop.customer.persistence.entity.CustomerEntity;
import sk.umb.eshop.customer.persistence.repository.CustomerRepository;
import org.mindrot.jbcrypt.*;

import java.util.ArrayList;
import java.util.List;
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    private final int logRounds = 10;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerDetailDTO> getAllCustomers() {
        return mapToDto(customerRepository.findAll());
    }


    public CustomerDetailDTO getCustomerById(Long customerId) {
        validateCustomerExists(customerId);
        return mapToDto(customerRepository.findById(customerId).get());
    }
    public CustomerDetailDTO getCustomerByEmail(String customerEmail) {
        return mapToDto(customerRepository.findByEmail(customerEmail));
    }

    public Long createCustomer(CustomerRequestDTO customerRequestDTO) {
        return customerRepository.save(mapToEntity(customerRequestDTO)).getId();
    }

    private List<CustomerDetailDTO> mapToDto(List<CustomerEntity> customerEntities) {
        List<CustomerDetailDTO> dtos = new ArrayList<>();

        for (CustomerEntity ce : customerEntities) {
            CustomerDetailDTO dto = new CustomerDetailDTO();

            dto.setId(ce.getId());
            dto.setFirstName(ce.getFirstName());
            dto.setLastName(ce.getLastName());
            dto.setPassword(ce.getPassword());
            dto.setEmail(ce.getEmail());
            dto.setPhone(ce.getPhone());
            dto.setAddress(ce.getAddress());
            dto.setCity(ce.getCity());
            dto.setState(ce.getState());
            dto.setZipCode(ce.getZipCode());
            dto.setRole(ce.getRole());

            dtos.add(dto);
        }

        return dtos;
    }
    private CustomerDetailDTO mapToDto(CustomerEntity customerEntity) {
        CustomerDetailDTO dto = new CustomerDetailDTO();

        dto.setId(customerEntity.getId());
        dto.setFirstName(customerEntity.getFirstName());
        dto.setLastName(customerEntity.getLastName());
        dto.setPassword(customerEntity.getPassword());
        dto.setEmail(customerEntity.getEmail());
        dto.setPhone(customerEntity.getPhone());
        dto.setAddress(customerEntity.getAddress());
        dto.setCity(customerEntity.getCity());
        dto.setState(customerEntity.getState());
        dto.setZipCode(customerEntity.getZipCode());
        dto.setRole(customerEntity.getRole());


        return dto;
    }

    private CustomerEntity mapToEntity(CustomerRequestDTO dto) {
        CustomerEntity ce = new CustomerEntity();
        ce.setFirstName(dto.getFirstName());
        ce.setLastName(dto.getLastName());
        ce.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(logRounds)));
        ce.setEmail(dto.getEmail());
        ce.setPhone(dto.getPhone());
        ce.setAddress(dto.getAddress());
        ce.setCity(dto.getCity());
        ce.setState(dto.getState());
        ce.setZipCode(dto.getZipCode());
        ce.setRole(dto.getRole());

        return ce;
    }

    public void updateCustomer(Long customerId, CustomerRequestDTO customerRequestDTO) {
        validateCustomerExists(customerId);

        CustomerEntity customerEntity = customerRepository.findById(customerId).get();

        if (! Strings.isEmpty(customerRequestDTO.getFirstName())) {
            customerEntity.setFirstName(customerRequestDTO.getFirstName());
        }

        if (! Strings.isEmpty(customerRequestDTO.getLastName())) {
            customerEntity.setLastName(customerRequestDTO.getLastName());
        }

        if (! Strings.isEmpty(customerRequestDTO.getPassword())) {
            customerEntity.setPassword(BCrypt.hashpw(customerRequestDTO.getPassword(), BCrypt.gensalt(logRounds)) );
        }

        if (! Strings.isEmpty(customerRequestDTO.getEmail())) {
            customerEntity.setEmail(customerRequestDTO.getEmail());
        }

        if (customerRequestDTO.getPhone() != null) {
            customerEntity.setPhone(customerRequestDTO.getPhone());
        }

        if (! Strings.isEmpty(customerRequestDTO.getAddress())) {
            customerEntity.setAddress(customerRequestDTO.getAddress());
        }

        if (! Strings.isEmpty(customerRequestDTO.getCity())) {
            customerEntity.setCity(customerRequestDTO.getCity());
        }

        if (! Strings.isEmpty(customerRequestDTO.getState())) {
            customerEntity.setState(customerRequestDTO.getState());
        }

        if (customerRequestDTO.getZipCode() != null) {
            customerEntity.setZipCode(customerRequestDTO.getZipCode());
        }

        if (! Strings.isEmpty(customerRequestDTO.getRole())) {
            customerEntity.setRole(customerRequestDTO.getRole());
        }

        customerRepository.save(customerEntity);
    }

    private void validateCustomerExists(Long customerId) {
        if (! customerRepository.existsById(customerId)) {
            throw new IllegalArgumentException("CustomerId: " + customerId + " does not exists!");
        }
    }

    public boolean verifyHash(String password, Long id) {
        String hash = customerRepository.findById(id).get().getPassword();
        System.out.println(BCrypt.checkpw(password, hash));
        return BCrypt.checkpw(password, hash);
    }
    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }
}
