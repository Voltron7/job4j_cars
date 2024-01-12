package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Owner;
import ru.job4j.cars.repository.OwnerRepository;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleOwnerService implements OwnerService {
    private final OwnerRepository ownerRepository;

    @Override
    public Owner save(Owner owner) {
        return ownerRepository.save(owner);
    }

    @Override
    public boolean deleteById(int id) {
        return ownerRepository.deleteById(id);
    }

    @Override
    public boolean update(Owner owner) {
        return ownerRepository.update(owner);
    }

    @Override
    public Optional<Owner> findById(int id) {
        return ownerRepository.findById(id);
    }

    @Override
    public List<Owner> findAll() {
        return ownerRepository.findAll();
    }
}
