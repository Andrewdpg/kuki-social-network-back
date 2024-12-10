package com.kuki.social_networking;

import com.kuki.social_networking.model.Tag;
import com.kuki.social_networking.repository.TagRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;

// TODO: Remake test
@Disabled
@SpringBootTest
public class TagRepositoryTest {

    @Autowired
    private TagRepository tagRepository;

    @Test
    public void contextLoads() {
        // Verifica que el repositorio no sea nulo, indicando que Spring lo ha inyectado correctamente
        assertNotNull(tagRepository, "TagRepository should be autowired and not null");
    }

    @Test
    public void testSaveTag() {
        // Crear un nuevo Tag
        Tag tag = new Tag();
        tag.setName("test-tag-id");

        // Guardar el Tag en la base de datos
        tagRepository.save(tag);

        // Recuperar el Tag de la base de datos
        Tag foundTag = tagRepository.findById("test-tag-id").orElse(null);

        // Verificar que el Tag ha sido guardado correctamente
        assertThat(foundTag).isNotNull();
        assertThat(foundTag.getName()).isEqualTo("test-tag-id");
    }
}