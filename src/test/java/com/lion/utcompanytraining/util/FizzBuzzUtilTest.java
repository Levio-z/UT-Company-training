package com.lion.utcompanytraining.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 案例：FizzBuzz
 */
public class FizzBuzzUtilTest {
    //被3整除，打印Fizz
    @Test
    @DisplayName("Divisible By Three")
    void testForDivisibleByThree(){
        String expected ="Fizz";
        assertEquals(expected,FizzBuzzUtil.compute(3),"Should return Fizz");
    }
    //被5整除，打印Buzz
    @Test
    @DisplayName("Divisible By Five")
    void testForDivisibleByFive(){
        String expected ="Buzz";
        assertEquals(expected,FizzBuzzUtil.compute(5),"Should return Buzz");
    }

    //被3和5整除，打印Buzz
    @Test
    @DisplayName("Divisible By Three And Five")
    void testForDivisibleByThreeAndFive(){
        String expected ="FizzBuzz";
        assertEquals(expected,FizzBuzzUtil.compute(15),"Should return FizzBuzz");
    }

    //都不满足，打印原来的数字
    @Test
    @DisplayName("Divisible By Three And Five")
    void testElse(){
        String expected ="1";
        assertEquals(expected,FizzBuzzUtil.compute(1),"Should return source");
    }
}
