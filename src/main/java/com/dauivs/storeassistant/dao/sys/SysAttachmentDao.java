package com.dauivs.storeassistant.dao.sys;

import com.dauivs.storeassistant.model.sys.SysAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysAttachmentDao extends JpaRepository<SysAttachment, Integer> {

    SysAttachment findByPath(String path);
}
