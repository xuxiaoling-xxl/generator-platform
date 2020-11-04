package com.digitalgd.tog.gen.modular.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.digitalgd.tog.gen.modular.model.TemplateInfo;

/**
 * Created by stephan on 20.03.16.
 */
public interface TemplateInfoRepository extends JpaRepository<TemplateInfo, String> {

	@Modifying
	@Query(value="SELECT * FROM template_info  WHERE status = 0",nativeQuery=true)
	List<TemplateInfo> findUseList();

}
