package com.sn.manageservice.repostory;

import com.sn.manageservice.pojo.Animal;
import com.sn.manageservice.pojo.Relation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 向南
 * @date 2021/12/28 11:14
 * @description
 */
@Repository
public interface RelationRepository extends JpaRepository<Relation, Integer> {

    List<Relation> findRelationByAccountIdAndAnimalId(Integer accountId,Integer animalId);

    List<Relation> findRelationByAnimalId(Integer animalId);

    List<Relation> findRelationByAccountId(Integer accountId);
}
