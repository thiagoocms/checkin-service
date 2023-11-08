package com.nassau.checkinservice.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.nassau.checkinservice.domain.ClassRoom;
import com.nassau.checkinservice.domain.User;
import com.nassau.checkinservice.dto.classroom.ClassRoomDTO;
import com.nassau.checkinservice.dto.classroom.ClassRoomSimpleDTO;
import com.nassau.checkinservice.dto.user.UserDTO;
import com.nassau.checkinservice.dto.user.UserFilterDTO;
import com.nassau.checkinservice.dto.user.UserSimpleDTO;
import com.nassau.checkinservice.exception.BadRequestException;
import com.nassau.checkinservice.repository.ClassRoomRepository;
import com.nassau.checkinservice.repository.UserRepository;
import com.nassau.checkinservice.search.ClassRoomSpecification;
import com.nassau.checkinservice.search.SearchCriteria;
import com.nassau.checkinservice.util.JsonParser;
import com.nassau.checkinservice.util.SearchCriteriaUtil;
import com.nassau.checkinservice.validation.ClassRoomValidation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ClassRoomService extends AbstractMessage {

    private final ClassRoomRepository classRoomRepository;
    private final ClassRoomValidation classRoomValidation;
    private final UserRepository userRepository;

    public List<ClassRoomDTO> createOrUpdateByList(List<ClassRoomDTO> dtoList) throws Throwable {
        List<ClassRoomDTO> arrayList = new ArrayList<>();
        for (ClassRoomDTO dto : dtoList) {
            if (Objects.isNull(dto.getId())) {
                arrayList.add(this.create(dto));
            } else {
                arrayList.add(this.update(dto.getId(), dto));
            }
        }
        return arrayList;
    }

    public ClassRoomDTO create(ClassRoomDTO dto) throws Throwable {
        ClassRoom classRoom = modelMapper.map(dto, ClassRoom.class);
        classRoomValidation.checkOwnerFieldsToCreate(classRoom);
        classRoomValidation.checkMandatoryFields(classRoom);
        classRoomValidation.checkRelations(classRoom);
        modelMapper.map(classRoomRepository.save(classRoom), dto);
        return dto;
    }

    public ClassRoomDTO update(Long id, ClassRoomDTO dto) throws Throwable {
        ClassRoom classRoom = modelMapper.map(dto, ClassRoom.class);
        classRoomValidation.checkUpdateConsistence(id, classRoom);
        classRoomValidation.checkMandatoryFields(classRoom);
        classRoomValidation.checkRelations(classRoom);
        modelMapper.map(classRoomRepository.save(classRoom), dto);
        return dto;
    }

    public ClassRoomDTO findById(Long id) throws Throwable {
        ClassRoom classRoom = classRoomValidation.checkExistClassRoom(id);
        return modelMapper.map(classRoom, ClassRoomDTO.class);
    }

    public Page<ClassRoomDTO> findByFilters(Pageable pageable, UserFilterDTO userFilterDTO) {
        List<SearchCriteria> criteria = SearchCriteriaUtil.buildCriteria(userFilterDTO);
        Page<ClassRoom> page = this.classRoomRepository.findAll(ClassRoomSpecification.listAllByCriteria(criteria), pageable);
        return modelMapper.map(page, new TypeToken<Page<ClassRoomDTO>>() {
        }.getType());
    }

    public ClassRoomDTO delete(Long id) throws Throwable {
        ClassRoom classRoom = classRoomValidation.checkExistClassRoom(id);
        classRoom.setDeleted(Boolean.TRUE);
        classRoomRepository.save(classRoom);
        return modelMapper.map(classRoom, ClassRoomDTO.class);
    }

    public byte[] generateQRCode(Long id) throws Throwable {
        ClassRoom classRoom = classRoomValidation.checkExistClassRoom(id);
        byte[] qrCode = new byte[0];
        try {
            ClassRoomSimpleDTO classRoomSimpleDTO = modelMapper.map(classRoom, ClassRoomSimpleDTO.class);
            classRoomSimpleDTO.setUserName(classRoom.getUser().getName());
            classRoomSimpleDTO.setUserId(classRoom.getUser().getId());
            String data = JsonParser.toJson(classRoomSimpleDTO);
            int width = 300;
            int height = 300;
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            BitMatrix bitMatrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, width, height, hints);

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? 0x000000 : 0xFFFFFF);
                }
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", outputStream);

            qrCode = outputStream.toByteArray();
            classRoom.setQrCode(Base64.getEncoder().encodeToString(qrCode));
            classRoomRepository.save(classRoom);
        } catch (IOException | WriterException e) {
            e.printStackTrace();
            throw new BadRequestException("Não foi possivel gerar o qrCode");
        }
        return qrCode;
    }

    public List<UserSimpleDTO> findCheck(Long id) {
        List<User> users = userRepository.findAllByCheck(id);
        return modelMapper.map(users, new TypeToken<List<UserSimpleDTO>>() {
        }.getType());
    }
}