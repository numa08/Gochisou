package net.numa08.gochisou.entity.mapper;

import com.google.gson.Gson;

import net.numa08.gochisou.domain.model.Post;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PostEntitiesMapperTest {

    @Test
    public void parse() throws IOException {
        final Gson gson = new PostEntitiesMapper().getGson();
        final InputStream input = PostEntitiesMapperTest.class.getClassLoader().getResourceAsStream("post.json");
        final String json = IOUtils.toString(input);
        IOUtils.closeQuietly(input);
        final Post post = gson.fromJson(json, Post.class);

        assertThat(post.getNumber(), is(1L));
        assertThat(post.getName(), is("hi!"));
        assertThat(post.getBodyMD(), is("# Getting Started"));
    }
}