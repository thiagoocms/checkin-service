package com.nassau.checkinservice.validation;

import com.nassau.checkinservice.domain.ClassRoom;
import com.nassau.checkinservice.domain.User;
import com.nassau.checkinservice.exception.BadRequestException;
import com.nassau.checkinservice.repository.ClassRoomRepository;
import com.nassau.checkinservice.repository.UserRepository;
import com.nassau.checkinservice.service.AbstractMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ClassRoomValidation extends AbstractMessage {

	//**********************************************************************************
	// PRIVATE ATTRIBUTES
	//**********************************************************************************

	private final ClassRoomRepository classRoomRepository;
	private final UserRepository userRepository;

	//**********************************************************************************
	// VALIDATIONS
	//**********************************************************************************

	public void checkOwnerFieldsToCreate(ClassRoom entity) throws IOException {
		entity.setId(null);
	}

	public void checkMandatoryFields(ClassRoom entity) throws IOException {
		List<String> notInformedFieldsList = new ArrayList<>();

		if(Objects.isNull(entity.getName()) || entity.getName().isEmpty()){
			notInformedFieldsList.add("Nome");
		}
		if(Objects.isNull(entity.getUser()) || Objects.isNull(entity.getUser().getId())){
			notInformedFieldsList.add("Usuario");
		}
		if(Objects.isNull(entity.getStartDate())){
			notInformedFieldsList.add("Data e hora de inicio");
		}
		if(Objects.isNull(entity.getStartDate())){
			notInformedFieldsList.add("Data e hora de fim");
		}

		if(!notInformedFieldsList.isEmpty()){
			badRequestException("Campos obrigatórios não informados: {0}", String.join(", ", notInformedFieldsList));
		}
	}

	public ClassRoom checkUpdateConsistence(Long id, ClassRoom toUpdateEntity) throws Throwable {

		if(Objects.isNull(id) || Objects.isNull(toUpdateEntity.getId())){
			throw new BadRequestException();
		}

		if(id.compareTo(toUpdateEntity.getId()) != 0){
			throw new BadRequestException();
		}
		ClassRoom persistedEntity = checkExistClassRoom(id);

		// Valores que não podem ser Alterados
		toUpdateEntity.setCreatedBy(persistedEntity.getCreatedBy());
		toUpdateEntity.setCreatedDate(persistedEntity.getCreatedDate());

		modelMapper.map(toUpdateEntity, persistedEntity);

		return persistedEntity;
	}

	public ClassRoom checkExistClassRoom(Long id) throws Throwable {
		ClassRoom classRoom = classRoomRepository.findFirstById(id);

		if(Objects.isNull(classRoom)){
			throw resourceNotFoundException("Sala não encontrada.");
		}
		return classRoom;
	}

	public ClassRoom checkRelations(ClassRoom classRoom) throws Throwable {
		checkUser(classRoom);
		return classRoom;
	}

	private ClassRoom checkUser(ClassRoom classRoom) throws Throwable {
		if (Objects.isNull(classRoom.getUser())) {
			return classRoom;
		}

		if (Objects.isNull(classRoom.getUser().getId())) {
			classRoom.setUser(null);
			return classRoom;
		}

		User user = userRepository.findFirstById(classRoom.getUser().getId());

		if (Objects.isNull(user)) {
			throw resourceNotFoundException("Usuário não encontrado.");
		}

		classRoom.setUser(user);

		return classRoom;
	}
}