package org.example.service;

import org.example.dto.payment.response.tag.TagDTO;
import org.example.dto.payment.response.tag.TagsResponse;
import org.example.model.Tag;
import org.example.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {
    @Autowired
    TagRepository tagRepository;

    public TagsResponse getAllTags() {
        TagsResponse resp = new TagsResponse();
        List<Tag> tags = tagRepository.findAll();

        List<TagDTO> dtos = new ArrayList<>();

        for (Tag tag : tags) {
            if (!tag.isDel()) {
                TagDTO d = new TagDTO();
                d.setId(tag.getId());
                d.setName(tag.getName());
                dtos.add(d);
            }
        }
        resp.setTags(dtos);
        return resp;
    }

    public TagDTO createTag(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("name cant be empty");
        }
        Tag tag = new Tag();
        tag.setName(name);
        tag = tagRepository.saveAndFlush(tag);
        TagDTO resp = new TagDTO();
        resp.setId(tag.getId());
        resp.setName(tag.getName());
        return resp;
    }

    public void deleteTag(Long id) {
        Tag tag = tagRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        tag.setDel(true);
        tagRepository.saveAndFlush(tag);
    }
}
