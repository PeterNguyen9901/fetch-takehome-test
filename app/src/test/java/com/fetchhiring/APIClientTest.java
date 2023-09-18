package com.fetchhiring;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import retrofit2.Retrofit;

public class APIClientTest {

    @Test
    public void testSingleton() {
        APIClient client1 = APIClient.getInstance();
        APIClient client2 = APIClient.getInstance();

        assertSame(client1, client2);
    }

    @Test
    public void testBaseUrl() {
        Retrofit retrofit = APIClient.getInstance().getRetrofit();
        assertEquals("https://fetch-hiring.s3.amazonaws.com/", retrofit.baseUrl().toString());
    }
}

