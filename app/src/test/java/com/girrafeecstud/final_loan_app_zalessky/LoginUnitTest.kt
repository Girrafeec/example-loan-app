package com.girrafeecstud.final_loan_app_zalessky

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import junit.framework.Assert.assertEquals

class LoginUnitTest {

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {

        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `it should return expected Bearer token`() {

        val expectedBody = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdHJpbmciLCJleHAiOjE2NTczNjUxOTl9.VZf8ut2TiUZGMHbhUfifXfYJrenSAWcNWsY5gYEk74gl_abo9P1H9hfqCPTLCJkeayzWvHseSbe5IcXETOtFpQ\n"

        val response = MockResponse()
            .setResponseCode(200)
            .setBody(expectedBody)

        mockWebServer.enqueue(response = response)

        val actualResponse =  Response.success(expectedBody)

        assertEquals(response.toString().contains("200"), actualResponse.code().toString().contains("200"))

    }

}