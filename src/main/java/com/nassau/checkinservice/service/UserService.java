package com.nassau.checkinservice.service;

import com.nassau.checkinservice.domain.Checkin;
import com.nassau.checkinservice.domain.ClassRoom;
import com.nassau.checkinservice.domain.User;
import com.nassau.checkinservice.dto.checkin.CheckinDTO;
import com.nassau.checkinservice.dto.user.UserDTO;
import com.nassau.checkinservice.dto.user.UserFilterDTO;
import com.nassau.checkinservice.dto.user.UserLoginDTO;
import com.nassau.checkinservice.exception.BadRequestException;
import com.nassau.checkinservice.exception.ResourceNotFoundException;
import com.nassau.checkinservice.repository.CheckinRepository;
import com.nassau.checkinservice.repository.ClassRoomRepository;
import com.nassau.checkinservice.repository.UserRepository;
import com.nassau.checkinservice.search.SearchCriteria;
import com.nassau.checkinservice.search.UserSpecification;
import com.nassau.checkinservice.util.SearchCriteriaUtil;
import com.nassau.checkinservice.validation.UserValidation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService extends AbstractMessage {

    private final UserRepository userRepository;
    private final UserValidation userValidation;
    private final CheckinRepository checkinRepository;
    private final ClassRoomRepository classRoomRepository;

    public List<UserDTO> createOrUpdateByList(List<UserDTO> dtoList) throws Throwable {
        List<UserDTO> userArrayList = new ArrayList<>();
        for (UserDTO dto : dtoList) {
            if (Objects.isNull(dto.getId())) {
                userArrayList.add(this.create(dto));
            } else {
                userArrayList.add(this.update(dto.getId(), dto));
            }
        }
        return userArrayList;
    }

    public UserDTO create(UserDTO dto) throws IOException {
        User user = modelMapper.map(dto, User.class);
        userValidation.checkOwnerFieldsToCreate(user);
        userValidation.checkMandatoryFields(user);
        modelMapper.map(userRepository.save(user), dto);
        return dto;
    }

    public UserDTO update(Long id, UserDTO dto) throws Throwable {
        User user = modelMapper.map(dto, User.class);
        userValidation.checkUpdateConsistence(id, user);
        userValidation.checkMandatoryFields(user);
        modelMapper.map(userRepository.save(user), dto);
        return dto;
    }

    public UserDTO findById(Long id) throws Throwable {
        User user = userValidation.checkExistUser(id);
        return modelMapper.map(user, UserDTO.class);
    }

    public Page<UserDTO> findByFilters(Pageable pageable, UserFilterDTO userFilterDTO) {
        List<SearchCriteria> criteria = SearchCriteriaUtil.buildCriteria(userFilterDTO);
        Page<User> page = this.userRepository.findAll(UserSpecification.listAllByCriteria(criteria), pageable);
        return modelMapper.map(page, new TypeToken<Page<UserDTO>>() {
        }.getType());
    }

    public UserDTO delete(Long id) throws Throwable {
        User user = userValidation.checkExistUser(id);
        user.setDeleted(Boolean.TRUE);
        userRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }

    public UserDTO login(UserLoginDTO dto) throws Throwable {
        if ((Objects.isNull(dto.getLogin()) || dto.getLogin().isEmpty()) || (Objects.isNull(dto.getPassword()) || dto.getPassword().isEmpty())) {
            throw new BadRequestException("Todos os campos são obrigatórios.");
        }
        User user = userRepository.findFirstByLoginIgnoreCaseAndPasswordAndDeletedIsFalse(dto.getLogin(), dto.getPassword());
        if (Objects.isNull(user)) {
            throw new ResourceNotFoundException("Usuário não encontrado.");
        }
        return modelMapper.map(user, UserDTO.class);
    }

    public CheckinDTO check(Long id, Long classRoomId) throws Throwable {
        User user = userValidation.checkExistUser(id);
        ClassRoom classRoom = classRoomRepository.findFirstById(classRoomId);
        if (classRoom == null) {
            throw new ResourceNotFoundException("Sala não encontrada.");
        }
        Checkin checkin = checkinRepository.save(new Checkin(user, classRoom));
        return modelMapper.map(checkin, CheckinDTO.class);
    }
}