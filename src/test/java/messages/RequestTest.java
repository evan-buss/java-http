package messages;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class RequestTest {

  private Request request = new Request();

  // @BeforeClass
  // public static void init() {
  //   System.out.println("Before Init Function Ran");
  // }

  @Test
  public void getMethod() {
    request.setType("GET /members?name=Evan HTTP/1.1");
    assertEquals(request.getMethod(), "GET");
    request.setType("POST /members/evan?name=Evan HTTP/1.1");
    assertEquals(request.getMethod(), "POST");
    request.setType("DELETE /home HTTP/1.1");
    assertEquals(request.getMethod(), "DELETE");
  }

  /**
   * getPath tests the getPath method
   *
   * <p>Should return the path without the HTTP Method and URI queries
   */
  @Test
  public void getPath() {
    request.setType("GET /members?name=Evan HTTP/1.1");
    assertEquals(request.getPath(), "/members");
    request.setType("POST /members/evan?name=Evan HTTP/1.1");
    assertEquals(request.getPath(), "/members/evan");
    request.setType("DELETE /home HTTP/1.1");
    assertEquals(request.getPath(), "/home");
  }

  /**
   * getQueries tests the getQueries method
   *
   * <p>Should return all of the queries as a Map of key and values If there are no queries, return
   * an empty map.
   */
  @Test
  public void getQueries() {
    Map<String, String> test;
    Map<String, String> expected = new HashMap<>();

    // TEST 1
    request.setType("GET /members?name=Evan");
    test = request.getQueries();
    expected.put("name", "Evan");

    assertThat(test, is(expected));
    assertThat(test.size(), is(1));

    // TEST 2
    request.setType("POST /members/evan?name=Bob&age=12");
    test = request.getQueries();
    expected.clear();
    expected.put("name", "Bob");
    expected.put("age", "12");

    assertThat(test, is(expected));
    assertThat(test.size(), is(2));

    // TEST 3
    request.setType("DELETE /home");
    expected.clear();
    test = request.getQueries();

    assertThat(test, is(expected));
    assertThat(test.size(), is(0));

    // TEST 4
    request.setType("GET /watch?v=s4iLX7eWwmE");
    test = request.getQueries();
    expected.clear();
    expected.put("v", "s4iLX7eWwmE");

    assertThat(test, is(expected));
    assertThat(test.size(), is(1));
  }
}
