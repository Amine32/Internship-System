package ru.tsu.hits.userservice.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.userservice.dto.CreateUpdateGroupDto;
import ru.tsu.hits.userservice.dto.GroupDto;
import ru.tsu.hits.userservice.service.GroupService;

import java.util.Optional;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
@Tag(name = "Group API")
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public GroupDto createGroup(@RequestBody CreateUpdateGroupDto dto) {
        return groupService.createGroup(dto.getGroupNumber());
    }

    @GetMapping
    public Page<GroupDto> getAllGroups(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size,
            @RequestParam Optional<String> sort) {
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(25), Sort.by(sort.orElse("groupNumber")));
        return groupService.getAllGroups(pageable);
    }

    @GetMapping("/{groupNumber}")
    public GroupDto getGroup(@PathVariable String groupNumber) {
        return groupService.getGroup(groupNumber);
    }

    @DeleteMapping("/{groupNumber}")
    public void removeGroup(@PathVariable String groupNumber) {
        groupService.removeGroup(groupNumber);
    }


    @PostMapping("/{groupNumber}/{studentId}")
    public GroupDto addStudent(@PathVariable String groupNumber, @PathVariable String studentId) {
        return groupService.addStudent(studentId, groupNumber);
    }
}
