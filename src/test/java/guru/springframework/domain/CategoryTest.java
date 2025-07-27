package guru.springframework.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryTest {
    Category category;

    @Before
    public void setUp(){
        category = new Category();
    }

    @Test
    public void getId() throws Exception{
        Long value = 1L;

        category.setId(value);

        assertEquals(value, category.getId());
    }

    @Test
    public void getDescription() throws Exception{
    }

    @Test
    public void getRecipes() throws Exception{
    }
}