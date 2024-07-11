import com.sensilabs.projecthub.project.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TechnologyServiceTest {
    TechnologyRepository technologyRepository = new TechnologyRepositoryMock();
    TechnologyService technologyService = new TechnologyServiceImpl(technologyRepository);
    @Test
    void createTechnologyTest(){
        Technology technology = technologyService.create(new CreateTechnologyForm("Java", "Język Java"));

        Assertions.assertEquals(technology.getDescription(), "Język Java");
        Assertions.assertEquals(technology.getName(), "Java");
    }

    @Test
    void findAllTechnologyTest(){
        Technology technology1 = technologyService.create(new CreateTechnologyForm("Java", "Język Java"));
        Technology technology2 = technologyService.create(new CreateTechnologyForm("C++", "Język C++"));
        Technology technology3 = technologyService.create(new CreateTechnologyForm("Python", "Język Python"));

        List<Technology> technologies = technologyService.findAll();

        Assertions.assertEquals(3, technologies.size());
        Assertions.assertTrue(technologies.contains(technology1));
        Assertions.assertTrue(technologies.contains(technology2));
        Assertions.assertTrue(technologies.contains(technology3));

    }
}
