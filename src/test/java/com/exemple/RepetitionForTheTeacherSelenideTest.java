package com.exemple;

import com.codeborne.selenide.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static java.nio.charset.StandardCharsets.*;
import static org.assertj.core.api.Assertions.assertThat;

public class RepetitionForTheTeacherSelenideTest {

    @Test
    void downloadTest() throws Exception {
        Selenide.open("https://github.com/junit-team/junit5/blob/main/README.md");
        File file = $("#raw-url").download();
        try (InputStream is = new FileInputStream(file)) {
            byte[] fileContent = is.readAllBytes();
            assertThat(new String(fileContent, UTF_8)).contains("Contributions to JUnit 5");
        }
    }

    @Test
    void uploadTest() {
        Selenide.open("https://fineuploader.com/demos.html");
        $("input[type=\"file\"]").uploadFromClasspath("CryptoProPDF_UserGuide.pdf");
        $$("div.qq-dialog-message-selector").
                findBy(Condition.text("CryptoProPDF_UserGuide.pdf has an invalid extension. " +
                        "Valid extension(s): jpeg, jpg, gif, png.")).
                shouldBe(visible);
    }
}
