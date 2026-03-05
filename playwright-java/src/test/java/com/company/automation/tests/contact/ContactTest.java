package com.company.automation.tests.contact;

import com.company.automation.pages.ContactPage;
import com.company.automation.tests.BaseTest;
import org.junit.jupiter.api.Test;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import static org.assertj.core.api.Assertions.assertThat;

public class ContactTest extends BaseTest {

        @Test
        void shouldSubmitContactFormWithAttachment() throws Exception {

                ContactPage contactPage = new ContactPage(page);

                // Navigate to contact page
                contactPage.open();

                // Enter data
                contactPage.enterFname("Van");
                contactPage.enterLname("Nguyen");
                contactPage.enterEmail("van.nguyen@test.com");
                contactPage.selectSubjectByIndex(1);
                contactPage.enterMessage(
                                "Practice Black Box Testing & Bug Hunting Practice Black Box Testing & Bug Hunting");
                // Upload File
                Path fileToUpload = Paths.get(
                        Objects.requireNonNull(
                                getClass()
                                .getClassLoader()
                                .getResource("data/test123.txt").toURI(),
                                         "❌ Cannot find resource: data/test123.txt"));
                contactPage.uploadFile(fileToUpload);

                assertThat(contactPage.getUploadedFileName())
                        .isEqualTo("test123.txt");

                // Submit 
                contactPage.clickSend();

                // Verify submit successfully
                assertThat(contactPage.getSuccessAlertText())
                        .contains("Thanks for your message! We will contact you shortly.");

        }

}
