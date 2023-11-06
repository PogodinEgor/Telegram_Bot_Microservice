package ru.relex.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.relex.entity.BinaryContent;
@Repository
public interface BinaryContentDAO extends JpaRepository<BinaryContent,Long> {
}
