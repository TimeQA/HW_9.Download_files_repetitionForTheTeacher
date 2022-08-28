package com.exemple;

import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Selenide;
import com.codeborne.xlstest.XLS;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.InputStream;

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
    }
}
