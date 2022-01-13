package com.sn.manageservice.repostory;

import com.sn.manageservice.pojo.Account;
import com.sn.manageservice.pojo.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 向南
 * @date 2021/12/28 11:14
 * @description
 */
@Repository
public interface AnimalRepository extends JpaRepository<Animal, Integer> {

    List<Animal> findAnimalByAnimalName(String animalName);
}
