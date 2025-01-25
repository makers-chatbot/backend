package icesi.cmr.utils;

import icesi.cmr.dto.UserDTO;
import icesi.cmr.model.relational.companies.Company;
import icesi.cmr.model.relational.companies.Department;
import icesi.cmr.model.relational.equipments.Contract;
import icesi.cmr.model.relational.equipments.DeliveryCertificate;
import icesi.cmr.model.relational.equipments.Equipment;
import icesi.cmr.model.relational.equipments.EquipmentCategory;
import icesi.cmr.model.relational.users.*;
import icesi.cmr.repositories.companies.CompanyRepository;
import icesi.cmr.repositories.companies.DepartmentRepository;
import icesi.cmr.repositories.equipments.ContractRepository;
import icesi.cmr.repositories.equipments.DeliveryCertificateRepository;
import icesi.cmr.repositories.equipments.EquipmentCategoryRepository;
import icesi.cmr.repositories.equipments.EquipmentRepository;
import icesi.cmr.repositories.users.RoleRepository;
import icesi.cmr.repositories.users.UserRepository;
import icesi.cmr.repositories.users.UserRoleRepository;
import icesi.cmr.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class DataInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EquipmentCategoryRepository equipmentCategoryRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private DeliveryCertificateRepository deliveryCertificateRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;


    public void init() {
        //--------------Roles Creation--------------


        Role platformAdmin = Role.builder()
                .name("ADMIN")
                .description("Administrator of the platform")
                .build();

        Role businessManager = Role.builder()
                .name("BUSINESS_MANAGER")
                .description("Manager of a business")
                .build();

        Role worker = Role.builder()
                .name("WORKER")
                .description("Worker of a business")
                .build();

        roleRepository.save(platformAdmin);
        roleRepository.save(businessManager);
        roleRepository.save(worker);

        //--------------Company and department Creation--------------


        Company company1 = Company.builder()
                .name("Company 1")
                .nit("123456789")
                .email("company1@example.com")
                .state("California")
                .phone("123-456-7890")
                .country("USA")
                .address("123 Main Street")
                .industry("Technology")
                .creationDate(System.currentTimeMillis())
                .build();

        companyRepository.save(company1);


        Department department = Department.builder().description("Description department 1").name("Department 1").company(company1).build();
        departmentRepository.save(department);

        company1.setDepartments(List.of(department));
        companyRepository.save(company1);


        //--------------User creation--------------

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("worker");
        userDTO.setPassword("worker");
        userDTO.setEmail("worker@example.com");
        userDTO.setCompanyId(company1.getId());
        userDTO.setDepartmentId(department.getId());
        userDTO.setRolesNames(List.of("WORKER"));

        UserDTO businessManagerDTO = new UserDTO();
        businessManagerDTO.setUsername("business");
        businessManagerDTO.setPassword("business");
        businessManagerDTO.setEmail("business@example.com");
        businessManagerDTO.setCompanyId(company1.getId());
        businessManagerDTO.setDepartmentId(department.getId());
        businessManagerDTO.setRolesNames(List.of("BUSINESS_MANAGER"));


        Admin admin1 = Admin.builder()
                .username("admin")
                .password("admin")
                .email("admin@example.com")
                .description("Admin in charge of the platform")
                .createdAt(System.currentTimeMillis())
                .build();

        admin1.setPassword(encoder.encode(admin1.getPassword()));

        userService.save(userDTO);
        userService.save(businessManagerDTO);
        userRepository.save(admin1);


        departmentRepository.save(department);
        companyRepository.save(company1);

        //--------------User Roles creation--------------

        UserRolePK userRolePK1 = UserRolePK.builder()
                .roleId(platformAdmin.getId())
                .userId(admin1.getId())
                .build();

        UserRole userRole1 = UserRole.builder()
                .id(userRolePK1)
                .user(admin1)
                .role(platformAdmin)
                .build();

        userRoleRepository.save(userRole1);

        //--------------Equipment staff creation--------------


        EquipmentCategory equipmentCategory1 = EquipmentCategory.builder()
                .name("Computacion")
                .description("Categoria de computacion")
                .build();

        EquipmentCategory equipmentCategory2 = EquipmentCategory.builder()
                .name("Impresion")
                .description("Categoría de impresion")
                .build();

        EquipmentCategory equipmentCategory3 = EquipmentCategory.builder()
                .name("Movil")
                .description("Categoría de movil")
                .build();


        Equipment equipment = Equipment.builder()
                .inventaryCode("123456")
                .stock(10)
                .build();

        equipmentCategoryRepository.save(equipmentCategory1);
        equipmentCategoryRepository.save(equipmentCategory2);
        equipmentCategoryRepository.save(equipmentCategory3);

        equipmentRepository.save(equipment);

        //--------------Contract and DeliveryCertificate creation--------------

        Contract contract = Contract.builder()
                .department(department)
                .startDate(System.currentTimeMillis())
                .endDate(System.currentTimeMillis() + 1000000)
                .monthlyCost(1000f)
                .contractNumber(UUID.randomUUID().toString())
                .build();

        contractRepository.save(contract);

        DeliveryCertificate deliveryCertificate = DeliveryCertificate.builder()
                .deliveryDate(System.currentTimeMillis())
                .equipment(equipment)
                .notes("Notes")
                .quantity(1)
                .build();

        deliveryCertificate.setContract(contract);
        contract.setDeliveryCertificate(List.of(deliveryCertificate));

        contractRepository.save(contract);
        deliveryCertificateRepository.save(deliveryCertificate);


    }
}
