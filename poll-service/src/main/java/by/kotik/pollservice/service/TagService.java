package by.kotik.pollservice.service;

import by.kotik.pollservice.entity.Tag;
import by.kotik.pollservice.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    @Transactional(readOnly = true)
    public Optional<Tag> findByName(String name) {
        return tagRepository.findByName(name);
    }

    @Transactional
    public Tag findByNameOrCreateNew(String name) {
        return tagRepository.findByName(name).orElseGet(()->{
                Tag tag = new Tag();
                tag.setName(name);
                return tagRepository.save(tag);
        });
    }

    @Transactional
    public Tag save(String name) {
        Tag tag = new Tag();
        tag.setName(name);
        return tagRepository.save(tag);
    }
}
