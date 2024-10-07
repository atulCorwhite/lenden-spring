package com.learn.learnSpring.repository;

import com.learn.learnSpring.entities.GroupMembers;
import com.learn.learnSpring.entities.GroupModal;
import com.learn.learnSpring.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<GroupModal,Long> {

    Optional<GroupModal> findById(long id);

    // This is JPA
    List<GroupModal> findByCreatedBy(Long userId);


    // This is JPA Query
    @Query("SELECT gm FROM GroupModal gm WHERE gm.createdBy = :userId")
    List<GroupModal> getGroupListByUserId(Long userId);


    //This is native sql query
    @Query(value = "SELECT gm.* FROM group_modal gm WHERE gm.created_by = :userId", nativeQuery = true)
    List<GroupModal> getGroupList(Long userId);

}
