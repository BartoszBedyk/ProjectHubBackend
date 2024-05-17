package com.sensilabs.projecthub.resource;

import com.sensilabs.projecthub.resources.ResourceService;
import com.sensilabs.projecthub.resources.ResourceServiceImpl;
import com.sensilabs.projecthub.resources.forms.*;
import com.sensilabs.projecthub.resources.model.Resource;
import com.sensilabs.projecthub.resources.model.ResourceType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

public class ResourceServiceTest {

    ResourceRepositoryMock resourceRepository = new ResourceRepositoryMock();
    ResourceService resourceService = new ResourceServiceImpl(resourceRepository);

    @Test
    void createAttachmentResourceTest() throws InterruptedException {

        Instant beforeDate = Instant.now();
        Thread.sleep(10);

        CreateAttachmentResourceForm form = new CreateAttachmentResourceForm(
                "Zalacznik1",
                "Opis1",
                "zalacznik/czas/zalacznik"
        );
        String environmentId = UUID.randomUUID().toString();
        String projectId = UUID.randomUUID().toString();
        String createdById = UUID.randomUUID().toString();
        Resource resource = resourceService.save(form, environmentId, projectId, createdById);

        Instant afterDate = Instant.now();
        Thread.sleep(10);

        Assertions.assertEquals(resource.getCreatedById(), createdById);
        Assertions.assertEquals(resource.getProjectId(), projectId);
        Assertions.assertEquals(resource.getEnvironmentId(), environmentId);

        Assertions.assertEquals(resource.getName(), "Zalacznik1");
        Assertions.assertEquals(resource.getDescription(), "Opis1");
        Assertions.assertEquals(resource.getValue(), "zalacznik/czas/zalacznik");

        Assertions.assertTrue(resource.getCreatedOn().isAfter(beforeDate) && resource.getCreatedOn().isBefore(afterDate));

        Assertions.assertEquals(resource.getResourceType(), ResourceType.ATTACHMENT);


    }

    @Test
    void createLinkResourceTest() throws InterruptedException {

        Instant beforeDate = Instant.now();
        Thread.sleep(10);

        CreateLinkResourceForm form = new CreateLinkResourceForm(
                "Zalacznik2",
                "Link do strony producenta najlepszych telefonów na świecie.",
                "https://store.google.com/product/pixel_8?hl=pl&pli=1"
        );
        String environmentId = UUID.randomUUID().toString();
        String projectId = UUID.randomUUID().toString();
        String createdById = UUID.randomUUID().toString();
        Resource resource = resourceService.save(form, environmentId, projectId, createdById);

        Instant afterDate = Instant.now();
        Thread.sleep(10);

        Assertions.assertEquals(resource.getCreatedById(), createdById);
        Assertions.assertEquals(resource.getProjectId(), projectId);
        Assertions.assertEquals(resource.getEnvironmentId(), environmentId);

        Assertions.assertEquals(resource.getName(), "Zalacznik2");
        Assertions.assertEquals(resource.getDescription(), "Link do strony producenta najlepszych telefonów na świecie.");
        Assertions.assertEquals(resource.getValue(), "https://store.google.com/product/pixel_8?hl=pl&pli=1");

        Assertions.assertTrue(resource.getCreatedOn().isAfter(beforeDate) && resource.getCreatedOn().isBefore(afterDate));

        Assertions.assertEquals(resource.getResourceType(), ResourceType.LINK);


    }

    @Test
    void createSecretResourceTest() throws InterruptedException {

        Instant beforeDate = Instant.now();
        Thread.sleep(10);
        String description = "D4C is among the most powerful Stands in the series. In its default form," +
                " it is a close-range Stand with above-average strength and speed." +
                " It is able to punch a grown man away violently ,With its most impressive" +
                " showing of strength being when D4C came out of Hot Pants' side and impaled her." +
                " D4C was also capable of rivalling a transformed Diego in speed.";

        CreateSecretResourceForm form = new CreateSecretResourceForm(
                "Zalacznik3",
                description,
                "Dirty Deeds Done Dirt Cheap"
        );
        String environmentId = UUID.randomUUID().toString();
        String projectId = UUID.randomUUID().toString();
        String createdById = UUID.randomUUID().toString();
        Resource resource = resourceService.save(form, environmentId, projectId, createdById);

        Instant afterDate = Instant.now();
        Thread.sleep(10);

        Assertions.assertEquals(resource.getCreatedById(), createdById);
        Assertions.assertEquals(resource.getProjectId(), projectId);
        Assertions.assertEquals(resource.getEnvironmentId(), environmentId);

        Assertions.assertEquals(resource.getName(), "Zalacznik3");
        Assertions.assertEquals(resource.getDescription(), description);
        Assertions.assertEquals(resource.getValue(), "Dirty Deeds Done Dirt Cheap");

        Assertions.assertTrue(resource.getCreatedOn().isAfter(beforeDate) && resource.getCreatedOn().isBefore(afterDate));

        Assertions.assertEquals(resource.getResourceType(), ResourceType.SECRET);


    }

