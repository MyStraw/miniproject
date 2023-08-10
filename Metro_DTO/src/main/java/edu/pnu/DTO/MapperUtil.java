package edu.pnu.DTO;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MapperUtil {

	@Autowired
	private ModelMapper modelMapper;

	public <D, T> D convertToDTO(T entity, Class<D> outClass) {
		return modelMapper.map(entity, outClass);
	}

	public <D, T> T convertToEntity(D dto, Class<T> outClass) {
		return modelMapper.map(dto, outClass);
	}
}
