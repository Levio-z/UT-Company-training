package com.lion.utcompanytraining.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 高级：
 * 1.参数化测试
 */
class FizzBuzzUtilParameterizedTest {
    @ParameterizedTest(name = "value ={0},expected={1}")
    @DisplayName("Divisible By Three And Five")
    @CsvSource({
            "1,1",
            "3,Fizz",
            "5,Buzz",
            "15,FizzBuzz"
    })
    void testCsvSource(Integer i,String expected){
        assertEquals(expected,FizzBuzzUtil.compute(i),"Should return source");
    }
    @ParameterizedTest(name = "value ={0},expected={1}")
    @DisplayName("Divisible By Three And Five")
    @CsvFileSource(resources = "/fizzBuzzUtilParameterizedTest/data.csv")
    void testElse(Integer i,String expected){
        assertEquals(expected,FizzBuzzUtil.compute(i),"Should return source");
    }
}
