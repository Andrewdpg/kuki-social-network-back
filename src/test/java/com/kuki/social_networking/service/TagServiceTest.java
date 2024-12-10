package com.kuki.social_networking.service;

import com.kuki.social_networking.model.Tag;
import com.kuki.social_networking.repository.TagRepository;
import com.kuki.social_networking.service.implementation.TagServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagServiceImpl tagService;

    private Tag tag1;
    private Tag tag2;
    private List<Tag> tags;


    @BeforeEach
    public void setUp() {
        tag1 = Tag.builder()
                .name("Tag1")
                .build();

        tag2 = Tag.builder()
                .name("Tag2")
                .build();

        tags = List.of(tag1, tag2);

    }

    @Test
    public void getAllTags() {
        when(tagRepository.findAll()).thenReturn(tags);

        List<Tag> result = tagService.getAllTags();
        assertEquals(tags, result);
    }

    @Test
    public void getTagByNameExisting() {
        when(tagRepository.findById("tag1")).thenReturn(java.util.Optional.of(tag1));
        when(tagRepository.findById("tag2")).thenReturn(java.util.Optional.of(tag2));
        Tag result1 = tagService.getTagByName("tag1");
        Tag result2 = tagService.getTagByName("tag2");

        assertEquals(tag1, result1);
        assertEquals(tag2, result2);
    }

    @Test
    public void getTagByNameNoExisting() {
        when(tagRepository.findById("Nonexistingtag")).thenReturn(Optional.empty());

        String tagName = "Nonexistingtag";

        try {
            tagService.getTagByName(tagName);
        } catch (IllegalArgumentException e) {
            assertEquals("Tag not found: " + tagName + ".", e.getMessage());
        }
    }

    @Test
    public void addTagNoExisting(){
        when(tagRepository.save(tag1)).thenReturn(tag1);
        when(tagRepository.save(tag2)).thenReturn(tag2);


        Tag result1 = tagService.addTag("tag1");
        Tag result2 = tagService.addTag("tag2");

        assertEquals(tag1, result1);
        assertEquals(tag2, result2);
    }

    @Test
    public void addTagExisting(){
        String tagName = "tag1";

        try {
            tagService.addTag(tagName);
        } catch (IllegalArgumentException e) {
            assertEquals("Tag already exists: " + tagName + ".", e.getMessage());
        }
    }

    @Test
    public void addTagInvalid(){
        String tagName = "tag1";

        try {
            tagService.addTag(tagName);
        } catch (IllegalArgumentException e) {
            assertEquals("Tag already exists: " + tagName + ".", e.getMessage());
        }
    }

    @Test
    public void deleteTagExisting() {
        String tagName = "Tag";

        Tag tag = Tag.builder()
                .name(tagName)
                .build();

        when(tagRepository.findById(tagName)).thenReturn(Optional.of(tag));

        tagService.deleteTag(tagName);

        verify(tagRepository).delete(tag);
    }

    @Test
    public void deleteTagNoExisting() {
        String tagName = "Nonexistingtag";

        when(tagRepository.findById(tagName)).thenReturn(Optional.empty());

        try {
            tagService.deleteTag(tagName);
        } catch (IllegalArgumentException e) {
            assertEquals("Tag not found: " + tagName + ".", e.getMessage());
        }
    }

}
