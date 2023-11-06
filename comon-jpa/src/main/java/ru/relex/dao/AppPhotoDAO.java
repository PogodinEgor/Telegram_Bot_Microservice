package ru.relex.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.relex.entity.AppPhoto;
@Repository
public interface AppPhotoDAO extends JpaRepository<AppPhoto,Long> {
}
