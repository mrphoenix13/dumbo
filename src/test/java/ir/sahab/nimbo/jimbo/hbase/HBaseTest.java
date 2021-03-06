package ir.sahab.nimbo.jimbo.hbase;

import ir.sahab.nimbo.jimbo.parser.Link;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class HBaseTest {


    static final String STACKOVERFLOW = "https://stackoverflow.com";
    static final String JAVA_CODE = "https://examples.javacodegeeks.com";
    static URL STACKOVERFLOWURL;
    static URL JAVA_CODE_URL;
    @BeforeClass
    public static void setUpALL() throws MalformedURLException {
        STACKOVERFLOWURL = new URL(STACKOVERFLOW);
        JAVA_CODE_URL = new URL(JAVA_CODE);
        HBase.getInstance();
    }
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getInstance() {
    }

    @Test
    public void getAndPutDataTest() throws MalformedURLException {
        ArrayList<Link> links = new ArrayList<>();
        String url = "http://www.test.com";
        String anchor = "test";
        links.add(new Link(new URL(url), anchor));
        HBase.getInstance().putData(url, links);
        byte[] res = HBase.getInstance().getData(url, "0link");
        byte[] res2 = HBase.getInstance().getData(url, "0anchor");
        assertEquals(url, new String(res));
        assertEquals(anchor, new String(res2));

    }

    @Test
    public void putAndGetMarkTest() throws MalformedURLException {
        String url = "http://www.test.com";
        HBase.getInstance().putMark(url, "test");
        byte[] res = HBase.getInstance().getMark(url, "qualif");
        assertEquals("test", new String(res));
    }

    @Test
    public void existData() {
        ArrayList<Link> links = new ArrayList<>();
        Link link = new Link(STACKOVERFLOWURL, "anchor");
        links.add(link);
        HBase.getInstance().putData(STACKOVERFLOW, links);
        assertTrue(HBase.getInstance().existData(STACKOVERFLOW));
        assertFalse(HBase.getInstance().existData(JAVA_CODE));
    }

    @Test
    public void existMark() {
        HBase.getInstance().putMark(STACKOVERFLOW, "value");
        assertTrue(HBase.getInstance().existMark(STACKOVERFLOW));
        assertFalse(HBase.getInstance().existMark(JAVA_CODE));
    }
    @Test
    public void revUrlTest() throws MalformedURLException {
        //assertEquals("https://com.google", HBase.getInstance().reverseUrl(new URL("https://google.com")));
        assertEquals("https://com.google.www", HBase.getInstance().reverseUrl(new URL("https://www.google.com")));
        assertEquals("https://com.google.dev.www", HBase.getInstance().reverseUrl(new URL("https://www.dev.google.com")));
        //assertEquals("https://com.google/test/test", HBase.getInstance().reverseUrl(new URL("https://google.com/test/test")));
        assertEquals("https://com.google.www/test/test", HBase.getInstance().reverseUrl(new URL("https://www.google.com/test/test")));
        assertEquals("https://com.google.dev.www/test/test", HBase.getInstance().reverseUrl(new URL("https://www.dev.google.com/test/test")));
    }
}