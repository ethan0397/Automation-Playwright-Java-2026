package com.company.automation.pages;

import java.nio.file.Path;
import com.company.automation.config.Environment;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.SelectOption;

public class ContactPage extends BasePage {

    private final String fileInput = "#attachment";
    private Locator successAlert;

    public ContactPage(Page page) {
        super(page);
        this.successAlert = page.locator("div[role='alert']");
    }

    public void open() {
        page.navigate(Environment.getBaseUrl() + Environment.getContactPath());
    }

    public void enterFname(String firstname) {
        page.locator("#first_name").fill(firstname);
    }

    public void enterLname(String lastname) {
        page.locator("#last_name").fill(lastname);
    }

    public void enterEmail(String email) {
        page.locator("#email").fill(email);
    }

    // Dropdown
    public void selectSubjectByIndex(int index) {
        page.locator("#subject").selectOption(
                new SelectOption().setIndex(index));
    }
     
    public void enterMessage(String msg) {
       page.locator("#message").fill(msg);
    }

    // ----- Upload File -------//

    // ✅ Upload file – Page chỉ nhận Path
    public void uploadFile(Path file) {
        page.setInputFiles(fileInput, file);
    }

    // ✅ Getter để test assert
    public String getUploadedFileName() {
        return (String) page.locator(fileInput)
                .evaluate("el => el.files[0].name");
    }

    public void clickSend() {
        page.locator("input.btnSubmit").scrollIntoViewIfNeeded();
        page.locator("input.btnSubmit").click();
    }

     public String getSuccessAlertText() {
        successAlert.waitFor();
        return successAlert.innerText().replaceAll("\\s+", " ").trim();
    }

}
