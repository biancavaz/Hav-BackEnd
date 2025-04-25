package com.hav.hav_imobiliaria;

import com.hav.hav_imobiliaria.model.entity.Address;
import com.hav.hav_imobiliaria.model.entity.Properties.*;
import com.hav.hav_imobiliaria.model.entity.Users.*;
import com.hav.hav_imobiliaria.repository.*;
import com.hav.hav_imobiliaria.security.configSecurity.TokenProvider;
import com.hav.hav_imobiliaria.security.modelSecurity.Role;
import com.hav.hav_imobiliaria.security.modelSecurity.UserSecurity;
import com.hav.hav_imobiliaria.security.repositorySecurity.UserRepositorySecurity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class HavImobiliariaApplication {

    public static void main(String[] args) {
        SpringApplication.run(HavImobiliariaApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(
            TokenProvider tokenProvider,
            UserRepositorySecurity userRepositorySecurity,
            PasswordEncoder passwordEncoder,
            AdmRepository admRepository,
            EditorRepository editorRepository,
            RealtorRepository realtorRepository,
            CustumerRepository customerRepository,
            PropertyRepository propertyRepository,
            AddressRepository addressRepository,
            PropertyFeatureRepository propertyFeatureRepository,
            TaxesRepository taxesRepository,
            AdditionalsRepository additionalsRepository,
            ProprietorRepository proprietorRepository) {
        return args -> {
            // Verificar se já existem usuários cadastrados
            if (userRepositorySecurity.count() > 0) {
                System.out.println("Já existem usuários cadastrados no sistema.");
                return;
            }

            // Verificar se já existem propriedades cadastradas
            if (propertyRepository.count() > 0) {
                System.out.println("Já existem propriedades cadastradas no sistema.");
                return;
            }

            System.out.println("Iniciando cadastro de dados iniciais...");

            // Create users
            UserSecurity adminSecurity = new UserSecurity();
            adminSecurity.setEmail("admin@hav.com");
            adminSecurity.setPassword(passwordEncoder.encode("admin123"));
            adminSecurity.setName("Admin User");
            adminSecurity.setRole(Role.ADMIN);
            userRepositorySecurity.save(adminSecurity);

            UserSecurity editorSecurity = new UserSecurity();
            editorSecurity.setEmail("editor@hav.com");
            editorSecurity.setPassword(passwordEncoder.encode("editor123"));
            editorSecurity.setName("Editor User");
            editorSecurity.setRole(Role.EDITOR);
            userRepositorySecurity.save(editorSecurity);

            UserSecurity realtorSecurity = new UserSecurity();
            realtorSecurity.setEmail("realtor@hav.com");
            realtorSecurity.setPassword(passwordEncoder.encode("realtor123"));
            realtorSecurity.setName("Realtor User");
            realtorSecurity.setRole(Role.REALTOR);
            userRepositorySecurity.save(realtorSecurity);

            UserSecurity customerSecurity = new UserSecurity();
            customerSecurity.setEmail("customer@hav.com");
            customerSecurity.setPassword(passwordEncoder.encode("customer123"));
            customerSecurity.setName("Customer User");
            customerSecurity.setRole(Role.CUSTOMER);
            userRepositorySecurity.save(customerSecurity);

            // Create user entities with their addresses
            Adm admin = new Adm();
            admin.setName("Admin User");
            admin.setEmail("admin@hav.com");
            admin.setUserSecurity(adminSecurity);
            Address adminAddress = new Address();
            adminAddress.setCep("12345-678");
            adminAddress.setStreet("Admin Street");
            adminAddress.setNeighborhood("Admin Neighborhood");
            adminAddress.setCity("Admin City");
            adminAddress.setState("Admin State");
            adminAddress.setPropertyNumber(1);
            admin.setAddress(adminAddress);
            admRepository.save(admin);

            Editor editor = new Editor();
            editor.setName("Editor User");
            editor.setEmail("editor@hav.com");
            editor.setUserSecurity(editorSecurity);
            Address editorAddress = new Address();
            editorAddress.setCep("12345-678");
            editorAddress.setStreet("Editor Street");
            editorAddress.setNeighborhood("Editor Neighborhood");
            editorAddress.setCity("Editor City");
            editorAddress.setState("Editor State");
            editorAddress.setPropertyNumber(2);
            editor.setAddress(editorAddress);
            editorRepository.save(editor);

            Realtor realtor = new Realtor();
            realtor.setName("Realtor User");
            realtor.setEmail("realtor@hav.com");
            realtor.setCreci("12345");
            realtor.setUserSecurity(realtorSecurity);
            Address realtorAddress = new Address();
            realtorAddress.setCep("12345-678");
            realtorAddress.setStreet("Realtor Street");
            realtorAddress.setNeighborhood("Realtor Neighborhood");
            realtorAddress.setCity("Realtor City");
            realtorAddress.setState("Realtor State");
            realtorAddress.setPropertyNumber(3);
            realtor.setAddress(realtorAddress);
            realtorRepository.save(realtor);

            Customer customer = new Customer();
            customer.setName("Customer User");
            customer.setEmail("customer@hav.com");
            customer.setUserSecurity(customerSecurity);
            Address customerAddress = new Address();
            customerAddress.setCep("12345-678");
            customerAddress.setStreet("Customer Street");
            customerAddress.setNeighborhood("Customer Neighborhood");
            customerAddress.setCity("Customer City");
            customerAddress.setState("Customer State");
            customerAddress.setPropertyNumber(4);
            customer.setAddress(customerAddress);
            customerRepository.save(customer);

            // Create proprietor with address
            Proprietor proprietor = new Proprietor();
            proprietor.setName("Proprietor User");
            proprietor.setEmail("proprietor@hav.com");
            proprietor.setCnpj("12345678901234");
            Address proprietorAddress = new Address();
            proprietorAddress.setCep("12345-678");
            proprietorAddress.setStreet("Proprietor Street");
            proprietorAddress.setNeighborhood("Proprietor Neighborhood");
            proprietorAddress.setCity("Proprietor City");
            proprietorAddress.setState("Proprietor State");
            proprietorAddress.setPropertyNumber(5);
            proprietor.setAddress(proprietorAddress);
            proprietorRepository.save(proprietor);

            // Create properties
            for (int i = 1; i <= 15; i++) {
                // Create property with address
                Property property = new Property();
                property.setPropertyCode("prop_" + i);
                property.setTitle("imovel_" + i + "_em_jaragua_do_sul");
                property.setPropertyDescription("excelente_imovel_localizado_em_jaragua_do_sul_com_" + (2 + i) + "_quartos_" + (i % 2 == 0 ? "apartamento" : "casa") + "_em_otimo_estado_de_conservacao");
                property.setPropertyType(i % 2 == 0 ? "apartamento" : "casa");
                property.setPurpose(i % 2 == 0 ? "venda" : "locacao");
                property.setPropertyStatus("disponivel");
                property.setArea(100.0 + i * 20);
                property.setPrice(200000.0 + i * 50000);

                // Randomly decide if this property should have a promotional price (50% chance)
                boolean hasPromotionalPrice = Math.random() < 0.5;

                if (hasPromotionalPrice) {
                    // Set promotional price (10-20% discount from regular price)
                    double discount = 0.10 + (Math.random() * 0.10); // 10-20% discount
                    double promotionalPrice = property.getPrice() * (1 - discount);
                    property.setPromotionalPrice(Math.round(promotionalPrice * 100.0) / 100.0);
                    property.setPropertyStatus("promocao"); // Change status to "promocao"
                } else {
                    property.setPromotionalPrice(0.0); // No promotional price
                }

                property.setHighlight(i % 2 == 0);
                property.setFloors(i);

                // Create and set address
                Address propertyAddress = new Address();
                propertyAddress.setCep("89250000");
                propertyAddress.setStreet("rua_" + (i == 1 ? "getulio_vargas" : i == 2 ? "reinoldo_rau" : i == 3 ? "joao_bauer" : i == 4 ? "mal_deodoro" : "alberto_bauer"));
                propertyAddress.setNeighborhood(i == 1 ? "centro" : i == 2 ? "nova_brasilia" : i == 3 ? "vila_lalau" : i == 4 ? "vila_lenzi" : "vila_nova");
                propertyAddress.setCity("jaragua_do_sul");
                propertyAddress.setState("santa_catarina");
                propertyAddress.setPropertyNumber(i * 100);
                property.setAddress(propertyAddress);

                // Create and set property features
                PropertyFeature features = new PropertyFeature();
                features.setAllowsPet(true);
                features.setBedRoom(2 + i);
                features.setLivingRoom(1);
                features.setBathRoom(2);
                features.setSuite(1);
                features.setGarageSpace(1);
                features.setIsFurnished(i % 2 == 0);
                property.setPropertyFeatures(features);

                // Create and set taxes
                Taxes taxes = new Taxes();
                taxes.setCondominiumFee(500.0 + i * 100);
                taxes.setIptu(1000.0 + i * 200);
                property.setTaxes(taxes);

                property.setProprietor(proprietor);
                property.setRealtors(List.of(realtor));
                propertyRepository.save(property);
            }

            System.out.println("Dados iniciais cadastrados com sucesso!");

            // Print user credentials
            System.out.println("\nUser Credentials:");
            System.out.println("Admin - Email: admin@hav.com, Password: admin123");
            System.out.println("Editor - Email: editor@hav.com, Password: editor123");
            System.out.println("Realtor - Email: realtor@hav.com, Password: realtor123");
            System.out.println("Customer - Email: customer@hav.com, Password: customer123");
        };
    }
}
