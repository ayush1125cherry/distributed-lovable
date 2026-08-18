package com.ayushrawat.distributed_lovable.workspace_service.service;
import com.ayushrawat.distributed_lovable.common_lib.dto.FileTreeDto;
import com.ayushrawat.distributed_lovable.workspace_service.dto.project.FileContentResponse;
import org.springframework.stereotype.Service;

@Service
public interface ProjectFileService {

    FileTreeDto getFileTree(Long projectId);


    String getFileContent(Long projectId, String path);


    void saveFile(Long projectId, String filePath, String fileContent);
}
