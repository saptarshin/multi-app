package com.sample.reporting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sample.reporting.model.ContentToSave;

@Repository
public interface ContentToSaveRepository extends JpaRepository<ContentToSave, Long> {

}
