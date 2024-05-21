package com.sensilabs.projecthub.project;

import com.sensilabs.projecthub.commons.ApplicationException;
import com.sensilabs.projecthub.commons.ErrorCode;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Service
public class ProjectMemberServiceImpl implements ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRepository projectRepository;

    public ProjectMemberServiceImpl(ProjectMemberRepository projectMemberRepository, ProjectRepository projectRepository) {
        this.projectMemberRepository = projectMemberRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public ProjectMember save(CreateProjectMemberForm createProjectMemberForm, String createdById) {
        if (projectRepository.findById(createProjectMemberForm.getProjectId()).isPresent()) {
            ProjectMember projectMember = ProjectMember.builder()
                    .role(createProjectMemberForm.getRole())
                    .firstName(createProjectMemberForm.getFirstName())
                    .lastName(createProjectMemberForm.getLastName())
                    .createdById(createdById)
                    .createdOn(Instant.now())
                    .projectId(createProjectMemberForm.getProjectId())
                    .userId(createProjectMemberForm.getUserId())
                    .build();
            return projectMemberRepository.save(projectMember);
        } else {
            throw new ApplicationException(ErrorCode.PROJECT_NOT_FOUND);
        }
    }

    @Override
    public ProjectMember update(UpdateProjectMemberForm updateProjectMemberForm) {
        ProjectMember existingMember = getOrThrow(updateProjectMemberForm.getUserId(), updateProjectMemberForm.getProjectId());
        existingMember.setRole(updateProjectMemberForm.getRole());
        return projectMemberRepository.save(existingMember);
    }

    @Override
    public void remove(String userId, String projectId) {
        ProjectMember existingMember = getOrThrow(userId, projectId);
        projectMemberRepository.delete(existingMember);
    }

    @Override
    public List<ProjectDTO> getProjects(String userId) {
        List<Object[]> results = projectMemberRepository.getProjects(userId);

        Map<String, ProjectDTO> projectMap = new HashMap<>();

        for (Object[] result : results) {
            String projectId = (String) result[0];
            String projectName = (String) result[1];
            String projectDescription = (String) result[2];
            Instant projectCreatedOn = ((Timestamp) result[3]).toInstant();
            String projectCreatedById = (String) result[4];
            String technologyId = (String) result[5];
            String technologyName = (String) result[6];
            String technologyDescription = (String) result[7];

            TechnologyDTO technologyDTO = TechnologyDTO.builder()
                    .technologyId(technologyId)
                    .technologyName(technologyName)
                    .technologyDescription(technologyDescription)
                    .build();

            ProjectDTO projectDTO = projectMap.computeIfAbsent(projectId, id -> ProjectDTO.builder()
                    .projectId(id)
                    .projectName(projectName)
                    .projectDescription(projectDescription)
                    .projectCreatedOn(projectCreatedOn)
                    .projectCreatedById(projectCreatedById)
                    .projectTechnologies(new ArrayList<>())
                    .build());

            projectDTO.getProjectTechnologies().add(technologyDTO);
        }
        return new ArrayList<>(projectMap.values());
    }

    @Override
    public ProjectMember getById(String userId, String projectId) {
        return getOrThrow(userId, projectId);
    }

    private ProjectMember getOrThrow(String userId, String projectId) {
        return projectMemberRepository.findById(userId, projectId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.PROJECT_MEMBER_NOT_FOUND));
    }
}
