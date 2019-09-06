package com.togest.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.togest.common.util.CollectionUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.domain.FileBlobDTO;
import com.togest.file.client.FileClient;

/**
 * <p>Title: FileUtils.java</p>
 * <p>Description: 获取批量文件信息</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.togest.com/</p>
 * @author 江政昌
 * @date 2018年12月11日下午5:43:18
 * @version 1.0
 */
@Component
public class FileUtils<T extends Data> {
	@Autowired
	private FileClient fileClient;
	
	public void getFileBlobDTOByIds(T entity) {
		getFileBlobDTOByIds(entity, 0);
	}
	
	public void getFileBlobDTOByIds(T entity, int status) {
		if(StringUtil.isNotBlank(entity.getFileId())) {
			List<FileBlobDTO> files = new ArrayList<>();
			if(StringUtil.isNotBlank(entity.getFileName())) {
				FileBlobDTO fileBlobDTO = new FileBlobDTO();
				fileBlobDTO.setId(entity.getFileId());
				fileBlobDTO.setFileName(entity.getFileName());
				files.add(fileBlobDTO );
			}else {
				files = fileClient.getByIds(Arrays.asList(entity.getFileId().split(","))).getData();
				if(!CollectionUtils.isEmpty(files)) {
					if(status == 0) {
						files.forEach(x ->{
							x.setFileName(x.getRealName());
						});
					}else {
						entity.setFileId(String.join(",",  files.stream().map(FileBlobDTO::getId).collect(Collectors.toList())));
						entity.setFileName(String.join(",",  files.stream().map(FileBlobDTO::getRealName).collect(Collectors.toList())));
					}
				}
			}
			entity.setFiles(files);
		}
	}
}