    @Test
    void createTextResourceTest() throws InterruptedException {

        Instant beforeDate = Instant.now();
        Thread.sleep(10);

        String text = "Long Felagund watched them, and love for them stirred in his heart;" +
                " buthe remained hidden in the trees until they had all fallen asleep." +
                " Then hewent among the sleeping people, and sat beside their dying fire where nonekept watch;" +
                "and he took up a rude harp which B?or had laid aside," +
                " and heplayed music upon it such as the ears of Men had not heard; for they had asyet no teachers in the art," +
                " save only the Dark Elves in the wild lands.Now men awoke and listened to Felagund as he harped and sang," +
                " andeach thought that he was in some fair dream, until he saw that his fellowswere awake also beside him;" +
                " but they did not speak or stir while Felagundstill played, because of the beauty of the music and the wonder of the song." +
                "Wisdom was in the words of the Elven-king, and the hearts grew wiser thathearkened to him; for the things of which he sang," +
                "of the making of Arda,and the bliss of Aman beyond the shadows of the Sea, came as clear visions\n" +
                "before their eyes, and  his  Elvish speech was  interpreted in  each mindaccording to its measure";

        CreateTextResourceForm form = new CreateTextResourceForm(
                "Zalacznik4",
                "No taka sobie książka Tolkiena polecam bardzo.",
                text
        );
        String environmentId = UUID.randomUUID().toString();
        String projectId = UUID.randomUUID().toString();
        String createdById = UUID.randomUUID().toString();
        Resource resource = resourceService.save(form, environmentId, projectId, createdById);

        Instant afterDate = Instant.now();
        Thread.sleep(10);

        Assertions.assertEquals(resource.getCreatedById(), createdById);
        Assertions.assertEquals(resource.getProjectId(), projectId);
        Assertions.assertEquals(resource.getEnvironmentId(), environmentId);

        Assertions.assertEquals(resource.getName(), "Zalacznik4");
        Assertions.assertEquals(resource.getDescription(), "No taka sobie książka Tolkiena polecam bardzo.");
        Assertions.assertEquals(resource.getValue(), text);

        Assertions.assertTrue(resource.getCreatedOn().isAfter(beforeDate) && resource.getCreatedOn().isBefore(afterDate));

        Assertions.assertEquals(resource.getResourceType(), ResourceType.TEXT);


    }

    @Test
    void updateResourceTest() throws InterruptedException {

        Instant beforeDate = Instant.now();
        Thread.sleep(10);



        CreateAttachmentResourceForm form = new CreateAttachmentResourceForm(
                "Zalacznik1",
                "Opis1",
                "zalacznik/czas/zalacznik"
        );
        String environmentId = UUID.randomUUID().toString();
        String projectId = UUID.randomUUID().toString();
        String createdById = UUID.randomUUID().toString();
        Resource resource = resourceService.save(form, environmentId, projectId, createdById);

        Instant afterDate = Instant.now();
        Thread.sleep(100);

        Assertions.assertEquals(resource.getCreatedById(), createdById);
        Assertions.assertEquals(resource.getProjectId(), projectId);
        Assertions.assertEquals(resource.getEnvironmentId(), environmentId);

        Assertions.assertEquals(resource.getName(), "Zalacznik1");
        Assertions.assertEquals(resource.getDescription(), "Opis1");
        Assertions.assertEquals(resource.getValue(), "zalacznik/czas/zalacznik");

        Assertions.assertTrue(resource.getCreatedOn().isAfter(beforeDate));
        Assertions.assertTrue(resource.getCreatedOn().isBefore(afterDate));

        Assertions.assertEquals(resource.getResourceType(), ResourceType.ATTACHMENT);

        Instant beforeUpdate = Instant.now();
        Thread.sleep(1000);

        UpdateResourceForm updateForm = new UpdateResourceForm(resource.getId().toString(), "UpdatedName", "UpdatedDescription", "UpdatedValue");

        Resource updated = resourceService.update(updateForm);
        Thread.sleep(1000);
        Instant afterUpdate = Instant.now();





        Assertions.assertEquals(updated.getCreatedById(), createdById);
        Assertions.assertEquals(updated.getProjectId(), projectId);
        Assertions.assertEquals(updated.getEnvironmentId(), environmentId);

        Assertions.assertEquals(updated.getName(), "UpdatedName");
        Assertions.assertEquals(updated.getDescription(), "UpdatedDescription");
        Assertions.assertEquals(updated.getValue(), "UpdatedValue");

        Assertions.assertTrue(updated.getLastModifiedOn().isAfter(beforeUpdate));
        Assertions.assertTrue(updated.getLastModifiedOn().isBefore(afterUpdate));

        Assertions.assertEquals(updated.getResourceType(), ResourceType.ATTACHMENT);


    }


}
