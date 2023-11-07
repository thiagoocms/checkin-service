package com.nassau.checkinservice.validation;

import com.nassau.checkinservice.domain.User;
import com.nassau.checkinservice.exception.BadRequestException;
import com.nassau.checkinservice.repository.UserRepository;
import com.nassau.checkinservice.service.AbstractMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserValidation extends AbstractMessage {

	//**********************************************************************************
	// PRIVATE ATTRIBUTES
	//**********************************************************************************

	private final UserRepository userRepository;


	//**********************************************************************************
	// VALIDATIONS
	//**********************************************************************************

	public void checkOwnerFieldsToCreate(User entity) throws IOException {
		entity.setId(null);
	}

	public void checkMandatoryFields(User entity) throws IOException {
		List<String> notInformedFieldsList = new ArrayList<>();

		if(Objects.isNull(entity.getName()) || entity.getName().isEmpty()){
			notInformedFieldsList.add("Nome");
		}
		if(Objects.isNull(entity.getDocumentNumber()) || entity.getDocumentNumber().isEmpty()){
			notInformedFieldsList.add("N° do documento");
		}
		if(Objects.isNull(entity.getDocumentType())){
			notInformedFieldsList.add("Tipo do documento");
		}
		if(Objects.isNull(entity.getPassword()) || entity.getPassword().isEmpty()){
			notInformedFieldsList.add("Senha");
		}
		if(Objects.isNull(entity.getLogin()) || entity.getLogin().isEmpty()){
			notInformedFieldsList.add("Login");
		}
		if(Objects.isNull(entity.getProfile())){
			notInformedFieldsList.add("Profile");
		}

		if(!notInformedFieldsList.isEmpty()){
			badRequestException("Campos obrigatórios não informados: {0}", String.join(", ", notInformedFieldsList));
		}

	}

	public User checkUpdateConsistence(Long id, User toUpdateEntity) throws Throwable {

		if(Objects.isNull(id) || Objects.isNull(toUpdateEntity.getId())){
			throw new BadRequestException();
		}

		if(id.compareTo(toUpdateEntity.getId()) != 0){
			throw new BadRequestException();
		}
		User persistedEntity = checkExistUser(id);

		// Valores que não podem ser Alterados
		toUpdateEntity.setCreatedBy(persistedEntity.getCreatedBy());
		toUpdateEntity.setCreatedDate(persistedEntity.getCreatedDate());

		modelMapper.map(toUpdateEntity, persistedEntity);

		return persistedEntity;
	}

	public User checkExistUser(Long id) throws Throwable {
		User user = userRepository.findFirstById(id);

		if(Objects.isNull(user)){
			throw resourceNotFoundException("Usuário não encontrado.");
		}
		return user;
	}
}