package br.com.fiap.contatos.util.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {

    @Bean
    public static ModelMapper getModel() {
        return new ModelMapper();
    }

}
