package ru.tsu.hits.application_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsu.hits.application_service.dto.GroupDto;
import ru.tsu.hits.application_service.dto.converter.GroupDtoConverter;
import ru.tsu.hits.application_service.exception.GroupNotFoundException;
import ru.tsu.hits.application_service.exception.StudentAlreadyBelongsToGroup;
import ru.tsu.hits.application_service.model.GroupEntity;
import ru.tsu.hits.application_service.model.StudentEntity;
import ru.tsu.hits.application_service.repository.GroupRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final StudentService studentService;

    @Transactional
    public GroupDto createGroup(String groupNumber) {
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setGroupNumber(groupNumber);

        groupEntity = groupRepository.save(groupEntity);

        return GroupDtoConverter.convertEntityToDto(groupEntity);
    }

    @Transactional
    public GroupDto addStudent(String studentId, String groupNumber) {
        GroupEntity group = getGroupById(groupNumber);

        StudentEntity student = studentService.getStudentById(studentId);

        //check if student already belongs to any group
        if (student.getGroup() != null) {
            throw new StudentAlreadyBelongsToGroup("Student already belongs to a group");
        }

        student.setGroup(group);

        Set<StudentEntity> students = group.getStudents();
        students.add(student);

        group.setStudents(students);

        group = groupRepository.save(group);
        studentService.updateStudent(student);

        return GroupDtoConverter.convertEntityToDto(group);
    }

    @Transactional(readOnly = true)
    public GroupDto getGroup(String groupNumber) {
        return GroupDtoConverter.convertEntityToDto(getGroupById(groupNumber));
    }

    @Transactional(readOnly = true)
    public Page<GroupDto> getAllGroups(Pageable pageable) {
        return groupRepository.findAll(pageable)
                .map(GroupDtoConverter::convertEntityToDto);
    }

    @Transactional(readOnly = true)
    public void removeGroup(String groupNumber) {
        groupRepository.delete(getGroupById(groupNumber));
    }

    private GroupEntity getGroupById(String groupNumber) {
        return groupRepository.findById(groupNumber)
                .orElseThrow(() -> new GroupNotFoundException("Group with number " + groupNumber + " not found"));
    }
}