package com.sensilabs.projecthub.activity;

import com.sensilabs.projecthub.activity.model.Activity;
import com.sensilabs.projecthub.commons.*;
import com.sensilabs.projecthub.utils.SearchSpecification;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.sensilabs.projecthub.activity.ActivityMapper.toActivityParam;

@Component
public class ActivityRepositoryAdapter implements ActivityRepository{

    private final ActivityRepositoryJpa activityRepository;
    private final ActivityParamRepositoryJpa activityParamRepository;

    public ActivityRepositoryAdapter(ActivityRepositoryJpa activityRepository, ActivityParamRepositoryJpa activityParamRepository)
    {
        this.activityRepository = activityRepository;
        this.activityParamRepository = activityParamRepository;
    }
    @Override
    public Activity save(Activity activity) {
        ActivityEntity activityEntity = ActivityMapper.toActivityEntity(activity);
        activityRepository.save(activityEntity);
        List<ActivityParamEntity> activityParamEntities =
                activity.getParams().stream().map(activityParam -> toActivityParam(activityParam, activityEntity)).toList();
        activityParamRepository.saveAll(activityParamEntities);
        activityEntity.setParams(activityParamEntities);
        return ActivityMapper.toActivity(activityEntity);
    }

    @Override
    public SearchResponse<Activity> search(SearchForm searchForm) {
        Specification<ActivityEntity> specification = SearchSpecification.buildSpecification(searchForm.getCriteria());
        Page<ActivityEntity> activityPage = activityRepository.findAll(specification, SearchSpecification.getPageRequest(searchForm));
        return SearchResponse.<Activity>builder()
                .items(activityPage.getContent().stream()
                        .map(ActivityMapper::toActivity)
                        .collect(Collectors.toList()))
                .total(activityPage.getTotalElements())
                .build();
    }
}
