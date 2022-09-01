package com.exemple;

import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Selenide;
import com.codeborne.xlstest.XLS;
import com.exemple.domain.Teacher;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;
import static java.nio.charset.StandardCharsets.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.codeborne.selenide.Selenide.$;

public class RepetitionForTheTeacherFileParseTest {

    ClassLoader classLoader = RepetitionForTheTeacherFileParseTest.class.getClassLoader();

    @Test
    void pdfTest() throws Exception {
        Selenide.open("https://www.cryptopro.ru/support/docs");
        File pdfFile = $("a[href*='/csp40/csp1lic.pdf']").download();
        PDF pdf = new PDF(pdfFile);
        assertThat(pdf.numberOfPages).isEqualTo(109);
    }

    @Test
    void xlsTest() throws Exception {
        Selenide.open("https://www.66.rospotrebnadzor.ru/news-all/-/asset_publisher/3Bmy/document/id/126552;jsessionid=10B16845739259290F081B576EEE00E3?redirect=http%3A%2F%2Fwww.66.rospotrebnadzor.ru%2Fnews-all%3Bjsessionid%3D10B16845739259290F081B576EEE00E3%3Fp_p_id%3D101_INSTANCE_3Bmy%26p_p_lifecycle%3D0%26p_p_state%3Dnormal%26p_p_mode%3Dview%26p_p_col_id%3Dcolumn-1%26p_p_col_pos%3D1%26p_p_col_count%3D2%26_101_INSTANCE_3Bmy_advancedSearch%3Dfalse%26_101_INSTANCE_3Bmy_keywords%3D%26_101_INSTANCE_3Bmy_delta%3D15%26_101_INSTANCE_3Bmy_cur%3D29%26_101_INSTANCE_3Bmy_andOperator%3Dtrue");
        File xslFile = $("a[href*='/laboratorii_1.xls'").download();
        XLS xls = new XLS(xslFile);
        assertThat(
                xls.excel.getSheetAt(0)
                        .getRow(2)
                        .getCell(2)
                        .getStringCellValue()
        ).isEqualTo("Наименование юр.лица");
    }

    @Test
    void csvTest() throws Exception {
        InputStream is = classLoader.getResourceAsStream("Пример импорта.csv");
        CSVReader csvReader = new CSVReader(new InputStreamReader(is, UTF_8));
        List<String[]> csv = csvReader.readAll();
        assertThat(csv).contains(
                new String[] {"1;Куриный бульон по-бургундски;"},
                new String[] {"2;Вафли из печеньев слоеные с тестом;"},
                new String[] {"3;Суп;"},
                new String[] {"4;Вареник;"}
        );

    }

    @Test
    void zipTest() throws Exception {
        InputStream is = classLoader.getResourceAsStream("simpleTextFileIntoArchive.zip");
        ZipInputStream zis = new ZipInputStream(is);
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            assertThat(entry.getName()).isEqualTo("simpleTextFile.txt");
        }
    }

    @Test
    void jsonTest() {
        InputStream is = classLoader.getResourceAsStream("teacher.json");
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(new InputStreamReader(is), JsonObject.class);
        assertThat(jsonObject.get("name").getAsString()).isEqualTo("Dmitrii");
        assertThat(jsonObject.get("isGoodTeacher").getAsBoolean()).isEqualTo(true);
    }

    @Test
    void jsonTestNG() {
        InputStream is = classLoader.getResourceAsStream("teacher.json");
        Gson gson = new Gson();
        Teacher jsonObject = gson.fromJson(new InputStreamReader(is), Teacher.class);
        assertThat(jsonObject.getName()).isEqualTo("Dmitrii");
        assertThat(jsonObject.isGoodTeacher()).isEqualTo(true);
        assertThat(jsonObject.getPassport().getNumber()).isEqualTo(1234);
    }
}
