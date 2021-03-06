package ir.sahab.nimbo.jimbo.fetcher;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static ir.sahab.nimbo.jimbo.fetcher.Validate.isEnglish;
import static org.junit.Assert.*;

public class ValidateTest {

    private static Document banSite;
    private static Document goodSite;
    private static String banSiteText;
    private static String goodSiteText;

    @Before
    public void prep() throws IOException {
        banSite = Jsoup.connect("http://www.porn.com").get();
        goodSite = Jsoup.connect("http://hbase.apache.org").get();
        banSiteText = banSite.text();
        goodSiteText = goodSite.text();
        Validate.init();
    }

    @Test
    public void isEnglishTest() {

        assertTrue(isEnglish(goodSiteText));
        Date f = new Date(System.currentTimeMillis());
        isEnglish(goodSiteText);
        Date s = new Date(System.currentTimeMillis());
        System.err.println("time of isEnglish time : " + String.valueOf(s.getTime() - f.getTime()));
        assertFalse(isEnglish("renمنشسیتبمنaksdjfl jslkfajslk jt نتشسبمنت شسنمت  شتمنسی"));
    }

    @Test
    public void banInitialListTest() {
        Validate.isValidBody(goodSite);
        assertEquals(Validate.banWords.size(), 10);
    }

    @Test
    public void isValidTest() {
        Date f = new Date(System.currentTimeMillis());
        assertFalse(Validate.isValidBody(banSite));
        Date s = new Date(System.currentTimeMillis());
        System.err.println("time of valid time : " + String.valueOf(s.getTime() - f.getTime()));
    }

    @Test
    public void notBanTest() {
        assertTrue(Validate.isNotBan(goodSite));
    }

    @Test
    public void banTest() {
        Date f = new Date(System.currentTimeMillis());
        assertFalse(Validate.isNotBan(banSite));
        Date s = new Date(System.currentTimeMillis());
        System.err.println("time of banTest time : " + String.valueOf(s.getTime() - f.getTime()));
    }
}